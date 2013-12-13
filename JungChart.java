/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.HashMap;
import java.util.HashSet;
/**
 *
 * @author ninjatrappeur
 * 
 * This chart implements IChart using 
 * jung chart.
 */
public class JungChart implements IChart {
    UndirectedSparseGraph<AbstractElement,Transition> _graph;
    
    public JungChart()
    {
        _graph = new UndirectedSparseGraph();
    }
    
    @Override
    public void addElement(AbstractElement state)
    {
        _graph.addVertex(state);
    }
    
    @Override
    public void removeElement(AbstractElement state){
        _graph.removeVertex(state);
    }
    
    @Override
    public void addTransition(Transition trans){
        _graph.addEdge(trans, trans.origin(), trans.target());
    }
    
    @Override
    public void addTransition(AbstractElement origin, AbstractElement target){
        _graph.addEdge(new Transition(origin,target), origin, target);
    }
    
    @Override
    public void removeTransition(Transition trans){
        _graph.removeEdge(trans);
    }
    
    @Override
    public void removeTransition(AbstractElement origin, AbstractElement target){
        _graph.removeEdge(new Transition(origin, target));
    }
    
    @Override
    public HashMap<Integer, HashSet<AbstractElement>> nestingLevels(){
        return null;
    }
    @Override
    public boolean isTransition(AbstractElement origin, AbstractElement target){
        return _graph.isNeighbor(origin, target);
    }
    
    public UndirectedSparseGraph getChart(){return _graph;}
}
