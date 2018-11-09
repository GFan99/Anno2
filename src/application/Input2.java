package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//EXTRA METHODE F‹R EIN OER MEHRERE M÷GLICKEITEN ODER MAP ERWEITERN?????

public class Input2 {
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
			
			String dateiname ="labels.txt";
			String ordnername = "Eingabe";
			
			File labels = Input.pfadNachOS(dateiname, ordnername);
			HashMap<String,ArrayList<String>> label = new HashMap<>();
			ArrayList<String> beschriftung=new ArrayList<>();
			String bezeichnung="";
			
			BufferedReader br = new BufferedReader(new FileReader(labels));
			String line = br.readLine();
			int zeile=1;
			while (line != "ENDE") {
				if(zeile%4==1) {
					bezeichnung=line;
				}
				else if(zeile%4==2) {
					if (line=="5rating") {
						beschriftung.add("trifft nicht zu");
						beschriftung.add("trifft eher nicht zu");
						beschriftung.add("ich weiﬂ nicht");
						beschriftung.add("trifft teilweise zu");
						beschriftung.add("trifft zu");
					}
					else if (line=="3rating zutreffen") {
						beschriftung.add("trifft nicht zu");
						beschriftung.add("ich weiﬂ nicht");
						beschriftung.add("trifft zu");
					}
					else if (line=="3rating ja") {
						beschriftung.add("nein");
						beschriftung.add("vielleicht");
						beschriftung.add("ja"); 
					}
					else {
						beschriftung=new ArrayList<String>(Arrays.asList(line.split(",")));
					}					
						
				}
				else if(zeile%4==3) {
					label.put(bezeichnung, beschriftung);
					beschriftung.clear();
				}
				line = br.readLine();
			}
			br.close();
			
			  
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
