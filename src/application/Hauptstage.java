package application;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * In dieser Klasse ist der Teil der GUI programmiert, der die meiste Zeit fuer den Nutzer sichtbar ist - also dass 
 * "Klassifizierungs-Fenster". Das Layout ist hauptsaechlich in 4 Abschnitte unterteilt, welche senkrecht im Fenster
 * angeordnet sind. Im ersten Teil (HBox teil1daten) befinden sich die Label zur Anzeige der Nutzer-ID und der Bear-
 * beitungszeit, sowie der Fortschrittsbalken. Im zweiten Abschnitt (ScrollPane teil2Texthalter) werden nacheinander
 * die zu klassifizierenden Texte angezeigt. Im dritten Teil (GridPane teil3Ranking) sind die Label, sowie die Check-
 * Boxen und RadioButtons mit denen der Nutzer den Labeln ihre Werte zuordnen kann. Der letze Abschnitt (HBox 
 * teil4GroesseAbsenden) beinhaltet Buttons zum Aendern der Schriftgroesse, einen Button zu Absenden der eingegebenen
 * Labelwerte, sowie einen Bereich zur Anzeige einer Fehlermeldung.
 * Im Folgenden ist der Quellcode bestmoeglich nach diesen vier Abschnitten sortiert, um die Uebersicht zu wahren.
 * 
 * @author becksusanna
 */
public class Hauptstage extends Stage {

	/*
	 * Der Klassifikator speichert die Texte und Label und bietet Zugriff auf diese, ohne
	 * dass sie jedes mal ueber Input3 neu eingelesen werden muessen. Da die Anzahl der 
	 * Texte insgegesamt und die Anzahl der bereits klassifizierten Texte hier oefter auf-
	 * gerufen werden um den Fortschrittsbalken zu aktualisieren, werden sie in dieser 
	 * noch mal als eigenstaendige Variablen gespeichert.  
	 */
	private Klassifikator klassif;
	private int texteges;
	private int texteklassi;
	
	//Das Klasspane beinhaltet die vier oben beschriebenen Teile und x und y geben die 
	//Groesse des Panes. Sie sind nicht final gesetzt, da sie von der Anzahl der Label 
	//abhaengig sind (s.u.).
	private VBox klasspane;
	private int x, y;
	
	//Panes fuer die vier Teile
	private HBox teil1Daten; 				//Teil 1 - "Daten-Leiste" --> ID, Fortschritt, Timer
	private TextArea teil2Textarea;		//Teil 2 - TextArea zeigt Text an
	private GridPane teil3Ranking;			//Teil 3 - Labelwertungssystem (tabellenfoermige Anordnung)
	private HBox teil4GroesseAbsenden;		//Teil 4 - Schriftgroessen-Buttons u Absenden-Button links und rechts, mittig Fehlermeldung
	
	//Teil1
	private ProgressBar fortschritt;
	private Timeline timeline;				//timeline "steuert" den Timer
	private int timeinsec;
	private double prozent;					//prozent gibt den Fortschritt in prozent -> "Wert" der ProgressBar
	
	//Teil2 -> keine weiteren Komponenten	
	
	//Teil3
	
	//Namen der Label werden in Form von Labeln angezeigt
	private Label label0, label1, label2, label3, label4;
	//Arrays von CheckBoxen bzw. RadioButtons je Label (0 bis 4)
	private RadioButton[] rbs0, rbs1, rbs2, rbs3, rbs4;
	private CheckBox[] cbs0, cbs1, cbs2, cbs3, cbs4;
	//ToggleGroups "vereinen" die RadioButtons in Gruppen, sodass immer nur einer ausgewaehlt sein kann,
	//eine Gruppe pro Label noetig (0 bis 4)
	private ToggleGroup rbgroup0, rbgroup1, rbgroup2, rbgroup3, rbgroup4;
	//zwei Arrays, die zu jedem Label speichern, ob RadioButtons oder CheckBoxen
	//initialisiert wurden. (Wert-Erklaerungen siehe unten)
	private char[] radioodercheck;
	private boolean[] mehrfachwahl;
	
