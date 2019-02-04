package application;
	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 * Dies ist die Klasse GUI. In ihr ist die graphische Oberflaeche programmiert. Sie enthaelt
 * entsprechend das erste angezeigte Fenster inklusive der zugehoerigen Komponenten und ruft
 * die weiteren Klassen der GUI (Hauptstage, FertigStage, ZeitEndeStage) auf.
 * 
 * @author becksusanna
 */
public class GUI extends Application {
		
	/**
	 * Da es sich bei GUI um eine Subklasse von javafx.application.Application handelt,
	 * enthaelt die Startklasse einzig den Aufruf der Methode start(), welche die
	 * Grafische Oberflaeche startet.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Diese Methode startet die GUI.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			//erstellen des Pane und der Komponenten der "IDStage"
			AnchorPane idpane = new AnchorPane();
			Label idfrage = new Label("Bitte geben Sie Ihre Nutzer-ID ein:");
			idfrage.setFont(Font.font("Tahoma"));
			TextField idtext = new TextField();
			idtext.setText(System.getProperty("user.dir"));
			idtext.setFont(Font.font("Tahoma"));
			Button idok = new Button("OK");
			idok.setFont(Font.font("Tahoma"));
			Button neueid = new Button("ID erstellen");
			neueid.setFont(Font.font("Tahoma"));
			Label fehlermeldung = new Label();
			fehlermeldung.setFont(Font.font("Tahoma"));
			//VorzugsBreite fuer Textfeld setzen, damit gesamte ID sichtbar eingegeben werden kann
			idtext.setPrefWidth(260.0);
			
			//Positionierung der Objekte der IDStage
			AnchorPane.setTopAnchor(idfrage, 25.0);
			AnchorPane.setLeftAnchor(idfrage, 25.0);
			AnchorPane.setTopAnchor(idtext, 52.0);
			AnchorPane.setLeftAnchor(idtext, 25.0);
			AnchorPane.setTopAnchor(idok, 95.0);
			AnchorPane.setLeftAnchor(idok, 25.0);
			AnchorPane.setTopAnchor(neueid, 95.0);
			AnchorPane.setLeftAnchor(neueid, 198.0);
			AnchorPane.setTopAnchor(fehlermeldung, 135.0);
			AnchorPane.setLeftAnchor(fehlermeldung, 25.0);
			//Zuordnung der Objekte zum Pane
			idpane.getChildren().addAll(idfrage, idtext, idok, neueid, fehlermeldung);
			
			//Erstellen der Scene aus dem Pane und Zuweisung eines Stylesheets
			Scene idabfrage = new Scene(idpane,315,150);
			
			//primaryStage erhaelt Scene und damit alle anderen Objekte --> Anzeigen der Stage
			primaryStage.setScene(idabfrage);
			primaryStage.show();
			
			//MouseEventHandler fuer Button 'neueid' 
			EventHandler<MouseEvent> idErstellen = new EventHandler<MouseEvent>() { 
			   @Override 
			   public void handle(MouseEvent e) { 
				   //bei Ausloesung des Events wird zuerst die 'IDStage' geschlossen 
				   primaryStage.close();
				   //dann Erstellung des Klassifikators mit einer neu generierten Nutzer-ID
				   String nutzerid = Klassifikator.generiereNutzer();
				   String[][] texts = Input3.texteLesen();
				   LinkedHashMap<String,ArrayList<String>> labels = Input3.labelLesen();	//Labelname als Key, Auswahlmoegl. als Value
				   Klassifikator klasse = new Klassifikator(nutzerid, labels, texts);
				   //Erstellen einer neuen Hauptstage oeffnet das naechste Fenster
				   new Hauptstage(klasse,0);
			   } 
			}; 
			neueid.addEventFilter(MouseEvent.MOUSE_CLICKED, idErstellen);
			//EventFilter zum EventHandler: das Event wird ausgeloest, wenn auf den Button
			// 'ID erstellen' geklickt wird
			
			//MouseEventHandler fuer Butten 'idok' 
			EventHandler<MouseEvent> idLesen = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					//bei Ausloesung des Events wird die eingegebene ID vom Programm ausgelesen
					//und anschliessend ueberprueft, ob diese ID bereits existiert 
					String id = idtext.getText();
					String[] vorhandeneIDs = Input3.vorhandeneIDs();
					
					if (Arrays.asList(vorhandeneIDs).contains(id)) {
						//wenn die ID existiert wird mit ihr ein Klassifikator erstellt
						String[][] texts = Input3.texteLesen();
						ArrayList<Integer> schonklassi = Input3.leseklassifizierte(id);
						LinkedHashMap<String,ArrayList<String>> labels = Input3.labelLesen();    //Labelname als Key, Auswahlmoegl. als Value
						Klassifikator klasse = new Klassifikator(id,labels,texts);	
						//die vom Nutzer bereits klassifizierten Texte werden aus der Liste der
						//Texte, die er noch klassifizieren muss entfernt
						int anzklassi = schonklassi.size();
						while (schonklassi.size()!=0) {
							klasse.getTextids().remove(schonklassi.remove(0));
						}
						//Schliessen der 'IDStage'
						primaryStage.close();
						//falls bereits alle Texte klassifiziert wurden, wird der Nutzer direkt zur FertigStage weitergeleitet
						if (anzklassi == texts.length) {
							new FertigStage();
						}
						else {
							//Erstellen einer neuen Hauptstage oeffnet das naechste Fenster
							new Hauptstage(klasse, anzklassi);
						}
					}
					else {
						//falls die ID noch nicht existiert, so erscheint eine Fehlermeldung und
						//die Groesse des Fensters wird entsprechend angepasst
						fehlermeldung.setText("Bitte Überprüfen Sie Ihre Eingabe. \nDiese ID existiert nicht!");
						primaryStage.setHeight(205.0);
					}
				}
			}; 
			idok.addEventFilter(MouseEvent.MOUSE_CLICKED, idLesen);
			//EventFilter zum EventHandler: das Event wird ausgeloest, wenn auf den Button
			// 'OK' geklickt wird
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}