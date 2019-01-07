package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FertigStage extends Stage {

	public FertigStage() {
		super();
		erstelleFertigStage();
	}
	
	public void erstelleFertigStage() {
		try {
			
			AnchorPane endpane=new AnchorPane();
			Label fertig = new Label("Sie haben alle Texte klassifiziert.");
			Button schliessen = new Button("Schliessen");

			AnchorPane.setLeftAnchor(fertig, 10.0);
			AnchorPane.setTopAnchor(fertig, 10.0);
			AnchorPane.setLeftAnchor(schliessen, 10.0);
			AnchorPane.setTopAnchor(schliessen, 40.0);
			endpane.getChildren().addAll(endpane);
			
			Scene beenden =new Scene(endpane,400,250);
			beenden.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			this.setScene(beenden);
			this.show();
			
			EventHandler<MouseEvent> ende = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					close();
				}
			};
			schliessen.addEventFilter(MouseEvent.MOUSE_CLICKED, ende);	  //EventFilter dazu
			
			this.setOnCloseRequest(event -> {
			    System.out.println("Stage is closing");
			    //Output.kontrollspeichern();
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
