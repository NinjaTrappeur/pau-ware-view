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
public abstract class AbstractElement {
    public AbstractElement(float length, float width){
        _length = length;
        _width = width;
    }
    public abstract void draw();
    protected float _length;
    protected float _width;
}
