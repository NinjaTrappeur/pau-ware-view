/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;
import java.util.HashMap;
import java.util.Collection;
import java.awt.geom.Point2D;

/**
 *
 * @author fbaylacj
 */
public class Layout implements ILayout{
    HashMap<AbstractElement, Position> _statesMap;
    HashMap<Transition, Collection<Point2D>> _transitionsMap;
    
    
    public Layout()
    {
        _statesMap = new HashMap();
    }
    
    @Override
    public void addPosition(AbstractElement elem, Position pos){
        _statesMap.put(elem, pos);
    }
    
    @Override
    public void addPosition(AbstractElement elem, float x, float y){
        Position pos= new Position(x,y);
        _statesMap.put(elem, pos);
    }
    
    @Override
    public void removePosition(AbstractElement elem){
        _statesMap.remove(elem);
    }
    
    @Override
    public Position getPosition(AbstractElement elem){
        return _statesMap.get(elem);
    }
    
    @Override
    public Collection<Point2D> getTransitionPath(Transition transition){
        return _transitionsMap.get(transition);
    }
    
    @Override
    public void addTransitionPath(Transition trans, Collection<Point2D> path){
        _transitionsMap.put(trans, path);
    }
    
    @Override
    public void removeTransitionPath(Transition trans){
        _transitionsMap.remove(trans);
    }
}
