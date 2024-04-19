package easv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/easv/MainWindow.fxml"));
        Parent root = loader.load();
        MainWindowController mainWindowController = loader.getController();
        primaryStage.setTitle("PixelPioneers");
        primaryStage.setScene(new Scene(root));
        addCloseListener(primaryStage,mainWindowController);
        primaryStage.show();
    }

    private void  addCloseListener(Stage stage,MainWindowController mainWindowController){
        stage.setOnCloseRequest(event->{
            mainWindowController.closeSlideShow();
        });
    }

}