/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.HashSet;
import java.awt.Dimension;
/**
 *
 * @author ninjatrappeur
 */
public class BasicLayoutProcessor implements ILayoutProcessor{
   
    IChart _chart;
    
    private void _handleSuperState(Position pos, Dimension size){
        
    }
    
    @Override
    public void init(IChart chart){
        HashSet<AbstractElement> nestingLevel;
        int nbStates, stateNumber;
        float width = 900, height = 900;
        Dimension size;
        float margin = 20;
        Position pos;
        _chart = chart;
        
        nestingLevel = _chart.nestingLevels().get(0);
        nbStates = nestingLevel.size();
        stateNumber = 1;
        for(AbstractElement elem: nestingLevel){
            size = new Dimension();
            if(nbStates == 1){
                size.setSize(width - 2*margin, height - 2*margin);
                pos = new Position(margin,margin);
            }
            else{
                float heightState = (float) (height/Math.ceil(nbStates/2)+2*Math.ceil(nbStates/2));
                size.setSize(width/2 - 4*margin, 
                        heightState);
                pos = new Position(margin + (2*margin + width)*nbStates%2,
                                    (float)(Math.floor(stateNumber/2)*(heightState+margin)));
            }
            if(elem instanceof SuperState)
                _handleSuperState(pos,size);
           stateNumber++; 
        }
    }
    
    @Override
    public ILayout getLayout(){
        ILayout layout = new Layout();
        return layout;
    }
    
    @Override
    public void processLayout(){
        
    }

}