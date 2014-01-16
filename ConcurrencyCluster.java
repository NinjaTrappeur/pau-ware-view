/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.Collection;
import java.util.HashSet;
import processing.core.PApplet;

/**
 *
 * @author josuah
 */
public class ConcurrencyCluster extends AbstractElement implements Drawable
{
    protected HashSet<AbstractElement> _subStates;
    
    public enum ClusterDrawSwitch {TOP, RIGHT, BOTTOM, LEFT, ALL, NONE};
    boolean _drawTop;
    boolean _drawRight;
    boolean _drawBottom;
    boolean _drawLeft;
    
    public ConcurrencyCluster(AbstractElement superstate)
    {
        super("cluster", superstate);
        _preInitReference(superstate, "constructor", "SuperState container");
        
        _subStates = new HashSet();
        setName("cluster_"+String.valueOf(_id)+"_"+superstate.name());
        
        _drawTop = true;
        _drawRight = true;
        _drawBottom = true;
        _drawLeft = true;
    }
    
    public boolean addState(AbstractElement state)
    {
        _preInitReference(state, "addState", "State to be added");
        boolean added = _subStates.add(state);

        if(added)
        {
            state.setContainer(this);
            ++_shallowContentSize;
            ++_deepContentSize;
            _deepContentSize += state.deepContentSize();
        }
        
        return added;
    }

    public boolean removeState(AbstractElement state)
    {
        _preInitReference(state, "removeState", "State to be removed");
        boolean removed = _subStates.remove(state);

        if(removed)
        {
            state.setContainer(_container); // raaa : possible issue
            --_shallowContentSize;
            --_deepContentSize;
            _deepContentSize -= state.deepContentSize();
        }
        
        return removed;
    }
    
    public void setDrawSwitch(ClusterDrawSwitch s)
    {
        switch(s)
        {
            case ALL:
                _drawTop = true;
                _drawRight = true;
                _drawBottom = true;
                _drawLeft = true;
                break;

            case NONE:
                _drawTop = false;
                _drawRight = false;
                _drawBottom = false;
                _drawLeft = false;
                break;

            case TOP:
                _drawTop = true;
                break;

            case RIGHT:
                _drawRight = true;
                break;

            case BOTTOM:
                _drawBottom = true;
                break;

            case LEFT:
                _drawLeft = true;
        }
    }
    
    public void setDrawSwitch(ClusterDrawSwitch s1, ClusterDrawSwitch s2, ClusterDrawSwitch s3, ClusterDrawSwitch s4)
    {
        setDrawSwitch(s1);
        setDrawSwitch(s2);
        setDrawSwitch(s3);
        setDrawSwitch(s4);
    }
    
    public void setDrawSwitch(ClusterDrawSwitch s1, ClusterDrawSwitch s2, ClusterDrawSwitch s3)
    {
        setDrawSwitch(s1);
        setDrawSwitch(s2);
        setDrawSwitch(s3);
    }
    
    public void setDrawSwitch(ClusterDrawSwitch s1, ClusterDrawSwitch s2)
    {
        setDrawSwitch(s1);
        setDrawSwitch(s2);
    }

    public Collection<AbstractElement> substates()
    {
        return _subStates;
    }
 
    @Override
    public void draw(processing.core.PApplet applet)
    {
        float dashMargin;
        
        dashMargin = 10F;

        applet.pushMatrix();
        applet.fill(130,0,0);
        if(_drawTop)
        {
//            System.err.println("ConcurrencyCluster.draw: drawing top side");
            _drawDottedLine(applet, 0, 0, _width, 0, dashMargin);
        }

        if(_drawRight)
        {
//            System.err.println("ConcurrencyCluster.draw: drawing right side");
            _drawDottedLine(applet, _width, 0, _width, _length, dashMargin);
        }

        if(_drawBottom)
        {
//            System.err.println("ConcurrencyCluster.draw: drawing bottom side");
            _drawDottedLine(applet, 0, _length, _width, _length, dashMargin);
        }

        if(_drawLeft)
        {
//            System.err.println("ConcurrencyCluster.draw: drawing left side");
            _drawDottedLine(applet, 0, 0, 0, _length, dashMargin);
        }
        applet.popMatrix();
    }
    
    private void _drawDottedLine(processing.core.PApplet applet, float startX,
            float startY, float stopX, float stopY, float dashMargin)
    {
        float x, y;
        float lineLength;
        int nbDash;

        lineLength = (float)Math.sqrt(Math.pow(stopX-startX, 2)+ Math.pow(stopY-startY, 2));
        nbDash = (int)Math.floor(lineLength / dashMargin);
//        System.err.println("ConcurrencyCluster._drawDottedLine: lineLength is "+lineLength);
//        System.err.println("ConcurrencyCluster._drawDottedLine: nbDash is "+nbDash);

        for(int i=0; i < nbDash; ++i)
        {
            x = PApplet.lerp(startX, stopX, ((float)i)/nbDash);
            y = PApplet.lerp(startY, stopY, ((float)i)/nbDash);
            applet.point(x,y);
//            System.err.println("ConcurrencyCluster._drawDottedLine: plotted at "+x+","+y);
        }
    }
    
    private void _preInitReference(AbstractElement ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("ConcurrencyCluster._preInitReference (for "
                    + methodName
                    + "): null pointer given gor initilization of " + argName);
        }
    }

}
