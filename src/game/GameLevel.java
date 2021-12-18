package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import basic.Counter;
import basic.Velocity;
import basic.Collidable;
import basic.Sprite;
import basic.HitListener;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import levels.LevelInformation;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * This class creates a game instance by using objects and methods from different classes.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment envi;
    private Sleeper sleeper;
    /**
     * The Vert.
     */
    static final int VERT = 600;
    /**
     * The Horiz.
     */
    static final int HORIZ = 800;
    /**
     * The Blocksize.
     */
    static final int BLOCKSIZE = 20;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private Counter lives;
    private List<Block> blocks;
    private ScoreIndicator scoreInd;
    private AnimationRunner runner;
    private boolean running;
    private biuoop.KeyboardSensor keyboard;
    private LevelInformation level;
    private List<Velocity> lvBallsVeloc;

    /**
     * constructor.
     *
     * @param level the level
     * @param ks    the ks
     * @param ar    the ar
     * @param score the score
     * @param lives the lives
     */
    public GameLevel(LevelInformation level, KeyboardSensor ks, AnimationRunner ar, Counter score, Counter lives) {
        //this.gui = new GUI("Arkanoid", HORIZ, VERT);
        this.envi = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.sleeper = new Sleeper();
        this.blocks = new ArrayList<Block>();
        this.remainingBlocks = new Counter(0);
        this.score = score;
        this.scoreInd = null;
        this.lives = lives;
        this.runner = ar;
        this.keyboard = ks;
        this.level = level;
        this.lvBallsVeloc = level.initialBallVelocities();
        this.remainingBalls = new Counter(0);
    }

    /**
     * This method adds a collidable object to the game environment.
     *
     * @param c - a collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.envi.addCollidable(c);
    }

    /**
     * This method adds a sprite to the Sprites collection.
     *
     * @param s - a sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * This method initializes the game by creating and initializing - gui, borders, blocks, paddle and balls.
     * It also adds listeners to objects in the game (like balls, blocks, etc) in order to determine in 'playOneTurn'
     * if a turn has ended
     */
    public void initialize() {
       /* Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color col = new Color(r, g, b); */
       //Color col = new Color(139, 139, 139);
        Map<Integer, Fill> fillList = new HashMap<Integer, Fill>();
        Fill newFill = new Fill(Color.gray.brighter(), null);
        fillList.put(0, newFill);
        //ass6 addition
        this.level.getBackground().addToGame(this);
        //ass5 addition
        HitListener blockRemover = new BlockRemover(this, this.remainingBlocks);
        HitListener ballRemover = new BallRemover(this, this.remainingBalls);
        HitListener scoreTracker = new ScoreTrackingListener(this.score);
        LivesIndicator livesInd = new LivesIndicator(this.lives);
        NamesIndicator namesInd = new NamesIndicator(this.level.levelName());
        this.scoreInd = new ScoreIndicator(this.score);
        Block topBorder = new Block(0, 15, BLOCKSIZE,
                HORIZ, 0, Color.gray.brighter(), fillList);
        Block rightBorder = new Block(HORIZ - BLOCKSIZE, BLOCKSIZE + 15, VERT - 15 - BLOCKSIZE,
                BLOCKSIZE, 0, Color.gray.brighter(), fillList);
        Block bottomBorder = new Block(0, VERT, BLOCKSIZE, HORIZ - 2 * BLOCKSIZE,
                0, Color.gray.brighter(), fillList);
        Block leftBorder = new Block(0, BLOCKSIZE + 15, VERT - 15 - BLOCKSIZE,
                BLOCKSIZE, 0, Color.gray.brighter(), fillList);
        topBorder.addToGame(this);
        rightBorder.addToGame(this);
        bottomBorder.addToGame(this);
        leftBorder.addToGame(this);
        namesInd.addToGame(this);
        scoreInd.addToGame(this);
        livesInd.addToGame(this);
        //ass 5 addition
        remainingBlocks.increase(this.level.blocks().size());
        bottomBorder.addHitListener(ballRemover);
        int i = 0;
        for (Block block : this.level.blocks()) {
            Block copyBlock = new Block(block);
            copyBlock.addToGame(this);
            copyBlock.addHitListener(blockRemover);
            copyBlock.addHitListener(scoreTracker);
        }
    }

    /**
     * This method play's on turn of the game and notifies all the sprites in the game at the same time
     * that a time unit has passed so all the objects in the game will be synchronized.
     * It also checks if a turn has ended (by checking the amount of balls, blocks and lives remaining)
     */
    public void playOneTurn() {
        Paddle pad = this.initBallsAndPaddle();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
        this.removeSprite(pad);
        this.envi.getColList().remove(pad);
    }

    /**
     * This method set's the blocks' colors.
     *
     * @param i - in order to choose a different color for each row.
     * @return - a color for a row of blocks
     */
    public Color setColor(int i) {
        Color[] cols = new Color[6];
        cols[0] = new Color(153, 153, 255);
        cols[1] = new Color(153, 255, 204);
        cols[2] = new Color(255, 255, 153);
        cols[3] = new Color(255, 153, 153);
        cols[4] = new Color(255, 204, 153);
        cols[5] = new Color(102, 255, 255);
        return cols[i];
    }

    /**
     * A method that removes a collidable from a game (by removing the collidable from the collidables' list).
     *
     * @param c - A collidable to remove from a game
     */
