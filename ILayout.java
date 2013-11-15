/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;

/**
 *
 * @author jaron
 * Interface for Layout objects.
 * A Layout is an object that holds a mapping of positiong for a set of objects
 */
public interface ILayout {
    void addPosition(AbstractElement elem, Position pos);
    void addPosition(AbstractElement elem, float x, float y);
    void removePosition(AbstractElement elem);
    
    Position getPosition(AbstractElement elem);
}
