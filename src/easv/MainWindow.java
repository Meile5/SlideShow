package easv;
import easv.be.ColorPixelCounter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.io.File;

public class MainWindow implements Initializable {
    @FXML
   public Label red;
    @FXML
    public Label blue;
    @FXML
    public Label green;
    @FXML
    public Label mixed;
    @FXML
    private Label imageName;
    @FXML
    private ImageView imageView, playPauseImage;
    @FXML
    private StackPane stackPane;
    @FXML
    private Button playButton, nextButton, previousButton, uploadButton;

    private List<Image> images = new ArrayList<>();
    private List<String> imageNames = new ArrayList<>();
    private int currentIndex = 0;
    private final Image play = new Image("easv/resources/play.png");
    private final Image pause = new Image("easv/resources/pause2.png");
    private SlideshowController slideshowController;

    private Service<ColorPixelCounter> colourService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> resizeImageView());
        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> resizeImageView());
        playPauseImage.setImage(play);
     //   updateColorLabels();
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
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stackPane.getScene().getWindow());
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                images.add(new Image(file.toURI().toString()));
                imageNames.add(file.getName());

            }
            if (!images.isEmpty()) {
                imageView.setImage(images.get(currentIndex));
                imageName.setText(imageNames.get(currentIndex));
                updateColorLabels();
            }
        }
    }

    public void playSlideshow(ActionEvent actionEvent) {
        if (playPauseImage.getImage() == play) {
            playPauseImage.setImage(pause);
            if (!images.isEmpty()) {
                if (slideshowController == null) {
                    slideshowController = new SlideshowController(images, imageNames, imageView, imageName, red, green, blue, mixed);
                    slideshowController.setCurrentIndex(currentIndex);
                    slideshowController.startSlideshow();
                    disableButtons();
                } else {
                    //currentIndex = slideshowController.getCurrentIndex();
                    slideshowController.setCurrentIndex(currentIndex);
                    slideshowController.resumeSlideshow();
                    disableButtons();
                }
            }
        } else {
            playPauseImage.setImage(play);
            if (slideshowController != null) {
                currentIndex = slideshowController.getCurrentIndex();
                slideshowController.pauseSlideshow();
                enableButtons();
            }
        }
    }

    public void nextPicture(ActionEvent actionEvent) {
        if (!images.isEmpty()) {
            currentIndex = (currentIndex + 1) % images.size();
            imageView.setImage(images.get(currentIndex));
            imageName.setText(imageNames.get(currentIndex));
            updateColorLabels();
        }
    }

    public void previousPicture(ActionEvent actionEvent) {
        if (!images.isEmpty()) {
            currentIndex = (currentIndex - 1 + images.size()) % images.size();
            imageView.setImage(images.get(currentIndex));
            imageName.setText(imageNames.get(currentIndex));
            updateColorLabels();
        }
    }

    public void disableButtons(){
        nextButton.setDisable(true);
        previousButton.setDisable(true);
        uploadButton.setDisable(true);
    }

    public void enableButtons(){
        nextButton.setDisable(false);
        previousButton.setDisable(false);
        uploadButton.setDisable(false);
    }
    private void updateColorLabels() {

        if (!images.isEmpty()) {
            colourService = new Service<ColorPixelCounter>() {
               @Override
               protected Task<ColorPixelCounter> createTask() {
                   return new Task<ColorPixelCounter>() {
                       @Override
                       protected ColorPixelCounter call() throws Exception {
                           ColorCounter colorCounter = new ColorCounter(images.get(currentIndex));
                           return colorCounter.call() ;
                       }
                   };
               }
           };
           colourService.setOnSucceeded((event)->{
               ColorPixelCounter colorPixelCounter = colourService.getValue();
               red.setText("Red: " + colorPixelCounter.getRedCount());
               green.setText("Green: " + colorPixelCounter.getGreenCount());
               blue.setText("Blue: " + colorPixelCounter.getBlueCount());
               mixed.setText("Mixed: " + colorPixelCounter.getMixedCount());
           });
            colourService.setOnFailed((event)->{
                red.setText("Red: " + "not available");
                green.setText("Green: " + "not available");
                blue.setText("Blue: " + "not available");
                mixed.setText("Mixed: " + "not available");
            });
        }
        colourService.restart();
    }



}
