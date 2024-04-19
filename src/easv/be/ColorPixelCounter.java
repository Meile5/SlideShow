package easv.be;

public class ColorPixelCounter {

    private int redCount;
    private int greenCount;
    private int blueCount;
    private int mixedCount;


    public ColorPixelCounter(int redCount, int greenCount, int blueCount, int mixedCount) {
        this.redCount = redCount;
        this.greenCount = greenCount;
        this.blueCount = blueCount;
        this.mixedCount = mixedCount;
    }

    public ColorPixelCounter() {
    }

    public int getRedCount() {
        return redCount;
    }

    public void setRedCount(int redCount) {
        this.redCount = redCount;
    }

    public int getGreenCount() {
        return greenCount;
    }

    public void setGreenCount(int greenCount) {
        this.greenCount = greenCount;
    }

    public int getBlueCount() {
        return blueCount;
    }

    public void setBlueCount(int blueCount) {
        this.blueCount = blueCount;
    }

    public int getMixedCount() {
        return mixedCount;
    }

    public void setMixedCount(int mixedCount) {
        this.mixedCount = mixedCount;
    }
}
