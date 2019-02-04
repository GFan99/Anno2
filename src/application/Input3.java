package application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.bioforscher.fosil.dataformatter.DataEntity;
import de.bioforscher.fosil.dataformatter.TextEntity;

/**
 * Die Klasse dient zum Einlesen der Texte, der vorhandenen Nutzer IDs und den Labeln.
 * Zudem werden die Text IDs der bereits gelabelten Texte eingelesen. 
 * Aus der Property Datei werden der Pfad und die vorgegebene Uhrzeit ausgelesen.
 * @author Laura
 *
 */

public class Input3 {
	
	private static ArrayList<Labelobjekt> labelobjekte= new ArrayList<>();
	
	/**
	 * Eine Methode, um den Dateipfad zurueckzugeben in Abhaengigkeit vom Betriebssystem.
	 */
	public static File pfadNachOS(String dateiname, String ordnername) {
		
		try {
			Properties properties = new Properties();
			BufferedInputStream stream;
			
			//Trennzeichen je nach Betriebssystem
			String i=System.getProperty("file.separator");
			
			//Property-File einlesen
			InputStream fstream = Input3.class.getClassLoader().getResourceAsStream("anno.properties");
			stream = new BufferedInputStream(fstream);
			properties.load(stream);
			stream.close();
			fstream.close();
			
			//Pfad auslesen und ggf. Dateiname anhaengen
			String pfad = properties.getProperty("pfad");
			if (dateiname!="") {
				dateiname=i+dateiname;
			}
			
			return new File(pfad+i+ordnername+dateiname);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new File("");
		} catch (IOException e) {
			e.printStackTrace();
			return new File("");
		}
		
			
		}
	
