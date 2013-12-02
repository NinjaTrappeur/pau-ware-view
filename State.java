/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;

public class State extends AbstractElement implements Drawable
{
    /**
     * This class represents a graphic state.
     * 
     * @param length Length of the rectangle representing the state.
     * @param width Width of the rectangle representing the state.
     * @param name Name of the state.
     */
    
    float _boxRadius;
    float _textSize;
    
    public State(float length, float width, String name)
    {
        super(name,length,width);
        _boxRadius = _width/5;
        _textSize = _boxRadius - 4;
    }
    
    public State(String name)
    {
        super(name, 10, 10);
        _boxRadius = _width/5;
        _textSize = _boxRadius - 4;
    }

    @Override
    public void draw(processing.core.PApplet applet)
    {
        applet.fill(255,255,255);
        applet.rect(0,0,_length,_width,_boxRadius);
        applet.line(0, _boxRadius, _length, _boxRadius);
        applet.fill(0, 0, 0);
        applet.textSize(_textSize);
        applet.textAlign(applet.CENTER);
        applet.text(_name, _boxRadius/2+_textSize/3, _boxRadius/10,
                _length-2*_boxRadius, _boxRadius);
    }
}
