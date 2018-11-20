package application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import com.sun.org.apache.xerces.internal.xs.XSModel;

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
	
	public static Document loadXsdDocument(String dateiname, String ordnername) {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        Document doc = null;
 
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final File inputFile = pfadNachOS(dateiname, ordnername);
            doc = builder.parse(inputFile);
        } catch (final Exception e) {
            e.printStackTrace();
        }
 
        return doc;
    }

	public static File xsdToXml() {
		String dateiname ="Eingabe.xsd";
		String ordnername = "XSD";
		Document doc = loadXsdDocument(dateiname, ordnername);
		 
        //Find the docs root element and use it to find the targetNamespace
		 Element rootElem = doc.getDocumentElement();
		String targetNamespace = null;
		if (rootElem != null && rootElem.getNodeName().equals("xs:schema")) {
			targetNamespace = rootElem.getAttribute("targetNamespace");
		}


        //Parse the file into an XSModel object
		XSModel xsModel = new XSParser().parse();

        //Define defaults for the XML generation
		XSInstance instance = new XSInstance();
		instance.minimumElementsGenerated = 1;
		instance.maximumElementsGenerated = 1;
		instance.generateDefaultAttributes = true;
		instance.generateOptionalAttributes = true;
		instance.maximumRecursionDepth = 0;
		instance.generateAllChoices = true;
		instance.showContentModel = true;
		instance.generateOptionalElements = true;

        //Build the sample xml doc
        //Replace first param to XMLDoc with a file input stream to write to file
		QName rootElement = new QName(targetNamespace, "HighSchoolTranscript");
		XMLDocument sampleXml = new XMLDocument(new StreamResult(System.out), true, 4, null);
		instance.generate(xsModel, rootElement, sampleXml);
		return File;
	} catch (TransformerConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
