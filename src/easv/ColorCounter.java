package easv;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class ColorCounter {
    private int redCount;
    private int greenCount;
    private int blueCount;
    private int mixedCount;

    public ColorCounter(Image image) {
        countColors(image);
    }

    private void countColors(Image image) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        //iterates over each pixel in the image. The outer loop iterates over the rows (y-coordinates),
        // the inner loop iterates over the columns (x-coordinates).
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF; //shifting the rgb colors to find the color that we are searching
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                if (red > green && red > blue) {
                    redCount++;
                } else if (green > red && green > blue) {
                    greenCount++;
                } else if (blue > red && blue > green) {
                    blueCount++;
                } else {
                    mixedCount++;
                }
            }
        }
    }

    public int getRedCount() {
        return redCount;
    }

    public int getGreenCount() {
        return greenCount;
    }

    public int getBlueCount() {
        return blueCount;
    }

    public int getMixedCount() {
        return mixedCount;
    }
}