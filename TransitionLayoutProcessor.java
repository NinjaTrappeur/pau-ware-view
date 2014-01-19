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

public class TransitionLayoutProcessor
{
    
    public enum HandlePos
    {
        TOP, LEFT, RIGHT, BOTTOM;
    }
    
    public enum DrawingOptions
    {
        BREAK_LINE, NO_BREAK_LINE;
    }
    
    private ILayout _layout;
    private IChart _chart;
    private DrawingOptions _drawingOption; 
    
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
    
    public TransitionLayoutProcessor(ILayout layout, IChart chart){
        _layout = layout;
        _chart = chart;
        _drawingOption = DrawingOptions.NO_BREAK_LINE;
    }
    
    public void setDrawingOption(DrawingOptions option)
    {
        _drawingOption = option;
    }
    
    public DrawingOptions drawingOption()
    {
        return _drawingOption;
    }
    
    private Point2D _getHandleExtension(HandlePos handle, Point2D pos)
    {
        Point2D p = new Point2D.Double();
        switch(handle)
        {
            case TOP:
                p.setLocation(pos.getX(), pos.getY()-Transition.HandleDelta);
                break;
            case RIGHT:
                p.setLocation(pos.getX()+Transition.HandleDelta, pos.getY());
                break;
            case BOTTOM:
                p.setLocation(pos.getX(), pos.getY()+Transition.HandleDelta);
                break;
            case LEFT:
                p.setLocation(pos.getX()-Transition.HandleDelta, pos.getY());
        }
        return p;
    }
    
    private void _breakLine(ArrayList<Point2D> path, HandlePos handleOrigin, Point2D posHandleOrigin, HandlePos handleTarget, Point2D posHandleTarget)
    {
        Point2D middlePoint, handleExtensionOrigin, handleExtensionTarget;
            //Breaking transitions line
            handleExtensionOrigin = _getHandleExtension(handleOrigin, posHandleOrigin);
            handleExtensionTarget = _getHandleExtension(handleTarget, posHandleTarget);
            if(handleExtensionOrigin != null && handleExtensionTarget != null)
            {
                middlePoint = new Point2D.Double(Math.max(handleExtensionOrigin.getX(), handleExtensionTarget.getX()),
                        Math.max(handleExtensionOrigin.getY(), handleExtensionTarget.getY()));
                path.add(handleExtensionOrigin);
                path.add(middlePoint);
                path.add(handleExtensionTarget);
            }
    }
    
    public void computeTransitionsLayout(){
        HandlePos handleOrigin = HandlePos.BOTTOM; //arbitrary default value
        HandlePos handleTarget = HandlePos.BOTTOM;
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
            if(_drawingOption == DrawingOptions.BREAK_LINE)
                _breakLine(path, handleOrigin, posHandleOrigin, handleTarget, posHandleTarget);
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
