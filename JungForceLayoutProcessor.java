/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

/**
 *
 * @author ninjatrappeur
 */
public class JungForceLayoutProcessor implements ILayoutProcessor {
    JungLayout _layout;
    JungChart _chart;
    
    
    @Override 
    public void init(IChart chart){
        
    }
    
    @Override
    Public ILayout getLayout(){
        return(_layout);
    }
}
