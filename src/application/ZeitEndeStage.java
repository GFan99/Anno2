package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ZeitEndeStage extends Stage {
	
	private Button schliessen;

	public ZeitEndeStage(double fortschrittprozent) {
		super();
		Scene scene = this.erstelleZeitendeScene(fortschrittprozent);
		this.setScene(scene);
		this.show();
		
		EventHandler<MouseEvent> closebutton = new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
				System.exit(0);
				//close();
			}
		};
		schliessen.addEventFilter(MouseEvent.MOUSE_CLICKED, closebutton);
	}

	public Scene erstelleZeitendeScene(double prozent) {
		
		BorderPane gameoverpane=new BorderPane();
		VBox box = new VBox();

		Label timeout = new Label("Ihre Zeit ist für heute abgelaufen. \nSie haben bereits " + prozent*100 + "% klassifiziert.");
		timeout.setFont(Font.font("Tahoma"));
		timeout.setTextAlignment(TextAlignment.CENTER);
		schliessen = new Button("Schliessen");
		schliessen.setFont(Font.font("Tahoma"));
		Region spacer = new Region();
		spacer.setMinHeight(20.0);
		
		Region spacerl = new Region();
		spacerl.setMinWidth(30.0);
		Region spacerr = new Region();
		spacerr.setMinWidth(30.0);
		Region spacero = new Region();
		spacero.setMinHeight(30.0);
		Region spaceru = new Region();
		spaceru.setMinHeight(30.0);
		
		Region spacerl2 = new Region();
		spacerl2.setMinWidth(68.0);
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(spacerl2, schliessen);
		
		box.getChildren().addAll(timeout, spacer, hbox);
		gameoverpane.setCenter(box);
		gameoverpane.setLeft(spacerl);
		gameoverpane.setRight(spacerr);
		gameoverpane.setTop(spacero);
		gameoverpane.setBottom(spaceru);
			
		Scene beenden = new Scene(gameoverpane,275,150);
			
		return beenden;
	}
}