//ass 5 methods
    public void removeCollidable(Collidable c) {
        this.envi.getColList().remove(c);
    }

    /**
     * A method that removes a sprite from a game (by removing the sprite from the sprites' list).
     *
     * @param s - A sprite to remove from a game
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSprites().remove(s);
    }

    /**
     * A method that adds a block to a game (by adding a block to the blocks' list).
     *
     * @param b - A block to be added to a game
     */
    public void addBlock(Block b) {
        this.blocks.add(b);
    }

    /**
     * A method that removes a block from a game (by removing a block from the blocks' list).
     *
     * @param b - A block to be removed from a game
     */
    public void removeBlock(Block b) {
        this.blocks.remove(b);
    }

    /**
     * A method that determines if a player has run out of lives.
     * If there are lives remaining, it will call playOneTurn (so another turn is played with the lives left)
     * If there are no lives left, it will end the game (by closing the gui)
     */
    public void run() {
        while (this.lives.getValue() != 0 && this.remainingBlocks.getValue() != 0) {
            playOneTurn();
        }
        return;
    }

    //ass 6 methods

    /**
     * Should stop boolean.
     *
     * @return the boolean - if the runner is running
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Initializes balls and a paddle.
     *
     * @return the paddle (and balls)
     */
    public Paddle initBallsAndPaddle() {
        //randomize pad color
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color col = new Color(r, g, b);
        //add paddle
        Paddle pad = new Paddle((double) (this.HORIZ - this.level.paddleWidth()) / 2,
                (double) this.VERT - 25, 20, this.level.paddleWidth(), this.BLOCKSIZE,
                col, this.keyboard);
        pad.addToGame(this);
        //add balls
        for (Velocity i : this.lvBallsVeloc) {
            Ball ball = new Ball((this.HORIZ / 2), ((this.VERT - this.BLOCKSIZE - 30)), 5, Color.red, this.envi);
            ball.setVelocity(i);
            this.remainingBalls.increase(1);
            ball.addToGame(this);
        }
        return pad;
    }

    /**
     * Do one frame.
     * checks if a pause screen is requested (if the user press "p")
     * also checks amount of remaining blocks and remaining balls to determine
     * if a level or a turn has been finished (by stopping the animation runner)
     *
     * @param d the Drawsurface to draw on
     */
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
  /*      if (this.keyboard.isPressed("q")) {
            this.remainingBlocks = new Counter(0);
        } */
        if (this.remainingBlocks.getValue() == 0) {
            this.running = false;
        } else {
            if (this.remainingBalls.getValue() == 0) {
                this.running = false;
            }
        }
    }

    /**
     * Win boolean.
     *
     * @return the boolean - win (true) or lose (false)
     */
    public boolean win() {
        return this.remainingBlocks.getValue() == 0;
    }
}
