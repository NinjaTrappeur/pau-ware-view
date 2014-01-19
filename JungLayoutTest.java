/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */

package com.PauWare.PauWare_view;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import java.io.PrintStream;

public class JungLayoutTest
{
    private Graph<String, Integer> _graph = new DirectedSparseGraph();
    private AggregateLayout<String,Integer> _clusteringLayout = new AggregateLayout(new FRLayout(_graph));
    
    public JungLayoutTest()
    {
        
        Graph<String, Integer> busySub = new DirectedSparseGraph();
        Graph<String, Integer> s1Sub = new DirectedSparseGraph();
        Graph<String, Integer> s2Sub = new DirectedSparseGraph();
        Graph<String, Integer> s3Sub = new DirectedSparseGraph();
        
        Layout<String,Integer> busySubLayout, s1SubLayout, s2SubLayout, s3SubLayout;
        Dimension baseSize, level1SuperSize, level0SuperSize;
        int baseWidth, baseHeight, width, height, xMargin, yMargin;
        int nbSubElements;

        
        _graph.addVertex("Idle");
        _graph.addVertex("start_to_Idle");
        _graph.addVertex("Busy");
        _graph.addVertex("S1");
        _graph.addVertex("S2");
        _graph.addVertex("S3");
        _graph.addVertex("S11");
        _graph.addVertex("start_to_S11");
        _graph.addVertex("S12");
        _graph.addVertex("S21");
        _graph.addVertex("S22");
        _graph.addVertex("start_to_S22");
        _graph.addVertex("S31");
        _graph.addVertex("S32");
        
        _graph.addEdge(0, "Idle", "Busy");
        _graph.addEdge(1, "Busy", "S21");
        _graph.addEdge(2, "Busy", "S12");
        _graph.addEdge(3, "S1", "S12");
        _graph.addEdge(4, "S12", "S11");
        _graph.addEdge(5, "S2", "Idle");
        _graph.addEdge(6, "S21", "Idle");
        _graph.addEdge(7, "S32", "S32");
        
        _graph.addEdge(8, "start_to_Idle", "Idle");
        _graph.addEdge(9, "start_to_S11", "S11");
        _graph.addEdge(10, "start_to_S22", "S22");
        
        /********** busy state subgraph *********/
        busySub.addVertex("S1");
        busySub.addVertex("S2");
        busySub.addVertex("S3");
        busySub.addVertex("S11");
        busySub.addVertex("start_to_S11");
        busySub.addVertex("S12");
        busySub.addVertex("S21");
        busySub.addVertex("S22");
        busySub.addVertex("start_to_S22");
        busySub.addVertex("S31");
        busySub.addVertex("S32");
        
        busySub.addEdge(3, "S1", "S12");
        busySub.addEdge(4, "S12", "S11");
        busySub.addEdge(7, "S32", "S32");        
        busySub.addEdge(9, "start_to_S11", "S11");
        busySub.addEdge(10, "start_to_S22", "S22");

        busySubLayout = new FRLayout(busySub);

        /********* S1 state subgraph *******/
        s1Sub.addVertex("S11");
        s1Sub.addVertex("S12");
        s1Sub.addVertex("start_to_S11");

        s1Sub.addEdge(4, "S12", "S11");
        s1Sub.addEdge(9, "start_to_S11", "S11");
        
        s1SubLayout = new FRLayout(s1Sub);

        /********* S2 state subgraph ********/
        s2Sub.addVertex("S21");
        s2Sub.addVertex("S22");
        s2Sub.addVertex("start_to_S22");

        s2Sub.addEdge(10, "start_to_S22", "S22");
        
        s2SubLayout = new FRLayout(s2Sub);
        
        /********** S3 state subgraph *********/
        s3Sub.addVertex("S31");
        s3Sub.addVertex("S32");

        s3Sub.addEdge(7, "S32", "S32");
        
        s3SubLayout = new FRLayout(s3Sub);

        
        
        /****** recursive sizes processing *******/
        baseWidth = 200;
        baseHeight = 100;
        xMargin = 25;
        yMargin = 25;

        baseSize = new Dimension(baseWidth, baseHeight);
        
        //level 2 is baseSize
        //level 1
        nbSubElements = 2;
        width = nbSubElements * baseWidth + xMargin;
        height = nbSubElements * baseHeight + yMargin;
        level1SuperSize = new Dimension(width, height);
        
        //level 0
        nbSubElements = 3;
        width = nbSubElements * width + xMargin;
        height = nbSubElements * height + yMargin;
        level0SuperSize = new Dimension(width, height);

        
        /******* Layout setting ********/
        busySubLayout.setSize(level0SuperSize);
        s2SubLayout.setSize(level1SuperSize);
        s2SubLayout.setSize(level1SuperSize);
        s3SubLayout.setSize(level1SuperSize);
        
        busySubLayout.initialize();
        s2SubLayout.initialize();
        s2SubLayout.initialize();
        s3SubLayout.initialize();
        
//        subLayout.setInitializer(vv.getGraphLayout());

        Point2D center = new Point2D.Double(0,0);
//        _clusteringLayout.put(busySubLayout, center);
//        _clusteringLayout.put(s1SubLayout, center);
//        _clusteringLayout.put(s2SubLayout, center);
//        _clusteringLayout.put(s3SubLayout, center);
        
        _clusteringLayout.setDelegate(busySubLayout);
        _clusteringLayout.setDelegate(s1SubLayout);
        _clusteringLayout.setDelegate(s2SubLayout);
        _clusteringLayout.setDelegate(s3SubLayout);
        
        _clusteringLayout.setSize(baseSize);

        _clusteringLayout.initialize();
        
        /*************  Affichage des positions **********/
        //somewhere
        //this._clusteringLayout.removeAll();
    }
    
    public void runTest()
    {
        int nbSteps = 4;
        for(int i = 0; i < nbSteps; ++i)
        {
            _clusteringLayout.step();
        }
        
        Point2D pos;
        PrintStream out = System.err;

        for(String elem : _graph.getVertices())
        {
            pos = _clusteringLayout.transform(elem);
            out.println("positions:");
            out.print("\t"+elem+": (");
            out.print(String.valueOf(pos.getX())+", "+String.valueOf(pos.getY()));
            out.println(")");
        }
    }
}
