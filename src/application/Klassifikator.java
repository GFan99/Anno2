package application;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import javafx.scene.text.Text;


/**
 * Dies wird die Klasse Klassifikator.
 * Sie ist der Mittelpunkt des gesamten Programms, denn in ihr liegen die Variablen, Methoden und
 * Funktionen, die von allen anderen Klassen benutzt werden. So gibt es hier:
 *  - label[]     : Das Array mit den Labels, nach denen die Texte "sortiert" werden.
 *  - texte[]      : Das Array mit den zu klassifizierenden Texten.
 *  - textids	   : Eine ArrayList, die die IDs der Texte speichert (ID=1 heisst (Index in texte[])=1).
 *  - ergebnisse   : ergebnisse ist eine Map, die als Key die jeweilige textid (Int) und als Value ein 
 *  				  boolean[] mit den fuer die Labels ausgewaehlten Werte hat.
 *  - waehleText() : Eine Funktion die aus textids zufaellig ein Element auswaehlt, dieses aus textids
 *  				  entfernt und dann zurueck gibt. Ist textids leer, so wird ein "Fehler-Wert"
 *  				  uebergeben (bspw. -1).
 *  - schrMap()    : Eine Methode, die eine TextID (Int) und ein boolean[] uebergeben bekommt und diese
 *  				  dann als Schluessel-Wert-Paar in ergebnisse eintraegt.
 *  - sortieren()  : Eine Funktion, die in der Lage ist, die uebergebene Map aufsteigend nach dem Key 
 *  				  zu sortieren und dann die sortierte Map zurueck gibt.
 * @author becksusanna
 * @version 0.1
 */
public class Klassifikator {
	
	private LinkedHashMap<String,ArrayList<String>> label;
	protected String[][] texte;
	public ArrayList<Integer> textids;
	private LinkedHashMap<Integer,boolean[]> ergebnisse;
	int idgroesse;							
	private String nutzerID;
	
	/**
	 * Ein leerer Konstruktor...
	 * @param 
	 * @return Klassifikator
	 */
	public Klassifikator() {
		this.label = new LinkedHashMap<String,ArrayList<String>>();
		this.texte = new String[0][0];
		this.textids = new ArrayList<Integer>();
		this.ergebnisse = new LinkedHashMap<Integer,boolean[]>();
		this.idgroesse = 0;
		this.setNutzerID("");
	}
	
	/**
	 * Ein Konstruktor mit Parametern.
	 * Es werden ein Array mit den Labeln und ein Array mit den Texten übergeben. Der Konstruktor
	 * erstellt gleich textids passend, so dass dort die Anzahl der Elemente/IDs der Anzahl der 
	 * Texte in texte entspricht.
	 * @param String[] label, String[] texte
	 * @return Klassifikator
	 */
	public Klassifikator(String nutzerID, LinkedHashMap<String,ArrayList<String>> label, String[][] texte) {
		this.label = label;
		this.texte = texte;
		this.textids = new ArrayList<Integer>();
		for (int i = 0; i<texte.length;i++) {
			textids.add(i);
		}
		this.idgroesse = texte.length;
		for (int i =0;i<idgroesse;i++) {
			textids.add(i);
		}	
		this.ergebnisse = new LinkedHashMap<Integer,boolean[]>();
		this.nutzerID = nutzerID;
	}
	
	/**
	 * Dies ist eine Funktion.
	 * @return
	 */
	public Integer waehleText() {
		if (textids.size()!=0) {
			int zahl = (int)((Math.random())*idgroesse);
			try {
				textids.remove(textids.indexOf(zahl));
			}
			catch (Exception e) {
				zahl = waehleText();
			}
			return zahl;
		}
		else return -1;
	}
	
	public void schrMap(Integer id, boolean[] werte) {
		this.ergebnisse.put(id, werte);
	}
	
	public LinkedHashMap<Integer,boolean[]> sortieren(LinkedHashMap<Integer,boolean[]> map) {
		LinkedHashMap<Integer,boolean[]> sort = new LinkedHashMap<Integer,boolean[]>();
		for (int i = 0; i<map.size();i++) {
			sort.put(i, map.get(i));
		}
		return sort;
	}
	
	public String[] getText() {
		int i = this.waehleText();
		if (i!=-1) {
			String[] rueck = new String[2];
			rueck[0]=""+i+"";
			rueck[1]=texte[i][1];
			return rueck;
		}
		else {
			String[] rueck2 = {"",""};
			return rueck2;
		}
		
	}
	
	public static String generiereNutzer() {
		final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder builder = new StringBuilder();
		int count = 16;
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
		//datenbank erstllen, abgleichen, neu generierenx
	}
	
	public String[][] getTexte() {
		return this.texte;
	}

	/**
	 * @return the nutzerID
	 */
	public String getNutzerID() {
		return nutzerID;
	}

	/**
	 * @param nutzerID the nutzerID to set
	 */
	public void setNutzerID(String nutzerID) {
		this.nutzerID = nutzerID;
	}
	
	public LinkedHashMap<String,ArrayList<String>> getLabel() {
		return label;
	}
}
