package game;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Fill.
 */
public class Fill {
    private Color col;
    private Image img;

    /**
     * Instantiates a new Fill.
     *
     * @param col the col
     * @param img the img
     */
    public Fill(Color col, Image img) {
        this.col = col;
        this.img = img;
    }

    /**
     * Gets col.
     *
     * @return the col
     */
    public Color getCol() {
        return this.col;
    }

    /**
     * Gets img.
     *
     * @return the img
     */
    public Image getImg() {
        return this.img;
    }

    /**
     * Is img boolean.
     *
     * @return the boolean
     */
    public boolean isImg() {
        if (this.col == null) {
            return true;
        }
        return false;
    }

    /**
     * Is col boolean.
     *
     * @return the boolean
     */
    public boolean isCol() {
        if (this.img == null) {
            return true;
        }
        return false;
    }

    /**
     * String to color java . awt . color.
     *
     * @param s the s
     * @return the java . awt . color
     */
    public static java.awt.Color stringToColor(String s) {
        if (s.contains("RGB")) {
            String[] helper1 = s.split("\\(");
            String[] helper2 = helper1[2].split("\\,");
            String[] helper3 = helper2[2].split("\\)");
            int r = Integer.parseInt(helper2[0]);
            int g = Integer.parseInt(helper2[1]);
            int b = Integer.parseInt(helper3[0]);
            return new Color(r, g, b);
        }
        String[] helper1 = s.split("\\(");
        String[] helper2 = helper1[1].split("\\)");
        Map<String, Color> colors = new HashMap<String, Color>();
        colors.put("red", java.awt.Color.RED);
        colors.put("yellow", java.awt.Color.YELLOW);
        colors.put("blue", java.awt.Color.BLUE);
        colors.put("cyan", java.awt.Color.CYAN);
        colors.put("green", java.awt.Color.GREEN);
        colors.put("magenta", java.awt.Color.MAGENTA);
        colors.put("black", java.awt.Color.BLACK);
        colors.put("white", java.awt.Color.WHITE);
        colors.put("pink", java.awt.Color.PINK);
        colors.put("orange", java.awt.Color.ORANGE);
        colors.put("gray", java.awt.Color.GRAY);
        colors.put("lightGray", java.awt.Color.LIGHT_GRAY);
        colors.put("darkGray", java.awt.Color.DARK_GRAY);
        return colors.get(helper2[0]);
    }

    /**
     * String to image image.
     *
     * @param path the path
     * @return the image
     */
    public static Image stringToImage(String path) {
        Image img = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("Failed to open the image");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.err.println("Closing Image failed");
                }
            }
        }
        return img;
    }
}
