package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
	
	/**
	 * Eine Methode, um den Dateipfad zur�ckzugeben in Abh�ngikeit von dem Betriebssystem
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
	 * als boolean[] zur�ckzugeben.
	 * (D�rfen mehrere M�glichkeiten angeklickt werden?)
	 * @param
	 * @return String[]
	 */
	public static boolean[] labelEigenschaft(){
		try {
			
			String dateiname ="labels.xml";
			String ordnername = "Eingabe";
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(pfadNachOS(dateiname, ordnername));
			NodeList nList = doc.getElementsByTagName("label");
			
			boolean[] eigenschaften=new boolean[nList.getLength()];
						
			//die Namen der Labels werden nacheinander in das String[]-Array geschrieben
			for (int i = 0; i < nList.getLength(); i++)
			{
			 Node node = nList.item(i);
			 
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element eElement = (Element) node;
			    eigenschaften[i]=Boolean.valueOf(eElement.getAttributeNode("multiple").getValue());
			 }
			}
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
	 * und als HashMap zur�ckzugeben
	 * @return
	 */
	public static HashMap<String,ArrayList<String>> labelLesen(){
		try {
			
			String dateiname ="labels.xml";
			String ordnername = "Eingabe";
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(pfadNachOS(dateiname, ordnername));
			NodeList nList = doc.getElementsByTagName("label");
			
			HashMap<String,ArrayList<String>> label = new HashMap<>();
			ArrayList<String> beschriftung=new ArrayList<>();

			
			//die Namen der Labels und deren Beschriftung werden nacheinander in das String[]-Array geschrieben
			for (int i = 0; i < nList.getLength(); i++)
			{
			 Node node = nList.item(i);
			 
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element eElement = (Element) node;
			    String key=eElement.getAttribute("name");
			    Attr attribute=eElement.getAttributeNode("beschreibung");
			    String bezeichnung = attribute.getValue();
			    System.out.println(bezeichnung=="5Rating");
			    if (bezeichnung=="5Rating") {
					beschriftung.add("Trifft nicht zu");
					beschriftung.add("Trifft eher nicht zu");
					beschriftung.add("Ich weiss nicht");
					beschriftung.add("Trifft teilweise zu");
					beschriftung.add("Trifft zu");
				}
				else if (bezeichnung=="3Rating zutreffen") {
					beschriftung.add("Trifft nicht zu");
					beschriftung.add("Ich weiss nicht");
					beschriftung.add("Trifft zu");
				}
				else if (bezeichnung=="3Rating ja") {
					beschriftung.add("Nein");
					beschriftung.add("Vielleicht");
					beschriftung.add("Ja"); 
				}
				else {
					beschriftung=new ArrayList<String>(Arrays.asList(bezeichnung.split(";")));
				}
			    label.put(key, beschriftung);
			 }
			}
					  
			return label;
		 } catch (Exception e) {
			e.printStackTrace();
			HashMap<String,ArrayList<String>> leer=new HashMap<String,ArrayList<String>>();
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
					texte[i][1]=texte[i]+line;
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
	 * zur�ckzugeben, indem der Dateiname der bereits vorhanden XML-Dateien ausgelesen wird.
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
	
	/**
	 * Eine Methode, die ein zweidimensionales String-Array zur�ckgibt mit den noch zu
	 * labelnden Texten in Abh�ngigkeit von der Nutzer-ID.
	 * @param id
	 * @return
	 */
	public static String[][] texteLesen(String id) {
		//texte Lesen aufrufen und aus dem Array vorhandene texte entfernen mittels ID
		
		try {
			String[][] alleTexte=Input2.texteLesen();
			
			String dateiname=id;
			String ordnername="Ausgabe";
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(pfadNachOS(dateiname, ordnername));
			NodeList nList = doc.getElementsByTagName("element");
			
			String[] vorhandeneTexte = new String[nList.getLength()];
			String[][] zuLabelndeTexte= new String[alleTexte.length-vorhandeneTexte.length][2];
			
			//die Namen der Labels werden nacheinander in das String[]-Array geschrieben
			for (int i = 0; i < nList.getLength(); i++)
			{
			 Node node = nList.item(i);
			 
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element eElement = (Element) node;
			    vorhandeneTexte[i]=eElement.getElementsByTagName("textID").item(0).getTextContent();
			 }
			}
			
			
			for(int i=0; i<alleTexte.length; i++) {
				//elgantere Moeglichkeit damit nur einmal geschriebne wird?
				boolean geschrieben=false;
				for(int j=0; j<vorhandeneTexte.length;j++) {
					String textid=vorhandeneTexte[j];
					if (textid!=alleTexte[i][0] && geschrieben==false){
						zuLabelndeTexte[i][0]=alleTexte[j][0];
						zuLabelndeTexte[i][1]=alleTexte[j][1];
						geschrieben=true;
					}
				}
			}
			
			return zuLabelndeTexte;
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String[][] leer=new String[0][0];
			return leer;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String[][] leer=new String[0][0];
			return leer;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String[][] leer=new String[0][0];
			return leer;
		}
		
	}
}
