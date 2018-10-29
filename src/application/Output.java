package application;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.*;

import org.w3c.dom.*;

/**
 * Dies wird die Klasse Output.
 * Sie ist zustaendig fuer alle Speichervorgaenge des Programms.
 *  - speichern()	 : Speichert Klassifikator.ergebnisse in Form einer XML-Datei.
 *  - abbruchSave()	 : Diese Methode speichert Klassifikator.textIDs, sowie das Klassifikator.texte[]
 *  					und Klassifikator.label[]. Ausserdem ruft sie speichern() auf, sodass auch 
 *  					Klassifikator.ergebnisse gespeichert wird.
 * @author becksusanna
 * @version 0.1
 */
public class Output {
	
	public void speichern(HashMap<Integer,boolean[]> erg, String[] texte) {
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			
			//ausgabe ist Wurzelelement
			Element ausgabe = doc.createElement("ausgabe");
			 for (int i=0; i<erg.size();i++) {
				 Element text = doc.createElement("text"+i);
				 text.appendChild(ausgabe);
				 Element inhalt = doc.createElement("inhalt");
				 inhalt.appendChild(text);
				 Text inhalt2 = doc.createTextNode(texte[i]);
				 inhalt2.appendChild(inhalt);
				 
				 
			 }
			
			
			
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	
	public void abbruchSave(ArrayList<Integer> id, String[] texte, String[] label, HashMap<Integer,boolean[]> erg) {
		
	}

}
