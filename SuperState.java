/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Dr Who
 */
public class SuperState extends State implements Drawable
{
    protected ArrayList<ConcurrencyCluster> _clusters;
    
    /**
     * This class represents a graphic super state.
     * 
     * @param length Length of the rectangle representing the state.
     * @param width Width of the rectangle representing the state.
     * @param name Name of the state.
     * @param container optional container (like superstate or statechart)
     */
    public SuperState(String name, float width, float length, AbstractElement container)
    {
        super(name, width, length, container);
        this._clusters = new ArrayList();
    }
    
    public SuperState(String name, float width, float length)
    {
        super(name, width, length);
        this._clusters = new ArrayList();
    }
    
    public SuperState(String name, AbstractElement container)
    {
        super(name, container);
        this._clusters = new ArrayList();
    }
    
    public SuperState(String name)
    {
        super(name);
        this._clusters = new ArrayList();
    }
    
    public void addSubState(AbstractElement state, int cluster)
    {
        _preAddSubState(state, cluster);
        if(cluster == _clusters.size())
        {
            _clusters.add(new ConcurrencyCluster(this));
        }
        
        boolean added = _clusters.get(cluster).addState(state);
        if(added)
        {
            //setting the container is done by the cluster (see above)
            ++_shallowContentSize;
            ++_deepContentSize;
            _deepContentSize += state.deepContentSize();
        }
    }
    
    public void addSubState(AbstractElement state, ConcurrencyCluster cluster)
    {
        _preReference(cluster, "addSubState(AbstractElement, ConcurrencyCluster)", "cluster");
        int icluster = _clusters.indexOf(cluster);
        addSubState(state, icluster);
    }
    
    public void addSubState(AbstractElement state)
    {
        _preReference(state, "addSubState(AbstractElement)", "state");
        addSubState(state, 0);
    }

    public boolean removeSubState(AbstractElement state, int cluster)
    {
        _preRemoveSubState(state, cluster);
        boolean removed = _clusters.get(cluster).removeState(state);
        if(removed)
        {
            --_shallowContentSize;
            --_deepContentSize;
            _deepContentSize -= state.deepContentSize();
        }

        return removed;
    }
    
    public boolean removeSubState(AbstractElement state, ConcurrencyCluster cluster)
    {
        _preRemoveSubState(state, cluster);
        int icluster = _clusters.indexOf(cluster);
        return removeSubState(state, icluster);
    }

    public boolean removeSubState(AbstractElement state)
    {
        _preReference(state, "removeSubState(AbstractElement)", "state");
        boolean removed;
        int cluster;
        
        removed = false;
        cluster = 0;
        while(cluster < _clusters.size() && !removed)
        {
            removed = removeSubState(state, cluster);
            ++cluster;
        }
        
        return removed;
    }
    
    public float computeClusterSize(ConcurrencyCluster cluster)
    {
        float clusterWidth;
        float ratio = 1F;
        
        if(this.deepContentSize() != 0)
        {
            ratio = ((float)cluster.deepContentSize()) / this.deepContentSize();
        }
        System.err.println("SuperState.computeAllClustersSize: computing size of cluster "+cluster.name());
        System.err.println("SuperState.computeAllClustersSize: cluster "+cluster.name()+" deepContentSize is "+cluster.deepContentSize());
        System.err.println("SuperState.computeAllClustersSize: computed ratio is "+ratio);

        clusterWidth = this.width() * ratio;
        
        cluster.setWidth(clusterWidth);
        cluster.setLength(this.length()); //there will be an issue: must begin after state heading
        
        System.err.println("SuperState.computeAllClustersSize: computed width is "+clusterWidth);

        return clusterWidth;
    }
    
    public void computeAllClustersSize()
    {
        System.err.println("SuperState.computeAllClustersSize: computing clusters sizes for "+this.name());
        System.err.println("SuperState.computeAllClustersSize: "+this.name()+" has "+_clusters.size()+" clusters");
        System.err.println("SuperState.computeAllClustersSize: deepContentSize of "+this.name()+" is "+this.deepContentSize());
        for(ConcurrencyCluster cluster : _clusters)
        {
            computeClusterSize(cluster);
        }
    }

    public Collection<AbstractElement> substates()
    {
        ArrayList<AbstractElement> all;
        
        all = new ArrayList();
        for(ConcurrencyCluster cluster : _clusters)
        {
            all.addAll(cluster._subStates);
        }

        return all;
    }
    
    public ConcurrencyCluster clusterOf(AbstractElement state)
    {
        ConcurrencyCluster cluster = null;
        boolean found = false;
        
        Iterator<ConcurrencyCluster> it = _clusters.iterator();
        while(it.hasNext() && !found)
        {
            cluster = it.next();
            found = cluster.substates().contains(state);
        }
        
        if(!found)
            cluster = null;

        return cluster;
    }

    public Collection<ConcurrencyCluster> clusters()
    {
        return _clusters;
    }
 
    @Override
    public void draw(processing.core.PApplet applet)
    {
        float clusterWidth;
        float offset = 0;
        
        super.draw(applet);
        
        //Dessin des clusters
        applet.pushMatrix();
        for(ConcurrencyCluster cluster : _clusters)
        {
            clusterWidth = computeClusterSize(cluster);
            
            applet.translate(offset, 0);
            cluster.draw(applet);
            
            offset += clusterWidth;
        }
        applet.popMatrix();
    }
    
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
        if(cluster < 0 || cluster > _clusters.size())
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
        _preReference(state, "_preRemoveSubState(AbstractElement,int)", "state");
        if(cluster < 0 || cluster >= _clusters.size())
        {
            throw new IllegalArgumentException("SuperState._preRemoveSubState:"+
                    " ConcurrencyCluster num out of range [0, nbClusters-1] = [0,"+String.valueOf(_clusters.size()-1)+"]");
            
        }
    }
    
    private void _preRemoveSubState(AbstractElement state, ConcurrencyCluster cluster) throws IllegalArgumentException
    {
        _preReference(state, "_preRemoveSubState(AbstractElement,ConcurrencyCluster)", "state");
        _preReference(cluster, "_preRemoveSubState(AbstractElement,ConcurrencyCluster)", "cluster");
    }
}
