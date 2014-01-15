
package com.PauWare.PauWare_view;
import java.awt.geom.Point2D;

/**
 *
 * @author ninjatrappeur
 */
public class Painter {
    IChart _chart;
    ILayout _chartLayout;
    processing.core.PApplet _displayApplet;
    int _p; //TEST ONLY, DELETE ASAP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    
    private void _displayElement(AbstractElement elem){
        Drawable drawableState;
        if (!(elem instanceof SuperState) && elem != null) {
//          Position pos  = _chartLayout.getPosition(elem);
            Position pos = new Position(_p, _p);
            _p = _p + 150;
            if (pos != null) {
                _displayApplet.pushMatrix();
                _displayApplet.translate(pos.x(), pos.y());
                drawableState = (Drawable) elem;
                drawableState.draw(_displayApplet);
                _displayApplet.popMatrix();
            }
        }
    }
    
    private void _displayTransition(Transition trans){
        for(int i=0; i<_chartLayout.getTransitionPath(trans).size()-1 ;++i){
            Point2D startPoint = _chartLayout.getTransitionPath(trans).get(i);
            Point2D endPoint = _chartLayout.getTransitionPath(trans).get(i+1);

            _displayApplet.line((float)startPoint.getX(), (float)startPoint.getY(),
                    (float)endPoint.getX(), (float)endPoint.getY());
        }
    }
    
    public Painter(IChart chart, ILayout layout,processing.core.PApplet applet){
        _chart = chart;
        _chartLayout = layout;
        _displayApplet = applet;
    }
    
    
    
    public void paint(){
        _p=0;
        //transitions display
        for(Transition trans:_chart.transitions())
        {
            _displayTransition(trans);
        }
        
        //states display
        for(AbstractElement elem:_chart.elements())
        {
            _displayElement(elem);
        }
    }
}
