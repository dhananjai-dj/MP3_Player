package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;



import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class Controller implements Initializable{
	
	
	@FXML
    private ImageView backImageView;
	@FXML
	private Label timeBox,artist,album,composer;
    @FXML
    private MediaView mediaView;
	@FXML
	private Label songLabel;
	@FXML
	private Button playButton, pauseButton, resetButton, previousButton, nextButton;
	@FXML
	private ComboBox<String> speedBox;
	@FXML
	private Slider volumeSlider;
	@FXML
	private ProgressBar songProgressBar;
	@FXML
	private Button imageView;
	@FXML
	private Pane backgroundPane;
	
	private Media media;
	private MediaPlayer mediaPlayer;
	
	private File directory;
	private File[] files;
	
	private ArrayList<File> songs;
	
	private int songNumber;
	private int[] speeds = {50, 100, 150};
	
	private Timer timer;
	private TimerTask task;
	
	private boolean running;
	
	boolean loop = false;
	boolean play = false;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		resetButton.setStyle("-fx-background-color:white");
		
		Image backImage = new Image("file:C:\\Users\\SANATH ABHINAV\\eclipse-workspace\\MP3\\images\\backward.jpeg");
		ImageView backImageView = new ImageView(backImage);
		backImageView.setFitWidth(30);
	    backImageView.setFitHeight(30);
	    previousButton.setGraphic(backImageView);
	        
		Image forwardImage = new Image("file:C:\\Users\\SANATH ABHINAV\\eclipse-workspace\\MP3\\images\\forward.jpeg");
		ImageView forwardImageView = new ImageView(forwardImage);
		forwardImageView.setFitWidth(30);
	    forwardImageView.setFitHeight(30);
		nextButton.setGraphic(forwardImageView);
		
		Image loopImage = new Image("file:///C:/Users/SANATH%20ABHINAV/eclipse-workspace/MP3/images/loopButton.png");
		ImageView loopImageView = new ImageView(loopImage);
		loopImageView.setFitWidth(30);
	    loopImageView.setFitHeight(30);
		resetButton.setGraphic(loopImageView);
		
		Image playImage = new Image("file:C:\\Users\\SANATH ABHINAV\\eclipse-workspace\\MP3\\images\\playButton.jpeg");
		ImageView playImageView = new ImageView(playImage);
		playImageView.setFitWidth(30);
	    playImageView.setFitHeight(30);
		playButton.setGraphic(playImageView);
		
		
		Image backGroundImage = new Image("file:C:\\Users\\SANATH ABHINAV\\eclipse-workspace\\MP3\\images\\mike.jpeg");
		ImageView backGroundImageView = new ImageView(backGroundImage);
 	    backGroundImageView.setFitHeight(250);
 	    backGroundImageView.setFitWidth(250);
 	    imageView.setGraphic(backGroundImageView);
		
	    
		songProgressBar.setStyle("-fx-accent: #bdbfc7");
		  
		songs = new ArrayList<File>();
		
		directory = new File("C:\\Users\\SANATH ABHINAV\\Music\\Songs");
		
		files = directory.listFiles();
		
		if(files != null) {
			
			for(File file : files) {
				
				songs.add(file);
			}
		}
		
		media = new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		
		songLabel.setText(songs.get(songNumber).getName());
		
		for(int i = 0; i < speeds.length; i++) {
			
			speedBox.getItems().add(Integer.toString(speeds[i])+"%");
		}
		speedBox.setOnAction(this::changeSpeed);
		
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				
				mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);			
			}
		});
		
	
	}
	
	public void playControl() {
		if(play) {
			pauseMedia();
			play = false;
			Image playImage = new Image("file:C:\\Users\\SANATH ABHINAV\\eclipse-workspace\\MP3\\images\\playButton.jpeg");
			ImageView playImageView = new ImageView(playImage);
			playImageView.setFitWidth(30);
		    playImageView.setFitHeight(30);
			playButton.setGraphic(playImageView);
		}
		else {
			playMedia();
			play = true;
			Image pauseImage = new Image("file:C:\\Users\\SANATH ABHINAV\\eclipse-workspace\\MP3\\images\\pauseButton.jpeg");
			ImageView pauseImageView = new ImageView(pauseImage);
			pauseImageView.setFitWidth(30);
		    pauseImageView.setFitHeight(30);
			playButton.setGraphic(pauseImageView);
		}
	}

	public void playMedia() {

		Image playImage = new Image("file:C:\\Users\\SANATH ABHINAV\\eclipse-workspace\\MP3\\images\\pauseButton.jpeg");
		ImageView playImageView = new ImageView(playImage);
		playImageView.setFitWidth(30);
	    playImageView.setFitHeight(30);
		playButton.setGraphic(playImageView);
		
		beginTimer();
		changeSpeed(null);
		
		
		mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
		mediaPlayer.play();
		
	}
	
	public void pauseMedia() {
		
		cancelTimer();
		mediaPlayer.pause();
	}
	
	public void resetMedia() {
		
		songProgressBar.setProgress(0);
		mediaPlayer.seek(Duration.seconds(0));
	}
	
	public void previousMedia() {
		
		
		if(songNumber > 0) {
			
			songNumber--;
			
			mediaPlayer.stop();
			
			if(running) {
				
				cancelTimer();
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);

			songLabel.setText(songs.get(songNumber).getName());
			
			playMedia();
		}
		else {
			
			songNumber = songs.size() - 1;
			
			mediaPlayer.stop();
			
			if(running) {
				
				cancelTimer();
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			songLabel.setText(songs.get(songNumber).getName());
			
			playMedia();
		}
	}
	
	public void nextMedia() {
		
		if(songNumber < songs.size() - 1) {
			
			songNumber++;
			
			mediaPlayer.stop();
			
			if(running) {
				
				cancelTimer();
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			songLabel.setText(songs.get(songNumber).getName());
			
			
			playMedia();
		}
		else {
			
			songNumber = 0;
			
			mediaPlayer.stop();
			
			media = new Media(songs.get(songNumber).toURI().toString());
			
			mediaPlayer = new MediaPlayer(media);
			
			songLabel.setText(songs.get(songNumber).getName());
			
			playMedia();
		}
	}
	
	public void changeSpeed(ActionEvent event) {
		
		if(speedBox.getValue() == null) {
			
			mediaPlayer.setRate(1);
		}
		else {
			mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
		}
	}
	
	public void beginTimer() {
	    timer = new Timer();
	    task = new TimerTask() {
	        public void run() {
	        			
	            running = true;
	            double current = mediaPlayer.getCurrentTime().toSeconds();
	            double currentTime = mediaPlayer.getCurrentTime().toMinutes();
	            double end = media.getDuration().toSeconds();
	            double endTime = media.getDuration().toMinutes();
	            String formattedCurrentTime = String.format("%.2f", currentTime);
	            String formattedEndTime = String.format("%.2f", endTime);
	            songProgressBar.setProgress(current / end);
	            Map<String,Object>metaData = media.getMetadata();
	            Platform.runLater(() -> {
	                timeBox.setText(formattedCurrentTime + "/" + formattedEndTime);
	                artist.setText(metaData.get("album artist").toString());
	                composer.setText(metaData.get("composer").toString());
	                album.setText(metaData.get("album").toString());	     
	            });
	            if (current / end == 1) {
	                cancelTimer();
	                if(loop!=true) {
	                	playNextSong();
	  	                }
	                	
	                else {
	                	resetMedia();
	                }
	                	
	                
	            }
	        }
	    };

	    timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	public void playNextSong() {
	    Platform.runLater(() -> {
	        songNumber++;
	        if (songNumber < songs.size()) {
	            mediaPlayer.stop();
	            media = new Media(songs.get(songNumber).toURI().toString());
	           
	            mediaPlayer = new MediaPlayer(media);
	            songLabel.setText(songs.get(songNumber).getName());
	            playMedia();
	        } else {
	            songNumber = 0;
	            mediaPlayer.stop();
	            media = new Media(songs.get(songNumber).toURI().toString());
	            mediaPlayer = new MediaPlayer(media);
	            songLabel.setText(songs.get(songNumber).getName());
	            playMedia();
	        }
	    });
	}


	
	public void cancelTimer() {
		
		running = false;
		timer.cancel();
	}
	
	public void setLoop() {
		if(loop) {
			loop = false;
			resetButton.setStyle("-fx-background-color:white");
		}
			
		else {
			loop = true;	
			resetButton.setStyle("-fx-background-color:skyblue");
		}
			
	}
	

}