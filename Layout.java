/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;
import java.util.HashMap;

/**
 *
 * @author fbaylacj
 */
public class Layout implements ILayout{
    HashMap<AbstractElement, Position> _map;
    
    
    @Override
    public void addPosition(AbstractElement elem, Position pos){
        _map.put(elem, pos);
    }
    
    @Override
    public void addPosition(AbstractElement elem, float x, float y){
        Position pos= new Position(x,y);
        _map.put(elem, pos);
    }
    
    @Override
    public void removePosition(AbstractElement elem){
        _map.remove(elem);
    }
    
    @Override
    public Position getPosition(AbstractElement elem){
        return _map.get(elem);
    }
    
}
