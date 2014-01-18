
package com.PauWare.PauWare_view;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author ninjatrappeur
 */
public class Painter {
    IChart _chart;
    ILayout _chartLayout;
    processing.core.PApplet _displayApplet;
    
    private void _displayElement(AbstractElement elem){
        Drawable drawableState;
        if (elem != null /*&& elem.name().equals("Busy")*/ ) {
          Position pos  = _chartLayout.getPosition(elem);
            if (pos != null) {
                _displayApplet.pushMatrix();
                _displayApplet.translate(pos.x(), pos.y());
                drawableState = (Drawable) elem;
                drawableState.draw(_displayApplet);
                _displayApplet.popMatrix();
            }
        }
    }
    
    private void _displayTransition(Transition trans) {
        for (int i = 0; i < _chartLayout.getTransitionPath(trans).size() - 1; ++i) {
            Point2D startPoint = _chartLayout.getTransitionPath(trans).get(i);
            Point2D endPoint = _chartLayout.getTransitionPath(trans).get(i + 1);
            _displayApplet.line((float) startPoint.getX(), (float) startPoint.getY(),
                    (float) endPoint.getX(), (float) endPoint.getY());
        }
    }
    
    public Painter(IChart chart, ILayout layout,processing.core.PApplet applet){
        _chart = chart;
        _chartLayout = layout;
        _displayApplet = applet;
    }
    
    
    
    public void paint(){
        //states display
        HashMap<Integer, HashSet<AbstractElement> > nestingLevels = _chart.nestingLevels();
        for(Integer level : nestingLevels.keySet())
            //System.err.println("Painter.paint: printing nesting level "+level);
            for(AbstractElement elem : nestingLevels.get(level))
                _displayElement(elem);
                //System.err.println("\tPainter.paint: printing elem "+elem.name());
        
        //transitions display
        for(Transition trans:_chart.transitions())
           _displayTransition(trans);
    }
}
