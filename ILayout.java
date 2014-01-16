/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author jaron
 * Interface for Layout objects.
 * A Layout is an object that holds a mapping of positiong for a set of objects
 */
public interface ILayout {
    void addPosition(AbstractElement elem, Position pos);
    void addPosition(AbstractElement elem, float x, float y);
    boolean removePosition(AbstractElement elem);
    void addTransitionPath(Transition transition, ArrayList<Point2D> path);
    boolean removeTransitionPath(Transition transition);
    
    ArrayList<Point2D> getTransitionPath(Transition transition);
    Position getPosition(AbstractElement elem);
}
