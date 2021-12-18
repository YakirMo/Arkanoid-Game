package filesio;

import levels.LevelInformation;
import menu.MenuSelect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level set reader.
 */
public class LevelSetReader {

    /**
     * From reader list.
     *
     * @param args the args
     * @return the list
     */
    public static List<MenuSelect> fromReader(String[] args) {
        List<MenuSelect> returnVal = new ArrayList<MenuSelect>();
        List<LevelInformation> levelSet;
        LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
        String line;
        String path;
        try {
            String key = null;
            String message = null;
            if (args.length == 0) {
                path = ("level_sets.txt");
            } else {
                path = args[0];
            }
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
            while ((line = ((BufferedReader) reader).readLine()) != null) {
                String[] param = line.split(":");
                key = param[0];
                message = param[1];
                line = ((BufferedReader) reader).readLine();
                Reader levelReader =
                        new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(line));
                levelSet = levelSpecificationReader.fromReader(levelReader);
                returnVal.add(new MenuSelect(key, message, levelSet));
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Opening file failed");
        }
        return returnVal;
    }
}
