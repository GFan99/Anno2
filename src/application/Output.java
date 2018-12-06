package application;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import de.bioforscher.fosil.dataformatter.DataEntity;

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
		
		DataEntity dataEntity= new DataEntity();
		dataEntity.setRaterID(value);
		dataEntity.setText(value);
		dataEntity.setTextID(value);
		dataEntity.setAnnotations(value);
		
				 
				 
			
			
			
			
			
		
		
	}
	
	public void abbruchSave(ArrayList<Integer> id, String[] texte, String[] label, HashMap<Integer,boolean[]> erg) {
		
	}

	public static void abbruchSave(Klassifikator klassif) {
		// TODO Auto-generated method stub
		
	}

	public static void schreibeWerte(Klassifikator klassif, String text, ArrayList<String> ergebnis) {
		// TODO Auto-generated method stub
		
	}

}
