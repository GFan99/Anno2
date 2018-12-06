package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Hauptstage extends Stage {

	Klassifikator klassif;
	
	//Teil1
	Label idanzeige;
	ProgressBar fortschritt;
	Label zeitanzeige;
	
	//Teil2
	TextArea teil2Textarea;
	
	//Teil3
	Label label0, label1, label2, label3, label4;
	RadioButton[] rbs0, rbs1, rbs2, rbs3, rbs4;
	CheckBox[] cbs0, cbs1, cbs2, cbs3, cbs4;
	ToggleGroup rbgroup0, rbgroup1, rbgroup2, rbgroup3, rbgroup4;
	char[] radioodercheck;
	boolean[] mehrfachwahl;
	ArrayList<String> labelarray;
	HashMap<String,ArrayList<String>> labels; //Labelname als Key, Auswahlmoegl. als Value
	boolean[] b0, b1, b2, b3, b4;
	RadioButton rb0x, rb1x, rb2x, rb3x, rb4x;
	
	//Teil4
	HBox schriftgroesse;
	Label schrift;
	Button schriftplus;
	Button schriftminus;
	Button labelabsenden;
	Label fehlermeldungHaupt;
	int sgroesse;
	
	public Hauptstage(Klassifikator klasse) {
		super();
		this.klassif=klasse;
		Scene scene = this.erstelleScene(klasse, klasse.getLabel());
		this.setScene(scene);
		start();
	}

	//initalisiert die Scene, die die Darstellung der Stage bestimmt
	public  Scene erstelleScene(Klassifikator klasse, HashMap<String,ArrayList<String>> labels) {   
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
		teil2Textarea = new TextArea();
		teil2Textarea.setEditable(false);
		teil2Textarea.setFont(new Font("Times",sgroesse));
		teil2Texthalter.setContent(teil2Textarea);
		teil2Texthalter.setFitToWidth(true);
		teil2Texthalter.setPrefWidth(400);
		teil2Texthalter.setPrefHeight(180);
		String text = klasse.getText();
		teil2Textarea.setText(text);
		
		//teil 3 
		//HashMap<String,ArrayList<String>> labels mit Labelname als Key und Auswahlmoegl. als Value
		GridPane pane = new GridPane();
		rbs0 = new RadioButton[0];
		rbs1 = new RadioButton[0];
		rbs2 = new RadioButton[0];
		rbs3 = new RadioButton[0];
		rbs4 = new RadioButton[0];
		cbs0 = new CheckBox[0];
		cbs1 = new CheckBox[0];
		cbs2 = new CheckBox[0];
		cbs3 = new CheckBox[0];
		cbs4 = new CheckBox[0];
		rbgroup0 = new ToggleGroup();
		rbgroup1 = new ToggleGroup();
		rbgroup2 = new ToggleGroup();
		rbgroup3 = new ToggleGroup();
		rbgroup4 = new ToggleGroup();
		char[] erstelleroc = {'n','n','n','n','n'};
		radioodercheck = erstelleroc;	//n = nicht gesetzt, r = radio buttons, c= check boxes
		//Zuordnung Label zu ihren Namen
		Set<String> labelliste = labels.keySet();
		labelarray = new ArrayList<String>();
		for (Iterator<String> it = labelliste.iterator(); it.hasNext();) {
			labelarray.add(it.next());
		}
		mehrfachwahl = Input3.labelEigenschaft();
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
							rb0x = new RadioButton();
							rb0x.setSelected(true);
							rb0x.setToggleGroup(rbgroup0);
							rbs0 = new RadioButton[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<rbs0.length;j++) {
								rbs0[j]=new RadioButton(labels.get(i).get(j));
								rbs0[j].setUserData(labels.get(i).get(j));
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
							rb1x = new RadioButton();
							rb1x.setSelected(true);
							rb1x.setToggleGroup(rbgroup1);
							rbs1 = new RadioButton[labels.get(i).size()];
							for(int j = 0; j<rbs1.length;j++) {
								rbs1[j]=new RadioButton(labels.get(i).get(j));
								rbs1[j].setUserData(labels.get(i).get(j));
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
							rb2x = new RadioButton();
							rb2x.setSelected(true);
							rb2x.setToggleGroup(rbgroup2);
							rbs2 = new RadioButton[labels.get(i).size()];
							for(int j = 0; j<rbs2.length;j++) {
								rbs2[j]=new RadioButton(labels.get(i).get(j));
								rbs2[j].setUserData(labels.get(i).get(j));
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
							rb3x = new RadioButton();
							rb3x.setSelected(true);
							rb3x.setToggleGroup(rbgroup3);
							rbs3 = new RadioButton[labels.get(i).size()];
							for(int j = 0; j<rbs3.length;j++) {
								rbs3[j]=new RadioButton(labels.get(i).get(j));
								rbs3[j].setUserData(labels.get(i).get(j));
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
							rb4x = new RadioButton();
							rb4x.setSelected(true);
							rb4x.setToggleGroup(rbgroup4);
							rbs4 = new RadioButton[labels.get(i).size()];
							for(int j = 0; j<rbs4.length;j++) {
								rbs4[j]=new RadioButton(labels.get(i).get(j));
								rbs4[j].setUserData(labels.get(i).get(j));
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
		schriftgroesse = new HBox();
		schrift = new Label("Schriftgröße");
		schriftplus = new Button("+");
		schriftminus = new Button("-");
		labelabsenden = new Button("Absenden");
		schriftgroesse.getChildren().addAll(schrift, schriftplus, schriftminus);
		fehlermeldungHaupt = new Label("");
		
		
		teil4GroesseAbsenden.setLeft(schriftgroesse);
		teil4GroesseAbsenden.setCenter(fehlermeldungHaupt);
		teil4GroesseAbsenden.setRight(labelabsenden);
		
					
			
		Scene klassi = new Scene(klasspane,x,y);
		//klasspane.getChildren().addAll(daten, textanz, ranking, labelok);
		
		klassi.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		return klassi;
	}
	
	//Methode, die die Eventhandler beinhaltet
	public void start() {
		this.show();
		 
		EventHandler<MouseEvent> schriftgroesseplus = new EventHandler<MouseEvent>() { 
		   @Override 
		   public void handle(MouseEvent e) { 
			   sgroesse = sgroesse+2;
			   teil2Textarea.setFont(new Font("Arial", sgroesse));			   
		   } 
		};
		schriftplus.addEventFilter(MouseEvent.MOUSE_CLICKED, schriftgroesseplus);
		
		EventHandler<MouseEvent> schriftgroesseminus = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent e) { 
			   sgroesse = sgroesse-2;
			   teil2Textarea.setFont(new Font("Arial", sgroesse));
			} 
		};
		schriftminus.addEventFilter(MouseEvent.MOUSE_CLICKED, schriftgroesseminus);
		
		this.setOnCloseRequest(event -> {
		    System.out.println("Stage is closing");
		    Output.abbruchSave(klassif);
		});
		
		EventHandler<MouseEvent> pruefenuabsenden = new EventHandler<MouseEvent>() { 
			   @Override 
			   public void handle(MouseEvent e) { 
				   if(check()) { //check zeigt dass je label (min) 1 Option ausgewählt ist
					   //auslesen der gewählten label-optionen
					   ArrayList<String> ergebnis = new ArrayList();
					   for (int i = 0; i<labelarray.size();i++) {
							switch (i) {
								case 0: if (radioodercheck[i]=='c') {
											b0 = new boolean[cbs0.length];
											for (int j = 0; j<cbs0.length; j++) {
												b0[j] = cbs0[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b0.length; k++) {
												if (b0[k]) {
													erg = erg+(cbs0[k].getText());
												}
											}
											ergebnis.add(erg);
										}
										else {
											//radioodercheck[i]='r';
											ergebnis.add(rbgroup0.getSelectedToggle().getUserData().toString());
										}
										break;
								case 1: if (radioodercheck[i]=='c') {
											b0 = new boolean[cbs0.length];
											for (int j = 0; j<cbs0.length; j++) {
												b0[j] = cbs0[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b0.length; k++) {
												if (b0[k]) {
													erg = erg+(cbs0[k].getText());
												}
											}
											ergebnis.add(erg);
										}
										else {
											//radioodercheck[i]='r';
											ergebnis.add(rbgroup0.getSelectedToggle().getUserData().toString());
										}
										break;
								case 2: if (radioodercheck[i]=='c') {
											b0 = new boolean[cbs0.length];
											for (int j = 0; j<cbs0.length; j++) {
												b0[j] = cbs0[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b0.length; k++) {
												if (b0[k]) {
													erg = erg+(cbs0[k].getText());
												}
											}
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup0.getSelectedToggle().getUserData().toString());
												}
												else {
													break;
												}
										}
										break;
								case 3: if (radioodercheck[i]=='c') {
											b0 = new boolean[cbs0.length];
											for (int j = 0; j<cbs0.length; j++) {
												b0[j] = cbs0[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b0.length; k++) {
												if (b0[k]) {
													erg = erg+(cbs0[k].getText());
												}
											}
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup0.getSelectedToggle().getUserData().toString());
												}
												else {
													break;
												}
										}
										break;
								case 4: if (radioodercheck[i]=='c') {
											b0 = new boolean[cbs0.length];
											for (int j = 0; j<cbs0.length; j++) {
												b0[j] = cbs0[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b0.length; k++) {
												if (b0[k]) {
													erg = erg+(cbs0[k].getText());
												}
											}
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup0.getSelectedToggle().getUserData().toString());
												}
												else {
													break;
												}
										}
										break;
								default:	break;
										
							}
						}
					   //schreiben der Werte in Output-Datei
					   Output.schreibeWerte(klassif,teil2Textarea.getText(),ergebnis);
					   //neuen Text laden und anzeigen
					   String neuertext = klassif.getText();
					   if (neuertext == "") {
						   close();
						   Output.schreibexml();
						   new FertigStage();
						   return;
					   }
					   teil2Textarea.setText(neuertext);
					   //neuinitialisierung der checkboxen und radiobuttons
					   for (int i = 0; i<labelarray.size();i++) {
							switch (i) {
								case 0: if (radioodercheck[i]=='c') {
											for (int j = 0; j<cbs0.length; j++) {
												cbs0[j].setSelected(false);
											}
										}
										else {
											//radioodercheck[i]='r';
											rb0x.setSelected(true);;
										}
										break;
								case 1: if (radioodercheck[i]=='c') {
											for (int j = 0; j<cbs1.length; j++) {
												cbs1[j].setSelected(false);
											}
										}
										else {
											//radioodercheck[i]='r';
											rb1x.setSelected(true);;
										}
										break;
								case 2: if (radioodercheck[i]=='c') {
											for (int j = 0; j<cbs2.length; j++) {
												cbs2[j].setSelected(false);
											}
										}
										else {
											if (radioodercheck[i]=='r') {
												rb2x.setSelected(true);
											}
											else break;
										}
										break;
								case 3: if (radioodercheck[i]=='c') {
											for (int j = 0; j<cbs3.length; j++) {
												cbs3[j].setSelected(false);
											}
										}
										else {
											if (radioodercheck[i]=='r') {
												rb3x.setSelected(true);
											}
											else break;
										}
										break;
								case 4: if (radioodercheck[i]=='c') {
											for (int j = 0; j<cbs4.length; j++) {
												cbs4[j].setSelected(false);
											}
										}
										else {
											if (radioodercheck[i]=='r') {
												rb4x.setSelected(true);
											}
											else break;
										}
										break;
								default:	break;
										
							}
						}
				   }
				   else {
					   fehlermeldungHaupt.setText("Bitte prüfen Sie, dass sie jedes Label zugeordnet haben!");
				   }
			   } 
		}; 
		labelabsenden.addEventFilter(MouseEvent.MOUSE_CLICKED, pruefenuabsenden);
	} 
	
	//prueft ob zu jedem Label (min.) 1 Moeglichkeit ausgewaehlt wurde -> true, wenn nicht false
	public boolean check() {
		boolean check = true;
		for (int i = 0; i<labelarray.size();i++) {
			switch (i) {
				case 0:
					if (radioodercheck[i]=='c') {
						for (int j = 0; j<cbs0.length; j++) {
							if(!b0[j]) {
								check =false;
								break;
							}
						}
					}
					else {
						if (rbgroup0.getSelectedToggle()==rb0x) {
							check=false;
							break;
						}
					}
					break;
				case 1: 
					if (radioodercheck[i]=='c') {
						for (int j = 0; j<cbs1.length; j++) {
							if(!b1[j]) {
								check =false;
								break;
							}
						}
					}
					else {
						if (rbgroup1.getSelectedToggle()==rb1x) {
							check=false;
							break;
						}
					}
					break;
				case 2:
					if (radioodercheck[i]=='c') {
						for (int j = 0; j<cbs2.length; j++) {
							if(!b2[j]) {
								check =false;
								break;
							}
						}
					}
					else {
						if (radioodercheck[i]=='r') {
							if (rbgroup2.getSelectedToggle()==rb2x) {
								check=false;
								break;
							}
						}
						else break;						
					}
					break;
				case 3:
					if (radioodercheck[i]=='c') {
						for (int j = 0; j<cbs3.length; j++) {
							if(!b3[j]) {
								check =false;
								break;
							}
						}
					}
					else {
						if (radioodercheck[i]=='r') {
							if (rbgroup3.getSelectedToggle()==rb3x) {
								check=false;
								break;
							}
						}
						else break;						
					}
					break;
				case 4: 
					if (radioodercheck[i]=='c') {
						for (int j = 0; j<cbs4.length; j++) {
							if(!b4[j]) {
								check =false;
								break;
							}
						}
					}
					else {
						if (radioodercheck[i]=='r') {
							if (rbgroup4.getSelectedToggle()==rb4x) {
								check=false;
								break;
							}
						}
						else break;						
					}
					break;
				default:
					break;
			}
		}	   
		return check;
	}
}