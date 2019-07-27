import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class Task implements Callable<int[]> {

    private BufferedImage image;
    private int widthStart;
    private int widthStop;

    public Task(BufferedImage image, int widthStart, int widthStop) {
        this.image = image;
        this.widthStart = widthStart;
        this.widthStop = widthStop;
    }

    @Override
    public int[] call() throws Exception {
        int[] histogram = new int[256];
        int height = image.getHeight();
        for (int column = widthStart; column<widthStop; column++ ){
            for (int row = 0; row<height; row++){
                int grayScale = Utils.rgbToGrayscale(Utils.splitRGB(image.getRGB(column,row)));
                histogram[grayScale]++;
            }
        }
        return histogram;
    }
}
