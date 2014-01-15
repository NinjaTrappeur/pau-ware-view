/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;

import java.util.Collection;
import java.util.ArrayList;

/**
 *
 * @author Dr Who
 */
public class SuperState extends State
{
    protected ArrayList<ConcurrencyCluster> _clusters;
    
    /**
     * This class represents a graphic super state.
     * 
     * @param length Length of the rectangle representing the state.
     * @param width Width of the rectangle representing the state.
     * @param name Name of the state.
     */
    public SuperState(String name, float width, float length)
    {
        super(name, width, length);
        this._clusters = new ArrayList();
    }
    
    public SuperState(String name)
    {
        super(name);
        this._clusters = new ArrayList();
    }
    
    public void addSubState(AbstractElement state, int cluster)
    {
        
    }
    
    public void addSubState(AbstractElement state, ConcurrencyCluster cluster)
    {
    }
    
    public void addSubState(AbstractElement state)
    {
    }

    public boolean removeSubState(AbstractElement state, int cluster)
    {
        return false;
    }

    public boolean removeSubState(AbstractElement state)
    {
        return false;
    }

    public Collection<AbstractElement> substates()
    {
        // à faire
        return new ArrayList();
    }

    public Collection<ConcurrencyCluster> clusters()
    {
        return _clusters;
    }
 
    /*
    @Override
        public void draw(processing.core.PApplet applet)
    {
    }
    */
    
    
    private void _preReference(AbstractElement ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("SuperState._preReference (for "
                    + methodName
                    + "): null pointer given gor initilization of " + argName);
        }
    }
    
    private void _preAddSubState(AbstractElement state, int cluster) throws IllegalArgumentException
    {
        _preReference(state, "_preAddSubState(AbstractElement,int)", "state");
        if(cluster > _clusters.size())
        {
            throw new IllegalArgumentException("SuperState._preAddSubState:"+
                    " ConcurrencyCluster num out of range [0, nbClusters] = [0,"+String.valueOf(_clusters.size())+"]");
            
        }
    }
    
    private void _preAddSubState(AbstractElement state, ConcurrencyCluster cluster) throws IllegalArgumentException
    {
        _preReference(state, "_preAddSubState(AbstractElement,ConcurrencyCluster)", "state");
        _preReference(cluster, "_preAddSubState(AbstractElement,ConcurrencyCluster)", "cluster");
    }

    private void _preRemoveSubState(AbstractElement state, int cluster)
    {
        _preReference(state, "_preRemoveSubState(AbstractElement,ConcurrencyCluster)", "state");
        if(cluster >= _clusters.size())
        {
            throw new IllegalArgumentException("SuperState._preAddSubState:"+
                    " ConcurrencyCluster num out of range [0, nbClusters-1] = [0,"+String.valueOf(_clusters.size()-1)+"]");
            
        }
    }
}
