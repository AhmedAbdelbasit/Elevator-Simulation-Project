/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

/**
 *
 * @author Poto
 */
public class ElevatorDoor {
    private final Floor locatedFloor;
    private final Elevator relatedElevator;
    
    public ElevatorDoor(Floor F, Elevator E){
        locatedFloor = F;
        relatedElevator = E;
    }
    
    public void openDoor(){
        
    }
    
    public void closeDoor(){
        
    }
}
