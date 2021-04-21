package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

import static byow.Core.Engine.WIDTH;
import static byow.Core.Engine.HEIGHT;

public class Game {
    private String gameString;
    private long seed;
    private Random r;
    private int replayIndex;
    private String replayString;

    private TETile[][] world;
    private Position avatarPos;

    /**
     * Creates new game object from scratch
     */
    public Game(long seed) {
        this.seed = seed;
        initGame(seed);
    }

    /**
     * Uses data saved in text file to load previous game
     * @Source https://www.w3schools.com/java/java_files_read.asp
     */
    public Game() {
        try {
            File myObj = new File("game.txt");
            Scanner myReader = new Scanner(myObj);
            this.seed = Long.parseLong(myReader.nextLine());
            String savedString = myReader.nextLine();
            myReader.close();
            //generate world with seed
            initGame(seed);
            //for char in game string, process char
            for (char c : savedString.toCharArray()) {
                processChar(c);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void startReplay() {
        replayIndex = 0;
        replayString = String.copyValueOf(gameString.toCharArray());
        initGame(seed);
    }

    public boolean hasNextReplayFrame() {
        return replayIndex < replayString.length();
    }

    public void processNextReplayFrame() {
        processChar(replayString.charAt(replayIndex));
        replayIndex++;
    }

    private void initGame(long seeds) {
        gameString = "";
        r = new Random(seeds);
        MapGenerator mg = new MapGenerator(r);
        world = mg.generateMap();
        avatarPos = getValidAvatarStart(world, r);
        world[avatarPos.xPos][avatarPos.yPos] = Tileset.AVATAR;
    }

    private void avatarMovementCommand(char c) {
        if (c == 'W' || c == 'w') {
            setAvatarPos(avatarPos.xPos, avatarPos.yPos + 1);
        } else if (c == 'A' || c == 'a') {
            setAvatarPos(avatarPos.xPos - 1, avatarPos.yPos);
        } else if (c == 'S' || c == 's') {
            setAvatarPos(avatarPos.xPos, avatarPos.yPos - 1);
        } else if (c == 'D' || c == 'd') {
            setAvatarPos(avatarPos.xPos + 1, avatarPos.yPos);
        } else {
            setAvatarPos(avatarPos.xPos, avatarPos.yPos);
        }
    }

    private void setAvatarPos(int xPos, int yPos) {
        if (world[xPos][yPos].equals(Tileset.FLOOR)) {
            world[avatarPos.xPos][avatarPos.yPos] = Tileset.FLOOR;
            avatarPos = new Position(xPos, yPos);
            world[avatarPos.xPos][avatarPos.yPos] = Tileset.AVATAR;
        }
    }

    private Position getValidAvatarStart(TETile[][] world3, Random random) {
        while (true) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            if (world3[x][y] == Tileset.FLOOR) {
                return new Position(x, y);
            }
        }
    }

    /**
     * Adds a move to the saved game data
     * @param move Single character move to be added to saved game data
     */
    public void saveMove(char move) {
        gameString += move;
    }


    /**
     * Saves game to text file at specified location
     * @Source: https://stackoverflow.com/questions/2885173/
     * how-do-i-create-a-file-and-write-to-it-in-java
     */
    public void saveGame() {
        //seed on one line
        //gameString on the next
        File file = new File("./game.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter("game.txt", StandardCharsets.UTF_8);
            writer.println(seed);
            writer.println(gameString);
            writer.close();

        } catch (IOException exception) {
            System.out.println(exception);
            System.exit(0);
        }
    }

    public static boolean isGameCharacter(char c) {
        return c == 'W' || c == 'w'
                || c == 'A' || c == 'a'
                || c == 'S' || c == 's'
                || c == 'D' || c == 'd';
    }

    public void processChar(char c) {
        avatarMovementCommand(c);
        saveMove(c);
    }

    public TETile[][] getWorld() {
        return world;
    }
}
