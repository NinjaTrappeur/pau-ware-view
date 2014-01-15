/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author josuah
 */
public class ConcurrencyCluster extends AbstractElement
{
    protected AbstractElement _superstate;
    protected ArrayList<AbstractElement> _subStates;
    
    public ConcurrencyCluster(AbstractElement superstate)
    {
        super("cluster");
        _preInitReference(superstate, "constructor", "SuperState container");
        _superstate = superstate;
        
        _subStates = new ArrayList();
        setName("cluster_"+String.valueOf(_id)+"_"+superstate.name());
    }
    
    public void addState(AbstractElement state)
    {
        _preInitReference(state, "addState", "State to be added");
        _subStates.add(state);
    }

    public void removeState(AbstractElement state)
    {
        _preInitReference(state, "removeState", "State to be removed");
        _subStates.remove(state);
    }

    public Collection<AbstractElement> substates()
    {
        return _subStates;
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
