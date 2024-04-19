package easv;

import easv.be.ColorPixelCounter;
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
    private final Label red;
    private final Label green;
    private final Label blue;
    private final Label mixed;


    public SlideshowController(List<Image> images, List<String> imageNames, ImageView imageView, Label name, Label red, Label green, Label blue, Label mixed) {
        this.images = images;
        this.imageView = imageView;
        this.imageNames = imageNames;
        this.imageName = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.mixed = mixed;
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
                    ColorCounter colorCounter = new ColorCounter(images.get(currentIndex));
                    try {
                        ColorPixelCounter colorPixelCounter =  colorCounter.call();
                        updateColorLabels(colorPixelCounter);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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

        private void updateColorLabels(ColorPixelCounter colorPixelCounter) {

        red.setText("Red: " + colorPixelCounter.getRedCount());
        green.setText("Green: " + colorPixelCounter.getGreenCount());
        blue.setText("Blue: " + colorPixelCounter.getBlueCount());
        mixed.setText("Mixed: " + colorPixelCounter.getMixedCount());
    }
}



