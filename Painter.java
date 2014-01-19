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
import java.util.HashMap;
import java.util.HashSet;

public class Painter
{
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
            System.err.println("Painter._displayTransition: treating transition "+trans.origin().name()+"-->"+trans.target().name());
            _displayArrow(_chartLayout.getTransitionPath(trans));
        }
    }
    
    private void _displayArrow(ArrayList<Point2D> path)
    {
        if(path.size() >= 2)
        {
            Point2D M = path.get(path.size()-2);
            Point2D N = path.get(path.size()-1);
            if(! M.equals(N))
            {
                Point2D K, A, B, BA, MN, s;
                double normMN, lambda;
                double d = Transition.ArrowMedian;
                double l = Transition.ArrowBase;

                MN = new Point2D.Double(N.getX() - M.getX(), N.getY() - M.getY());
                normMN = Math.sqrt(MN.getX()*MN.getX() + MN.getY()*MN.getY());
                lambda = d / normMN;

                K = new Point2D.Double(N.getX() - lambda*MN.getX(), N.getY() - lambda*MN.getY());
                s = new Point2D.Double(MN.getX()/normMN, MN.getY()/normMN);
                BA = new Point2D.Double(-l*s.getY(), l*s.getX());

                A = new Point2D.Double(K.getX() + BA.getX()/2, K.getY() + BA.getY()/2);
                B = new Point2D.Double(K.getX() - BA.getX()/2, K.getY() - BA.getY()/2);

                _displayApplet.line((float)A.getX(), (float)A.getY(), (float)N.getX(), (float)N.getY());
                _displayApplet.line((float)B.getX(), (float)B.getY(), (float)N.getX(), (float)N.getY());
                System.err.println("Painter._displayArrow: M("+M.getX()+","+M.getY()+")\tN("+N.getX()+","+N.getY()+")\tMN("+MN.getX()+","+MN.getY()+")\tnormMN="+normMN);
                System.err.println("Painter._displayArrow: lambda="+lambda);
                System.err.println("Painter._displayArrow: K("+K.getX()+","+K.getY()+")\ts("+s.getX()+","+s.getY()+")\tBA("+BA.getX()+","+BA.getY()+")");
                System.err.println("Painter._displayArrow: A("+A.getX()+","+A.getY()+")\tB("+B.getX()+","+B.getY()+")");

            }
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
