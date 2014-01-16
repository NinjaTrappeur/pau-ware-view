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
    
    private void _preReferenceElem(AbstractElement ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("Layout._preReferenceElem (for "
                    + methodName
                    + "): null pointer given for parameter of " + argName);
        }
    }
    
    private void _preReferencePosition(Position ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("Layout._preReferencePosition (for "
                    + methodName
                    + "): null pointer given for parameter of " + argName);
        }
    }
    
    private void _preReferenceTransition(ArrayList<Point2D> ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("Layout._preReferenceTransition (for "
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
    public void addPosition(AbstractElement elem, Position pos)
    {
        _preReferenceElem(elem, "addPosition", "elem");
        _preReferencePosition(pos, "addPosition", "pos");
        _statesMap.put(elem, pos);
    }
    
    @Override
    public void addPosition(AbstractElement elem, float x, float y)
    {
        Position pos= new Position(x,y);
        addPosition(elem, pos);
    }
    
    @Override
    public boolean removePosition(AbstractElement elem){
        return _statesMap.remove(elem) != null;
    }
    
    private void _preGetPosition(AbstractElement elem)
    {
        if(elem == null)
        {
            throw new IllegalArgumentException("Layout._preGetPosition:"+
                    "null pointer given for parameter of elem");
        }
    }
    
    private void _postGetPsotion(AbstractElement elem, Position pos)
    {
        if(pos == null)
        {
            if(!_statesMap.keySet().contains(elem))
            {
                throw new IllegalArgumentException("Layout._postGetPosition:"+
                        "state "+elem.name()+" is not in the layout");
            }
            else
            {
                throw new IllegalStateException("Layout._postGetPosition:"+
                        "null position contained in the layout");

            }
        }
    }
    
    @Override
    public Position getPosition(AbstractElement elem)
    {
        _preGetPosition(elem);
        Position pos = _statesMap.get(elem);
        _postGetPsotion(elem, pos);

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
    public boolean removeTransitionPath(Transition trans){
        return _transitionsMap.remove(trans) != null;
    }
}