	/**
	 * Eine Methode, um die Eigenschaft der Labels aus den Labelobjekten auszulesen und
	 * als boolean[] zurueckzugeben.
	 * (Duerfen mehrere Moeglichkeiten angeklickt werden?)
	 */
	public static boolean[] labelEigenschaft(){
		try {
					
			boolean[] eigenschaften=new boolean[labelobjekte.size()];
			
			//labelobjekte auslesen und in Array schreiben
			for(int i=0; i<labelobjekte.size();i++) {
				Labelobjekt objekt=labelobjekte.get(i);
				eigenschaften[i]=objekt.isEigenschaft();
			}
		
			return eigenschaften;
		} catch (Exception e) {
			e.printStackTrace();
			boolean[] leer=new boolean[0];
			return leer;
		}
		
	}
	
	
	/**
	 * Eine Methode, um die Label und deren Beschriftung aus der XML-Datei auszulesen 
	 * und als LinkedHashMap<String,ArrayList<String>> zurueckzugeben.
	 * Zudem wird labelobjekte, welche in labelEigenschaft() verwendet wird, gefuellt.
	 */
	public static LinkedHashMap<String,ArrayList<String>> labelLesen(){
		try {
			
			String dateiname ="labels.xml";
			String ordnername = "Eingabe";
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(pfadNachOS(dateiname, ordnername));
			NodeList nList = doc.getElementsByTagName("label");
			
			LinkedHashMap<String,ArrayList<String>> label = new LinkedHashMap<>();
			ArrayList<String> beschriftung=new ArrayList<>();
			boolean[] eigenschaften=new boolean[nList.getLength()];

			
			//die Namen der Labels und deren Beschriftung werden nacheinander in das String[]-Array geschrieben
			for (int i = 0; i < nList.getLength(); i++)
			{
			 Node node = nList.item(i);
			 
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element eElement = (Element) node;
			    String key=eElement.getAttribute("name");
			    Attr attribute=eElement.getAttributeNode("beschreibung");
			    String bezeichnung = attribute.getValue();
			    if (bezeichnung.equals("5Rating")) {
					beschriftung.add("Trifft nicht zu");
					beschriftung.add("Trifft eher nicht zu");
					beschriftung.add("Ich weiß nicht");
					beschriftung.add("Trifft teilweise zu");
					beschriftung.add("Trifft zu");
				}
				else if (bezeichnung.equals("3Rating zutreffen")) {
								
					beschriftung.add("Trifft nicht zu");
					beschriftung.add("Ich weiß nicht");
					beschriftung.add("Trifft zu");
					}
				else if (bezeichnung.equals("3Rating ja")){
					beschriftung.add("Nein");
					beschriftung.add("Ich weiß nicht");
					beschriftung.add("Ja"); 
					}
				else {
					beschriftung=new ArrayList<String>(Arrays.asList(bezeichnung.split(";")));
						}
			    label.put(key, beschriftung);
			    eigenschaften[i]=Boolean.valueOf(eElement.getAttributeNode("multiple").getValue());
				beschriftung = new ArrayList<String>();
			    
					}
			    
			   	}
			
			//labelobjekte fuellen
			int i=0;
			Set keys = label.keySet();
			Iterator it = keys.iterator();
			while(it.hasNext()) {
				Object key = it.next();
				Labelobjekt objekt=new Labelobjekt((String) key,label.get(key),eigenschaften[i]);
				labelobjekte.add(objekt);
				i++;
			}
			
			 
					  
			return label;
		 } catch (Exception e) {
			e.printStackTrace();
			LinkedHashMap<String,ArrayList<String>> leer=new LinkedHashMap<String,ArrayList<String>>();
			return leer;
		 }
		
	}
	
	/**
	 * Eine Methode, um den Ordner mit den Texten einzulesen und als String[][] zurueckzugeben.
	 */
	public static String[][] texteLesen(){
		try {
			String dateiname="";
			String ordnername="Texte";
			File[] dateien =pfadNachOS(dateiname,ordnername).listFiles();
			String[][] texte= new String[dateien.length][2];
			
			//Textdateien auslesen und in zweisimensionales Array mit TextID schreiben
			for (int i=0; i<dateien.length; i++) {
				BufferedReader br = new BufferedReader(new FileReader(dateien[i]));
				String line = br.readLine();
				while (line != null) {
					texte[i][1]=texte[i][1]+"\n"+line;
					texte[i][0]=String.valueOf(i);
					line = br.readLine();
					}
				br.close();
			}

			return texte;
		 } catch (Exception e) {
			e.printStackTrace();
			String[][] leer=new String[0][0];
			return leer;
		 }
		
	}
	
	
	/**
	 * Eine Methode, welche ein String-Array mit allen bereits vorhandenen Nutzer-IDs
	 * zurueckzugeben, indem der Dateiname der bereits vorhanden XML-Dateien ausgelesen wird.
	 */
	public static String[] vorhandeneIDs() {
		String dateiname="";
		String ordnername="Ausgabe";
		File[] dateien =pfadNachOS(dateiname,ordnername).listFiles();
		String[] vorhandeneIDs =new String[dateien.length];
		
		//Namen der vorhandenen Dateien einlesen, .xml abschneiden und in Array schreiben
		for(int i=0; i<dateien.length; i++) {
			String name=dateien[i].getName();
			vorhandeneIDs[i]=name.replace(".xml","");
		}
		return vorhandeneIDs;
	}
	
	/**
	 * Es werden die bereits gelabelelten Texte gelesen und von diesen die Text ID
	 * als ArrayList<Integer> zurueckgegeben.
	 */
	public static ArrayList<Integer> leseklassifizierte(String id) {
		try {
			
			String dateiname=id+".xml";
			String ordnername="Ausgabe";
			
			JAXBContext jContextneu = JAXBContext.newInstance(DataEntity.class);
		    
		    Unmarshaller unmarshObj = jContextneu.createUnmarshaller();
		    DataEntity dataEntityalt= (DataEntity) unmarshObj.unmarshal(pfadNachOS(dateiname, ordnername));
		    ArrayList<Integer> vorhandeneTexte=new ArrayList<Integer>();
		    
		    //fuer alle vorhandenen TextEntities die TextID auslesen und in Liste schreiben
		    for(TextEntity textEntity: dataEntityalt.getTextlst()) {
		    	String textid=textEntity.getTextID();
		    	vorhandeneTexte.add(Integer.valueOf(textid));
		    }
			 return vorhandeneTexte;
			
			} catch (JAXBException e) {
				e.printStackTrace();
				ArrayList<Integer> leer = new ArrayList<Integer>();
				return leer;
			}
	}
	
	/**
	 * Die Methode liest die vorgegebene Zeit aus dem Property File
	 * und gibt diese zurueck.
	 */
	public static int getTime() {
		try {
			Properties properties = new Properties();
			BufferedInputStream stream;
			
			//Property-File einlesen
			InputStream fstream=Input3.class.getClassLoader().getResourceAsStream("anno.properties");
			stream = new BufferedInputStream(fstream);
			properties.load(stream);
			stream.close();
			fstream.close();
			
			//Zeit auslesen
			int zeit = Integer.parseInt(properties.getProperty("zeit"));
			
			return zeit;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}
