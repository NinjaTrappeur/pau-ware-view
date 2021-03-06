/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;

import java.util.HashMap;
import java.util.HashSet;


public abstract class AbstractLayoutProcessor implements ILayoutProcessor
{
    protected ILayout _layout;
    protected IChart _chart;
    protected AbstractElement _chartAsAabstractElement;
    ITransitionLayoutProcessor _transitionProcessor;

    
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
    public void init(IChart chart, ITransitionLayoutProcessor transitionProcessor)
    {
        // pre: _chart instanceof AbstractElement
        _chart = chart;
        if(_chart instanceof AbstractElement)
            _chartAsAabstractElement = (AbstractElement)chart;
        this.setTransitionLayoutProcessor(transitionProcessor);
    }
    
    @Override
    public void init(IChart chart)
    {
        if(_transitionProcessor == null)
        {
            BasicTransitionLayoutProcessor btlp = new BasicTransitionLayoutProcessor(_layout, chart);
            btlp.setDrawingOption(BasicTransitionLayoutProcessor.DrawingOptions.BREAK_LINE);
            _transitionProcessor = btlp;
        }
        
        this.init(chart, _transitionProcessor);
    }
    
    @Override
    public void setTransitionLayoutProcessor(ITransitionLayoutProcessor transitionProcessor)
    {
        _transitionProcessor = transitionProcessor;
        _transitionProcessor.setLayout(_layout);
        _transitionProcessor.setChart(_chart);
    }

    @Override
    public void setLayout(ILayout layout)
    {
        _layout = layout;
        
        if(_transitionProcessor != null)
        {
            _transitionProcessor.setLayout(_layout);
        }
    }

    @Override
    public void setChart(IChart chart)
    {
        _chart = chart;
        
        if(_transitionProcessor != null)
        {
            _transitionProcessor.setChart(_chart);
        }
    }
    
    @Override
    public ILayout getLayout()
    {
        return _layout;
    }
    
    @Override
    public IChart getChart()
    {
        return _chart;
    }
    
    //for use in _setSize()
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

    protected void _setClustersPositions(SuperState superState)
    {
        //pre : _layout already has position of superState
        
        float x, y;
        Position superstatePosition;
        float offset = 0;
        
        superstatePosition = _layout.getPosition(superState);
        y = superstatePosition.y() + superState.roundedCornerRadius();
        
        for(ConcurrencyCluster cluster : superState.clusters())
        {
            x = superstatePosition.x() + offset;
            
            _layout.addPosition(cluster, new Position(x,y));

            offset += cluster.width();
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
