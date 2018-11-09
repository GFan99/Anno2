package application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.*;

import org.w3c.dom.*;

/**
 * Dies wird die Klasse Input.
 * Input dient dazu die bereitgestellte XML-Datei zu lesen und daraus die zu bearbeitenden Texte
 * mit den gewuenschten Labeln abzuleiten.
 * Sie hat Zugriff auf die Klasse Klassifikator, damit sie dort in das label[] - Array die Namen
 * der Label und in das text[] - Array die zu bearbeitenden Texte eintragen kann.
 * Input ist die erste Klasse, die von Steuerung angesprochen wird, da sie fuer das Laden der Da-
 * ten zustaendig ist, mit denen das Programm dann arbeiten soll.
 * Zusaetzlich kann sie die Dateien lesen, die erstellt werden, falls das Programm vor abarbeiten
 * aller Texte beendet wird. Dies dient dazu, dass der Nutzer mit dem Klassifizieren/Labeln dort
 * weitermachen kann, wo er zuvor aufgehoert hat, ohne einen Text doppelt zu bearbeiten.
 * 
 * 
 * Texte als txt aus Ordner lesen
 * xml mittels dom einlesen
 * ja nein veilleicht in schema festlegen
 * 
 *
 * @author susannabeck
 * @version 0.1
 */
public class Input {
	
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
			return new File("../"+ordnername+dateiname);
		}
			
		}
	
	/**
	 * Eine Methode, um die XML-Datei mit den Labels einzulesen und als String[]-Array zurueckzugeben.
	 * @param
	 * @return String[]
	 */
	/**public static String[] labelLesen(){
		try {
			
			String dateiname ="labels.xml";
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(pfadNachOS(dateiname));
			NodeList nList = doc.getElementsByTagName("label");
			
			String[] label = new String[nList.getLength()];
			
			//die Namen der Labels werden nacheinander in das String[]-Array geschrieben
			for (int i = 0; i < nList.getLength(); i++)
			{
			 Node node = nList.item(i);
			 
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element eElement = (Element) node;
			    label[i]=eElement.getElementsByTagName("name").item(0).getTextContent();
			 }
			}
			return label;
		 } catch (Exception e) {
			e.printStackTrace();
			String[] leer=new String[0];
			return leer;
		 }
		
	}**/
	
	public static HashMap<String,ArrayList<String>> labelLesen(){
		try {
			
			String dateiname ="labels.xml";
			String ordnername = "XML";
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(pfadNachOS(dateiname, ordnername));
			NodeList nList = doc.getElementsByTagName("element");
			
			HashMap<String,ArrayList<String>> label = new HashMap<>();
			
			//die Namen der Labels werden nacheinander in das String[]-Array geschrieben
			for (int i = 0; i < nList.getLength(); i++)
			{
			 Node node = nList.item(i);
			 
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element eElement = (Element) node;
			    //label[i]=eElement.getElementsByTagName("name").item(0).getTextContent();
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
	 * Eine Methode, um die XML-Datei mit den Texten einzulesen und als String[]-Array zurueckzugeben.
	 * @param
	 * @return String[]
	 */
	public static String[] texteLesen(){
		try {
			String dateiname="";
			String ordnername="Texte";
			File[] dateien =pfadNachOS(dateiname,ordnername).listFiles();
			String[] texte= new String[dateien.length-1];
			
			for (int i=0; i<dateien.length; i++) {
				BufferedReader br = new BufferedReader(new FileReader(dateien[i]));
				String line = br.readLine();
				while (line != null) {
					texte[i]=texte[i]+line;
					line = br.readLine();
					}
				br.close();
			}

			return texte;
		 } catch (Exception e) {
			e.printStackTrace();
			String[] leer=new String[0];
			return leer;
		 }
		
	}


}
