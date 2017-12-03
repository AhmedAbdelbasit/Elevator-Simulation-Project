/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class PersonElevator extends Elevator {
    private final int personsCapacity;
    
    
    public PersonElevator(Building B, int eNumber, int capacity){
        setElevatorNumber(eNumber);
        personsCapacity = capacity;
        setParentBuilding(B);
    }
    
    public int getCapacity(){
        return personsCapacity;
    }
}
