# MP3 Player Readme

This Java application implements a simple MP3 player with basic playback controls, a progress bar, volume control, and song information display. The graphical user interface (GUI) is built using JavaFX and Scene Builder. The code is organized into two classes: `Controller` and `Main`.

## Requirements
- Java Development Kit (JDK)
- Eclipse IDE (Integrated Development Environment)
- Scene Builder

## How to Run
1. Open the project in Eclipse.
2. Ensure that the JavaFX library is configured in your project.
3. Run the `Main` class, which serves as the entry point for the application.

## GUI Components
The main GUI is designed using FXML and Scene Builder. It consists of the following components:

- Song Label: Displays the name of the currently playing song.
- Song Progress Bar: Indicates the progress of the currently playing song.
- Volume Slider: Adjusts the volume of the media player.
- Playback Buttons: Includes previous, play/pause, next, and reset buttons for controlling media playback.
- Speed Box: A combo box for adjusting the playback speed.
- **Time Box:** Displays the current and total duration of the song.
- **GridPane:** Displays additional metadata such as artist, composer, and album.
- **ImageView:** Displays an image associated with the song.
- **Background Image:** The background of the application.

## Controller Class (`Controller.java`)
- Handles the logic and events related to the media player.
- Provides methods for playing, pausing, resetting, and navigating through songs.
- Allows adjustment of volume and playback speed.
- Implements a timer to update the song progress and display metadata.

## Main Class (`Main.java`)
- Contains the `main` method and serves as the entry point for the application.
- Loads the FXML file and sets up the main stage.

## Additional Notes
- The application assumes a directory structure for MP3 files located at "C:\\Users\\SANATH ABHINAV\\Music\\Songs." Adjust this path as needed.
- Image paths are hardcoded. Update them if necessary.
- Closing the application window triggers a clean exit.



https://github.com/dhananjai-dj/MP3_Player/assets/119773597/9ab0991d-6096-42cb-aaab-782547f66941

