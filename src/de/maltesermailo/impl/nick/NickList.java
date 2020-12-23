package de.maltesermailo.impl.nick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;

import de.maltesermailo.impl.MinigameAPI;

/**
 * Represents a List of Several Nicks loaded from a File.
 */
public class NickList {

    private static ArrayList<String> names;

    public static ArrayList<String> getNames() {
        return names;
    }

    /**
     * Loads the Nicks from the nicks.txt in the Havoc Folder.
     */
    public static void loadNicks() {
        File nicks = new File(MinigameAPI.instance().getDataFolder() + File.separator + "nicks.txt");
        try {
            if (!nicks.exists())
                nicks.createNewFile();
            else {
                names = (ArrayList<String>) Files.readAllLines(nicks.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a Random Nick from the NickList.
     *
     * @return The random Nick.
     */
    public static String getRandomNick() {
        Random random = new Random();
        
        String nick = names.get(random.nextInt(names.size()));
        
        boolean nickFound = MinigameAPI.getNickHandler().isNickFree(nick);
        
        int notEnoughNames = 0;
        
        while (!nickFound && notEnoughNames < names.size() - 1) {
            nick = names.get(random.nextInt(names.size()));
            nickFound = MinigameAPI.getNickHandler().isNickFree(nick);
            notEnoughNames++;
        }
        
        if (notEnoughNames == names.size() - 1)
            return null;
        
        return nick;
    }
}