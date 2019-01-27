package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * In dieser Klasse ist die FertigStage programmiert, die der Nutzer sieht, wenn er alle Texte gelabelt hat.
 * 
 * @author becksusanna
 */
public class FertigStage extends Stage {

	private Button schliessen;
	
	/**
	 * Der Konstruktor erstellt die Scene zur Stage, fuegt sie zusammen und zeigt sie an. Ausserdem beinhaltet
	 * sie den EventHandler fuer den 'Schliessen'-Button.
	 */
	public FertigStage() {
		super();
		Scene sc = erstelleFertigStage();
		this.setScene(sc);
		this.show();
		
		EventHandler<MouseEvent> ende = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.exit(0);
			}
		};
		schliessen.addEventFilter(MouseEvent.MOUSE_CLICKED, ende);	  //EventFilter dazu
	}
	
	/**
	 * Diese Funktion erstellt die Scene zur FertigStage und gibt sie dann zurueck.
	 */
	public Scene erstelleFertigStage() {
		//Erstellung von Pane, Button und Label
		AnchorPane endpane=new AnchorPane();
		Label fertig = new Label("Sie haben alle Texte klassifiziert.");
		fertig.setFont(Font.font("Tahoma"));
		schliessen = new Button("Schließen");
		schliessen.setFont(Font.font("Tahoma"));
		//Einstellung des Layouts
		AnchorPane.setLeftAnchor(fertig, 25.0);
		AnchorPane.setTopAnchor(fertig, 25.0);
		AnchorPane.setLeftAnchor(schliessen, 75.0);
		AnchorPane.setTopAnchor(schliessen, 52.0);
		endpane.getChildren().addAll(fertig, schliessen);
		//Erstellung und Rueckgabe der Scene	
		Scene beenden = new Scene(endpane,240,105);
		return beenden;
	}

}
