/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author josuah
 */
public abstract class AbstractLayoutProcessor implements ILayoutProcessor
{
    protected ILayout _layout;
    protected IChart _chart;
    
    private void _construct(ILayout initializedBySubclass)
    {
        if(initializedBySubclass == null)
        {
            _layout = new Layout();
        }
        
        else
        {
            _layout = initializedBySubclass;
        }
    }
    
    public AbstractLayoutProcessor(ILayout initializedBySubclass)
    {
        _construct(initializedBySubclass);
    }
    
    public AbstractLayoutProcessor()
    {
        _construct(null);
    }

    @Override
    public void init(IChart chart)
    {
        _chart = chart;
    }
    
    @Override
    public ILayout getLayout()
    {
        return _layout;
    }
    
    protected void _setClustersSizes(SuperState superState)
    {
        float clusterWidth;
        float ratio = 1F;
        
        for(ConcurrencyCluster cluster : superState.clusters())
        {
            if(superState.deepContentSize() != 0)
            {
                ratio = ((float)cluster.deepContentSize()) / superState.deepContentSize();
            }

            clusterWidth = superState.width() * ratio;

            cluster.setWidth(clusterWidth);
            cluster.setLength(superState.length() - superState.roundedCornerRadius());
        }
    }

    protected void _setSize(AbstractElement state)
    {
        float width, length;
        float ratio = 1.F;
        
        if(state.container().deepContentSize() != 0)
            ratio = ((float)(state.deepContentSize()+1))/state.container().deepContentSize();

        width  = ratio * AbstractElement._coveredAreaRatio * state.container().width();
        length = ratio * AbstractElement._coveredAreaRatio * state.container().length();
        
        if(! (state instanceof PseudoState))
            state.setSize(width, length);
        
        if(state instanceof SuperState)
            _setClustersSizes((SuperState)state);
    }
    
    protected void _setAllSizes()
    {
        HashMap<Integer, HashSet<AbstractElement>> nestings = _chart.nestingLevels();

        for(Integer level : _chart.nestingLevels().keySet())
        {
            for(AbstractElement state : nestings.get(level))
            {
                _setSize(state);
            }
        }
    }
}
