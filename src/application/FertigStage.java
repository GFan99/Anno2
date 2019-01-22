package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
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
			fertig.setFont(Font.font("Tahoma"));
			Button schliessen = new Button("Schliessen");
			schliessen.setFont(Font.font("Tahoma"));

			AnchorPane.setLeftAnchor(fertig, 25.0);
			AnchorPane.setTopAnchor(fertig, 25.0);
			AnchorPane.setLeftAnchor(schliessen, 75.0);
			AnchorPane.setTopAnchor(schliessen, 52.0);
			endpane.getChildren().addAll(fertig, schliessen);
			
			Scene beenden = new Scene(endpane,240,105);
			
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
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
