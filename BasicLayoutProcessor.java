/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;

import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;
import java.awt.Dimension;

public class BasicLayoutProcessor extends AbstractLayoutProcessor
{
   
    float _margin;
    HashMap<AbstractElement,Position> _positionMap;
    
    private void _computeSubLayout(Collection<AbstractElement> elemSet,
            Position pos, Dimension size)
    {
        
        int nbStates = elemSet.size();
        Position newPos;
        Dimension newSize;
        int stateNumber = 1;
        
        for(AbstractElement elem: elemSet)
        {
            newSize = new Dimension();
            if(nbStates == 1)
            {
                newSize.setSize(size.getWidth() - 2*_margin,
                        size.getHeight() - 2*_margin);
                newPos = new Position(pos.x()+_margin,pos.y()+_margin);
            }
            else
            {
                newSize.setSize(size.getWidth()/2 - 2*_margin, 
                        size.getHeight()/nbStates - 2*_margin);
                newPos = new Position( ((stateNumber%2)*((float)size.getWidth()/2)+_margin+pos.x()),
                        (float)Math.floor((stateNumber-1)/2) * 
                                ((float)size.getHeight() / (float)Math.ceil(nbStates/2)) +_margin);
            }

            if(!(elem instanceof PseudoState))
            {
                elem.setLength((float)newSize.getHeight());
                elem.setWidth((float)newSize.getWidth());
            }
            _positionMap.put(elem, newPos);
            if(elem instanceof SuperState)
            {
                SuperState superState = (SuperState) elem;
                _computeSubLayout(superState.substates(), newPos, newSize);
            }
            stateNumber++;
        }
    }
    
    public BasicLayoutProcessor()
    {
        super();
        _margin = 3;
        _positionMap = new HashMap();
    }
    
    @Override
    public void processLayout()
    {
        HashSet<AbstractElement> nestingLevel;
        Dimension size = new Dimension(900, 600);
        Position pos = new Position(0, 0);
        nestingLevel = _chart.nestingLevels().get(0);
        _computeSubLayout(nestingLevel, pos, size);

        for(AbstractElement elem :_chart.elements())
        {
            _layout.addPosition(elem, _positionMap.get(elem));
        }

        //Computing transitions
        _transitionProcessor.computeTransitionsLayout();
    }

}