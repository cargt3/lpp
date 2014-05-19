/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocol;

import Player.Coordinates;

/**
 *
 * @author Karol
 */
public class CoordinatesPacket {
    private int id;
    private Coordinates coordinates;
    public CoordinatesPacket(int id, Coordinates coordinates)
    {
        this.id = id;
        this.coordinates = coordinates;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    
}
