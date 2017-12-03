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
    private int buildingNumber;
    private ArrayList<Floor> floorsList;
    private ArrayList<PersonElevator> personElevatorsList;
    private final Brain brain;
    
    public Building(Brain b){
        brain = b;
        floorsList = new ArrayList();
        personElevatorsList = new ArrayList();
        /**
         * here, register the building in the brain
         */ 
    }
    
    public Building(Brain b, int nOfFloors, int nOfKeypads, int nOfElevators, int eCapacity){
        brain = b;
        
        floorsList = new ArrayList();
        Floor F;
        for(int i=0 ; i< nOfFloors ; i++){
            F = new Floor(this, i, nOfElevators);
            this.addFloor(F);
        }
        
        personElevatorsList = new ArrayList();
        PersonElevator PE;
        for(int i=0 ; i< nOfElevators ; i++){
            PE = new PersonElevator(this, i, eCapacity);
            this.addElevator(PE);
        }
    }
    
    public int getBuildingNumber(){
        return buildingNumber;
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
    
    public int getNumOfElevators(){
        return personElevatorsList.size();
    }
    
    public int getNumOfFloors(){
        return floorsList.size();
    }
    
}
