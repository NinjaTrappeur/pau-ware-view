/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author josuah
 */
public class ConcurrencyCluster extends AbstractElement implements Drawable
{
    protected AbstractElement _superstate;
    protected HashSet<AbstractElement> _subStates;
    
    public ConcurrencyCluster(AbstractElement superstate)
    {
        super("cluster");
        _preInitReference(superstate, "constructor", "SuperState container");
        _superstate = superstate;
        
        _subStates = new HashSet();
        setName("cluster_"+String.valueOf(_id)+"_"+superstate.name());
    }
    
    public boolean addState(AbstractElement state)
    {
        _preInitReference(state, "addState", "State to be added");
        boolean added = _subStates.add(state);

        if(added)
        {
            ++_shallowContentSize;
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
            --_shallowContentSize;
            _deepContentSize -= state.deepContentSize();
        }
        
        return removed;
    }

    public Collection<AbstractElement> substates()
    {
        return _subStates;
    }
 
    @Override
    public void draw(processing.core.PApplet applet)
    {
        // à faire
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
