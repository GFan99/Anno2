package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ZeitEndeStage {

	public ZeitEndeStage() {
		// TODO Auto-generated constructor stub
	}

	public void erstelleZeitendeStage(Stage thirdStage, Klassifikator klasse) {
		try {
			
			AnchorPane gameoverpane=new AnchorPane();
			//prozent noch ueberarbeiten!!
			double prozent=klasse.texte.length/klasse.idgroesse;
			Label timeout= new Label("Ihre Zeit ist für heute abgelaufen. \n Sie haben bereits " +prozent+ "% klassifiziert.");
			Button schliessen = new Button("Schliessen");
			
			AnchorPane.setLeftAnchor(timeout, 10.0);
			AnchorPane.setTopAnchor(timeout, 10.0);
			AnchorPane.setLeftAnchor(schliessen, 10.0);
			AnchorPane.setTopAnchor(schliessen, 40.0);
			gameoverpane.getChildren().addAll(timeout, schliessen);
			
			Scene beenden =new Scene(gameoverpane,400,250);
			beenden.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			thirdStage.setScene(beenden);
			thirdStage.show();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
