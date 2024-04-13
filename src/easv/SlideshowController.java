package easv;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SlideshowController extends Thread {
    private final List<Image> images;
    private final List<String> imageNames;
    private final ImageView imageView;
    private final Label imageName;
    private final ScheduledExecutorService executorService;
    private int currentIndex = 0;
    private boolean running = false;


    public SlideshowController(List<Image> images, List<String> imageNames, ImageView imageView, Label name) {
        this.images = images;
        this.imageView = imageView;
        this.imageNames = imageNames;
        this.imageName = name;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void startSlideshow() {
        running = true;
        executorService.scheduleAtFixedRate(() -> {
            if (running) {
                Platform.runLater(() -> {
                    currentIndex = (currentIndex + 1) % images.size();
                    imageView.setImage(images.get(currentIndex));
                    imageName.setText(imageNames.get(currentIndex));
                });
            }
        }, 2, 2, TimeUnit.SECONDS);
    }

    public void pauseSlideshow() {
        running = false;
    }

    public void resumeSlideshow() {
        running = true;
    }

    public void stopSlideshow() {
        running = false;
        executorService.shutdown();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
