/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;
import edu.uci.ics.jung.algorithms.layout.FRLayout;


/**
 *
 * @author ninjatrappeur
 * 
 * This layout implements ILayout using
 * jung force layout.
 */
public class JungLayout implements ILayout{
    JungChart _chart;
    FRLayout _layout;
    
    public JungLayout(JungChart chart){
        _chart = chart;
        _layout = new FRLayout(_chart.getChart());
    }
    
    @Override
    public void addPosition(AbstractElement elem, Position pos){
        if(_chart.getChart().containsVertex(elem)){
            _layout.setLocation(elem, pos.x(), pos.y());
        }
    }
    
    @Override
    public void addPosition(AbstractElement elem, float x, float y) {
        if (_chart.getChart().containsVertex(elem)) {
            _layout.setLocation(elem, x, y);
        }
    }
    
    @Override
    public void removePosition(AbstractElement elem){
        _layout.setLocation(elem, 0, 0);
    }
    
    @Override
    public Position getPosition(AbstractElement elem){
        Double x = _layout.getX(elem);
        Double y = _layout.getY(elem);
        Position position = new Position();
        position.set(x.floatValue(),y.floatValue());
        return position;
    }
}
