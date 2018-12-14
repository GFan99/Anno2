package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Input3 {
	
	private static ArrayList<Labelobjekt> labelobjekte= new ArrayList<>();
	
	/**
	 * Eine Methode, um den Dateipfad zurückzugeben in Abhängigkeit vom Betriebssystem
	 * @param dateiname
	 * @param ordnername
	 * @return
	 */
	public static File pfadNachOS(String dateiname, String ordnername) {
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") != -1) {
			String pfad = System.getProperty("user.dir");//user.dir ist workspace
			if (dateiname!="") {
				dateiname="//"+dateiname;
			}
			return new File(pfad+"//"+ordnername+dateiname);
		}
		else {
			if (dateiname!="") {
				dateiname="/"+dateiname;
			}
			return new File("./"+ordnername+dateiname);
		}
			
		}
	
	/**
	 * Eine Methode, um die Eigenschaft der Labels aus der XML-Datei auszulesen und
	 * als boolean[] zurückzugeben.
	 * (Dürfen mehrere Möglichkeiten angeklickt werden?)
	 * @param
	 * @return String[]
	 */
	public static boolean[] labelEigenschaft(){
		try {
					
			boolean[] eigenschaften=new boolean[labelobjekte.size()];
			
			for(int i=0; i<labelobjekte.size();i++) {
				Labelobjekt objekt=labelobjekte.get(i);
				eigenschaften[i]=objekt.isEigenschaft();
			}
						
			//die Namen der Labels werden nacheinander in das String[]-Array geschrieben
		
			System.out.println("eigenschaften");
			return eigenschaften;
		} catch (Exception e) {
			e.printStackTrace();
			boolean[] leer=new boolean[0];
			return leer;
		}
		
	}
	
	
	/**
	 * Eine Metho//lalalade, um die Label und deren Beschriftung aus der XML-Datei auszulesen 
	 * und als HashMap zurückzugeben
	 * @return
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
					beschriftung.add("Ich weiss nicht");
					beschriftung.add("Trifft teilweise zu");
					beschriftung.add("Trifft zu");
				}
				else if (bezeichnung.equals("3Rating zutreffen")) {
								
					beschriftung.add("Trifft nicht zu");
					beschriftung.add("Ich weiss nicht");
					beschriftung.add("Trifft zu");
					}
				else if (bezeichnung.equals("3Rating ja")){
					beschriftung.add("Nein");
					beschriftung.add("Ich weiss nicht");
					beschriftung.add("Ja"); 
					}
				else {
					beschriftung=new ArrayList<String>(Arrays.asList(bezeichnung.split(";")));
						}
			    label.put(key, beschriftung);
			    eigenschaften[i]=Boolean.valueOf(eElement.getAttributeNode("multiple").getValue());
			    //Labelobjekt objekt=new Labelobjekt(key,beschriftung,eigenschaften[i]);
				//labelobjekte.add(objekt);
				beschriftung = new ArrayList<String>();
			    
					}
			    
			   	}
			
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
	 * Eine Methode, um den Ordner mit den Texten einzulesen und als String[]-Array zurueckzugeben.
	 * @param
	 * @return String[]
	 */
	public static String[][] texteLesen(){
		try {
			String dateiname="";
			String ordnername="Texte";
			System.out.println(pfadNachOS(dateiname,ordnername));
			File[] dateien =pfadNachOS(dateiname,ordnername).listFiles();
			String[][] texte= new String[dateien.length][2];
			
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
	 * zurückzugeben, indem der Dateiname der bereits vorhanden XML-Dateien ausgelesen wird.
	 * @return
	 */
	public static String[] vorhandeneIDs() {
		String dateiname="";
		String ordnername="Ausgabe";
		File[] dateien =pfadNachOS(dateiname,ordnername).listFiles();
		String[] vorhandeneIDs =new String[dateien.length];
		for(int i=0; i<dateien.length; i++) {
			String name=dateien[i].getName();
			vorhandeneIDs[i]=name.replace(".xml","");
		}
		return vorhandeneIDs;
	}
	
	
	public static ArrayList<Integer> leseklassifizierte(String id) {
		try {
			
			String dateiname=id+".xml";
			String ordnername="Ausgabe";
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(pfadNachOS(dateiname, ordnername));
			NodeList nList = doc.getElementsByTagName("element");
			
			ArrayList<Integer> vorhandeneTexte = new ArrayList<Integer>();
			
			//die Namen der Labels werden nacheinander in das String[]-Array geschrieben
			for (int i = 0; i < nList.getLength(); i++)
			{
			 Node node = nList.item(i);
			 
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element eElement = (Element) node;
			    String inhalt=eElement.getElementsByTagName("textID").item(0).getTextContent();
			    vorhandeneTexte.add(Integer.parseInt(inhalt));
			 }
			}
			 
			 return vorhandeneTexte;
			 
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ArrayList<Integer> leer = new ArrayList<Integer>();;
				return leer;
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ArrayList<Integer> leer = new ArrayList<Integer>();
				return leer;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ArrayList<Integer> leer = new ArrayList<Integer>();
				return leer;
			}
	}
}
