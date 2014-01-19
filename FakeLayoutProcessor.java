/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;

import java.util.HashMap;
import java.util.ArrayList;
import java.awt.geom.Point2D;

/**
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
        AbstractElement chart = _states.get("Idle").container();
        ArrayList<Point2D> path;
        Transition trans;
//        System.err.println("FakeLayoutProcessor.processLayout: Chart size is "+chart.width()+","+chart.length());
//        System.err.println("FakeLayoutProcessor.processLayout: deepContentSize of Chart is "+chart.deepContentSize());

        _layIt("Idle", 720, 250);
        _layIt("Busy", 50, 50);
        _layIt("start_to_Idle", 800, 350);
        
        _layIt("S1", 220, 200);
        _layIt("S2", 415, 200);
        _layIt("S3", 65, 200);
        
        _layIt("S11", 230, 280);
        _layIt("S12", 330, 280);
        _layIt("S21", 425, 280);
        _layIt("S22", 520, 280);
        _layIt("S31", 70, 280);
        _layIt("S32", 130, 280);
        _layIt("start_to_S11", 280, 400);
        _layIt("start_to_S22", 500, 405);
        
        TransitionLayoutProcessor transitionProcessor= new TransitionLayoutProcessor(_layout,_chart);
        transitionProcessor.computeTransitionsLayout();
    }
    
    private void _layIt(String stateName, float x, float y)
    {
        AbstractElement state;
        float width, length;
        float ratio = 1.F;
        
        state = _states.get(stateName);
        if(state.container().deepContentSize() != 0)
            ratio = ((float)(state.deepContentSize()+1))/state.container().deepContentSize();

//        System.err.println("FakeProcessor: deepContentSize for "+state.name()+" is "+state.deepContentSize());
//        System.err.println("FakeProcessor: ratio for "+state.name()+" is "+String.valueOf(state.deepContentSize()+1)+"/"+state.container().deepContentSize()+"="+ratio);
//        System.err.println("FakeProcessor: container of "+state.name()+" is "+state.container().name());
//        System.err.println("FakeProcessor: size of container "+state.container().name()+" is "+state.container().width()+","+state.container().length());

        width  = ratio * AbstractElement._coveredAreaRatio * state.container().width();
        length = ratio * AbstractElement._coveredAreaRatio * state.container().length();
        
        if(! (state instanceof PseudoState))
            state.setSize(width, length);
//        System.err.println("FakeProcessor: Setting size of "+state.name()+" to "+String.valueOf(width)+","+String.valueOf(length));

        _layout.addPosition(state, x, y);
//        System.err.println("FakeProcessor: Setting position of "+state.name()+" to "+String.valueOf(x)+","+String.valueOf(y));
    }
}
