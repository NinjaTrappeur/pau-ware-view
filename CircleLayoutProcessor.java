/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */

package com.PauWare.PauWare_view;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class CircleLayoutProcessor extends AbstractLayoutProcessor
{    
    public CircleLayoutProcessor()
    {
        super();
    }
    
    @Override
    public void processLayout()
    {
        HashMap<Integer, HashSet<AbstractElement>> nestings;
        HashSet<AbstractElement> elems;
        
        //calcul des tailles
        _setAllSizes();
        
        //top nesting level computing
        System.err.println("CircleLayoutProcessor.processLayout: chart size is "+_chartAsAabstractElement.width()+","+_chartAsAabstractElement.length());
        _computePositionsInContext(_chartAsAabstractElement);
        
        //all other nesting levels
        for(Integer level : _chart.nestingLevels().keySet())
        {
            for(AbstractElement state : _chart.nestingLevels().get(level))
            {
                if(state instanceof SuperState)
                {
                    SuperState superState = (SuperState)state;
                    for(ConcurrencyCluster cluster : superState.clusters())
                    {
                        _computePositionsInContext(cluster);
                    }
                }
            }
        }
        
        //Computing transitions
        TransitionLayoutProcessor transitionProcessor= new TransitionLayoutProcessor(_layout,_chart);
        transitionProcessor.setDrawingOption(TransitionLayoutProcessor.DrawingOptions.BREAK_LINE);
        transitionProcessor.computeTransitionsLayout();
    }
    
    protected float _computeRadius(AbstractElement container, AbstractElement substate, float maxX, float maxY, float angle, float marginX, float marginY)
    {
        float radiusCos = 0;
        float radiusSin = 0;
        float radius = 0;
        float maxRadius;
        
        maxRadius = (float) Math.sqrt(Math.pow(maxX-marginX, 2) + Math.pow(maxY-marginY, 2));
        
        //compute the values
        if(Math.cos(angle) != 0) //avoid dividing by zero
        {
            radiusCos = (float) ((container.width()/2 - (substate.width()/2 + marginX)) / Math.cos(angle));
            radiusCos = Math.abs(radiusCos);
        }

        if(Math.sin(angle) != 0)
        {
            radiusSin = (float) ((container.length()/2 - (substate.length()/2 + marginY)) / Math.sin(angle));
            radiusSin = Math.abs(radiusSin);
        }
        
        System.err.println("CircleLayoutProcessor._computeRadius: cos("+angle+") is "+Math.cos(angle));
        System.err.println("CircleLayoutProcessor._computeRadius: radiusCos is "+radiusCos);
        System.err.println("CircleLayoutProcessor._computeRadius: sin("+angle+") is "+Math.sin(angle));
        System.err.println("CircleLayoutProcessor._computeRadius: radiusSin is "+radiusSin);
        
        //choose the good value
        if(0 <= angle && angle <= 0.785398163 ||
           2.35619449 <= angle && angle <= 3.926990817 ||
           5.497787144 <= angle && angle <= 6.283185307
                )
        {
            radius = radiusCos;
        }
        
        else
        {
            radius = radiusSin;
        }
        
        return radius;
    }
    
    protected void _computePositionsInContext(AbstractElement container)
    {
        //pre: container is a container (statechart, cluster   ... but not SuperState)
        //pre: container is already layed, ie _layout.getPosition(container) fonctionne  --> C'est aussi le post de getPosition (pas besoin ? ou try catch?)
        
        Collection<AbstractElement> substates;
        AbstractElement state;
        SuperState superState;
        Position containerPos;
        float transX, transY;
        float angleStep, alpha;
        float marginX, marginY;
        float radius;
        float x, y;
        
        if(container instanceof StateChart)
        {
            substates = ((StateChart)container).nestingLevels().get(0);
        }
        
        else
        {
            substates = ((ConcurrencyCluster)container).substates();
            // safe because of precondition
        }
        
        if(!substates.isEmpty())
        {
            //Translation factor to get from center to top left corner
            containerPos = (container instanceof StateChart) ? new Position(0,0) : _layout.getPosition(container);
            transX = containerPos.x() + (container.width() / 2);
            transY = containerPos.y() + (container.length() / 2);
            System.err.println("CircleLayoutProcessor._computePositionsInContext: computed translation is "+transX+","+transY);

            //angle step
            angleStep = (float) (2F*3.1415 / substates.size());
            System.err.println("CircleLayoutProcessor._computePositionsInContext: computed angle step is "+angleStep);

            // inner margins
            marginX = AbstractElement._innerMarginRatio * container.width();
            marginY = AbstractElement._innerMarginRatio * container.length();
            System.err.println("CircleLayoutProcessor._computePositionsInContext: computed margin are "+marginX+","+marginY);

            //process each substate
            int i = 0;
            Iterator<AbstractElement> it = substates.iterator();
            while(it.hasNext() && i < substates.size())
            {
                state = it.next();
                System.err.println("CircleLayoutProcessor._computePositionsInContext: computing position of "+state.name());
                System.err.println("CircleLayoutProcessor._computePositionsInContext: size of "+state.name()+" is "+state.width()+","+state.length());

                alpha = i * angleStep;
                System.err.println("CircleLayoutProcessor._computePositionsInContext: angle at step "+i+" is "+alpha);
                
                //radius
                if(substates.size() > 1)
                    radius = _computeRadius(container, state, container.width()/2, container.length()/2, alpha, marginX, marginY);
                else // if only one, center it
                    radius = 0;
                
                System.err.println("CircleLayoutProcessor._computePositionsInContext: computed radius is "+radius);

                //coordinates
                x = (float) (radius * Math.cos(alpha));
                y = (float) (radius * Math.sin(alpha));
                System.err.println("CircleLayoutProcessor._computePositionsInContext: computed centered coordinates are "+x+","+y);

                //translate to origin
                x += transX;
                y += transY;
                System.err.println("CircleLayoutProcessor._computePositionsInContext: computed tranlated coordinates are "+x+","+y);

                //coordinates from objet's centre to left top corner
                x -= state.width()/2;
                y -= state.length()/2;
                System.err.println("CircleLayoutProcessor._computePositionsInContext: computed corned coordinates are "+x+","+y);
                
                //add to layout
                _layout.addPosition(state, new Position(x,y));

                //for SuperStates, compute clusters positions
                if(state instanceof SuperState)
                {
                    superState = (SuperState)state;
                    _setClustersPositions(superState);
                }

                ++i;
            }

        }
    }
}
