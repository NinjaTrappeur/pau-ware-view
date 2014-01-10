/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;


import java.util.Iterator;
/**
 *
 * @author ninjatrappeur
 */
public class Painter {
    IChart _chart;
    ILayout _chartLayout;
    processing.core.PApplet _displayApplet;
    
    public Painter(IChart chart, ILayout layout,processing.core.PApplet applet){
        _chart = chart;
        _chartLayout = layout;
        _displayApplet = applet;
    }
    
    public void paint(){
        AbstractElement elem;
        Drawable drawableState;
        for(Iterator<AbstractElement> it=_chart.elements().iterator();
                it.hasNext();)
        {
            elem = it.next();
            if(!(elem instanceof SuperState))
            {
                Position pos  = _chartLayout.getPosition(elem);
                _displayApplet.pushMatrix();
                _displayApplet.translate(pos.x(), pos.y());
                drawableState = (Drawable)elem;
                drawableState.draw(_displayApplet);
                _displayApplet.popMatrix();
            }
        }
    }
}
