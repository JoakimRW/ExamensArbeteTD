package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by MichaelSjogren on 2017-02-22.
 */
public class Node{

    private Vector2 cordinates;
    private Node parent;
    private double fCost;
    private double gCost;
    private double hCost;

    public Node(Vector2 cordinates , Node parent , double gCost , double hCost ){
        this.cordinates = cordinates;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = this.gCost + this.hCost;
    }

    /** getters  **/
    public Vector2 getCordinates() {
        return cordinates;
    }

    public Node getParent() {
        return parent;
    }

    public double getfCost() {
        return fCost;
    }

    public double getgCost() {
        return gCost;
    }

    public double gethCost() {
        return hCost;
    }
}
