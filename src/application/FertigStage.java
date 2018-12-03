package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FertigStage {

	public FertigStage() {
		// TODO Auto-generated constructor stub
	}
	
	public void erstelleFertigStage(Stage fourthStage) {
		try {
			
			AnchorPane endpain=new AnchorPane();
			Label fertig = new Label("Sie haben alle Texte klassifiziert.");
			Button schliessen = new Button("Schliessen");

			AnchorPane.setLeftAnchor(fertig, 10.0);
			AnchorPane.setTopAnchor(fertig, 10.0);
			AnchorPane.setLeftAnchor(schliessen, 10.0);
			AnchorPane.setTopAnchor(schliessen, 40.0);
			endpain.getChildren().addAll(endpain);
			
			Scene beenden =new Scene(endpain,400,250);
			beenden.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			fourthStage.setScene(beenden);
			fourthStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
