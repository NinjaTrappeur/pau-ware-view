/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author josuah
 */
public class CircleLayoutProcessor extends AbstractLayoutProcessor
{    
    @Override
    public void processLayout()
    {
        //calcul des tailles
        _setAllSizes();
        
        //
    }
    
    protected void _computeSizesInContext(AbstractElement container)
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
        }
        
        if(!substates.isEmpty())
        {
            //Translation from origin
            containerPos = (container instanceof StateChart) ? new Position(0,0) : _layout.getPosition(container);
            transX = containerPos.x() + (container.width() / 2);
            transY = containerPos.y() + (container.length() / 2);

            //angle step
            angleStep = (float) (2F*3.1415 / substates.size());

            // inner margins
            marginX = AbstractElement._innerMarginRatio * container.width();
            marginY = AbstractElement._innerMarginRatio * container.length();

            //treat each substate
            int i = 0;
            Iterator<AbstractElement> it = substates.iterator();
            while(it.hasNext() && i < substates.size())
            {
                state = it.next();
                alpha = i * angleStep;
                
                //radius
                if(Math.cos(alpha) == 0) //avoid dividing by zero
                {
                    radius = (float) ((state.length() - (state.length()/2 + marginY)) / Math.sin(alpha));
                }
                
                else
                {
                    radius = (float) ((state.width() - (state.width()/2 + marginX)) / Math.cos(alpha));
                }
                
                radius = Math.abs(radius);

                //coordinates
                x = (float) (radius * Math.cos(alpha));
                y = (float) (radius * Math.sin(alpha));

                //translate to origin
                x += transX;
                y += transY;

                //coordinates from objet's centre to left top corner
                x -= state.width()/2;
                y -= state.length()/2;
                
                //adde to layout
                _layout.addPosition(state, new Position(x,y));

                //clusters positions for SuperStates
                if(state instanceof SuperState)
                {
                    superState = (SuperState)state;
                }

                ++i;
            }

        }
    }
}