	//Liste, die Labelnamen beinhaltet
	private ArrayList<String> labelarray;
	//Variablen, die beim Auslesen der gewaehlten RadioButtons und Checkboxen helfen
	private boolean[] b0, b1, b2, b3, b4;
	private int boolz0, boolz1, boolz2, boolz3, boolz4;
	//"Hilfs-RadioButtons", damit es bei (Neu-)Initialisierung, der RadioButtons so aussieht als waere 
	//keiner ausgewaehlt (sind Teil der ToggleGroups rbgroup0 - rbgroup4) Je ToggleGroup darf naemlich
	//immer nur genau ein RadioButton auf 'selected' gesetzt werden darf, also auch nicht keiner, was
	//diese HilfsButtons erforderlich macht.
	private RadioButton rb0x, rb1x, rb2x, rb3x, rb4x;
	
	//Teil4
	private HBox schriftgroesse;
	//die folgenden drei Elemente sind Teil der HBox schriftgroesse:
	private Label schrift;
	private Button schriftplus;
	private Button schriftminus;
	//die anderen zwei Elemente von Teil 4:
	private Button labelabsenden;
	private Label fehlermeldungHaupt;
	//sgroesse speichert dauerhaft die aktuelle Schriftgroesse, sodass diese auf ein Max/Minimum 
	//beschraenkbar ist
	private int sgroesse;
	
	
	/**
	 * Der Konstruktor der Klasse Hauptstage erzeugt ein Stage-Objekt, welches auch gleich 
	 * eine javafx-Scene zugeordnet bekommt und dann angezeigt wird.
	 */
	public Hauptstage(Klassifikator klasse, int anzklassifizierte) {
		super();
		this.klassif=klasse;
		this.texteges=klassif.getTexte().length;
		this.texteklassi=anzklassifizierte;
		prozent=(double)anzklassifizierte/klasse.getTexte().length;
		this.fortschritt = new ProgressBar(prozent);
		int zeit = Input3.getTime();	//Einlesen der erlaubten Zeit aus der Property-Datei
		//"Bau" der Scene, Zuordnung zur Stage u anzeigen Stage
		Scene scene = this.erstelleScene(klassif.getLabel(), zeit);
		this.setScene(scene);
		this.show();
		
		//Eventhandler fuer den '+'-Button, mit dem sich die Schriftgroesse veraendern laesst
		EventHandler<MouseEvent> schriftgroesseplus = new EventHandler<MouseEvent>() { 
		   @Override 
		   public void handle(MouseEvent e) { 
			   //Schrift soll nicht zu gross werden, daher auf 24 begrenzt
			   if (sgroesse <=24) {
				   sgroesse = sgroesse+2;
				   teil2Textarea.setFont(Font.font("Courier New", sgroesse));
			   }
		   } 
		};
		schriftplus.addEventFilter(MouseEvent.MOUSE_CLICKED, schriftgroesseplus);
		
		//Eventhandler fuer den '-'-Button, mit dem sich die Schriftgroesse veraendern laesst
		EventHandler<MouseEvent> schriftgroesseminus = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent e) { 
				//Schrift soll nicht zu klein werden, daher nach unten auf 10 begrenzt
				if (sgroesse >=10) {
				   sgroesse = sgroesse-2;
				   teil2Textarea.setFont(Font.font("Courier New", sgroesse));
			   }
			} 
		};
		schriftminus.addEventFilter(MouseEvent.MOUSE_CLICKED, schriftgroesseminus);
		
		//Eventhandler fur den 'Absenden'-Button
		EventHandler<MouseEvent> pruefenuabsenden = new EventHandler<MouseEvent>() { 
			   @Override 
			   public void handle(MouseEvent e) { 
				   //check zeigt dass je label (min) 1 Option ausgewaehlt ist
				   if(check()) { 
					   //Erstellen eines String-Arrays fuer die gewaehlten Optionen
					   ArrayList<String> ergebnis = new ArrayList<String>();
					   //Iteration ueber alle Label
					   for (int i = 0; i<labelarray.size();i++) {
							switch (i) {
								//jeder case i bezieht sich auf Label i. Dabei wird immer zuerst radioodercheck
								//ausgelesen. Weiterer Verlauf beispielhaft in case0 beschrieben: 
								case 0: if (radioodercheck[i]=='c') { //'c' fuer Checkbox
											//zum CheckBoxArray cbs0 wird ein boolean-Array b0 erstellt mit true 
											//fuer einen selected Button und false fuer einen nicht gewaehlten		
											b0 = new boolean[cbs0.length];
											for (int j = 0; j<cbs0.length; j++) {
												b0[j] = cbs0[j].isSelected();
											}
											//die ausgewaehlten Werte werden nun mit Semikolon getrennt in einen
											//String erg geschrieben
											String erg = "";
											for (int k = 0; k<b0.length; k++) {
												if (b0[k]) {
													erg = erg+(cbs0[k].getText());
													erg=erg+";";
												}
											}
											//Der String erg wird schliesslich zur ergebnis-Liste hinzugefuegt.
											//Da immer ein Semikolon an erg angehaengt wird, wird hier mittels
											//substring das letzte Zeichen wieder abgeschnitten.
											erg=erg.substring(0,erg.length()-1);
											ergebnis.add(erg);
										}
										else {
											//Hier ist also radioodercheck[i]='r' .
											//Einfacherweise wird nun einfach der "Text" des gewaehlten RadioButtons
											//zu ergebnis hinzugefuegt, also bspw "Trifft zu".											
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
					   
					   //ProgressBar updaten
					   texteklassi++;
					   prozent=(double)texteklassi/texteges;
					   fortschritt.setProgress(prozent);
					   //schreiben der ausgelesenen Werte in Output-Datei
					   Output.schreibeWerte(klassif,teil2Textarea.getText() , ergebnis);
					   
					   //neuen Text laden
					   String[] neuertext = klassif.getText();
					   String neuertext2 = neuertext[1];	//neuertext[0] waere ID des Textes
					   //wenn kein Text mehr vorhanden wird die Stage geschlossen und eine neue FertigStage geoeffnet
					   if (neuertext2 == "") {
						   close();
						   new FertigStage();
						   return;
					   }
					   //neuen Text anzeigen
					   teil2Textarea.setText(neuertext2);
					   
					   //Neuinitialisierung der CheckBoxen und RadioButtons mittel Iteration ueber alle Label
					   for (int i = 0; i<labelarray.size();i++) {
							switch (i) {
								case 0: if (radioodercheck[i]=='c') {
											//CheckBoxen werden alle auf nicht gewaehlt gesetzt
											for (int j = 0; j<cbs0.length; j++) {
												cbs0[j].setSelected(false);
											}
										}
										else {
											//radioodercheck[i]='r'
											//hier wird nicht angezeigter "Hilfs-RadioButton" als gewaehlt gesetzt
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
					   //Falls nicht jedes Label zugeordnet wurde wird die Fehlermeldung sichtbar
					   fehlermeldungHaupt.setVisible(true);
				   }
			   } 
		}; 
		labelabsenden.addEventFilter(MouseEvent.MOUSE_CLICKED, pruefenuabsenden);
	}

	/**
	 * Methode, die die Scene - und damit das Aussehen der Stage - initialisiert
	 */
	public  Scene erstelleScene(LinkedHashMap<String,ArrayList<String>> labels, int erlaubtezeit) {   
		//Groesse der Scene - y wird anhand der Anzahl der Label berechnet
		int ywith5labels = 600;
		x = 935;
		y = ywith5labels + (labels.size()-5)*30;
		
		
		//Initialisierung des klasspanes und der vier einzelnen Teile
		klasspane = new VBox();
		teil1Daten = new HBox(157.0);
		teil2Textarea = new TextArea();
		teil3Ranking = new GridPane();
		teil4GroesseAbsenden = new HBox(142.0);

		
		//Teil1 - obere Zeile der Anzeige
		//Wertzuweisung u Groessen- u Schrifteinstellungen des NutzerID-Labels u des Fortschrittbalkens
		String nutzerstring = klassif.getNutzerID();
		Label idanzeige = new Label("Nutzer-ID: "+nutzerstring);
		idanzeige.setFont(Font.font("Tahoma"));
		idanzeige.setMinWidth(200);
		HBox.setHgrow(idanzeige, Priority.ALWAYS);
		fortschritt.setPrefWidth(300);
		fortschritt.setMinWidth(300);
		HBox.setHgrow(fortschritt, Priority.ALWAYS);
		if ((texteklassi/texteges) > 0) {
			fortschritt.setProgress(texteklassi/texteges);
		}
		
		//Wertzuweisung u Groessen- u Schrifteinstellungen des Timerlabels
		Label timerlabel = new Label("");
		timerlabel.setFont(Font.font("Tahoma"));
		int timeinmin = erlaubtezeit;
		timerlabel.setText(timeinmin+":00");
		timerlabel.setMinWidth(41);
		HBox.setHgrow(timerlabel, Priority.ALWAYS);
		
		//Timeline-Event, dass sofort mit Anzeigen der Stage gestartet wird und den Timer steuert
		setOnShowing(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle( WindowEvent event) {
		    	//Timerlabel auf Startzeit u auf sichtbar setzen
		    	timerlabel.setText(timeinmin+":00");
				timerlabel.setVisible(true);
				//falls timeline (versehentlich) bereits vorher initialisiert wurde, wird sie hier beendet
				if (timeline != null) {
					timeline.stop();
				}
				timeinsec = timeinmin*60;
				//starten einer neuen Timeline
				timeline = new Timeline();
				//timeline so einstellen, dass handle() jede Sekunde ausgefuehrt wird
				timeline.setCycleCount(Timeline.INDEFINITE);
	    		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() {
	    			public void handle(ActionEvent e) {
	    				//timerlabel updaten:
	    				//dafuer zeit in sekunden aufteilen in Minuten und Sekunden
	    				timeinsec--;
	    				int min=timeinsec/60;
	    				int sec = timeinsec%60;
	    				//Anzeige der Zeit, dabei Unterscheidung in ein- und zweiziffrige Werte fuer Sekunden, 
	    				//damit nicht bspw. 35:5, sonder lieber 35:05 angezeigt wird
	    				if (sec > 9) {
	    					timerlabel.setText(min+":"+sec);
	    				}
	    				else timerlabel.setText(min+":0"+sec);
	    				//wenn Zeit abgelaufen wird die timeline gestoppt u timesup() aufgerufen
	    				if (timeinsec <= 0) {
	    					timesup();
	    					timeline.stop();
	    				}
	    			}
	    		}));
	    		timeline.playFromStart();
		    }
		});
		
		//teil1Daten bekommt seine Elemente zugeordnet und wird auf die passende Breite gesetzt
		teil1Daten.getChildren().addAll(idanzeige, fortschritt, timerlabel);
		teil1Daten.setPrefWidth(850);
		teil1Daten.setMinWidth(850.0);
		teil1Daten.setMaxWidth(850.0);
		
		
		//Teil2 - Scrollabe TextArea
		
		//TextArea soll vom Nutzer nicht bearbeitbar sein:
		teil2Textarea.setEditable(false);
		//Einstellen der Schriftart und -groesse fuer den Text
		sgroesse=14;
		teil2Textarea.setFont(Font.font("Courier New",sgroesse));
		//Groesseneinstellungen fuer die TextArea, welche den tatsaechlichen Text beinhaltet
		teil2Textarea.setMinSize(850, 299);
		teil2Textarea.setMaxWidth(850);
		//falls Text breiter ist als TextArea, werden automatisch Textumbrueche eingefuegt
		teil2Textarea.setWrapText(true);
		//laden und anzeigen des ersten Texts
		String[] t = klassif.getText();
		String text = t[1];
		teil2Textarea.setText(text);
		teil2Textarea.setVisible(true);
		
		//Teil 3 - Labelwertungssystem
		//Initalisierung der RadioButtons, CheckBoxen, ToggleGroups dieses Teils
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
		//Einfuegen der Label in labelarray
		labelarray = new ArrayList<String>();
		for (Iterator<String> it = labels.keySet().iterator(); it.hasNext();) {
			labelarray.add(it.next());
		}
		//Abfragen der LabelEigenschaften -> Array mit boolean-Werten, die angeben ob mehrere 
		//Labelwerte ausgewaehlt werden duerfen -> Index gibt jeweils Labelnummer
		mehrfachwahl = Input3.labelEigenschaft();
		//in der labellabelliste werden die javafx-Label mit den Labelnamen als Text fuer den
		//spaeteren Zugriff gesichert
		ArrayList<Label> labellabelliste = new ArrayList<Label>();
		//Im Folgenden werden je nachdem die CheckBoxen bzw RadioButtons und die Label mit den
		//Labelnamen erstellt und ihren jeweiligen Listen und Arrays zugeordnet ueber die sie 
		//im weiteren Verlauf angesprochen werden (labellabelliste, cbs'i'[] und rbs'i'[]).
		//Ausserdem werden Werte zu radioodercheck hinzugefuegt (Moeglichkeiten s.o.)
		for (int i = 0; i<labelarray.size();i++) {
			switch (i) {
				case 0: label0=new Label(labelarray.get(i));
						label0.setFont(Font.font("Tahoma"));
						labellabelliste.add(label0);
						if (mehrfachwahl[i]) {
							radioodercheck[i]='c';
							cbs0 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs0.length;j++) {
								cbs0[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));
								cbs0[j].setFont(Font.font("Tahoma"));
								cbs0[j].setVisible(true);
							}
						}
						else {
							radioodercheck[i]='r';
							//hier wird zusaetzlich noch rb'i'x erstellt, der "Hilfs-RadioButton" durch den
							//es moeglich wird, dass aus Nutzersicht kein RadioButton vorausgewaehlt ist
							rb0x = new RadioButton();
							rb0x.setSelected(true);
							rb0x.setVisible(false);
							rb0x.setToggleGroup(rbgroup0);
							rbs0 = new RadioButton[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<rbs0.length;j++) {
								rbs0[j]=new RadioButton(labels.get(labelarray.get(i)).get(j));
								rbs0[j].setUserData(labels.get(labelarray.get(i)).get(j));
								rbs0[j].setToggleGroup(rbgroup0);
								rbs0[j].setFont(Font.font("Tahoma"));
								rbs0[j].setVisible(true);
							}
						}
						break;
				case 1: label1=new Label(labelarray.get(i));
						label1.setFont(Font.font("Tahoma"));
						labellabelliste.add(label1);
						if (mehrfachwahl[i]) {
							radioodercheck[i]='c';
							cbs1 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs1.length;j++) {
								cbs1[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));
								cbs1[j].setFont(Font.font("Tahoma"));
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
								rbs1[j].setFont(Font.font("Tahoma"));
								rbs1[j].setVisible(true);
							}
						}
						break;
				case 2: label2=new Label(labelarray.get(i));
						label2.setFont(Font.font("Tahoma"));
						labellabelliste.add(label2);
						if (mehrfachwahl[i]) {
							radioodercheck[i]='c';
							cbs2 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs2.length;j++) {
								cbs2[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));	
								cbs2[j].setFont(Font.font("Tahoma"));
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
								rbs2[j].setFont(Font.font("Tahoma"));
								rbs2[j].setVisible(true);
							}
						}
						break;
				case 3: label3=new Label(labelarray.get(i));
						label3.setFont(Font.font("Tahoma"));
						labellabelliste.add(label3);
						if (mehrfachwahl[i]) {
							radioodercheck[i]='c';
							cbs3 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs3.length;j++) {
								cbs3[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));
								cbs3[j].setFont(Font.font("Tahoma"));
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
								rbs3[j].setFont(Font.font("Tahoma"));
								rbs3[j].setVisible(true);
							}
						}
						break;
				case 4: label4=new Label(labelarray.get(i));
						label4.setFont(Font.font("Tahoma"));
						labellabelliste.add(label4);
						if (mehrfachwahl[i]) {
							radioodercheck[i]='c';
							cbs4 = new CheckBox[labels.get(labelarray.get(i)).size()];
							for(int j = 0; j<cbs4.length;j++) {
								cbs4[j]=new CheckBox(labels.get(labelarray.get(i)).get(j));
								cbs4[j].setFont(Font.font("Tahoma"));
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
								rbs4[j].setFont(Font.font("Tahoma"));
								rbs4[j].setVisible(true);
							}
						}
						break;
				default:	break;
						
			}
		}
		//Label ins GridPane einfuegen
		for(Integer i=0;i<labellabelliste.size();i++) {
			GridPane.setColumnIndex(labellabelliste.get(i), 0);
			GridPane.setRowIndex(labellabelliste.get(i), i);
			teil3Ranking.getChildren().add(labellabelliste.get(i));
		}
		//nun werden alle RadioButtons und CheckBoxen in das GridPane eingefuegt
		for(int i=0;i<radioodercheck.length;i++) {
			switch(i) {
			case 0:
				switch(radioodercheck[i]) {
				case 'r':
					//oben iteriert i ueber die Label, hier iteriert j ueber die moeglichen Labelwerte
					for (int j=0;j<rbs0.length;j++) {
						//i+1, weil in Spalte/Column 0 der Labelname steht
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
		//Layouteinstellungen fuer das GridPane
		teil3Ranking.setHgap(20);
		teil3Ranking.setVgap(5);
		teil3Ranking.setPrefWidth(900);
		teil3Ranking.setMinWidth(900);
		
		//Pane fuer optische Gruppierung der Label (="Streifen")
		AnchorPane gestreift = new AnchorPane();
		Region[] streifen = new Region[labels.size()];
		//Erstellen eines Streifens je Label, diese werden im streifen-Array gesammelt
		//und koennen im Folgenden darueber angesprochen werden
		for (int i = 0; i< streifen.length; i++) {
			streifen[i]=new Region();
			streifen[i].setPrefWidth(850.0);
			streifen[i].setPrefHeight(16.0);
			streifen[i].setStyle("-fx-background-color: gainsboro;");
			AnchorPane.setLeftAnchor(streifen[i], 0.0);
			AnchorPane.setTopAnchor(streifen[i], i*(17.0+4.0));
			gestreift.getChildren().add(streifen[i]);
			System.out.println(streifen[i].getHeight());
		}
		//hinzufuegen von teil3Ranking zum AnchorPane gestreift
		AnchorPane.setTopAnchor(teil3Ranking, 0.0);
		AnchorPane.setLeftAnchor(teil3Ranking, 0.0);
		teil3Ranking.setPadding(new Insets(0.0,5.0,0.0,5.0));
		gestreift.getChildren().add(teil3Ranking);
		
		
		//Teil 4 - Absenden und Schriftgroeße
		//Initialisierung der Label und Buttons
		schriftgroesse = new HBox();
		schrift = new Label("Schriftgröße");
		schrift.setFont(Font.font("Tahoma"));
		HBox.setHgrow(schrift, Priority.ALWAYS);
		schriftplus = new Button("+");
		schriftplus.setFont(Font.font("Tahoma"));
		HBox.setHgrow(schriftplus, Priority.ALWAYS);
		schriftminus = new Button("-");
		schriftminus.setFont(Font.font("Tahoma"));
		HBox.setHgrow(schriftminus, Priority.ALWAYS);
		schriftplus.setPrefWidth(28);
		schriftplus.setMinWidth(28);
		schriftminus.setPrefWidth(28);
		schriftminus.setMinWidth(28);
		
		//Spacer fuer Textgroessen-Buttons
		Region hboxspaceri = new Region();
		hboxspaceri.setPrefWidth(5);
		HBox.setHgrow(hboxspaceri, Priority.ALWAYS);
		Region hboxspacerj = new Region();
		hboxspacerj.setPrefWidth(2);
		HBox.setHgrow(hboxspacerj, Priority.ALWAYS);
		schriftgroesse.getChildren().addAll(schrift, hboxspaceri, schriftplus, hboxspacerj, schriftminus);
		schriftgroesse.setMinWidth(135);
		HBox.setHgrow(schriftgroesse, Priority.ALWAYS);
		
		//Initialisierung des Absenden-Buttons und des Fehlermeldung-Labels
		labelabsenden = new Button("Absenden");
		labelabsenden.setFont(Font.font("Tahoma"));
		labelabsenden.setMinWidth(77);
		HBox.setHgrow(labelabsenden, Priority.ALWAYS);
		fehlermeldungHaupt = new Label("Bitte prüfen Sie, dass Sie jedes Label zugeordnet haben!");
		fehlermeldungHaupt.setFont(Font.font("Tahoma"));
		fehlermeldungHaupt.setVisible(false);
		HBox.setHgrow(fehlermeldungHaupt, Priority.ALWAYS);
		fehlermeldungHaupt.setMinWidth(343.0);
		
		//Zuordnung aller Child-Nodes zur HBox
		teil4GroesseAbsenden.getChildren().addAll(schriftgroesse,fehlermeldungHaupt,labelabsenden);	
		teil4GroesseAbsenden.setPrefWidth(850);
		teil4GroesseAbsenden.setMinWidth(850);teil4GroesseAbsenden.setMaxWidth(850);
		
		//Layouteinstellungen fuer das klasspane
		klasspane.setPrefWidth(900);
		klasspane.setMinWidth(900);
		klasspane.getChildren().addAll(teil1Daten, teil2Textarea, gestreift, teil4GroesseAbsenden);
		klasspane.setPadding(new Insets(30,40,50,40));
		klasspane.setSpacing(25);
		
		//erstellen einer Scene, die das klasspane beinhaltet
		Scene klassi = new Scene(klasspane,x,y);
		//Rueckgabe dieser Scene
		return klassi;
	}
	
	/**
	 * Diese Funktion prueft, ob zu jedem angezeigten Label (min.) 1 Moeglichkeit 
	 * ausgewaehlt wurde und gibt entsprechend true oder false zurueck.
	 */
	public boolean check() {
		//Rueckgabevariable
		boolean check = true;
		//Iteration durch alle Label
		for (int i = 0; i<labelarray.size();i++) {
			switch (i) {
				case 0:
					if (radioodercheck[i]=='c') {
						//bei CheckBoxen wird durchgezaehlt wie viele Boxen ausgewaehlt wurden
						boolz0 = 0;
						for (int j = 0; j<cbs0.length; j++) {
							if(cbs0[j].isSelected()) {
								boolz0++;
							}	
						}
						//wenn das Zaehlergebnis 0 ist, wird check auf false gesetzt
						if(boolz0==0) {
							check=false;
							break;
						}
					}
					else {
						//hier wird nur getestet, dass nicht mehr der "Hilfs-RadioButton" gewaehlt ist
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
		//Rueckgabe von check
		return check;
	}
	
	/**
	 * Diese Methode schliesst das aktuelle Fenster und oeffnet eine ZeitEndeStage, also das naechste Fenster.
	 */
	public void timesup() {
		close();
		new ZeitEndeStage(fortschritt.getProgress());
	}

}