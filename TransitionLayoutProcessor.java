/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;
import java.awt.geom.Point2D;
import java.util.ArrayList;
/**
 *
 * @author minoulefou
 */
public class TransitionLayoutProcessor {
    
    private ILayout _layout;
    private IChart _chart;
    
    private HandlePos _getHandle(AbstractElement elem, Transition trans){
        //Calculing center of the 2 elements of the transition
        Position elemPos = _layout.getPosition(elem);
        AbstractElement otherElem;
        if(trans.origin().equals(elem))
            otherElem = trans.target();
        else
            otherElem = trans.origin();
        Position otherElemPos = _layout.getPosition(otherElem);
        Point2D otherElemCenter = new Point2D.Double(otherElemPos.x()+otherElem.width()/2,
                                             otherElemPos.y()+otherElem.length()/2);
        Point2D elemCenter = new Point2D.Double(elemPos.x()+elem.width()/2,
                                                elemPos.y()+ elem.length()/2);
        
        //Determinating which handle we should use
        if(otherElemCenter.getY()+elemCenter.getY() > elemCenter.getX() - otherElemCenter.getX() &&
                otherElemCenter.getY()+elemCenter.getY() > elemCenter.getX() + otherElemCenter.getX())
            return HandlePos.TOP;
        else if(otherElemCenter.getY()+elemCenter.getY() > elemCenter.getX() + otherElemCenter.getX() &&
                otherElemCenter.getY()+elemCenter.getY() < elemCenter.getX() - otherElemCenter.getX())
            return HandlePos.LEFT;
        else if(otherElemCenter.getY()+elemCenter.getY() < elemCenter.getX() + otherElemCenter.getX() &&
                otherElemCenter.getY()+elemCenter.getY() > elemCenter.getX() - otherElemCenter.getX())
            return HandlePos.RIGHT;
        else
            return HandlePos.BOTTOM;
    }
    
    private Point2D _getHandlePos(AbstractElement elem, HandlePos pos){
        Point2D handle = new Point2D.Double();
        Position elemPos = _layout.getPosition(elem);
        switch(pos){
            case TOP: handle.setLocation(elemPos.x()+elem.width()/2, 
                                                  elemPos.y());
                break;
            case RIGHT: handle.setLocation(elemPos.x()+elem.width(), 
                                                  elemPos.y()+elem.length()/2);
                break;
            case BOTTOM: handle.setLocation(elemPos.x()+elem.width()/2, 
                                                  elemPos.y()+elem.length());
                break;
            case LEFT: handle.setLocation(elemPos.x(), 
                                                  elemPos.y()+elem.length()/2);
                break;
        }
        return handle;
    }
    
    public enum HandlePos{
        TOP, LEFT, RIGHT, BOTTOM;
    }
    
    public TransitionLayoutProcessor(ILayout layout, IChart chart){
        _layout = layout;
        _chart = chart;
    }
    
    public void computeTransitionsLayout(){
        HandlePos handleOrigin;
        HandlePos handleTarget;
        Point2D posHandleOrigin;
        Point2D posHandleTarget;
        ArrayList<Point2D> path;

        for (Transition trans : _chart.transitions()) {
            path = new ArrayList();
            if (trans.origin() instanceof State) {
                if(trans.origin() instanceof SuperState)
                    handleOrigin = HandlePos.BOTTOM;
                else
                    handleOrigin = _getHandle(trans.origin(), trans);
                posHandleOrigin = _getHandlePos(trans.origin(), handleOrigin);
            } 
            else {
                posHandleOrigin = _layout.getPosition(trans.origin()).getPoint2D();
                posHandleOrigin.setLocation(posHandleOrigin.getX() + trans.origin().width() / 2,
                        posHandleOrigin.getY() + trans.origin().length() / 2);
            }

            if (trans.target() instanceof State) {                
                if(trans.target() instanceof SuperState)
                    handleTarget = HandlePos.BOTTOM;
                else
                    handleTarget = _getHandle(trans.target(), trans);
                posHandleTarget = _getHandlePos(trans.target(), handleTarget);
            } 
            else {
                posHandleTarget = _layout.getPosition(trans.target()).getPoint2D();
                posHandleTarget.setLocation(posHandleTarget.getX() + trans.target().width() / 2,
                        posHandleTarget.getY() + trans.target().length() / 2);
            }

            path.add(posHandleOrigin);
            path.add(posHandleTarget);
            _layout.addTransitionPath(trans, path);
        }
    }
    
    public ILayout getLayout(){
        return _layout;
    }
    
    public IChart getChart(){
        return _chart;
    }
}
