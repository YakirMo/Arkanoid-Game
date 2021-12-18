package filesio;

import game.BlockCreator;
import game.BlockFactory;
import game.Fill;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class instantiates Blocks definition reader.
 */
public class BlocksDefinitionReader {

    /**
     * Split string to file's path.
     *
     * @param s the string
     * @return the file's path
     */
    public static String splitStringToPath(String s) {
        String[] str = s.split("\\(");
        String path = str[1].split("\\)")[0];
        return path;
    }

    /**
     * This method reads the blocks' definitions and pattern in order to create them from file.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        int defHeight = 0, defWidth = 0, defHit = 0, spacerWidth = 0;
        Color stroke = null;
        Map<Integer, Fill> fill = new HashMap<Integer, Fill>();
        Map<String, BlockCreator> blocksAndSymbols = new HashMap<String, BlockCreator>();
        Map<String, Integer> spacers = new HashMap<String, Integer>();
        String symbol = null, spacerSymbol = null;
        try {
            do {
                line = ((BufferedReader) br).readLine();
                String[] param = line.split(" ");
                line = line.trim();
                if (!line.equals("") && !line.startsWith("#")) {
                    if (param[0].equals("default")) {
                        for (int i = 0; i < param.length; i++) {
                            String[] subString = param[i].split(":");
                            if (subString[0].equals("height")) {
                                defHeight = Integer.parseInt(subString[1]);
                            } else if (subString[0].equals("width")) {
                                defWidth = Integer.parseInt(subString[1]);
                            } else if (subString[0].equals("hit_points")) {
                                defHit = Integer.parseInt(subString[1]);
                            } else if (subString[0].equals("stroke")) {
                                stroke = Fill.stringToColor(subString[1]);
                            } else if (subString[0].equals("fill")) {
                                if (subString[1].contains("image")) {
                                    String path = BlocksDefinitionReader.splitStringToPath(subString[1]);
                                    fill.put(0, new Fill(null, Fill.stringToImage(path)));
                                } else {
                                    fill.put(0, new Fill(Fill.stringToColor(subString[1]), null));
                                }
                            } else if (subString[0].equals("fill-")) {
                                if (subString[1].contains("image")) {
                                    String path = BlocksDefinitionReader.splitStringToPath(subString[1]);
                                    int num = Integer.parseInt(subString[0].split("\\-")[1]);
                                    fill.put(num, new Fill(null, Fill.stringToImage(path)));
                                } else {
                                    int num = Integer.parseInt(subString[0].split("\\-")[1]);
                                    fill.put(num, new Fill(Fill.stringToColor(subString[1]), null));
                                }
                            }
                        }
                    } else if (param[0].equals("bdef")) {
                        Map<Integer, Fill> bdefFill = new HashMap<Integer, Fill>();
                        int height = defHeight, width = defWidth, hits = defHit;
                        Color bdefStroke = stroke;
                        java.util.List<Integer> keys = new ArrayList<Integer>(fill.keySet());
                        for (int i = 0; i < fill.size(); i++) {
                            bdefFill.put(keys.get(i), fill.get(i));
                        }
                        for (int i = 1; i < param.length; i++) {
                            String[] subString = param[i].split(":");
                            if (subString[0].equals("symbol")) {
                                symbol = subString[1];
                            } else if (subString[0].equals("height")) {
                                height = Integer.parseInt(subString[1]);
                            } else if (subString[0].equals("width")) {
                                width = Integer.parseInt(subString[1]);
                            } else if (subString[0].equals("hit_points")) {
                                hits = Integer.parseInt(subString[1]);
                            } else if (subString[0].equals("stroke")) {
                                bdefStroke = Fill.stringToColor(subString[1]);
                            } else if (subString[0].equals("fill")) {
                                if (subString[1].contains("image")) {
                                    String path = BlocksDefinitionReader.splitStringToPath(subString[1]);
                                    bdefFill.put(0, new Fill(null, Fill.stringToImage(path)));
                                } else {
                                    bdefFill.put(0, new Fill(Fill.stringToColor(subString[1]), null));
                                }
                            } else if (subString[0].contains("fill-")) {
                                if (subString[1].contains("image")) {
                                    String path = BlocksDefinitionReader.splitStringToPath(subString[1]);
                                    int num = Integer.parseInt(subString[0].split("\\-")[1]);
                                    bdefFill.put(num, new Fill(null, Fill.stringToImage(path)));
                                } else {
                                    int num = Integer.parseInt(subString[0].split("\\-")[1]);
                                    bdefFill.put(num, new Fill(Fill.stringToColor(subString[1]), null));
                                }
                            }
                        }
                        if (height <= 0 || width <= 0 || hits <= 0 || bdefFill.isEmpty() || symbol == null) {
                            System.out.println("Invalid parameters");
                            System.exit(1);
                        } else {
                            blocksAndSymbols.put(symbol, new BlockFactory(height, width, hits, bdefStroke, bdefFill));
                        }
                    } else if (param[0].equals("sdef")) {
                        for (int i = 0; i < param.length; i++) {
                            String[] subString = param[i].split(":");
                            if (subString[0].equals("symbol")) {
                                spacerSymbol = subString[1];
                            } else if (subString[0].equals("width")) {
                                spacerWidth = Integer.parseInt(subString[1]);
                            }
                        }
                        spacers.put(spacerSymbol, spacerWidth);
                    }
                }
            } while (line != null);
        } catch (IOException e) {
            System.err.println("Failed reading file");
        } finally {
            if (br != null) {
                return new BlocksFromSymbolsFactory(blocksAndSymbols, spacers);
            }
        }
        return new BlocksFromSymbolsFactory(blocksAndSymbols, spacers);
    }
}
