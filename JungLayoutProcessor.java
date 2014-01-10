/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.graph.Graph;
import java.awt.geom.Point2D;
import java.util.HashSet;
/**
 *
 * @author josuah
 */
public class JungLayoutProcessor implements ILayoutProcessor
{
    private ILayout _layout;
    private Graph<AbstractElement, Integer> _graph;
    private IChart _chart;
    private AggregateLayout<AbstractElement,Integer> _clusteringLayout;
    
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
    
    private void _processSuperState(SuperState state)
    {
        Graph<AbstractElement, Integer> graph = new DirectedSparseGraph();
        for(AbstractElement subState: state.substates())
        {
            if(subState instanceof SuperState)
            {
                SuperState superState = (SuperState) subState;
                _processSuperState(superState);
            }
        }
    }
    
    private void _convertLayout()
    {
        for(AbstractElement elem: _graph.getVertices())
        {
            if(!(elem instanceof SuperState))
            {
                float x = (float)_clusteringLayout.transform(elem).getX();
                float y = (float)_clusteringLayout.transform(elem).getY();
                _layout.addPosition(elem, new Position(x,y));
            }
        }
    }
    
    private void _subLayoutsFrom(IChart chart)
    {   
        HashSet<AbstractElement> nestingLevelElements;
        Graph<AbstractElement, Integer> graph;
        FRLayout layout;
        graph = new DirectedSparseGraph();
        nestingLevelElements = chart.nestingLevels().get(0);
        for (AbstractElement elem : nestingLevelElements) {
            if (elem instanceof SuperState) {
                SuperState superState = (SuperState) elem;
                _processSuperState(superState);
            } 
            else {
                graph.addVertex(elem);
            }
        }
        layout = new FRLayout(graph);
        _clusteringLayout.put(layout, new Point2D.Double(0, 0));
        _clusteringLayout.setDelegate(layout);
    }

    public JungLayoutProcessor() {
        _layout = new Layout();
        _graph = new DirectedSparseGraph();
        _clusteringLayout = new AggregateLayout(new FRLayout(_graph));
    }

    @Override
    public void init(IChart chart)
    {
        _chart = chart;
        _verticesFrom(chart);
        _edgesFrom(chart);
        _subLayoutsFrom(chart);
    }
    
    @Override
    public ILayout getLayout()
    {   
        _convertLayout();
        return _layout;
    }
    
    @Override
    public void processLayout()
    {
        _clusteringLayout.step();
    }
    
}
