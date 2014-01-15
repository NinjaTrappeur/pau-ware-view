/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.geom.Point2D;

/**
 *
 * @author fbaylacj
 */
public class Layout implements ILayout{
    HashMap<AbstractElement, Position> _statesMap;
    HashMap<Transition, ArrayList<Point2D>> _transitionsMap;
    
    private void _preReferencePosition(Position ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("Transition._preReference (for "
                    + methodName
                    + "): null pointer given for parameter of " + argName);
        }
    }
    
    private void _preReferenceTransition(ArrayList<Point2D> ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("Transition._preReference (for "
                    + methodName
                    + "): null pointer given for parameter of " + argName);
        }
    }
    
    public Layout()
    {
        _statesMap = new HashMap();
        _transitionsMap = new HashMap();
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
        Position pos = _statesMap.get(elem);
        _preReferencePosition(pos, "getPosition", "elem");
        return pos;
    }
    
    @Override
    public ArrayList<Point2D> getTransitionPath(Transition transition){
        ArrayList<Point2D> path;
        path=_transitionsMap.get(transition);
        _preReferenceTransition(path, "getTransitionPath", "transition");
        return path;
    }
    
    @Override
    public void addTransitionPath(Transition trans, ArrayList<Point2D> path){
        _transitionsMap.put(trans, path);
    }
    
    @Override
    public void removeTransitionPath(Transition trans){
        _transitionsMap.remove(trans);
    }
}
