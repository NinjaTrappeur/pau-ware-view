/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.Collection;
import java.util.HashSet;

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
        
        Collection<AbstractElement> substates;
        float transX, transY;
        float angleStep;
        float marginX, marginY;
        
        if(container instanceof StateChart)
        {
            substates = ((StateChart)container).nestingLevels().get(0);
        }
        
        else
        {
            substates = ((ConcurrencyCluster)container).substates();
        }
        
        //Translation from origin
        
        //angle step
        
        // inner margins
        
        //radius
        
        //coordinates
        
        //translate to origin
        
        //coordinates from objet's centre to left pu corner
        
        //clusters positions for SuperStates
    }
}
