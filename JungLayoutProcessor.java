/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

/**
 *
 * @author josuah
 */
public class JungLayoutProcessor implements ILayoutProcessor
{
    private Layout _layout;
    private edu.uci.ics.jung.graph.Graph<AbstractElement, Integer> _graph;
    private edu.uci.ics.jung.algorithms.layout.AggregateLayout<String,Integer> _clusteringLayout;
    
    private void _verticesFrom(IChart chart)
    {
        for(AbstractElement elem : chart.elements())
        {
            _graph.addVertex(elem);
        }
    }
    
    private void _edgesFrom(IChart chart)
    {
        Integer i;
        
        i = 0;
        for(Transition t : chart.transitions())
        {
            _graph.addEdge(i, t.origin(), t.target());
            ++i;
        }
    }
    
    private void _subLayoutsFrom(IChart chart)
    {
        
    }
    
    public JungLayoutProcessor()
    {
        _layout = new Layout();
        _graph = new edu.uci.ics.jung.graph.DirectedSparseGraph();
        _clusteringLayout = new edu.uci.ics.jung.algorithms.layout.AggregateLayout(new edu.uci.ics.jung.algorithms.layout.FRLayout(_graph));
    }
    
    public void init(IChart chart)
    {
        _verticesFrom(chart);
        _edgesFrom(chart);
        _subLayoutsFrom(chart);
    }
    
    public ILayout getLayout()
    {
        return _layout;
    }
    
}
