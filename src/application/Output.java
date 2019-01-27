package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.bioforscher.fosil.dataformatter.AnnotationItem;
import de.bioforscher.fosil.dataformatter.DataEntity;
import de.bioforscher.fosil.dataformatter.Label;
import de.bioforscher.fosil.dataformatter.TextEntity;

/**
 * Die Klasse ist für das Speichern der Ergebnisse verantortlich.
 * @author Laura
 *
 */
public class Output {
	
	/**
	 * Die Ergebnisse werden in eine XML-Datei gespeichert, welche die Nutzer ID als Namen traegt.
	 * Dabei wird geprueft, ob bereits eine Datei mit dieser ID vorhanden ist. Wenn vorhanden, wird
	 * die XML eingelesen und mit den hinzugekommenen Werten neu geschrieben.
	 */
	public static void schreibeWerte(Klassifikator klassif, String text, ArrayList<String> ergebnis) {
		
		try{
		    	
		    File file = Input3.pfadNachOS(klassif.getNutzerID()+".xml", "Ausgabe");
		    
		    //wenn File mit RaterID noch nicht vorhanden
		    if (file.exists()==false) {
		    	
			    JAXBContext jContextneu = JAXBContext.newInstance(DataEntity.class);
			    Marshaller marshallObj = jContextneu.createMarshaller();
			    marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			    DataEntity dataEntity= new DataEntity();
			    List<TextEntity> textlst=new ArrayList<TextEntity>();
			    TextEntity textEntityneu= new TextEntity();
			    
				textEntityneu.setRaterID(klassif.getNutzerID());
				textEntityneu.setText(text);
				
				String[][] texte=klassif.getTexte();
				String id="";
				int length=texte.length;
				
				//TextID auslesen
				for (int i = 0; i <length; i++) {
					String text1=texte[i][1];
				     if (text1.substring(5).equals(text)) {
				    	 id=String.valueOf(i);
				     }
				}
				textEntityneu.setTextID(id);
				
				
				HashMap<String,ArrayList<String>> labelmap =klassif.getLabel();
				String[] labelname=new String[labelmap.size()];
				int[] anzButton=new int[labelmap.size()];
				int k=0;
				
				//Bezeichnung der Label in Liste schreiben und wie viele Buttons Label hat (fuer spaetere Auswertung)
				for(String key : labelmap.keySet()) {
					labelname[k]=key;
					anzButton[k]=labelmap.get(key).size()+1;
					k++;
				}
				
				List<AnnotationItem> annolst=new ArrayList<AnnotationItem>();
				
				//fuer jedes Label annoItem und Label erstellen
				for (int i=0; i<klassif.getLabel().size();i++) {
					AnnotationItem annoItem=new AnnotationItem();
					Label label=new Label();
					label.setName(labelname[i]);
					int anzahl= anzButton[i];
					
					//ergebnis auswerten und in Label schreiben
					String erg=ergebnis.get(i);
					
					//auswerten(String,String, int) aufrufen, wenn 100 zurueckgegeben wird term schreiben
					int wert=auswerten(erg,anzahl);
					if (wert!=100) {
						BigInteger bi=BigInteger.valueOf(wert);
						label.setClassRating(bi);
					}
					else {
						label.setClassTerms(erg);;
					}
						
					annoItem.setLabel(label);
					annolst.add(annoItem);
					}
				
				textEntityneu.setAnnolst(annolst);
				
				textlst.add(textEntityneu);
				
				dataEntity.setTextlst(textlst);
				
				 OutputStream os = new FileOutputStream(Input3.pfadNachOS(klassif.getNutzerID()+".xml", "Ausgabe"));
			     marshallObj.marshal(dataEntity, os );
			     os.close();
		    }
		
		   //wenn File mit RaterID bereits vorhanden
		    else {
			   		   
			    JAXBContext jContextneu = JAXBContext.newInstance(DataEntity.class);
			    
			    Unmarshaller unmarshObj = jContextneu.createUnmarshaller();
			    DataEntity dataEntityalt= (DataEntity) unmarshObj.unmarshal(file);
			    List<TextEntity> textlst=new ArrayList<TextEntity>();
			    
			    //bereits vorhandene TextEntities werden eingelesen und in Liste geschrieben
			    for(TextEntity textEntity: dataEntityalt.getTextlst()) {
			    	textlst.add(textEntity);
			    }
			    
			    Marshaller marshallObj = jContextneu.createMarshaller();
			    marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			    DataEntity dataEntity= new DataEntity();
			    
			    //neue TextEntity wird erzeugt, Werte zugewiesen und zu Liste hinzugefügt
			    TextEntity textEntityneu= new TextEntity();
			    
				textEntityneu.setRaterID(klassif.getNutzerID());
				textEntityneu.setText(text);
				
				String[][] texte=klassif.getTexte();
				String id="";
				int length=texte.length;
				
				//TextID auslesen
				for (int i = 0; i <length; i++) {
					String text1=texte[i][1];
				     if (text1.substring(5).equals(text)) {
				    	 id=String.valueOf(i);
				     }
				}
				textEntityneu.setTextID(id);
				
				
				HashMap<String,ArrayList<String>> labelmap =klassif.getLabel();
				String[] labelname=new String[labelmap.size()];
				int[] anzButton=new int[labelmap.size()];
				int k=0;
				
				//Bezeichnung der Label in Liste schreiben und wie viele Buttons Label hat(fuer spaetere Auswertung)
				for(String key : labelmap.keySet()) {
					labelname[k]=key;
					anzButton[k]=labelmap.get(key).size()+1;
					k++;
				}
				
				List<AnnotationItem> annolst=new ArrayList<AnnotationItem>();
				
				//fuer jedes Label annoItem und Label erstellen
				for (int i=0; i<klassif.getLabel().size();i++) {
					AnnotationItem annoItem=new AnnotationItem();
					Label label=new Label();
					label.setName(labelname[i]);
					int anzahl= anzButton[i];
					
					//ergebnis auswerten und in Label schreiben
					String erg=ergebnis.get(i);
					
					//auswerten(String,String, int) aufrufen, wenn 100 zurueckgegeben wird term schreiben, ansonsten repraesentative Zahl
					int wert=auswerten(erg,anzahl);
					if (wert!=100) {
						BigInteger bi=BigInteger.valueOf(wert);
						label.setClassRating(bi);
					}
					else {
						label.setClassTerms(erg);;
					}
						
					annoItem.setLabel(label);
					annolst.add(annoItem);
					}
				textEntityneu.setAnnolst(annolst);
				
				textlst.add(textEntityneu);
				
				dataEntity.setTextlst(textlst);
				
				 OutputStream os = new FileOutputStream(Input3.pfadNachOS(klassif.getNutzerID()+".xml", "Ausgabe"));
			     marshallObj.marshal(dataEntity, os );
			     os.close();
		    }
		    
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Die Methode wertet die Ergenisse aus.
	 * Wenn keines zutrifft wird 100 zurueckgegeben.
	 */
	public static int auswerten(String erg, int anzahl) {
		int wert=100;
		if (erg.equals("Trifft nicht zu")) {
			wert=-2;
		}
		else if(erg.equals("Trifft eher nicht zu")) {
			wert=-1;
		}
		else if(erg.equals("Trifft teilweise zu")) {
			wert=1;
		}
		else if(erg.equals("Trifft zu")) {
			wert=2;
		}
		else if(erg.equals("Nein")) {
			wert=-2;
		}
		else if(erg.equals("Ja")) {
			wert=2;
		}
		else if(erg.equals("Ich weiss nicht")) {
			wert=0;
		}
		
		return wert;
	}

	
}
