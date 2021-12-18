package game;

import java.awt.Color;
import java.util.Map;

/**
 * The type Block factory.
 */
public class BlockFactory implements BlockCreator {
    private int height;
    private int width;
    private int hits;
    private Color stroke;
    private Map<Integer, Fill> fill;

    /**
     * Instantiates a new Block factory.
     *
     * @param height the height
     * @param width  the width
     * @param hits   the hits
     * @param stroke the stroke
     * @param fill   the fill
     */
    public BlockFactory(int height, int width, int hits, Color stroke, Map<Integer, Fill> fill) {
        this.height = height;
        this.width = width;
        this.hits = hits;
        this.stroke = stroke;
        this.fill = fill;
    }
    @Override
    public Block create(int xpos, int ypos) {
        return new Block(xpos, ypos, this.height, this.width, this.hits, this.stroke, this.fill);
    }
}
