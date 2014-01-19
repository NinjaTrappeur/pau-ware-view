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

public class TransitionLayoutProcessor {
    
    private ILayout _layout;
    private IChart _chart;
    
    private HandlePos _getHandle(AbstractElement elem, Transition trans){
        //Calculing center of the 2 elements of the transition + bottom left 
        Position elemPos = _layout.getPosition(elem);
        AbstractElement otherElem;
        if(trans.origin().equals(elem))
            otherElem = trans.target();
        else
            otherElem = trans.origin();
        Position otherElemPos = _layout.getPosition(otherElem);
        Point2D otherElemCenter;
        if(!(otherElem instanceof SuperState))
            otherElemCenter = new Point2D.Double(otherElemPos.x()+otherElem.width()/2,
                                             otherElemPos.y()+otherElem.length()/2);
        else // we take bottom handle as center in this case.
            otherElemCenter = new Point2D.Double(otherElemPos.x()+otherElem.width()/2,
                                             otherElemPos.y()+otherElem.length());
        
        Point2D elemCenter = new Point2D.Double(elemPos.x()+elem.width()/2,
                                                elemPos.y()+ elem.length()/2);

        Point2D bottomLeft = new Point2D.Double(elemPos.x(),elemPos.y()+elem.length());
        
        //Calculating lines position
        double pos1=(elemCenter.getX()-bottomLeft.getX())*(otherElemCenter.getY()-bottomLeft.getY())-
                (elemCenter.getY()-bottomLeft.getY())*(otherElemCenter.getX()-bottomLeft.getX());
        double pos2=(elemCenter.getX()-elemPos.x())*(otherElemCenter.getY()-elemPos.y())-
                (elemCenter.getY()-elemPos.y())*(otherElemCenter.getX()-elemPos.x());
        
        //returning pos
        if(pos1<0 && pos2<0)
            return HandlePos.TOP;
        else if(pos1<0 && pos2>0)
            return HandlePos.LEFT;
        else if(pos1>0 && pos2>0)
            return HandlePos.BOTTOM;
        else
            return HandlePos.RIGHT;
    }
    
    private Point2D _getHandlePos(AbstractElement elem, HandlePos pos, Transition trans) {
        Point2D handle = new Point2D.Double();
        Position elemPos = _layout.getPosition(elem);
        if ((!(elem instanceof SuperState))) {
            switch (pos) {
                case TOP:
                    handle.setLocation(elemPos.x() + elem.width() / 2,
                            elemPos.y());
                    break;
                case RIGHT:
                    handle.setLocation(elemPos.x() + elem.width(),
                            elemPos.y() + elem.length() / 2);
                    break;
                case BOTTOM:
                    handle.setLocation(elemPos.x() + elem.width() / 2,
                            elemPos.y() + elem.length());
                    break;
                case LEFT:
                    handle.setLocation(elemPos.x(),
                            elemPos.y() + elem.length() / 2);
                    break;
            }
        } 
        else {
            handle.setLocation(elemPos.x() + elem.width() / 2,
                    elemPos.y() + elem.length());
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
                handleOrigin = _getHandle(trans.origin(), trans);
                posHandleOrigin = _getHandlePos(trans.origin(), handleOrigin, trans);
            } 
            else {
                posHandleOrigin = _layout.getPosition(trans.origin()).getPoint2D();
                posHandleOrigin.setLocation(posHandleOrigin.getX() + trans.origin().width() / 2,
                        posHandleOrigin.getY() + trans.origin().length() / 2);
            }

            if (trans.target() instanceof State) {                
                handleTarget = _getHandle(trans.target(), trans);
                posHandleTarget = _getHandlePos(trans.target(), handleTarget, trans);
            } 
            else {
                posHandleTarget = _layout.getPosition(trans.target()).getPoint2D();
                posHandleTarget.setLocation(posHandleTarget.getX() + trans.target().width() / 2,
                        posHandleTarget.getY() + trans.target().length() / 2);
            }

            path.add(posHandleOrigin);
            path.add(posHandleTarget);
            _layout.addTransitionPath(trans, path);
            System.out.println("Taille du path: " + path.size());
        }
    }
    
    public ILayout getLayout(){
        return _layout;
    }
    
    public IChart getChart(){
        return _chart;
    }
}
