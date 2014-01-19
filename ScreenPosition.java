/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;

public class ScreenPosition extends Position
{
    private float _screenHeight;
    private float _screenWidth;
    
    private void _preSetCoord(float coord, float max, String coordName, String methodName) throws IllegalArgumentException {
        if(coord < 0)
        {
            throw new IllegalArgumentException("AbstractElement._preSetMetric (for "
                    + methodName
                    + "): " + coordName
                    + " can not be negative");
        }
        
        if(max != 0 && coord > max)
        {
            throw new IllegalArgumentException("AbstractElement._preSetMetric (for "
                    + methodName
                    + "): " + coordName
                    + " can not be greater than max size "+String.valueOf(max));
        }
    }
    
    private void _preSetCoord(float coord, String coordName, String methodName) throws IllegalArgumentException
    {
        _preSetCoord(coord, 0, coordName, methodName);
    }
    
    public ScreenPosition(float x, float y)
    {
        super(x,y);
        _screenHeight = 0;
        _screenWidth = 0;
        _preSetCoord(x, "x", "constructor");
        _preSetCoord(y, "y", "constructor");
    }
    
    public ScreenPosition(float x, float y, float screenWidth, float screenHeight)
    {
        super(x,y);
        _preSetCoord(screenWidth, "screen width", "constructor");
        _preSetCoord(screenHeight, "screen height", "constructor");
        _screenWidth = screenWidth;
        _screenHeight = screenHeight;

        _preSetCoord(x, _screenWidth, "x", "constructor");
        _preSetCoord(y, _screenHeight, "y", "constructor");
        
    }
    
    public void setScreenSize(float width, float height)
    {
        _preSetCoord(width, "screen width", "constructor");        
        _preSetCoord(height, "screen height", "constructor");
        _screenWidth = width;
        _screenHeight = height;
    }
    
    @Override
    public void setX(float x)
    {
        _preSetCoord(x, _screenWidth, "x", "setX");
        super.setX(x);
    }
    
    @Override
    public void setY(float y)
    {
        _preSetCoord(y, _screenHeight, "y", "setY");
        super.setY(y);
    }
    
    @Override
    public void set(float x, float y)
    {
        _preSetCoord(x, _screenWidth, "x", "constructor");
        _preSetCoord(y, _screenHeight, "y", "constructor");
        super.set(x, y);
    }
}
