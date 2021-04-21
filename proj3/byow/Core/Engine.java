package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.awt.*;

public class Engine {
    //TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;

    private enum State {
        DRAW_MENU,
        MENU,
        START_NEW,
        LOAD,
        REPLAY,
        GAME,
        QUIT
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        InputSource is = new KeyboardInputSource();
        play(is, true);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     * @source https://stackoverflow.com/questions/7683448/
     * in-java-how-to-get-substring-from-a-string-till-a-character-c
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        InputSource is = new StringInputDevice(input);
        return play(is, false);
    }

    private TETile[][] play(InputSource input, boolean rE) {
        TERenderer ter = null;
        Game game = null;
        long seed = 0;
        boolean command = false;

        State state;
        if (rE) {
            ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            state = State.DRAW_MENU;
        } else {
            state = State.MENU;
        }
        while (input.possibleNextInput()) {
            char c = input.getNextKey();
            switch (state) {
                case DRAW_MENU:
                    drawStartMenu();
                    state = State.MENU;
                    break;
                case MENU:
                    if (c == 'N' || c == 'n') {
                        //start new game
                        if (rE) {
                            drawGettingSeedPage(Long.toString(seed));
                        }
                        state = State.START_NEW;
                    }
                    if (c == 'L' || c == 'l') {
                        //load game
                        game = new Game();
                        state = State.GAME;
                    }
                    if (c == 'R' || c == 'r') {
                        //replay moves
                        game = new Game();
                        game.startReplay();
                        state = State.REPLAY;
                    }
                    if (c == 'Q' || c == 'q') {
                        //quit without generating world
                        return null;
                    }
                    break;
                case START_NEW:
                    if (Character.isDigit(c)) {
                        seed = seed * 10 + Character.getNumericValue(c);
                        if (rE) {
                            drawGettingSeedPage(Long.toString(seed));
                        }
                    }
                    if (c == 'S' || c == 's') {
                        game = new Game(seed);
                        state = State.GAME;
                    }
                    break;
                case REPLAY:
                    while (game.hasNextReplayFrame()) {
                        game.processNextReplayFrame();
                        if (rE) {
                            ter.renderFrame(game.getWorld());
                            drawHud(getMousePos(), currentTime(), game.getWorld());
                            delayMillis(5);
                        }
                    }
                    state = State.GAME;
                    break;
                case GAME:
                    //look for characters to break out of this state
                    if (command && (c == 'Q' || c == 'q')) {
                        state = State.QUIT;
                        break;
                    }
                    if (c == ':') {
                        command = true;
                    } else if (c != '\0') {
                        command = false;
                    }
                    //if the character is a game command
                    if (Game.isGameCharacter(c)) {
                        game.processChar(c);
                    }
                    if (rE) {
                        ter.renderFrame(game.getWorld());
                        drawHud(getMousePos(), currentTime(), game.getWorld());
                    }
                    break;
                case QUIT:
                    game.saveGame();
                    return game.getWorld();

                default:
                    drawStartMenu();
                    state = State.MENU;

            }
        }
        if (game != null) {
            return game.getWorld();
        }
        return null;
    }

    private Position getMousePos() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        return new Position(Math.min(x, WIDTH - 1), Math.min(y, HEIGHT - 1));
    }

    public void drawFrame(String s) {

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Manaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);

        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT, s);
        StdDraw.setFont(fontSmall);

        StdDraw.show();
    }

    private String currentTime() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(date));
        return today;
    }

    private void drawHud(Position position, String today, TETile[][] world2) {
        //StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.line(0, HEIGHT - 2, WIDTH, HEIGHT - 2);
        StdDraw.textRight(WIDTH, HEIGHT - 1, world2[position.xPos][position.yPos].description());
        StdDraw.textLeft(0, HEIGHT - 1, today);

        StdDraw.show();
    }

    private void drawStartMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Serif", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        // Draw title
        Font fontTitle = new Font("Serif", Font.BOLD, 60);
        StdDraw.setFont(fontTitle);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 15 / 20, "61B: THE GAME");
        // Draw menu series
        Font fontMenu = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontMenu);
        // draw input screen
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 10 / 20, "New Game (N)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 8 / 20, "Load Game (L)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 6 / 20, "Replay (R)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 4 / 20, "Quit (Q)");

        StdDraw.show();
    }

    private void drawGettingSeedPage(String userSeed) {
        //StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        Font fontSeed = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSeed);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 15 / 20, "Enter your Seed and,"
               + " press 'S' to confirm your Seed ");

        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 10 / 20, userSeed);
        font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontSeed);
        StdDraw.show();
    }

    private void delayMillis(long delay) {
        long start = System.currentTimeMillis();
        long i = 0;
        while (System.currentTimeMillis() < start + delay) {
            i++;
        }
    }
}
