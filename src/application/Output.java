package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import de.bioforscher.fosil.dataformatter.AnnotationItem;
import de.bioforscher.fosil.dataformatter.DataEntity;
import de.bioforscher.fosil.dataformatter.Label;

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
	
	/**
	 *DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();
	Document document = builder.parse(XMLFILENAME); 
	 *
	 */
	
	public void speichern(HashMap<Integer,boolean[]> erg, String[] texte) {
		
		
		
		
				 
				 
			
			
			
			
			
		
		
	}
	
	public void abbruchSave(ArrayList<Integer> id, String[] texte, String[] label, HashMap<Integer,boolean[]> erg) {
		
	}

	public static void abbruchSave(Klassifikator klassif) {
		
	}

	public static void schreibeWerte(Klassifikator klassif, String text, ArrayList<String> ergebnis) {
		
		try{
		    
			//getting the xml file to read
		    File file = Input3.pfadNachOS(klassif.getNutzerID()+".xml", "Ausgabe");
		    
		    //if (file.exists()==false) {
		    	//creating the JAXB context
			    JAXBContext jContextneu = JAXBContext.newInstance(DataEntity.class);
			    //creating the marshaller object
			    Marshaller marshallObj = jContextneu.createMarshaller();
			    //setting the property to show xml format output
			    marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			    //setting the values in POJO class
			    DataEntity dataEntityneu= new DataEntity();
				dataEntityneu.setRaterID(klassif.getNutzerID());
				dataEntityneu.setText(text);
				
				String[][] texte=klassif.getTexte();
				String id="";
				int length=texte.length;
				
				//TextID auslesen
				for (int i = 0; i <length; i++) {
					String text1=texte[i][1];
				     if (text1.equals(text)) {
				    	 id=String.valueOf(i);
				     }
				}
				dataEntityneu.setTextID(id);
				
				
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
				
				AnnotationItem[] items = new AnnotationItem[klassif.getLabel().size()];
				
				//für jedes Label annoItem und Label erstellen
				for (int i=0; i<klassif.getLabel().size();i++) {
					AnnotationItem annoItem=new AnnotationItem();
					Label label=new Label();
					label.setName(labelname[i]);
					int anzahl= anzButton[i];
					
					//ergebnis auswerten und in Label schreiben
					String erg=ergebnis.get(i);
					
					//auswerten(String,String, int) aufrufen, wenn 100 zurückgegeben wird term schreiben
					int wert=auswerten(erg,anzahl);
					if (wert!=100) {
						BigInteger bi=BigInteger.valueOf(wert);
						label.setClassRating(bi);
					}
					else {
						label.setClassTerms(erg);;
					}
						
					annoItem.setLabel(label);
					dataEntityneu.setAnnotations(annoItem);
					}
					
				
			    //calling the marshall method
				 OutputStream os = new FileOutputStream(Input3.pfadNachOS(klassif.getNutzerID()+".xml", "Ausgabe"));
			     marshallObj.marshal(dataEntityneu, os );
			     os.close();
		
		   /** else {
		    	//creating the JAXB context
			    JAXBContext jContext = JAXBContext.newInstance(DataEntity.class);
			    //creating the unmarshall object
			    Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
			    //calling the unmarshall method
			    DataEntity dataEntity=(DataEntity) unmarshallerObj.unmarshal(file);

				
			    //creating the JAXB context
			    JAXBContext jContextneu = JAXBContext.newInstance(DataEntity.class);
			    //creating the marshaller object
			    Marshaller marshallObj = jContextneu.createMarshaller();
			    //setting the property to show xml format output
			    marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			    //setting the values in POJO class
			    DataEntity dataEntityneu= new DataEntity();
				dataEntityneu.setRaterID(klassif.getNutzerID());
				dataEntityneu.setText(value);
				dataEntityneu.setTextID(value);
				dataEntityneu.setAnnotations(value);
			    //calling the marshall method
			    marshallObj.marshal(dataEntityneu, new FileOutputStream(Input3.pfadNachOS(klassif.getNutzerID()+".xml", "Ausgabe").getAbsolutePath()));
		    }**/
		    
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	//auswerten; wenn keines zutrifft 100 zurückgeben
	public static int auswerten(String erg, int anzahl) {
		int wert=100;
		if (erg.equals("Trifft nicht zu") && anzahl==5 ) {
			wert=-2;
		}
		else if(erg.equals("Trifft eher nicht zu")) {
			wert=-1;
		}
		else if(erg.equals("Trifft teilweise zu")) {
			wert=1;
		}
		else if(erg.equals("Trifft zu") && anzahl==5) {
			wert=2;
		}
		else if(erg.equals("Trifft nicht zu") && anzahl==3) {
			wert=-1;
		}
		else if(erg.equals("Trifft zu") && anzahl==3) {
			wert=1;
		}
		else if(erg.equals("nein")) {
			wert=-1;
		}
		else if(erg.equals("ja")) {
			wert=1;
		}
		else if(erg.equals("Ich weiss nicht")) {
			wert=0;
		}
		
		return wert;
	}
	

	public static void schreibexml() {
		
	}
}
