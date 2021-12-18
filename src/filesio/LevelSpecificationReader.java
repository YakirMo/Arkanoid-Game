package filesio;

import basic.Velocity;
import game.Block;
import game.Fill;
import levels.GenericLevel;
import levels.LevelInformation;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    private int elements = 8;

    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levels = new ArrayList<>();
        BufferedReader br = new BufferedReader(reader);
        String levelName = null;
        List<Velocity> ballsVeloc = new ArrayList<Velocity>();
        Map<Integer, String> blocksLocation = new HashMap<Integer, String>();
        List<Block> blocks = new ArrayList<Block>();
        String blockDef = null, image, color, line;
        Fill background = null;
        int paddleSpeed = 0, paddleWidth = 0, blocksNum = 0, ballNum = 0, rowHeight = 0;
        Image img;
        Color col;
        int x = 0, y = 0;
        try {
            // Create file reader and file writer objects and wrap it
            while ((line = ((BufferedReader) br).readLine()) != null) {
                line = line.trim();
                if (!line.equals("") && !line.startsWith("#")) {
                    if (line.equals("START_LEVEL")) {
                        line = ((BufferedReader) br).readLine();
                    }
                    if (line.equals("START_BLOCKS")) {
                        int i = 1;
                        line = ((BufferedReader) br).readLine();
                        while (!line.equals("END_BLOCKS")) {
                            blocksLocation.put(i, line);
                            i++;
                            line = ((BufferedReader) br).readLine();
                        }
                        elements--;
                    }
                    if (line.equals("END_LEVEL")) {
                        if (elements == 0) {
                            blocks = this.blockRead(blocksLocation, x, y, rowHeight, blockDef, br);
                            Map<String, Integer> padDetails = new HashMap<String, Integer>();
                            padDetails.put("paddle width", paddleWidth);
                            padDetails.put("paddle speed", paddleSpeed);
                            GenericLevel genericLevel = new GenericLevel(levelName, ballsVeloc, background,
                                    padDetails, blocksNum, ballNum, blocks);
                            levels.add(genericLevel);
                            //reset variables
                            this.elements = 8;
                            levelName = null;
                            ballsVeloc = new ArrayList<Velocity>();
                            background = null;
                            image = "";
                            img = null;
                            color = "";
                            col = null;
                            paddleSpeed = 0;
                            paddleWidth = 0;
                            ballNum = 0;
                            blocksNum = 0;
                            blocks = new ArrayList<Block>();
                            blocksLocation = new HashMap<Integer, String>();
                            x = 0;
                            y = 0;
                            rowHeight = 0;
                            blockDef = null;
                        } else {
                            System.out.println("Not enough parameters");
                        }
                    }
                    String[] parts = line.split(":");
                    switch (parts[0]) {
                        case "level_name":
                            levelName = parts[1];
                            elements--;
                            break;
                        case "ball_velocities":
                            String[] veloc = parts[1].split(" ");
                            for (int i = 0; i < veloc.length; i++) {
                                //split with coma (,)
                                String[] param = veloc[i].split(",");
                                //get speed and angle
                                int angle = Integer.parseInt(param[0]);
                                int speed = Integer.parseInt(param[1]);
                                Velocity vel = Velocity.fromAngleAndSpeed(angle, speed);
                                ballsVeloc.add(vel);
                                ballNum++;
                            }
                            elements--;
                            elements--;
                            break;
                        case "background":
                            String[] param = parts[1].split("\\(");
                            if (param[0].equals("image")) {
                                image = param[1].split("\\)")[0];
                                img = Fill.stringToImage(image);
                                background = new Fill(null, img);
                                //color
                            } else if (param[0].equals("color")) {
                                if (param[1].equals("RGB")) {
                                    color = "(" + param[1] + "(" + param[2];
                                } else {
                                    color = param[1].split("\\)")[0];
                                    color = "(" + color + ")";
                                }
                                col = Fill.stringToColor(color);
                                background = new Fill(col, null);
                            }
                            elements--;
                            break;
                        case "paddle_speed":
                            paddleSpeed = Integer.parseInt(parts[1]);
                            elements--;
                            break;
                        case "paddle_width":
                            paddleWidth = Integer.parseInt(parts[1]);
                            elements--;
                            break;
                        case "block_definitions":
                            blockDef = parts[1];
                            break;
                        case "blocks_start_x":
                            x = Integer.parseInt(parts[1]);
                            break;
                        case "blocks_start_y":
                            y = Integer.parseInt(parts[1]);
                            break;
                        case "row_height":
                            rowHeight = Integer.parseInt(parts[1]);
                            break;
                        case "num_blocks":
                            blocksNum = Integer.parseInt(parts[1]);
                            elements--;
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levels;
    }

    /**
     * Block read list.
     *
     * @param blockLocation the block location
     * @param x             the x
     * @param y             the y
     * @param rowHeight     the row height
     * @param path          the path
     * @param reader        the reader
     * @return the list
     */
    public List<Block> blockRead(Map<Integer, String> blockLocation, int x, int y, int rowHeight, String path,
                                 java.io.Reader reader) {
        List<Block> blockList = new ArrayList<>();
        int xpos = x, ypos = y;
        try {
            reader =
                    new BufferedReader(
                            new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
            BlocksFromSymbolsFactory factory = BlocksDefinitionReader.fromReader(reader);
            for (Integer key : blockLocation.keySet()) {
                String data = blockLocation.get(key);
                for (int i = 0; i < data.length(); i++) {
                    String symbol = String.valueOf(data.charAt(i));
                    if (factory.isSpaceSymbol(symbol)) {
                        xpos += factory.getSpacerWidth(symbol);
                    } else if (factory.isBlockSymbol(symbol)) {
                        Block b = factory.getBlock(symbol, xpos, ypos);
                        blockList.add(b);
                        xpos += b.getCollisionRectangle().getWidth();
                    }
                }
                ypos += rowHeight;
                xpos = x;
            }
        } catch (Exception e) {
            System.err.println("File loading failed");
        }
        return blockList;
    }
}
