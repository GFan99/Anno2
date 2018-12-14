package application;
	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Set;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Dies wird die Klasse GUI.
 * In ihr wird die gesamte graphische Oberflaeche progammiert.
 *  - start()		 : Diese Methode startet die GUI.
 *  - idabfrage()	 : Diese Funktion soll den Nutzer nach seiner ID fragen und diese dann auch zurück geben.
 *  - getLabel()  	 : Diese Methode soll die vom Nutzer ausgewaehlten Label erfassen und speichern. Dazu
 *  					nutzt sie zunaechst die Funktion Klassifikator.waehleText() und stellt den zur
 *  					erhaltenen TextID gehoerigen Text dar. Dann wartet sie auf ein Clicked-Event des 
 *  					OK-Buttons und prueft dann die Eingaben auf Vollstaendigkeit, bevor sie die zu 
 *  					den Labels erfassten Werte in einem Array speichert, welches sie zusammen mit der
 *  					textID der Methode Klassifikator.schreibeText() uebergibt.
 *  					Sie beginnt nun ihre Arbeit von vorne, solange bis sie durch 
 *  					Klassifikator.waehleText() den Abbruch-Code uebergeben bekommt. Sie schliesst dann
 *  					die GUI.
 *  - schliessen()	 : Beendet die graphische Darstellung und startet den Speichervorgang.
 *  - abbruch()		 : Diese Methode wird ausgeloest, wenn die graphische Oberflaeche vom Nutzer vorzeitig
 *  					geschlossen wird. Sie startet die "Abbruchspeicherung".
 *  - fortschritt	 : Zeigt ein kleines Fenster mit dem Fortschritt.
 * @author becksusanna
 * @version 0.1
 */
public class GUI extends Application {
		
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Test-Bereich:
			
			//new ZeitEndeStage(0.68);
			
			LinkedHashMap<String,ArrayList<String>> testmap = new LinkedHashMap<String,ArrayList<String>>();
			ArrayList<String> wert1 = new ArrayList<String>();
			wert1.add("Vogel");
			wert1.add("Meise");
			wert1.add("Pferd");
			ArrayList<String> wert2 = new ArrayList<String>();
			wert2.add("ja");
			wert2.add("nein");
			wert2.add("vielleicht");
			testmap.put("Tier?", wert1);
			testmap.put("magst du?",wert2);
			String[][] testtexte = new String[2][2];
			testtexte[0][0]="0";
			testtexte[0][1]="lalala ich bin ein vogel";
			testtexte[1][0]="1";
			testtexte[1][1]="keks";
			
			String testerg = "ja";
			String testerg2 = "Vogel;Meise";
			
			ArrayList<String> testergx = new ArrayList<String>();
			testergx.add(testerg);
			testergx.add(testerg2);
			
			Klassifikator ktest = new Klassifikator("abcnutzer",testmap, testtexte);
			
			//Output.schreibeWerte(ktest, "keks", testergx);
			//Output.schreibeWerte(ktest, "lalala ich bin ein vogel", testergx);
		

			
			
			//------------------------------------------------------------------------------------------
			//           erstes Fenster
			//------------------------------------------------------------------------------------------
			
			//erstellen des Pane und der Komponenten der "IDStage"
			AnchorPane idpane = new AnchorPane();
			Label idfrage = new Label("Bitte geben Sie ihre Nutzer-ID ein:");
			TextField idtext = new TextField();
			Button idok = new Button("OK");
			Button neueid = new Button("ID erstellen");
			//VorzugsBreite fuer Textfeld setzen, damit gesamte ID sichtbar eingegeben werden kann
			idtext.setPrefWidth(260.0);
			Label fehlermeldung = new Label();
			
			
			//lalalala
			//Positionierung der Objekte der IDStage und Zuordnung zum Pane
			AnchorPane.setTopAnchor(idfrage, 25.0);
			AnchorPane.setLeftAnchor(idfrage, 25.0);
			AnchorPane.setTopAnchor(idtext, 52.0);
			AnchorPane.setLeftAnchor(idtext, 25.0);
			AnchorPane.setTopAnchor(idok, 95.0);
			AnchorPane.setLeftAnchor(idok, 25.0);
			AnchorPane.setTopAnchor(neueid, 95.0);
			AnchorPane.setLeftAnchor(neueid, 198.0);
			AnchorPane.setTopAnchor(fehlermeldung, 125.0);
			AnchorPane.setLeftAnchor(fehlermeldung, 10.0);
			idpane.getChildren().addAll(idfrage, idtext, idok, neueid, fehlermeldung);
			
			//Erstellen der Scene aus dem Pane und Zuweisung eines Stylesheets
			Scene idabfrage = new Scene(idpane,315,150);
			idabfrage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//primaryStage erhaelt Scene und damit alle anderen Objekte --> zeigen der Stage
			primaryStage.setScene(idabfrage);
			primaryStage.show();
			
			//MouseEventHandler fuer Butten "neueid" 
			EventHandler<MouseEvent> idErstellen = new EventHandler<MouseEvent>() { 
			   @Override 
			   public void handle(MouseEvent e) { 
				   primaryStage.close();
				   	//Erstellen des klassifikators aus der Eingabe des Nutzers
					String nutzerid = Klassifikator.generiereNutzer();
					System.out.println("Neue ID generiert: "+nutzerid);
					String[][] texts = Input3.texteLesen();
					LinkedHashMap<String,ArrayList<String>> labels = Input3.labelLesen();	//Labelname als Key, Auswahlmoegl. als Value
					
					Klassifikator klasse = new Klassifikator(nutzerid, labels, texts);
					primaryStage.close();
					
					new Hauptstage(klasse,0);

			   } 
			};  neueid.addEventFilter(MouseEvent.MOUSE_CLICKED, idErstellen);   //EventFilter dazu 
			
			//MouseEventHandler fuer Butten "idok" 
			EventHandler<MouseEvent> idLesen = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					fehlermeldung.setText("");
					String id = idtext.getText();
					String[] vorhandeneIDs = Input3.vorhandeneIDs();
					
					if (Arrays.asList(vorhandeneIDs).contains(id)) {
						String[][] texts = Input3.texteLesen();
						ArrayList<Integer> schonklassi = Input3.leseklassifizierte(id);
						LinkedHashMap<String,ArrayList<String>> labels = Input3.labelLesen();    //Labelname als Key, Auswahlmoegl. als Value
						Klassifikator klasse = new Klassifikator(id,labels,texts);	
						int anzklassi = schonklassi.size();
						while (schonklassi.size()!=0) {
							klasse.textids.remove(schonklassi.get(0));
						}
						primaryStage.close();
						new Hauptstage(klasse, anzklassi);
					}
					else {
						fehlermeldung.setText("Bitte überprüfen Sie Ihre Eingabe. \nDiese ID existiert nicht!");
					}
				}
			};  idok.addEventFilter(MouseEvent.MOUSE_CLICKED, idLesen);	  //EventFilter dazu
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}