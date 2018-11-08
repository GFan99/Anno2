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
 * @author susannabeck
 * @version 0.1
 */
public class Input {
	
	public static File pfadNachOS(String dateiname) {
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") != -1) {
			String pfad = System.getProperty("user.dir");//user.dir ist workspace
			return new File(pfad+"//Eingabe//"+dateiname);
		}
		else {
			return new File("../Eingabe/"+dateiname);
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
			/**
			 * File labels = Input.pfadNachOS(dateiname);
			 * JAXBContext context = JABXContext.newInstance(DataEntity.class);
			 * Unmarshaller unmarshallerObj = context.createUnmarshaller();
			 * DataEntity dataEntity = (DataEntity) unmarshallerObj.unmarshal(labels);
			 * HashMap<String,ArrayList<String>> label = new HashMap<>();
			 * 
			 * for(DataEntity data: dataEntity.getDataEntity()){
			 * 		AnnotationItem item=data.getAnnotations();
			 * 		
			 * }
			 */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(pfadNachOS(dateiname));
			NodeList nList = doc.getElementsByTagName("label");
			
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
			String dateiname="texte.xml";
			
			/**
			 * File labels = Input.pfadNachOS(dateiname);
			 * JAXBContext context = JABXContext.newInstance(DataEntity.class);
			 * Unmarshaller unmarshallerObj = context.createUnmarshaller();
			 * DataEntity dataEntity = (DataEntity) unmarshallerObj.unmarshal(labels);
			 * HashMap<String,ArrayList<String>> label = new HashMap<>();
			 * 
			 * for(DataEntity data: dataEntity.getDataEntity()){
			 * 		;
			 * 		
			 * }
			 */
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(pfadNachOS(dateiname));
			NodeList nList = doc.getElementsByTagName("text");
			
			String[] texte = new String[nList.getLength()];
			
			//der Inhalt der Texte werden nacheinander in das String[]-Array geschrieben
			for (int i = 0; i < nList.getLength(); i++)
			{
			 Node node = nList.item(i);
			 
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element eElement = (Element) node;
			    texte[i]=eElement.getElementsByTagName("inhalt").item(0).getTextContent();
			 }
			}
			return texte;
		 } catch (Exception e) {
			e.printStackTrace();
			String[] leer=new String[0];
			return leer;
		 }
		
	}


}
