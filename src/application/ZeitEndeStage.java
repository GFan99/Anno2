package application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * In dieser Klasse ist das Fenster programmiert, welches dem Nutzer angezeigt wird, wenn seine
 * Bearbeitungszeit fuer die entsprechende Sitzung abgelaufen ist.
 * 
 * @author becksusanna
 */
public class ZeitEndeStage extends Stage {
	
	private Button schliessen;

	/**
	 * Der Konstruktor fuer ZeitEndeStage. Er erstellt die Stage u zugehoerige Scene und zeigt
	 * sie an. Als Parameter benoetigt er den Prozentsatz der bereits klassifizierten Texte.
	 */
	public ZeitEndeStage(double fortschrittprozent) {
		super();
		Scene scene = this.erstelleZeitendeScene(fortschrittprozent);
		this.setScene(scene);
		this.show();
		
		//EventHandler fuer den 'Schliessen'-Button mit dem sich das Fenster u das gesamte
		//Programm schliessen lassen.
		EventHandler<MouseEvent> closebutton = new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
				System.exit(0);
			}
		};
		schliessen.addEventFilter(MouseEvent.MOUSE_CLICKED, closebutton);
	}

	/**
	 * Diese Funktion erstellt die Scene, die der ZeitEndeStage im Konstruktor hinzugefuegt wird.
	 * Sie bekommt als Parameter den Prozentsatz der bereits klassifizierten Texte.
	 */
	public Scene erstelleZeitendeScene(double prozent) {
		//erstellen der Panes
		VBox box = new VBox();
		//Initialisierung des Labels und des Buttons
		Label timeout = new Label("Ihre Zeit ist für heute abgelaufen. \nSie haben bereits " + prozent*100 + "% klassifiziert.");
		timeout.setFont(Font.font("Tahoma"));
		timeout.setTextAlignment(TextAlignment.CENTER);
		schliessen = new Button("Schliessen");
		schliessen.setFont(Font.font("Tahoma"));
		//Layoutoptionen der VBox
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(timeout, schliessen);
		box.setPadding(new Insets(30.0,30.0,30.0,30.0));
		box.setSpacing(20.0);
		
		Scene beenden = new Scene(box,275,140);
		return beenden;
	}
}