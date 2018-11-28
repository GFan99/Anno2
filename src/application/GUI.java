package application;
	
import java.util.ArrayList;
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
				   GUI.neueIDerstellen = true;
			   } 
			};   
			//EventFilter dazu 
			neueid.addEventFilter(MouseEvent.MOUSE_CLICKED, idErstellen);
			
			//MouseEventHandler fuer Butten "idok" 
			EventHandler<MouseEvent> idLesen = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					GUI.neueIDerstellen = false;
					
				}
			};
			//EventFilter dazu 
			idok.addEventFilter(MouseEvent.MOUSE_CLICKED, idLesen);
			
			//Erstellen des klassifikators aus der Eingabe des Nutzers
			String nutzerid;
			String[] texts;
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
				//String[] texts = Input.labellesen(nutzerID);
				texts = new String[0];
				//HashMap<String,ArrayList<String>> labels = Input.textlesen(nutzerID);
				labels = new HashMap<String,ArrayList<String>>();
			}
			klasse = new Klassifikator(nutzerid, labels, texts);
			primaryStage.close();
			
			//------------------------------------------------------------------------------------------
			//           zweites Fenster
			//------------------------------------------------------------------------------------------
			
			//Erstelle neue Stage mithilfe des erzeugten Klassifikators
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
			
			//teil 3 
			//HashMap<String,ArrayList<String>> labels mit Labelname als Key und Auswahlmoegl. als Value
			GridPane pane = new GridPane();
			Label label0, label1, label2, label3, label4;
			RadioButton[] rbs0 = new RadioButton[0];
			RadioButton[] rbs1 = new RadioButton[0];
			RadioButton[] rbs2 = new RadioButton[0];
			RadioButton[] rbs3 = new RadioButton[0];
			RadioButton[] rbs4 = new RadioButton[0];
			CheckBox[] cbs0 = new CheckBox[0];
			CheckBox[] cbs1 = new CheckBox[0];
			CheckBox[] cbs2 = new CheckBox[0];
			CheckBox[] cbs3 = new CheckBox[0];
			CheckBox[] cbs4 = new CheckBox[0];
			ToggleGroup rbgroup0 = new ToggleGroup();
			ToggleGroup rbgroup1 = new ToggleGroup();
			ToggleGroup rbgroup2 = new ToggleGroup();
			ToggleGroup rbgroup3 = new ToggleGroup();
			ToggleGroup rbgroup4 = new ToggleGroup();
			
			char[] radioodercheck = {'n','n','n','n','n'};	//n = nicht gesetzt, r = radio buttons, c= check boxes
			//Zuordnung Label zu ihren Namen
			Set<String> labelliste = labels.keySet();
			ArrayList<String> labelarray = new ArrayList<String>();
			for (Iterator<String> it = labelliste.iterator(); it.hasNext();) {
				labelarray.add(it.next());
			}
			boolean[] mehrfachwahl = Input2.labelEigenschaft(labels);
			ArrayList<Label> labellabelliste = new ArrayList<Label>();
			for (int i = 0; i<labelarray.size();i++) {
				switch (i) {
					case 0: label0=new Label(labelarray.get(i));
							labellabelliste.add(label0);
							if (mehrfachwahl[i]) {
								radioodercheck[i]='c';
								cbs0 = new CheckBox[labels.get(i).size()];
								for(int j = 0; j<cbs0.length;j++) {
									cbs0[j]=new CheckBox(labels.get(i).get(j));
								}
							}
							else {
								radioodercheck[i]='r';
								rbs0 = new RadioButton[labels.get(i).size()];
								for(int j = 0; j<cbs0.length;j++) {
									rbs0[j]=new RadioButton(labels.get(i).get(j));
									rbs0[j].setToggleGroup(rbgroup0);
								}
							}
							break;
					case 1: label1=new Label(labelarray.get(i));
							labellabelliste.add(label1);
							if (mehrfachwahl[i]) {
								radioodercheck[i]='c';
								cbs1 = new CheckBox[labels.get(i).size()];
								for(int j = 0; j<cbs1.length;j++) {
									cbs1[j]=new CheckBox(labels.get(i).get(j));
								}
							}
							else {
								radioodercheck[i]='r';
								rbs1 = new RadioButton[labels.get(i).size()];
								for(int j = 0; j<cbs1.length;j++) {
									rbs1[j]=new RadioButton(labels.get(i).get(j));
									rbs1[j].setToggleGroup(rbgroup1);
								}
							}
							break;
					case 2: label2=new Label(labelarray.get(i));
							labellabelliste.add(label2);
							if (mehrfachwahl[i]) {
								radioodercheck[i]='c';
								cbs2 = new CheckBox[labels.get(i).size()];
									for(int j = 0; j<cbs2.length;j++) {
										cbs2[j]=new CheckBox(labels.get(i).get(j));		
									}
							}
							else {
								radioodercheck[i]='r';
								rbs2 = new RadioButton[labels.get(i).size()];
								for(int j = 0; j<cbs2.length;j++) {
									rbs2[j]=new RadioButton(labels.get(i).get(j));
									rbs2[j].setToggleGroup(rbgroup2);
								}
							}
							break;
					case 3: label3=new Label(labelarray.get(i));
							labellabelliste.add(label3);
							if (mehrfachwahl[i]) {
								radioodercheck[i]='c';
								cbs3 = new CheckBox[labels.get(i).size()];
								for(int j = 0; j<cbs3.length;j++) {
									cbs3[j]=new CheckBox(labels.get(i).get(j));
								}
							}
							else {
								radioodercheck[i]='r';
								rbs3 = new RadioButton[labels.get(i).size()];
								for(int j = 0; j<cbs3.length;j++) {
									rbs3[j]=new RadioButton(labels.get(i).get(j));
									rbs3[j].setToggleGroup(rbgroup3);
								}
							}
							break;
					case 4: label4=new Label(labelarray.get(i));
							labellabelliste.add(label4);
							if (mehrfachwahl[i]) {
								radioodercheck[i]='c';
								cbs4 = new CheckBox[labels.get(i).size()];
								for(int j = 0; j<cbs4.length;j++) {
									cbs4[j]=new CheckBox(labels.get(i).get(j));
								}
							}
							else {
								radioodercheck[i]='r';
								rbs4 = new RadioButton[labels.get(i).size()];
								for(int j = 0; j<cbs4.length;j++) {
									rbs4[j]=new RadioButton(labels.get(i).get(j));
									rbs4[j].setToggleGroup(rbgroup4);
								}
							}
							break;
					default:	break;
							
				}
			}
			//Label ins GridPane einfügen
			for(Integer i=0;i<labellabelliste.size();i++) {
				GridPane.setColumnIndex(labellabelliste.get(i), 0);
				GridPane.setRowIndex(labellabelliste.get(i), i);
				pane.getChildren().add(labellabelliste.get(i));
			}
			for(int i=0;i<radioodercheck.length;i++) {
				switch(i) {
				case 0:
					switch(radioodercheck[i]) {
					case 'r':
						for (int j=0;j<rbs0.length;j++) {
							GridPane.setColumnIndex(rbs0[j], j+1);
							GridPane.setRowIndex(rbs0[j], 0);
							pane.getChildren().add(rbs0[j]);
						}
						break;
					case 'c':
						for (int j=0;j<cbs0.length;j++) {
							GridPane.setColumnIndex(cbs0[j], j+1);
							GridPane.setRowIndex(cbs0[j], 0);
							pane.getChildren().add(cbs0[j]);
						}
						break;
					default: break;
					}
					break;
				case 1:
					switch(radioodercheck[i]) {
					case 'r':
						for (int j=0;j<rbs1.length;j++) {
							GridPane.setColumnIndex(rbs1[j], j+1);
							GridPane.setRowIndex(rbs1[j], 0);
							pane.getChildren().add(rbs1[j]);
						}
						break;
					case 'c':
						for (int j=0;j<cbs1.length;j++) {
							GridPane.setColumnIndex(cbs1[j], j+1);
							GridPane.setRowIndex(cbs1[j], 0);
							pane.getChildren().add(cbs1[j]);
						}
						break;
					default: break;
					}
					break;
				case 2:
					switch(radioodercheck[i]) {
					case 'r':
						for (int j=0;j<rbs2.length;j++) {
							GridPane.setColumnIndex(rbs2[j], j+1);
							GridPane.setRowIndex(rbs2[j], 0);
							pane.getChildren().add(rbs2[j]);
						}
						break;
					case 'c':
						for (int j=0;j<cbs2.length;j++) {
							GridPane.setColumnIndex(cbs2[j], j+1);
							GridPane.setRowIndex(cbs2[j], 0);
							pane.getChildren().add(cbs2[j]);
						}
						break;
					default: break;
					}
					break;
				case 3:
					switch(radioodercheck[i]) {
					case 'r':
						for (int j=0;j<rbs3.length;j++) {
							GridPane.setColumnIndex(rbs3[j], j+1);
							GridPane.setRowIndex(rbs3[j], 0);
							pane.getChildren().add(rbs3[j]);
						}
						break;
					case 'c':
						for (int j=0;j<cbs3.length;j++) {
							GridPane.setColumnIndex(cbs3[j], j+1);
							GridPane.setRowIndex(cbs3[j], 0);
							pane.getChildren().add(cbs3[j]);
						}
						break;
					default: break;
					}
					break;
				case 4:
					switch(radioodercheck[i]) {
					case 'r':
						for (int j=0;j<rbs4.length;j++) {
							GridPane.setColumnIndex(rbs4[j], j+1);
							GridPane.setRowIndex(rbs4[j], 0);
							pane.getChildren().add(rbs4[j]);
						}
						break;
					case 'c':
						for (int j=0;j<cbs4.length;j++) {
							GridPane.setColumnIndex(cbs4[j], j+1);
							GridPane.setRowIndex(cbs4[j], 0);
							pane.getChildren().add(cbs4[j]);
						}
						break;
					default: break;
					}
					break;
				}
			}
			
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
				
			Scene klassi = new Scene(klasspane,x,y);
			//klasspane.getChildren().addAll(daten, textanz, ranking, labelok);
			
			klassi.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			secondStage.setScene(klassi);
			secondStage.show();
			
			

			
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