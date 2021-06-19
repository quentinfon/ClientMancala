package ensi.controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {

    private HashMap<String, Pane> screenMap = new HashMap<>();

    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    /**
     * Add a screen to the list
     * @param name the name of the screen
     * @param pane the pane linked
     */
    public void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    /**
     * Remove a screen from the list
     * @param name the name of the screen
     */
    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    /**
     * Put on screen a pane
     * @param name the name of the screen
     */
    public void activate(String name){
        main.setRoot( screenMap.get(name) );
    }

}