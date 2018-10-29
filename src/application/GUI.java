package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class GUI extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane idpane = new AnchorPane();
			
			Label idfrage = new Label("Bitte geben Sie ihre Nutzer-ID ein:");
			TextField id = new TextField();
			Button idok = new Button("OK");
			Button neueid = new Button("ID erstellen");
			AnchorPane.setTopAnchor(idfrage, 10.0);
			AnchorPane.setLeftAnchor(idfrage, 10.0);
			AnchorPane.setTopAnchor(id, 40.0);
			AnchorPane.setLeftAnchor(id, 10.0);
			AnchorPane.setTopAnchor(idok, 80.0);
			AnchorPane.setLeftAnchor(idok, 25.0);
			AnchorPane.setTopAnchor(neueid, 80.0);
			AnchorPane.setLeftAnchor(neueid, 80.0);
			idpane.getChildren().addAll(idfrage, id, idok, neueid);
			
			Scene idabfrage = new Scene(idpane,400,250);
			idabfrage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(idabfrage);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
