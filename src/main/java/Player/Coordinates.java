/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Player;

import java.io.Serializable;

/**
 *
 * @author Karol
 */
public class Coordinates implements Serializable{
    
    private double x;
    private double y;
    
    public Coordinates(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }
    
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
    
}
