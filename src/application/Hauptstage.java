package application;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.GroupLayout.Alignment;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Hauptstage extends Stage {

	Klassifikator klassif;
	int texteges;
	int texteklassi;
	
	VBox klasspane;
	int x, y;
	
	HBox teil1Daten; 				//Leiste für Teil 1
	ScrollPane teil2Texthalter;		//Teil 2 - scrollable textarea */
	GridPane teil3Ranking;			//Teil 3 - Wertungssystem (tabellenfoermige Anordnung)
	HBox teil4GroesseAbsenden;		//Teil 4 - Buttons werden links und rechts angezeigt
	
	Region vboxspacer0,vboxspacer1,vboxspacer2,vboxspacer3,vboxspacer4;
	
	//Teil1
	Label idanzeige;
	ProgressBar fortschritt;
	Label zeitanzeige;
	Label timerlabel;
	Timeline timeline;
	int timeinsec;
	
	double prozent;
	
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
	LinkedHashMap<String,ArrayList<String>> labels; //Labelname als Key, Auswahlmoegl. als Value
	boolean[] b0, b1, b2, b3, b4;
	int boolz0, boolz1, boolz2, boolz3, boolz4;
	RadioButton rb0x, rb1x, rb2x, rb3x, rb4x;
	
	//Teil4
	HBox schriftgroesse;
	Label schrift;
	Button schriftplus;
	Button schriftminus;
	Button labelabsenden;
	Label fehlermeldungHaupt;
	int sgroesse;
	
	boolean timesup;

	
	public Hauptstage(Klassifikator klasse, int anzklassifizierte) {
		super();
		this.klassif=klasse;
		this.texteges=klassif.getTexte().length;
		this.texteklassi=anzklassifizierte;
		this.fortschritt = new ProgressBar(0.0);
		Scene scene = this.erstelleScene(klassif.getLabel());
		this.setScene(scene);
		this.show();
		 
		EventHandler<MouseEvent> schriftgroesseplus = new EventHandler<MouseEvent>() { 
		   @Override 
		   public void handle(MouseEvent e) { 
			   if (sgroesse <=24) {
				   sgroesse = sgroesse+2;
				   teil2Textarea.setFont(new Font("Times", sgroesse));
			   }
		   } 
		};
		schriftplus.addEventFilter(MouseEvent.MOUSE_CLICKED, schriftgroesseplus);
		
		EventHandler<MouseEvent> schriftgroesseminus = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent e) { 
			   if (sgroesse >=10) {
				   sgroesse = sgroesse-2;
				   teil2Textarea.setFont(new Font("Times", sgroesse));
			   }
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
				   if(check()) { //check zeigt dass je label (min) 1 Option ausgewaehlt ist
					   //auslesen der gewaehlten label-optionen
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
													erg=erg+";";
												}
											}
											erg=erg.substring(0,erg.length()-1);
											ergebnis.add(erg);
										}
										else {
											//radioodercheck[i]='r';
											ergebnis.add(rbgroup0.getSelectedToggle().getUserData().toString());
										}
										break;
								case 1: if (radioodercheck[i]=='c') {
											b1 = new boolean[cbs1.length];
											for (int j = 0; j<cbs1.length; j++) {
												b1[j] = cbs1[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b1.length; k++) {
												if (b1[k]) {
													erg = erg+(cbs1[k].getText());
													erg=erg+";";
												}
											}
											erg=erg.substring(0,erg.length()-1);
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup1.getSelectedToggle().getUserData().toString());
												}
												else {
													break;
												}
										}
										break;
								case 2: if (radioodercheck[i]=='c') {
											b2 = new boolean[cbs2.length];
											for (int j = 0; j<cbs2.length; j++) {
												b2[j] = cbs2[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b2.length; k++) {
												if (b2[k]) {
													erg = erg+(cbs2[k].getText());
													erg=erg+";";
												}
											}
											erg=erg.substring(0,erg.length()-1);
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup2.getSelectedToggle().getUserData().toString());
												}
												else {
													break;
												}
										}
										break;
								case 3: if (radioodercheck[i]=='c') {
											b3 = new boolean[cbs3.length];
											for (int j = 0; j<cbs3.length; j++) {
												b3[j] = cbs3[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b3.length; k++) {
												if (b3[k]) {
													erg = erg+(cbs3[k].getText());
													erg=erg+";";
												}
											}
											erg=erg.substring(0,erg.length()-1);
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup3.getSelectedToggle().getUserData().toString());
												}
												else {
													break;
												}
										}
										break;
								case 4: if (radioodercheck[i]=='c') {
											b4 = new boolean[cbs4.length];
											for (int j = 0; j<cbs4.length; j++) {
												b4[j] = cbs4[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b4.length; k++) {
												if (b4[k]) {
													erg = erg+(cbs4[k].getText());
													erg=erg+";";
												}
											}
											erg=erg.substring(0,erg.length()-1);
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup4.getSelectedToggle().getUserData().toString());
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
					   //Output.schreibeWerte(klassif,teil2Textarea.getText(),ergebnis);
					   
					   System.out.println(klassif.texte.length);
					   System.out.println(klassif.textids.size());
					   
					   //ProgressBar updaten
					   texteklassi++;
					   
					   System.out.println("texteklassi:    "+texteklassi);
					   System.out.println("texteges:     "+texteges);
					   System.out.println("(double) texteklassi/texteges:   "+(double)texteklassi/texteges);
					   prozent=(double)texteklassi/texteges;
					   System.out.println("Wert prozent:    "+prozent);
					   
					   System.out.print("ist javafx appli thread:   ");
					   System.out.println(Platform.isFxApplicationThread());
					   //fortschritt = new ProgressBar(prozent);
					   //System.out.println("getProgress:      "+fortschritt.getProgress());
					   //System.out.println("isVisible:      "+fortschritt.isVisible());
					   
					   //Scene s = updateScene(timerlabel.getText(),prozent);
					   //setScene(s);
					   
					   fortschritt.setProgress(prozent);
					   Output.schreibeWerte(klassif,teil2Textarea.getText() , ergebnis);
					   
					   //neuen Text laden und anzeigen
					   String[] neuertext = klassif.getText();
					   String neuertext2 = neuertext[1];
					   if (neuertext2 == "") {
						   close();
						   //Output.schreibexml();
						   new FertigStage();
						   return;
					   }
					   teil2Textarea.setText(neuertext2);
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
					   fehlermeldungHaupt.setVisible(true);
					   //fehlermeldungHaupt.setText("Bitte prüfen Sie, dass sie jedes Label zugeordnet haben!");
				   }
				   
			   } 
		}; 
		labelabsenden.addEventFilter(MouseEvent.MOUSE_CLICKED, pruefenuabsenden);
		//start();
	}

	//initalisiert die Scene, die die Darstellung der Stage bestimmt
	public  Scene erstelleScene(LinkedHashMap<String,ArrayList<String>> labels) {   
		// Groesse der Scene
		x = 935;
		y = 600;
		
		// Spacer fuer gesamtes Pane
		vboxspacer0 = new Region();
		vboxspacer0.setPrefHeight(0);
		VBox.setVgrow(vboxspacer0, Priority.ALWAYS);
		vboxspacer1 = new Region();
		vboxspacer1.setPrefHeight(0);
		VBox.setVgrow(vboxspacer1, Priority.ALWAYS);
		vboxspacer2 = new Region();
		vboxspacer2.setPrefHeight(0);
		VBox.setVgrow(vboxspacer2, Priority.ALWAYS);
		vboxspacer3 = new Region();
		vboxspacer3.setPrefHeight(20);
		VBox.setVgrow(vboxspacer3, Priority.ALWAYS);
		vboxspacer4 = new Region();
		vboxspacer4.setPrefHeight(0);
		VBox.setVgrow(vboxspacer4, Priority.ALWAYS);
		
		// Spacer fuer abstaende links u rechts aussen im fenster
		//Region hboxspacer0 = new Region();
		//hboxspacer0.setPrefWidth(40);
		//HBox.setHgrow(hboxspacer0, Priority.ALWAYS);
		//Region hboxspacer1 = new Region();
		//hboxspacer1.setPrefWidth(40);
		//HBox.setHgrow(hboxspacer1, Priority.ALWAYS);
		
		//Spacer fuer teil1Daten
		Region hboxspacerx = new Region();
		hboxspacerx.setPrefWidth(15.0);
		//HBox.setHgrow(hboxspacerx, Priority.ALWAYS);
		Region hboxspacery = new Region();
		hboxspacery.setPrefWidth(15.0);
		//HBox.setHgrow(hboxspacery, Priority.ALWAYS);
		
		//Spacer fuer Textgroessen-Buttons
		Region hboxspaceri = new Region();
		hboxspaceri.setPrefWidth(5);
		//HBox.setHgrow(hboxspaceri, Priority.ALWAYS);
		Region hboxspacerj = new Region();
		hboxspacerj.setPrefWidth(2);
		//HBox.setHgrow(hboxspacerj, Priority.ALWAYS);
		
		
		/**
		 * VBox besteht aus 4 untereinanderliegenden Teilen
		 * Teil 1:	allgemeine Angaben --> ID, Fortschrite, Timer
		 * Teil 2:	scrollable textarea
		 * Teil 3:	Labelwertungssystem
		 * Teil 4:	schriftgroessen buttons + abschicken button
		 */	
		klasspane = new VBox();
		//spacehalter = new HBox();
		/** Leiste für Teil 1 */
		teil1Daten = new HBox(157.0);
		/** Teil 2 - scrollable textarea */
		teil2Texthalter = new ScrollPane();
		/** Teil 3 - Wertungssystem (tabellenfoermige Anordnung) */
		teil3Ranking = new GridPane();
		/** Teil 4 - Buttons werden links und rechts angezeigt */
		teil4GroesseAbsenden = new HBox(142.0);

		String nutzerstring = klassif.getNutzerID();
		
		
		//Teil1 - obere Zeile der Anzeige
		Label idanzeige = new Label("Nutzer-ID: "+nutzerstring);
		HBox.setHgrow(idanzeige, Priority.ALWAYS);
		//ProgressBar fortschritt = new ProgressBar(0.01);
		//fortschritt.impl_updatePeer();
		fortschritt.setPrefWidth(300);
		HBox.setHgrow(fortschritt, Priority.ALWAYS);
		//fortschritt.setProgress(0.01);
		if ((texteklassi/texteges) > 0) {
			//fortschritt = new ProgressBar(texteklassi/texteges);
			fortschritt.setProgress(texteklassi/texteges);
		}
		
		//Label zeitanzeige = new Label("Verbleibende Zeit: "); 	//anpassen, so dass Zeit angezeigt wird
		
		Label timerlabel = new Label("");
		int timeinmin = 60;										//Zeit fuer Timer wird hier eingestellt
		timerlabel.setText(timeinmin+":00");
		HBox.setHgrow(timerlabel, Priority.ALWAYS);
		
		setOnShowing(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle( WindowEvent event) {
		    	//schriftplus.fire();schriftplus.fire();schriftplus.fire();schriftplus.fire();schriftplus.fire();
		    	timerlabel.setText(timeinmin+":00");
				timerlabel.setVisible(true);
				if (timeline != null) {
					timeline.stop();
				}
				timeinsec = timeinmin*60;
	    	 
				// update timerLabel
				int min = timeinsec/60;
				int sec = timeinsec%60;
				if (sec > 9) {
					timerlabel.setText(min+":"+sec);
				}
				else timerlabel.setText(min+":0"+sec);
				timerlabel.setVisible(true);
				timeline = new Timeline();
				timeline.setCycleCount(Timeline.INDEFINITE);
	    		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() {
	    			public void handle(ActionEvent e) {
	    				timeinsec--;
	    				int min=timeinsec/60;
	    				int sec = timeinsec%60;
	    				if (sec > 9) {
	    					timerlabel.setText(min+":"+sec);
	    				}
	    				else timerlabel.setText(min+":0"+sec);
	    				if (timeinsec <= 0) {
	    					timesup();
	    					timeline.stop();
	    				}
	    			}
	    		}));
	    		timeline.playFromStart();
		    }
		});
		
		teil1Daten.getChildren().addAll(idanzeige, fortschritt, timerlabel);
		
		//Teil2 - scrollabe TextArea
		teil2Textarea = new TextArea();
		teil2Textarea.setEditable(false);
		sgroesse=14;
		teil2Textarea.setFont(new Font("Times",sgroesse));
		teil2Texthalter.setContent(teil2Textarea);
		teil2Texthalter.setFitToWidth(true);
		teil2Texthalter.setPrefWidth(850);
		teil2Texthalter.setPrefHeight(300);
		teil2Texthalter.setMinSize(850, 300);
		teil2Texthalter.setMaxSize(850, 300);
		teil2Textarea.setPrefSize(830, 1500);
		teil2Textarea.setMinSize(830, 299);
		teil2Textarea.setMaxWidth(830);
		teil2Textarea.setWrapText(true);
		
		String[] t = klassif.getText();
		String text = t[1];
		teil2Textarea.setText(text);
		teil2Textarea.setVisible(true);
		
		//teil 3 
		//LinkedHashMap<String,ArrayList<String>> labels mit Labelname als Key und Auswahlmoegl. als Value
		teil3Ranking = new GridPane();
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
							cbs0 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs0.length;j++) {
								cbs0[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));
								cbs0[j].setVisible(true);
							}
						}
						else {
							radioodercheck[i]='r';
							rb0x = new RadioButton();
							rb0x.setSelected(true);
							rb0x.setVisible(false);
							rb0x.setToggleGroup(rbgroup0);
							rbs0 = new RadioButton[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<rbs0.length;j++) {
								rbs0[j]=new RadioButton(labels.get(labelarray.get(i)).get(j));
								rbs0[j].setUserData(labels.get(labelarray.get(i)).get(j));
								rbs0[j].setToggleGroup(rbgroup0);
								rbs0[j].setVisible(true);
							}
						}
						break;
				case 1: label1=new Label(labelarray.get(i));
						labellabelliste.add(label1);
						if (mehrfachwahl[i]) {
							radioodercheck[i]='c';
							cbs1 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs1.length;j++) {
								cbs1[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));
								cbs1[j].setVisible(true);
							}
						}
						else {
							radioodercheck[i]='r';
							rb1x = new RadioButton();
							rb1x.setSelected(true);
							rb1x.setVisible(false);
							rb1x.setToggleGroup(rbgroup1);
							rbs1 = new RadioButton[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<rbs1.length;j++) {
								rbs1[j]=new RadioButton(labels.get(labelarray.get(i)).get(j));
								rbs1[j].setUserData(labels.get(labelarray.get(i)).get(j));
								rbs1[j].setToggleGroup(rbgroup1);
								rbs1[j].setVisible(true);
							}
						}
						break;
				case 2: label2=new Label(labelarray.get(i));
						labellabelliste.add(label2);
						if (mehrfachwahl[i]) {
							radioodercheck[i]='c';
							cbs2 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs2.length;j++) {
								cbs2[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));	
								cbs2[j].setVisible(true);
							}
						}
						else {
							radioodercheck[i]='r';
							rb2x = new RadioButton();
							rb2x.setSelected(true);
							rb2x.setVisible(false);
							rb2x.setToggleGroup(rbgroup2);
							rbs2 = new RadioButton[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<rbs2.length;j++) {
								rbs2[j]=new RadioButton(labels.get(labelarray.get(i)).get(j));
								rbs2[j].setUserData(labels.get(labelarray.get(i)).get(j));
								rbs2[j].setToggleGroup(rbgroup2);
								rbs2[j].setVisible(true);
							}
						}
						break;
				case 3: label3=new Label(labelarray.get(i));
						labellabelliste.add(label3);
						if (mehrfachwahl[i]) {
							radioodercheck[i]='c';
							cbs3 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs3.length;j++) {
								cbs3[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));
								cbs3[j].setVisible(true);
							}
						}
						else {
							radioodercheck[i]='r';
							rb3x = new RadioButton();
							rb3x.setSelected(true);
							rb3x.setVisible(false);
							rb3x.setToggleGroup(rbgroup3);
							rbs3 = new RadioButton[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<rbs3.length;j++) {
								rbs3[j]=new RadioButton(labels.get(labelarray.get(i)).get(j));
								rbs3[j].setUserData(labels.get(labelarray.get(i)).get(j));
								rbs3[j].setToggleGroup(rbgroup3);
								rbs3[j].setVisible(true);
							}
						}
						break;
				case 4: label4=new Label(labelarray.get(i));
						labellabelliste.add(label4);
						if (mehrfachwahl[i]) {
							radioodercheck[i]='c';
							cbs4 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs4.length;j++) {
								cbs4[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));
								cbs4[j].setVisible(true);
							}
						}
						else {
							radioodercheck[i]='r';
							rb4x = new RadioButton();
							rb4x.setSelected(true);
							rb4x.setVisible(false);
							rb4x.setToggleGroup(rbgroup4);
							rbs4 = new RadioButton[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<rbs4.length;j++) {
								rbs4[j]=new RadioButton(labels.get(labelarray.get(i)).get(j));
								rbs4[j].setUserData(labels.get(labelarray.get(i)).get(j));
								rbs4[j].setToggleGroup(rbgroup4);
								rbs4[j].setVisible(true);
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
			teil3Ranking.getChildren().add(labellabelliste.get(i));
		}
		for(int i=0;i<radioodercheck.length;i++) {
			switch(i) {
			case 0:
				switch(radioodercheck[i]) {
				case 'r':
					for (int j=0;j<rbs0.length;j++) {
						GridPane.setColumnIndex(rbs0[j], j+1);
						GridPane.setRowIndex(rbs0[j], i);
						teil3Ranking.getChildren().add(rbs0[j]);
					}
					break;
				case 'c':
					for (int j=0;j<cbs0.length;j++) {
						GridPane.setColumnIndex(cbs0[j], j+1);
						GridPane.setRowIndex(cbs0[j], i);
						teil3Ranking.getChildren().add(cbs0[j]);
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
						GridPane.setRowIndex(rbs1[j], i);
						teil3Ranking.getChildren().add(rbs1[j]);
					}
					break;
				case 'c':
					for (int j=0;j<cbs1.length;j++) {
						GridPane.setColumnIndex(cbs1[j], j+1);
						GridPane.setRowIndex(cbs1[j], i);
						teil3Ranking.getChildren().add(cbs1[j]);
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
						GridPane.setRowIndex(rbs2[j], i);
						teil3Ranking.getChildren().add(rbs2[j]);
					}
					break;
				case 'c':
					for (int j=0;j<cbs2.length;j++) {
						GridPane.setColumnIndex(cbs2[j], j+1);
						GridPane.setRowIndex(cbs2[j], i);
						teil3Ranking.getChildren().add(cbs2[j]);
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
						GridPane.setRowIndex(rbs3[j], i);
						teil3Ranking.getChildren().add(rbs3[j]);
					}
					break;
				case 'c':
					for (int j=0;j<cbs3.length;j++) {
						GridPane.setColumnIndex(cbs3[j], j+1);
						GridPane.setRowIndex(cbs3[j], i);
						teil3Ranking.getChildren().add(cbs3[j]);
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
						GridPane.setRowIndex(rbs4[j], i);
						teil3Ranking.getChildren().add(rbs4[j]);
					}
					break;
				case 'c':
					for (int j=0;j<cbs4.length;j++) {
						GridPane.setColumnIndex(cbs4[j], j+1);
						GridPane.setRowIndex(cbs4[j], i);
						teil3Ranking.getChildren().add(cbs4[j]);
					}
					break;
				default: break;
				}
				break;
			}
		}
		
		/**
		Region ghspacer0 = new Region();
		ghspacer0.setPrefHeight(30);
		GridPane.setHgrow(ghspacer0, Priority.ALWAYS);
		Region ghspacer1 = new Region();
		ghspacer1.setPrefHeight(30);
		GridPane.setHgrow(ghspacer1, Priority.ALWAYS);
		Region ghspacer2 = new Region();
		ghspacer2.setPrefHeight(30);
		GridPane.setHgrow(ghspacer2, Priority.ALWAYS);
		Region ghspacer3 = new Region();
		ghspacer3.setPrefHeight(30);
		GridPane.setHgrow(ghspacer3, Priority.ALWAYS);
		Region ghspacer4 = new Region();
		ghspacer4.setPrefHeight(30);
		GridPane.setHgrow(ghspacer4, Priority.ALWAYS);

		Region gvspacer0 = new Region();
		gvspacer0.setPrefHeight(50);
		GridPane.setVgrow(gvspacer0, Priority.ALWAYS);
		Region gvspacer1 = new Region();
		gvspacer1.setPrefHeight(50);
		GridPane.setVgrow(gvspacer1, Priority.ALWAYS);
		Region gvspacer2 = new Region();
		gvspacer2.setPrefHeight(50);
		GridPane.setVgrow(gvspacer2, Priority.ALWAYS);
		Region gvspacer3 = new Region();
		gvspacer3.setPrefHeight(50);
		GridPane.setVgrow(gvspacer3, Priority.ALWAYS);
		Region gvspacer4 = new Region();
		gvspacer4.setPrefHeight(50);
		GridPane.setVgrow(gvspacer4, Priority.ALWAYS);
		
		GridPane.setConstraints(ghspacer0, 1, 0);
		GridPane.setConstraints(ghspacer1, 3, 0);
		GridPane.setConstraints(ghspacer2, 5, 0);
		GridPane.setConstraints(ghspacer3, 7, 0);
		GridPane.setConstraints(ghspacer4, 9, 0);
		
		GridPane.setConstraints(gvspacer0, 0, 1);
		GridPane.setConstraints(gvspacer1, 0, 3);
		GridPane.setConstraints(gvspacer2, 0, 5);
		GridPane.setConstraints(gvspacer3, 0, 7);
		GridPane.setConstraints(gvspacer4, 0, 9);
		*/
		
		teil3Ranking.setHgap(20);
		teil3Ranking.setVgap(5);
		teil3Ranking.setPrefWidth(900);
		teil3Ranking.setMinWidth(900);
		
		//Teil 4 - Absenden und Schriftgroeße
		schriftgroesse = new HBox();
		schrift = new Label("Schriftgröße");
		HBox.setHgrow(schrift, Priority.ALWAYS);
		schriftplus = new Button("+");
		HBox.setHgrow(schriftplus, Priority.ALWAYS);
		schriftminus = new Button("-");
		HBox.setHgrow(schriftminus, Priority.ALWAYS);
		schriftplus.setPrefWidth(28);
		schriftplus.setMinWidth(28);
		schriftminus.setPrefWidth(28);
		schriftminus.setMinWidth(28);
		//schrift.setMinHeight(schriftplus.getHeight());
		//schrift.setTextOrigin(VPos.CENTER);
		//schrift.setTextAlignment(VPos.CENTER);
		labelabsenden = new Button("Absenden");
		HBox.setHgrow(labelabsenden, Priority.ALWAYS);
		schriftgroesse.getChildren().addAll(schrift, hboxspaceri, schriftplus, hboxspacerj, schriftminus);
		fehlermeldungHaupt = new Label("Bitte prüfen Sie, dass Sie jedes Label zugeordnet haben!");
		fehlermeldungHaupt.setVisible(false);
		HBox.setHgrow(fehlermeldungHaupt, Priority.ALWAYS);
		
		//fehlermeldungHaupt.setMinWidth(343.0);
		//fehlermeldungHaupt.setMaxWidth(343.0);
		//fehlermeldungHaupt.setPrefWidth(343.0);
		
		teil4GroesseAbsenden.getChildren().addAll(schriftgroesse,fehlermeldungHaupt,labelabsenden);			
		
		System.out.println(fehlermeldungHaupt.getWidth());
		
		klasspane.setPrefWidth(900);
		klasspane.setMinWidth(900);
		//klasspane.setFillWidth(true);
		klasspane.getChildren().addAll(teil1Daten, teil2Texthalter, teil3Ranking, teil4GroesseAbsenden);
		//spacehalter.getChildren().addAll(hboxspacer0, klasspane, hboxspacer1);
		klasspane.setPadding(new Insets(30,40,50,40));
		klasspane.setSpacing(25);
		Scene klassi = new Scene(klasspane,x,y);
		//klassi.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		return klassi;
	}
	
	
	/**
	//Methode, die die Eventhandler beinhaltet
	public void start() {
		this.show();  --> nicht noetig, da extends stage, nicht application
		 
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
													erg=erg+";";
													//laberlaber
												}
											}
											erg=erg.substring(0,erg.length()-2);
											ergebnis.add(erg);
										}
										else {
											//radioodercheck[i]='r';
											ergebnis.add(rbgroup0.getSelectedToggle().getUserData().toString());
										}
										break;
								case 1: if (radioodercheck[i]=='c') {
											b1 = new boolean[cbs1.length];
											for (int j = 0; j<cbs1.length; j++) {
												b1[j] = cbs1[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b1.length; k++) {
												if (b1[k]) {
													erg = erg+(cbs1[k].getText());
													erg=erg+";";
												}
											}
											erg=erg.substring(0,erg.length()-2);
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup1.getSelectedToggle().getUserData().toString());
												}
												else {
													break;
												}
										}
										break;
								case 2: if (radioodercheck[i]=='c') {
											b2 = new boolean[cbs2.length];
											for (int j = 0; j<cbs2.length; j++) {
												b2[j] = cbs2[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b2.length; k++) {
												if (b2[k]) {
													erg = erg+(cbs2[k].getText());
													erg=erg+";";
												}
											}
											erg=erg.substring(0,erg.length()-2);
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup2.getSelectedToggle().getUserData().toString());
												}
												else {
													break;
												}
										}
										break;
								case 3: if (radioodercheck[i]=='c') {
											b3 = new boolean[cbs3.length];
											for (int j = 0; j<cbs3.length; j++) {
												b3[j] = cbs3[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b3.length; k++) {
												if (b3[k]) {
													erg = erg+(cbs3[k].getText());
													erg=erg+";";
												}
											}
											erg=erg.substring(0,erg.length()-2);
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup3.getSelectedToggle().getUserData().toString());
												}
												else {
													break;
												}
										}
										break;
								case 4: if (radioodercheck[i]=='c') {
											b4 = new boolean[cbs4.length];
											for (int j = 0; j<cbs4.length; j++) {
												b4[j] = cbs4[j].isSelected();
											}
											String erg = "";
											for (int k = 0; k<b4.length; k++) {
												if (b4[k]) {
													erg = erg+(cbs4[k].getText());
													erg=erg+";";
												}
											}
											erg=erg.substring(0,erg.length()-2);
											ergebnis.add(erg);
										}
										else {	if (radioodercheck[i]=='r') {
													ergebnis.add(rbgroup4.getSelectedToggle().getUserData().toString());
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
					   //ProgressBar updaten
					   fortschritt.setProgress((klassif.texte.length - klassif.textids.size())/klassif.texte.length);
					   //neuen Text laden und anzeigen
					   String[] neuertext = klassif.getText();
					   String neuertext2 = neuertext[1];
					   if (neuertext2 == "") {
						   close();
						   Output.schreibexml();
						   new FertigStage();
						   return;
					   }
					   teil2Textarea.setText(neuertext2);
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
					   fehlermeldungHaupt.setVisible(true);
					   //fehlermeldungHaupt.setText("Bitte prüfen Sie, dass sie jedes Label zugeordnet haben!");
				   }
			   } 
		}; 
		labelabsenden.addEventFilter(MouseEvent.MOUSE_CLICKED, pruefenuabsenden);
	} 
	*/
	
	//prueft ob zu jedem Label (min.) 1 Moeglichkeit ausgewaehlt wurde -> true, wenn nicht false
	public boolean check() {
		boolean check = true;
		for (int i = 0; i<labelarray.size();i++) {
			switch (i) {
				case 0:
					if (radioodercheck[i]=='c') {
						boolz0 = 0;
						for (int j = 0; j<cbs0.length; j++) {
							if(cbs0[j].isSelected()) {
								boolz0++;
							}	
						}
						if(boolz0==0) {
							check=false;
							break;
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
						boolz1 = 0;
						for (int j = 0; j<cbs1.length; j++) {
							if(cbs1[j].isSelected()) {
								boolz1++;
							}	
						}
						if(boolz1==0) {
							check=false;
							break;
						}
					}
					else {
						if (radioodercheck[i]=='r') {
							if (rbgroup1.getSelectedToggle()==rb1x) {
								check=false;
								break;
							}
						}
						else break;						
					}
					break;
				case 2:
					if (radioodercheck[i]=='c') {
						boolz2 = 0;
						for (int j = 0; j<cbs2.length; j++) {
							if(cbs2[j].isSelected()) {
								boolz2++;
							}	
						}
						if(boolz2==0) {
							check=false;
							break;
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
						boolz3 = 0;
						for (int j = 0; j<cbs3.length; j++) {
							if(cbs3[j].isSelected()) {
								boolz3++;
							}	
						}
						if(boolz3==0) {
							check=false;
							break;
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
						boolz4 = 0;
						for (int j = 0; j<cbs4.length; j++) {
							if(cbs4[j].isSelected()) {
								boolz4++;
							}	
						}
						if(boolz4==0) {
							check=false;
							break;
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
	
	public Scene updateScene(String zeit, double fortschrittinprozent) {
		
		//Teil1 - obere Zeile der Anzeige
		Label idanzeige = new Label("Nutzer-ID: "+klassif.getNutzerID());
		ProgressBar fortschritt = new ProgressBar(0.01);
		fortschritt.setPrefWidth(300);
		fortschritt.setProgress(fortschrittinprozent);
		
		System.out.println(zeit.substring(0, 1));
		System.out.println(zeit.substring(3, 4));
		
		int zeitmin = Integer.parseInt(zeit.substring(0, 1));
		int zeitsec = Integer.parseInt(zeit.substring(3, 4));
		//Label zeitanzeige = new Label("Verbleibende Zeit: "); 	//anpassen, so dass Zeit angezeigt wird
			
		Label timerlabel = new Label("");
		
		//Zeit fuer Timer wird hier eingestellt
		timerlabel.setText(zeitmin+":"+zeitsec);
				
		setOnShowing(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		    	schriftplus.fire();schriftplus.fire();schriftplus.fire();schriftplus.fire();schriftplus.fire();
		    	timerlabel.setText(zeitmin+":"+zeitsec);
				timerlabel.setVisible(true);
				if (timeline != null) {
					timeline.stop();
				}
				timeinsec = (zeitmin*60)+zeitsec;
			    	 
				// update timerLabel
				int min = timeinsec/60;
				int sec = timeinsec%60;
				if (sec > 9) {
					timerlabel.setText(min+":"+sec);
				}
				else timerlabel.setText(min+":0"+sec);
				timerlabel.setVisible(true);
				timeline = new Timeline();
				timeline.setCycleCount(Timeline.INDEFINITE);
			    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() {
			    	public void handle(ActionEvent e) {
			    		timeinsec--;
			    		int min=timeinsec/60;
			    		int sec = timeinsec%60;
			    		if (sec > 9) {
			    			timerlabel.setText(min+":"+sec);
			    		}
			    		else timerlabel.setText(min+":0"+sec);
			   			if (timeinsec <= 0) {
			   				timesup();
			   				timeline.stop();
			   			}
			   		}
			   }));
			   timeline.playFromStart();
		    }
		});
		teil1Daten.getChildren().addAll(idanzeige, fortschritt, timerlabel);
				
		klasspane.setPrefWidth(900);
		klasspane.setMinWidth(900);
		//klasspane.setFillWidth(true);
		klasspane.getChildren().addAll(vboxspacer0,teil1Daten, vboxspacer1, teil2Texthalter, vboxspacer2, teil3Ranking, vboxspacer3, teil4GroesseAbsenden, vboxspacer4);
		//spacehalter.getChildren().addAll(hboxspacer0, klasspane, hboxspacer1);
		klasspane.setPadding(new Insets(10,40,10,40));
		Scene sc = new Scene(klasspane,x,y);
		//klassi.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				
		return sc;
	}
	
	public void timesup() {
		//provozierefehler - notdoneyet
		//Output.abbruchSave(klassif);
		close();
		new ZeitEndeStage(fortschritt.getProgress());
	}
}