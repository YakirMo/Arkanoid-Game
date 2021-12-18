package menu;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Fill;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private String title;
    private KeyboardSensor ks;
    private AnimationRunner runner;
    private List<String> messages;
    private List<T> options;
    private List<Menu<T>> subMenu;
    private List<String> keys;
    private T status;
    private boolean stop;
    private List<Boolean> isOpt;
    private Image menuImg;

    /**
     * Instantiates a new Menu animation.
     *
     * @param title the title
     * @param ks    the ks
     * @param ar    the ar
     */
    public MenuAnimation(String title, KeyboardSensor ks, AnimationRunner ar) {
        this.title = title;
        this.ks = ks;
        this.runner = ar;
        this.messages = new ArrayList<String>();
        this.options = new ArrayList<T>();
        this.subMenu = new ArrayList<Menu<T>>();
        this.keys = new ArrayList<String>();
        this.stop = false;
        this.isOpt = new ArrayList<Boolean>();
        this.menuImg = Fill.stringToImage("Image/menuImg.jpg");
    }
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.options.add(returnVal);
        this.isOpt.add(true);
        this.subMenu.add(null);
    }
    @Override
    public T getStatus() {
        return this.status;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawImage(0, 0, this.menuImg);
        d.setColor(Color.black);
        d.drawRectangle(70, 200, 325, 200);
        d.setColor(Color.orange);
        d.fillRectangle(70, 200, 325, 200);
        for (int i = 0; i < this.keys.size(); i++) {
            d.setColor(Color.black);
            d.drawText(100, 250 + i * 50, "(" + (this.keys.get(i)) + ")", 40);
            d.setColor(Color.white);
            d.drawText(98, 246 + i * 50, "(" + (this.keys.get(i)) + ")", 40);
            d.setColor(Color.black);
            d.drawText(170, 250 + i * 50, this.messages.get(i), 40);
            d.setColor(Color.white);
            d.drawText(168, 246 + i * 50, this.messages.get(i), 40);
        }
        for (int i = 0; i < this.keys.size(); i++) {
            if (this.ks.isPressed(this.keys.get(i))) {
                if (this.isOpt.get(i)) {
                    this.status = this.options.get(i);
                    this.stop = true;
                } else {
                    this.runner.run(this.subMenu.get(i));
                    this.status = this.subMenu.get(i).getStatus();
                    this.subMenu.get(i).resrtStatus();
                    this.stop = true;
                    break;
                }
            }
        }
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
    @Override
    public void resrtStatus() {
        this.status = null;
        this.stop = false;
    }
    @Override
    public void addSubMenu(String key, String message, Menu<T> subM) {
        this.keys.add(key);
        this.messages.add(message);
        this.subMenu.add(subM);
        this.options.add(null);
        this.isOpt.add(false);
    }
}
