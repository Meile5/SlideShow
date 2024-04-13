package easv;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.io.File;

public class MainWindow implements Initializable {
    public Label imageName;
    @FXML
    private ImageView imageView, playPauseImage;
    @FXML
    private BorderPane borderPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Button playButton, nextButton, previousButton, uploadButton;

    private List<Image> images = new ArrayList<>();
    private int currentIndex = 0;
    private boolean isPlaying = false;
    private final Image play = new Image("easv/resources/play.png");
    private final Image pause = new Image("easv/resources/pause2.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> resizeImageView());
        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> resizeImageView());
    }

    private void resizeImageView() {
        double width = stackPane.getWidth()-200;
        double height = stackPane.getHeight()-100;
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public void uploadPictures(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stackPane.getScene().getWindow());
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                images.add(new Image(file.toURI().toString()));
            }
            if (!images.isEmpty()) {
                imageView.setImage(images.get(0));
            }
        }
    }

    public void previousPicture(ActionEvent actionEvent) {
        if (!images.isEmpty()) {
            currentIndex = (currentIndex - 1 + images.size()) % images.size();
            imageView.setImage(images.get(currentIndex));
        }
    }

    public void playSlideshow(ActionEvent actionEvent) {
        if(playPauseImage.getImage() == play) {
            playPauseImage.setImage(pause);
            if (!images.isEmpty()) {
                if (!isPlaying) {
                    isPlaying = true;
                    Thread slideshowThread = new Thread(() -> {
                        while (isPlaying) {
                            try {
                                Thread.sleep(3000);
                                Platform.runLater(() -> {
                                    currentIndex = (currentIndex + 1) % images.size();
                                    imageView.setImage(images.get(currentIndex));
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    slideshowThread.setDaemon(true);
                    slideshowThread.start();
                } else {
                    isPlaying = false;
                }
            }
        } else {
            playPauseImage.setImage(play);
        }
    }

    public void nextPicture(ActionEvent actionEvent) {
        if (!images.isEmpty()) {
            currentIndex = (currentIndex + 1) % images.size();
            imageView.setImage(images.get(currentIndex));
        }
    }
}
