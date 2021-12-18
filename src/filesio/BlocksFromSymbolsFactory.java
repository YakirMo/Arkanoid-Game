package filesio;

import game.Block;
import game.BlockCreator;

import java.util.Map;

/**
 * This class instantiates factory that defines Blocks from symbols.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, BlockCreator> blockCreator;
    private Map<String, Integer> spacerWidth;

    /**
     * Instantiates a new Blocks from symbols factory.
     *
     * @param blockCreator the block creator
     * @param spacerWidth  the spacer width
     */
    public BlocksFromSymbolsFactory(Map<String, BlockCreator> blockCreator, Map<String, Integer> spacerWidth) {
        this.blockCreator = blockCreator;
        this.spacerWidth = spacerWidth;
    }

    /**
     * Is space symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidth.containsKey(s);
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isBlockSymbol(String s) {
        return blockCreator.containsKey(s);
    }

    /**
     * Gets spacer width.
     *
     * @param s the s
     * @return the spacer width
     */
    public int getSpacerWidth(String s) {
        return this.spacerWidth.get(s);
    }

    /**
     * Gets block.
     *
     * @param s    the s
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
    public Block getBlock(String s, int xpos, int ypos) {
        Block b = this.blockCreator.get(s).create(xpos, ypos);
        return b;
    }
}
