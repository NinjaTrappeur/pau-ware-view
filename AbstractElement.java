/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;

/**
 * Abstract graphic element.
 * @param width Width of the rectangle representing the element.
 * @param length Length of the rectangle representing the element.
 * 
 */
public abstract class AbstractElement implements Drawable {
    protected String _name;
    protected float _length;
    protected float _width;

    public AbstractElement(String name, float length, float width)
    {
        _name = name;
        _length = length;
        _width = width;
    }
    
    public void setName(String name)
    {
        _name = name;
    }
    
    public void setLength(float length)
    {
        _length = length;
    }
    
    public void setWidth(float width)
    {
        _width = width;
    }
    
    public String name()
    {
        return _name;
    }
    
    public float length()
    {
        return _length;
    }
    
    public float width()
    {
        return _width;
    }
    
    @Override
    public abstract void draw(processing.core.PApplet applet);
}
