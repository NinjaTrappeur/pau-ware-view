/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;
import java.awt.Dimension;
/**
 *
 * @author ninjatrappeur
 */
public class BasicLayoutProcessor implements ILayoutProcessor{
   
    IChart _chart;
    float _margin;
    HashMap<AbstractElement,Position> _positionMap;
    
    private void _computeSubLayout(Collection<AbstractElement> elemSet,
            Position pos, Dimension size){
        
        int nbStates = elemSet.size();
        Position newPos;
        Dimension newSize;
        int stateNumber = 1;
        
        for(AbstractElement elem: elemSet){
            newSize = new Dimension();
            if(nbStates == 1){
                newSize.setSize(size.getWidth() - 2*_margin,
                        size.getHeight() - 2*_margin);
                newPos = new Position(pos.x()+_margin,pos.y()+_margin);
            }
            else{
                newSize.setSize(size.getWidth()/2 - 2*_margin, 
                        size.getHeight()/nbStates - 2*_margin);
                newPos = new Position( ((stateNumber%2)*((float)size.getWidth()/2)+_margin+pos.x()),
                        (float)Math.floor((stateNumber-1)/2) * 
                                ((float)size.getHeight() / (float)Math.ceil(nbStates/2)) +_margin);
            }
//            if(!(elem instanceof PseudoState))
//            {
//                elem.setLength((float)newSize.getHeight());
//                elem.setWidth((float)newSize.getWidth());
//            }
            _positionMap.put(elem, newPos);
            if(elem instanceof SuperState){
                SuperState superState = (SuperState) elem;
                _computeSubLayout(superState.substates(), newPos, newSize);
                        }
            System.out.println("State number = "+stateNumber);
            stateNumber++;
            System.out.println("Taille de "+elem.name()+" "+elem.width()+"x"+elem.length()+" à "+newPos.x()+"/" + newPos.y());
        }
    }
    
    public BasicLayoutProcessor(){
        _margin = 3;
    }
    
    @Override
    public void init(IChart chart){
        _chart = chart; 
        _positionMap = new HashMap();
    }
    
    @Override
    public ILayout getLayout(){
        ILayout layout = new Layout();
        for(AbstractElement elem :_chart.elements())
        {
            layout.addPosition(elem, _positionMap.get(elem));
        }
        return layout;
    }
    
    @Override
    public void processLayout(){
        HashSet<AbstractElement> nestingLevel;
        Dimension size = new Dimension(900, 900);
        Position pos = new Position(0, 0);
        nestingLevel = _chart.nestingLevels().get(0);
        _computeSubLayout(nestingLevel, pos, size);
    }

}