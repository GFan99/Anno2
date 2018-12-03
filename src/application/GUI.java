package application;
	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	
	private static boolean neueIDerstellen =true;
	
	@Override
	public void start(Stage primaryStage) {
		try {
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
			
			//Positionierung der Objekte der IDStage und Zuordnung zum Pane
			AnchorPane.setTopAnchor(idfrage, 10.0);
			AnchorPane.setLeftAnchor(idfrage, 10.0);
			AnchorPane.setTopAnchor(idtext, 40.0);
			AnchorPane.setLeftAnchor(idtext, 10.0);
			AnchorPane.setTopAnchor(idok, 80.0);
			AnchorPane.setLeftAnchor(idok, 25.0);
			AnchorPane.setTopAnchor(neueid, 80.0);
			AnchorPane.setLeftAnchor(neueid, 80.0);
			idpane.getChildren().addAll(idfrage, idtext, idok, neueid);
			
			//Erstellen der Scene aus dem Pane und Zuweisung eines Stylesheets
			Scene idabfrage = new Scene(idpane,300,150);
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
					String nutzerid;
					String[][] texts;
					HashMap<String,ArrayList<String>> labels;	//Labelname als Key, Auswahlmoegl. als Value
					Klassifikator klasse;
					
					nutzerid = Klassifikator.generiereNutzer();
					System.out.println("Neue ID generiert: "+nutzerid);
					texts = Input2.texteLesen();
					labels = Input2.labelLesen();					
					
					klasse = new Klassifikator(nutzerid, labels, texts);
			   } 
			};   
			//EventFilter dazu 
			neueid.addEventFilter(MouseEvent.MOUSE_CLICKED, idErstellen);
			
			//MouseEventHandler fuer Butten "idok" 
			EventHandler<MouseEvent> idLesen = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					String id = idtext.getText();
					String[] vorhandeneIDs = Input2.vorhandeneIDs();
					if (Arrays.asList(vorhandeneIDs).contains(id)) {
						
					}
					
					primaryStage.close();
					
					//Erstellen des klassifikators aus der Eingabe des Nutzers
					String nutzerid;
					String[][] texts;
					HashMap<String,ArrayList<String>> labels;	//Labelname als Key, Auswahlmoegl. als Value
					Klassifikator klasse;
					
					if(GUI.neueIDerstellen) {
						nutzerid = Klassifikator.generiereNutzer();
						System.out.println("Neue ID generiert: "+nutzerid);
						texts = Input2.texteLesen();
						labels = Input2.labelLesen();					
					}
					else {
						nutzerid = idtext.getText();
						//String[] texts = Input.labelLesen(nutzerID);
						texts = new String[0][0];
						//HashMap<String,ArrayList<String>> labels = Input.texteLesen(nutzerID);
						labels = new HashMap<String,ArrayList<String>>();
					}
					klasse = new Klassifikator(nutzerid, labels, texts);
				}
			};
            			//EventFilter dazu 
			idok.addEventFilter(MouseEvent.MOUSE_CLICKED, idLesen);
			
			
			
			
			//------------------------------------------------------------------------------------------
			//           zweites Fenster
			//------------------------------------------------------------------------------------------
			
			//Erstelle neue Stage mithilfe des erzeugten Klassifikators
			/*
			 * statischer Teil 
			 */
			/**
			 * hier alles ausgeschnitten
			 */
			//secondStage.show();
			
			

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage erstelleHauptStage(Klassifikator klasse) {
		
		/* 
		 * statischer Teil 
		 */
		Stage secondStage = new Stage();	
		int x = 800;
		int y = 500;
		
		/**
		 * VBox besteht aus 4 untereinanderliegenden Teilen
		 * Teil 1:	allgemeine Angaben --> ID, Fortschrite, Timer
		 * Teil 2:	scrollable textarea
		 * Teil 3:	Labelwertungssystem
		 * Teil 4:	schriftgroessen buttons + abschicken button
		 */	
		VBox klasspane= new VBox();
		/** Leiste für Teil 1 */
		HBox teil1Daten = new HBox();
		/** Teil 2 - scrollable textarea */
		ScrollPane teil2Texthalter = new ScrollPane();
		/** Teil 3 - Wertungssystem (tabellenfoerimge Anordnung) */
		GridPane teil3Ranking = new GridPane();
		/** Teil 4 - Buttons werden links und rechts angezeigt */
		BorderPane teil4GroesseAbsenden = new BorderPane();

		String nutzerstring = klasse.getNutzerID();
		
		
		//Teil1 - obere Zeile der Anzeige
		Label idanzeige = new Label("Nutzer-ID: "+nutzerstring);
		ProgressBar fortschritt = new ProgressBar(0);
		Label zeitanzeige = new Label("Counter"); //anpassen, so dass Zeit angezeigt wird
		
		teil1Daten.getChildren().addAll(idanzeige, fortschritt);
		
		//Teil2 - scrollabe TextArea
		TextArea teil2Textarea = new TextArea();
		teil2Texthalter.setContent(teil2Textarea);
		teil2Texthalter.setFitToWidth(true);
		teil2Texthalter.setPrefWidth(400);
		teil2Texthalter.setPrefHeight(180);
		String text = klasse.getText();
		teil2Textarea.setText(text);
		
		//teil 3 siehe dynamisch
		
		//Teil 4 - Absenden und Schriftgroeße
		HBox schriftgroesse = new HBox();
		Label schrift = new Label("Schriftgröße");
		Button schriftplus = new Button("+");
		Button schriftminus = new Button("-");
		Button labelabsenden = new Button("Absenden");
		schriftgroesse.getChildren().addAll(schrift, schriftplus, schriftminus);
		
		teil4GroesseAbsenden.setLeft(schriftgroesse);
		teil4GroesseAbsenden.setRight(labelabsenden);
		
		//kommt bei Klick auf absenden 
		String neuertext = klasse.getText();
		teil2Textarea.setText(neuertext);
		
		
		
		
			
		
		
		
		
		/*
		 *  dynamischer Teil 
		 */
		
		//Wie kann man jeden button/label anders benennen, um sie
				//sp?ter bei Event ansprechen zu k?nnen???
				int zeile=1;
				for(String key : Input2.labelLesen().keySet()) {
					Label label0 = new Label(key);
					GridPane.setConstraints(label0, 1, zeile);
					for(int i=0; i<Input2.labelLesen().get(key).size();i++){
						RadioButton rbutton0 = new RadioButton(Input2.labelLesen().get(key).get(i));
						GridPane.setConstraints(rbutton0, i+2, zeile);
					}
					zeile++;
				}	
		Scene klassi = new Scene(klasspane,x,y);
		//klasspane.getChildren().addAll(daten, textanz, ranking, labelok);
		
		klassi.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		secondStage.setScene(klassi);
		return secondStage;
	}
	

	
	
	public void erstelleZeitendeStage(Stage thirdStage, Klassifikator klasse) {
		try {
			
			AnchorPane gameoverpane=new AnchorPane();
			//prozent noch ueberarbeiten!!
			double prozent=klasse.texte.length/klasse.idgroesse;
			Label timeout= new Label("Ihre Zeit ist für heute abgelaufen. \n Sie haben bereits " +prozent+ "% klassifiziert.");
			Button schliessen = new Button("Schliessen");
			
			AnchorPane.setLeftAnchor(timeout, 10.0);
			AnchorPane.setTopAnchor(timeout, 10.0);
			AnchorPane.setLeftAnchor(schliessen, 10.0);
			AnchorPane.setTopAnchor(schliessen, 40.0);
			gameoverpane.getChildren().addAll(timeout, schliessen);
			
			Scene beenden =new Scene(gameoverpane,400,250);
			beenden.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			thirdStage.setScene(beenden);
			thirdStage.show();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void erstelleFertigStage(Stage fourthStage) {
		try {
			
			AnchorPane endpain=new AnchorPane();
			Label fertig = new Label("Sie haben alle Texte klassifiziert.");
			Button schliessen = new Button("Schliessen");

			AnchorPane.setLeftAnchor(fertig, 10.0);
			AnchorPane.setTopAnchor(fertig, 10.0);
			AnchorPane.setLeftAnchor(schliessen, 10.0);
			AnchorPane.setTopAnchor(schliessen, 40.0);
			endpain.getChildren().addAll(endpain);
			
			Scene beenden =new Scene(endpain,400,250);
			beenden.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			fourthStage.setScene(beenden);
			fourthStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}