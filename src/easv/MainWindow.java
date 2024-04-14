package easv;

import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    public ImageView imageView;
    public BorderPane borderPane;
    public StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Listen for changes in the size of the StackPane and adjust the ImageView accordingly
        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> resizeImageView());
        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> resizeImageView());
    }

    // Method to adjust the size of the ImageView based on the StackPane size
    private void resizeImageView() {
        double width = stackPane.getWidth()-200;
        double height = stackPane.getHeight()-100;
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }
}
