/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * This class is intend to test produce a static predefined layout that
 * is for use with the Chart build by class PauWare_Component
 */
public class FakeLayoutProcessor extends AbstractLayoutProcessor
{
    HashMap<String, AbstractElement> _states;
    
    public FakeLayoutProcessor()
    {
        super();
        _states = new HashMap();
    }
    
    private void _init()
    {
        for(AbstractElement state : _chart.elements())
        {
            _states.put(state.name(), state);
        }
    }
    
    @Override
    public void init(IChart chart, ITransitionLayoutProcessor transitionProcessor)
    {
        super.init(chart, transitionProcessor);
        _init();
    }

    @Override
    public void init(IChart chart)
    {
        super.init(chart);
        _init();
    }
    
    @Override
    public void processLayout()
    {
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
        
//        _transitionProcessor.computeTransitionsLayout();
      
         ArrayList<Point2D> path;
        Transition trans;
        trans = new Transition(_states.get("Idle"),_states.get("Busy"));
        path = new ArrayList();
        path.add(new Point2D.Double(720,270));
        path.add(new Point2D.Double(599,270));
        _layout.addTransitionPath(trans, path);
        
        trans = new Transition(_states.get("S12"),_states.get("S11"));
        path = new ArrayList();
        path.add(new Point2D.Double(330,320));
        path.add(new Point2D.Double(273,320));
        _layout.addTransitionPath(trans, path);
        
        trans= new Transition(_states.get("Busy"),_states.get("S12"));
        path = new ArrayList();
        path.add(new Point2D.Double(399,320));
        path.add(new Point2D.Double(373,320));
        _layout.addTransitionPath(trans, path);
        
        trans= new Transition(_states.get("Busy"),_states.get("S21"));
        path = new ArrayList();
        path.add(new Point2D.Double(200,320));
        path.add(new Point2D.Double(230,320));
        _layout.addTransitionPath(trans, path);
        
        trans= new Transition(_states.get("S2"),_states.get("Idle"));
        path = new ArrayList();
        path.add(new Point2D.Double(575,290));
        path.add(new Point2D.Double(720,290));
        _layout.addTransitionPath(trans, path);
        
        trans = new Transition(_states.get("S21"), _states.get("Idle"));
        path = new ArrayList();
        path.add(new Point2D.Double(450, 355));
        path.add(new Point2D.Double(450, 450));
        path.add(new Point2D.Double(750, 450));
        path.add(new Point2D.Double(750, 295));
        _layout.addTransitionPath(trans, path);
        
        trans = new Transition(_states.get("S1"), _states.get("S12"));
        path = new ArrayList();
        path.add(new Point2D.Double(379, 380));
        path.add(new Point2D.Double(350, 380));
        path.add(new Point2D.Double(350, 355));
        _layout.addTransitionPath(trans, path);
        
        trans= new Transition(_states.get("S32"),_states.get("S32"));
        path = new ArrayList();
        path.add(new Point2D.Double(143,502));
        path.add(new Point2D.Double(143,530));
        path.add(new Point2D.Double(163,530));
        path.add(new Point2D.Double(163,502));
        _layout.addTransitionPath(trans, path);
        
        trans= new Transition(_states.get("start_to_S22"),_states.get("S22"));
        path = new ArrayList();
        path.add(new Point2D.Double(510,420));
        path.add(new Point2D.Double(540,420));
        path.add(new Point2D.Double(540,355));
        _layout.addTransitionPath(trans, path);
        
        trans= new Transition(_states.get("start_to_Idle"),_states.get("Idle"));
        path = new ArrayList();
        path.add(new Point2D.Double(815,350));
        path.add(new Point2D.Double(815,275));
        path.add(new Point2D.Double(765,275));
        _layout.addTransitionPath(trans, path);
        
        trans= new Transition(_states.get("start_to_S11"),_states.get("S11"));
        path = new ArrayList();
        path.add(new Point2D.Double(295,415));
        path.add(new Point2D.Double(250,415));
        path.add(new Point2D.Double(250,355));
        _layout.addTransitionPath(trans, path);
    }
    
    private void _layIt(String stateName, float x, float y)
    {
        AbstractElement state;
        float width, length;
        float ratio = 1.F;
        
        state = _states.get(stateName);
        if(state.container().deepContentSize() != 0)
            ratio = ((float)(state.deepContentSize()+1))/state.container().deepContentSize();

        width  = ratio * AbstractElement._coveredAreaRatio * state.container().width();
        length = ratio * AbstractElement._coveredAreaRatio * state.container().length();
        
        if(! (state instanceof PseudoState))
            state.setSize(width, length);

        _layout.addPosition(state, x, y);
    }
}
