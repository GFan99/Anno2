package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


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
			return new File("./"+ordnername+dateiname);
		}
			
		}
	
	/**
	 * Eine Methode, um die XML-Datei mit den Labels einzulesen und als String[]-Array zurueckzugeben.
	 * @param
	 * @return String[]
	 */
	public static boolean[] labelEigenschaft(HashMap<String,ArrayList<String>> list){
		try {
			
			String dateiname ="labels.txt";
			String ordnername = "Eingabe";
			
			File labels = Input2.pfadNachOS(dateiname, ordnername);
			boolean[] eigenschaften=new boolean[list.size()];
			
			BufferedReader br = new BufferedReader(new FileReader(labels));
			String line = br.readLine();
			int zeile=1;
			int i=0;
			while (line != "ENDE") {
				if(zeile%4==3) {
					if (line=="j") {
						eigenschaften[i]=true;
					}
					else {
						eigenschaften[i]=false;
					}
					i++;
				}
			}
			return eigenschaften;
		} catch (Exception e) {
			e.printStackTrace();
			boolean[] leer=new boolean[0];
			return leer;
		}
		
	}
	
	public static HashMap<String,ArrayList<String>> labelLesen(){
		try {
			
			String dateiname ="labels.txt";
			String ordnername = "Eingabe";
			
			File labels = Input2.pfadNachOS(dateiname, ordnername);
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
					if (line=="5Rating") {
						beschriftung.add("Trifft nicht zu");
						beschriftung.add("Trifft eher nicht zu");
						beschriftung.add("Ich wei� nicht");
						beschriftung.add("Trifft teilweise zu");
						beschriftung.add("Trifft zu");
					}
					else if (line=="3Rating zutreffen") {
						beschriftung.add("Trifft nicht zu");
						beschriftung.add("Ich wei� nicht");
						beschriftung.add("Trifft zu");
					}
					else if (line=="3Rating ja") {
						beschriftung.add("Nein");
						beschriftung.add("Vielleicht");
						beschriftung.add("Ja"); 
					}
					else {
						beschriftung=new ArrayList<String>(Arrays.asList(line.split(";")));
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
			System.out.println(pfadNachOS(dateiname,ordnername));
			File[] dateien =pfadNachOS(dateiname,ordnername).listFiles();
			String[] texte= new String[dateien.length];
			
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
