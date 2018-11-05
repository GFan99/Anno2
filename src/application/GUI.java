package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Dies wird die Klasse GUI.
 * In ihr wird die gesamte graphische Oberflaeche progammiert.
 *  - start()		 : Diese Methode startet die GUI.
 *  - idabfrage()	 : Diese Funktion soll den Nutzer nach seiner ID fragen und diese dann auch zurück geben.
 *  - getLabel()  	 : Diese Methode soll die vom Nutzer ausgewaehlten Label erfassen und speichern. Dazu
 *  					nutzt sie zunaechst die Funktion Klassifikator.waehleText() und stellt den zur
 *  					erhaltenen TextID gehoerigen Text dar. Dann wartet sie auf ein Clicked-Event des 
 *  					OK-Buttons und prueft dann die Eingaben auf Vollstaendigkeit, bevor sie die zu 
 *  					den Labels erfassten Werte in einem Array speichert, welches sie zusammen mit der
 *  					textID der Methode Klassifikator.schreibeText() uebergibt.
 *  					Sie beginnt nun ihre Arbeit von vorne, solange bis sie durch 
 *  					Klassifikator.waehleText() den Abbruch-Code uebergeben bekommt. Sie schliesst dann
 *  					die GUI.
 *  - schliessen()	 : Beendet die graphische Darstellung und startet den Speichervorgang.
 *  - abbruch()		 : Diese Methode wird ausgeloest, wenn die graphische Oberflaeche vom Nutzer vorzeitig
 *  					geschlossen wird. Sie startet die "Abbruchspeicherung".
 *  - fortschritt	 : Zeigt ein kleines Fenster mit dem Fortschritt.
 * @author becksusanna
 * @version 0.1
 */
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
			
			int x = 800;
			int y = 500;
			VBox klasspane= new VBox();
			HBox daten = new HBox();
			GridPane ranking = new GridPane();
			ScrollPane textanz = new ScrollPane();
			
			//Input-Aufruf
			
			Klassifikator klasse = new Klassifikator(); //inputs als parameter übergeben
			ProgressBar fortschritt = new ProgressBar(0);
			Label idanzeige = new Label("Nutzer-ID: "+Steuerung.nutzerID);
			Text text = new Text(klasse.getText());
			
			
			
			/**for(int i=0; i<Input.labelLesen().length;i++){
				Label label0 =new Label(Input.labelLesen()[i]);
				GridPane.setConstraints(label0, 1, i+1);;
			}**/
			//Wie kann man jeden button/label anders benennen, um sie
			//sp�ter bei Event ansprechen zu k�nnen???
			int zeile=1;
			for(String key : Input.labelLesen2().keySet()) {
				Label label0 = new Label(key);
				GridPane.setConstraints(label0, 1, zeile);
				for(int i=0; i<Input.labelLesen2().get(key).size();i++){
					RadioButton rbutton0 = new RadioButton(Input.labelLesen2().get(key).get(i));
					GridPane.setConstraints(rbutton0, i+2, zeile);
				}
				zeile++;
			}
			
			daten.getChildren().addAll(idanzeige, fortschritt);
			textanz.setContent(text);
			
			
			Scene klass = new Scene(klasspane,x,y);
			klasspane.getChildren().addAll(daten, textanz, ranking);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
