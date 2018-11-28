package application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Timer {
	private static final Integer timeInMin = 60;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private Integer timeInSec = timeInMin*60;
    private Button button = new Button();
    
    public void timer(int zeitInMin) {
    	timerLabel.setText(timeInMin.toString()+":00");
    	EventHandler<MouseEvent> timerdown=new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent e) {
    			if (timeline != null) {
    	            timeline.stop();
    	        }
    	        timeInSec = timeInMin*60;
    	 
    	        // update timerLabel
    	        int min = timeInSec/60;
                int sec = timeInSec%60;
    	        timerLabel.setText(min+":"+sec);
    	        timeline = new Timeline();
    	        timeline.setCycleCount(Timeline.INDEFINITE);
    	        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() {
    	                    public void handle(ActionEvent e) {
    	                        timeInSec--;
    	                        int min=timeInSec/60;
    	                        int sec = timeInSec%60;
    	                        timerLabel.setText(min+":"+sec);
    	                        if (timeInSec <= 0) {
    	                            timeline.stop();
    	                        }
    	                      }
    	                }));
    	        timeline.playFromStart();
    		}
    	};
    	button.addEventFilter(MouseEvent.MOUSE_CLICKED, timerdown);
    	
    }
    
}
