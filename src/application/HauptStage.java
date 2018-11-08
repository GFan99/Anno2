package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HauptStage extends Stage {
	
	public HauptStage(Klassifikator klasse) {
		
	
		Stage secondStage = new Stage();
			
		int x = 800;
		int y = 500;
			
		VBox klasspane= new VBox();
		HBox daten = new HBox();
		GridPane ranking = new GridPane();
		ScrollPane textanz = new ScrollPane();
			
		//Input-Aufruf
		
		ProgressBar fortschritt = new ProgressBar(0);
		Label idanzeige = new Label("Nutzer-ID: "+Steuerung.nutzerID);
		Text text = new Text(klasse.getText());
		Button labelok = new Button("OK");
			
		//Wie kann man jeden button/label anders benennen, um sie
		//sp�ter bei Event ansprechen zu k�nnen???
		int zeile=1;
		for(String key : Input.labelLesen().keySet()) {
			Label label0 = new Label(key);
			GridPane.setConstraints(label0, 1, zeile);
			for(int i=0; i<Input.labelLesen().get(key).size();i++){
				RadioButton rbutton0 = new RadioButton(Input.labelLesen().get(key).get(i));
				GridPane.setConstraints(rbutton0, i+2, zeile);
			}
			zeile++;
		}
		
		daten.getChildren().addAll(idanzeige, fortschritt);
		textanz.setContent(text);
		
			
		Scene klassi = new Scene(klasspane,x,y);
		klasspane.getChildren().addAll(daten, textanz, ranking, labelok);
		
		klassi.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		secondStage.setScene(klassi);
		secondStage.show();
	}
}
