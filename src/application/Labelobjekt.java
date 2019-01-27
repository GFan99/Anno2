package application;

import java.util.ArrayList;

/**
 * Die Klasse dient dazu ein Objekt erstellen zu koennen, welches die Eigenschaften der Labels
 * speichert und spaeter wieder ausgelesen werden kann, damit sich die Reihenfolge und somit die
 * Zuordnung nicht veraendert.
 * @author Laura
 *
 */

public class Labelobjekt {
	private String name;
	private ArrayList<String> beschriftung;
	private boolean eigenschaft;
	
	
	public Labelobjekt(String name, ArrayList<String> beschriftung, boolean eigenschaft) {
		this.setName(name);
		this.setBeschriftung(beschriftung);
		this.setEigenschaft(eigenschaft);	
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<String> getBeschriftung() {
		return beschriftung;
	}


	public void setBeschriftung(ArrayList<String> beschriftung) {
		this.beschriftung = beschriftung;
	}


	public boolean isEigenschaft() {
		return eigenschaft;
	}


	public void setEigenschaft(boolean eigenschaft) {
		this.eigenschaft = eigenschaft;
	}
	
	

}
