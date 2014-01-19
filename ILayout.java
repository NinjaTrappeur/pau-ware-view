/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
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
