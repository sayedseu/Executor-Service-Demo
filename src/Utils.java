import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Utils {

    public static final String getFileName = "harley-davidson.jpg";
    public static final int getAvailableOfProcessor = Runtime.getRuntime().availableProcessors();

    public static BufferedImage getBufferedImage(){
        File file = new File(getFileName);
        try {
            return ImageIO.read(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] splitRGB(int rgb) {
        int colors[] = new int[3];
        for (int i = 0; i < 3; i++) {
            colors[i] = (rgb >> (8 * i)) & 0xFF;
        }
        return colors;
    }



    public static int rgbToGrayscale(int colors[]) {
        return (int) (colors[2] * 0.2126 + colors[1] * 0.7152 + colors[0] * 0.0722);
    }

    public static int[] calculateHistogram(String filename) {
        int histogram[] = new int[256];
        try {
            File file = new File(filename);
            BufferedImage image = ImageIO.read(file);

            int width = image.getWidth();
            int height = image.getHeight();
            for (int c = 0; c < width; c++) {
                for (int r = 0; r < height; r++) {
                    int grayscale = rgbToGrayscale(splitRGB(image.getRGB(c, r)));
                    histogram[grayscale]++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return histogram;
    }
}
