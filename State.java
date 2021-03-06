/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;

import com.pauware.pauware_engine._Core.AbstractStatechart;

public class State extends AbstractElement implements Drawable
{
    protected float _boxRadius;
    protected float _textSize;
    protected float _headerInnerTopMargin;
    protected AbstractStatechart _pauwareRef;
    
    private void _setDerived()
    {
        _boxRadius = _length/5;
        _textSize = 12 * (_width/100);
        /* Why this formula for textsize is just I found
            textsize=12 fine for length=100, so just keep the ratio
        */
        _headerInnerTopMargin = _boxRadius * 0.1F;
    }
    
    /**
     * This class represents a graphic state.
     * 
     * @param length Length of the rectangle representing the state.
     * @param width Width of the rectangle representing the state.
     * @param state Pauware state.
     * @param container optional container (like superstate or statechart)
     */
    public State(AbstractStatechart state, float width, float length, AbstractElement container)
    {
        super(state.name(), width, length, container);
        _setDerived();
    }
    
    public State(AbstractStatechart state, float width, float length)
    {
        super(state.name(), width, length);
        _setDerived();
    }
    
    public State(AbstractStatechart state, AbstractElement container){
        super(state.name(),150,100,container);
        _pauwareRef=state;
        _setDerived();
    }
    
    public State(AbstractStatechart state)
    {
        super(state.name(), 150, 100);
        _pauwareRef=state;
        _setDerived();
    }
    
    @Override
    public void setLength(float length){
        super.setLength(length);
        _setDerived();
    }

    @Override
    public void draw(processing.core.PApplet applet)
    {
        applet.pushMatrix();
        if(this.isActive() && (!(this instanceof SuperState)))
            applet.fill(255,104,51);
        else
            applet.fill(255,255,255);
        applet.rect(0,0,_width,_length,_boxRadius);
        applet.line(0, _boxRadius, _width, _boxRadius);
        applet.fill(0, 0, 0);
        applet.textSize(_textSize);
        applet.textAlign(applet.CENTER);
        applet.text(_name, 0, 0+_headerInnerTopMargin,
                _width, _boxRadius);
        applet.popMatrix();
    }
    
    public Boolean isActive(){
        _prePauwareAccess(_pauwareRef, "isActive");
        return _pauwareRef.active();
    }
    
    public float roundedCornerRadius()
    {
        return _boxRadius;
    }
    
    public AbstractStatechart getPauwareState()
    {
        return _pauwareRef;
    }
    
    public void setPauwareState( AbstractStatechart state){
        _pauwareRef = state;
    }
    
        private void _prePauwareAccess(AbstractStatechart ref, String methodName) throws IllegalArgumentException
    {
         if(ref == null)
        {
            throw new IllegalArgumentException("SuperState._preReference (for "
                    + methodName
                    + "): null pointer given gor initilization of pauWare state");
        }
    }
}
