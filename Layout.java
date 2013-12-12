/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
/**
 *
 * @author fbaylacj
 */
public class Layout implements ILayout{
    
    UndirectedGraph<Integer, AbstractElement> _graph;
    FRLayout<Integer, AbstractElement> _layout;
    
    public Layout(){
        _graph = new UndirectedSparseGraph();
        _layout = new FRLayout(_graph);
    }
    
    @Override
    public void addPosition(AbstractElement elem, Position pos){
        
    }
    
    @Override
    public void addPosition(AbstractElement elem, float x, float y){
        
    }
    
    @Override
    public void removePosition(AbstractElement elem){
        
    }
    
    @Override
    public Position getPosition(AbstractElement elem){
        return new Position();
    }
    
}
