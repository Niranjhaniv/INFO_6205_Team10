/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.domain;

/**
 *
 * @author Niranjanii
 */
public class Room {
    private String number;
    private int seatCapacity;

    public Room(String number, int seatCapacity) {
        this.number = number;
        this.seatCapacity = seatCapacity;
    }

    public String getNumber() {
        return number;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

  
    
}
