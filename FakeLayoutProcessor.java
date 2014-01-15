/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.HashMap;

/**
 *
 * @author josuah
 * 
 * This class is intend to test produce a static predefined layout that
 * is for use with the Chart build by class PauWare_Component
 */
public class FakeLayoutProcessor implements ILayoutProcessor
{
    ILayout _layout;
    IChart _chart;
    HashMap<String, AbstractElement> _states;
    
    FakeLayoutProcessor()
    {
        _layout = new Layout();
        _states = new HashMap();
    }

    @Override
    public void init(IChart chart)
    {
        _chart = chart;

        for(AbstractElement state : _chart.elements())
        {
            _states.put(state.name(), state);
        }
    }
    
    @Override
    public ILayout getLayout()
    {
        return _layout;
    }
    
    @Override
    public void processLayout()
    {
        _layIt("Idle", 620, 210);
        _layIt("Busy", 50, 50);
        
        _layIt("S1", 80, 150);
        _layIt("S2", 410, 150);
        _layIt("S3", 80, 370);
        
        _layIt("S11", 90, 200);
        _layIt("S12", 180, 200);
        _layIt("S21", 420, 200);
        _layIt("S22", 420, 310);
        _layIt("S31", 90, 410);
        _layIt("S32", 180, 410);
    }
    
    private void _layIt(String stateName, float x, float y)
    {
        AbstractElement state;
        float ratio = 1.F;
        
        state = _states.get(stateName);
        if(state.container().deepContentSize() != 0)
            ratio = ((float)(state.deepContentSize()+1))/state.container().deepContentSize();
        state.setSize(ratio*state.container().width(), ratio*state.container().length());
        _layout.addPosition(state, x, y);
    }
}
