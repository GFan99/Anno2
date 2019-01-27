package application;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import javafx.scene.text.Text;


/**
 * Dies wird die Klasse Klassifikator.
 * Sie ist der Mittelpunkt des gesamten Programms, denn in ihr liegen die Variablen, Methoden und
 * Funktionen, die von allen anderen Klassen benutzt werden. So gibt es hier:
 *  - label    	   : Eine LinkedHashMap mit den Labels, nach denen die Texte klassifiziert werden.
 *  - texte        : Das Array mit den zu klassifizierenden Texten.
 *  - textids	   : Eine ArrayList, die die IDs der noch nicht klassifizierten Texte speichert.
 *  - nutzerID	   : Die ID des Nutzers.
 * @author becksusanna
 */
public class Klassifikator {
	
	private LinkedHashMap<String,ArrayList<String>> label;
	protected String[][] texte;
	public ArrayList<Integer> textids;
	private String nutzerID;
	
	/**
	 * Ein leerer Konstruktor.
	 */
	public Klassifikator() {
		this.label = new LinkedHashMap<String,ArrayList<String>>();
		this.texte = new String[0][0];
		this.textids = new ArrayList<Integer>();
		this.setNutzerID("");
	}
	
	/**
	 * Ein Konstruktor mit Parametern:
	 * Es werden ein Array mit den Labeln und ein Array mit den Texten übergeben. Der Konstruktor
	 * erstellt gleich textids passend, so dass dort die Anzahl der Elemente/IDs der Anzahl der 
	 * Texte in texte entspricht.
	 */
	public Klassifikator(String nutzerID, LinkedHashMap<String,ArrayList<String>> label, String[][] texte) {
		this.label = label;
		this.texte = texte;
		this.textids = new ArrayList<Integer>();
		for (int i =0;i<texte.length;i++) {
			textids.add(i);
		}	
		this.nutzerID = nutzerID;
	}
	
	/**
	 * Diese Funktion gibt ein String-Array aus einem Text (Index 1) und der zugehoerigen ID (Index 0) 
	 * zurueck. Gewaehlt werden dabei nur die Texte, die noch nicht klassifiziert wurden.
	 */
	public String[] getText() {
		int i = this.waehleText();
		//wenn waehleText() nicht den "Fehler-Wert" -1 zurueckgibt, so wird hier aus der gewaehlten
		//Text-ID und dem zugehoerigen Text ein Array gebaut 
		if (i!=-1) {
			String[] rueck = new String[2];
			rueck[0]=""+i+"";
			//Substring() wird angewendet, da die Texte in der Form in der sie von der Input geliefert
			//werden jeweils mit einem "null\n" beginnen und dies dem Nutzer nicht angezeigt werden soll
			rueck[1]=texte[i][1].substring(5);
			return rueck;
		}
		else {
			//sonst wird ein leeres Array erstellt und zurueckgegeben
			String[] rueck2 = {"",""};
			return rueck2;
		}
		
	}
	
	/**
	 * Diese Funktion waehlt zufaellig ein Element aus textids aus, entfernt es aus der ArrayList und 
	 * gibt es zurueck. Ist textids leer, so wird der "Fehler-Wert" -1 zurueckgegeben.
	 */
	public Integer waehleText() {
		if (textids.size()!=0) {
			int zahl = (int)((Math.random())*texte.length);
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
	
	/**
	 * Funktion, die einen 16-Zeichen-langen alphanumerischen String generiert und zurueckgibt.
	 */
	public static String generiereNutzer() {
		//String aller erlaubten Zeichen
		final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder builder = new StringBuilder();
		int laenge = 16;
		while (laenge-- != 0) {
			//solange laenge ungleich 0 ist, wird zufaellig ein Zeichen aus ALPHA_NUMERIC_STRING
			//ausgewaehlt und zum Builder hinzugefuegt
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	/**
	 *  getter- und setter-Methoden
	 */
	public String[][] getTexte() {
		return this.texte;
	}
	public String getNutzerID() {
		return nutzerID;
	}
	public void setNutzerID(String nutzerID) {
		this.nutzerID = nutzerID;
	}
	public LinkedHashMap<String,ArrayList<String>> getLabel() {
		return label;
	}
}
