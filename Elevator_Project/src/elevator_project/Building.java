/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import java.util.ArrayList;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class Building {
    private int buildingID;
    private ArrayList<Floor> floorsList;
    private ArrayList<PersonElevator> personElevatorsList;
    private final Brain brain;
    
    public Building(Brain b){
        brain = b;
    }
    
    public int getBuildingNumber(){
        return buildingID;
    }
    
    public void addFloor(Floor F){
        floorsList.add(F);
    }
    
    public void addElevator(PersonElevator e){
        personElevatorsList.add(e);
    }
    
    public Brain getBrain(){
        return brain;
    }
    
    public void enableElevator(PersonElevator E){
        E.enable();
    }
    
    public void disableElevator(PersonElevator E){
        E.disable();
    }
    
    public Elevator getElevator(int i){
        return personElevatorsList.get(i);
    }
    
}
