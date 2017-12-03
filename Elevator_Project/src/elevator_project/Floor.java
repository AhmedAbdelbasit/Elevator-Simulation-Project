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

public class Floor {
    private final Building parentBuilding;
    private final int floorNumber;
    private ArrayList<Keypad> listOfKeypads;
    private ArrayList<ElevatorDoor> listOfElevatorDoors;
    
    public Floor(Building B, int floorNum){
        parentBuilding = B;
        floorNumber = floorNum;
        listOfKeypads = new ArrayList();
        listOfElevatorDoors = new ArrayList();
    }    
    
    public Floor(Building B, int floorNum,int NumOfElevators){
        parentBuilding = B;
        floorNumber = floorNum;
        listOfKeypads = new ArrayList();
        listOfElevatorDoors = new ArrayList();
        ElevatorDoor ED ;
        for(int i=0; i<NumOfElevators ; i++){
            ED = new ElevatorDoor(this,this.getBuilding().getElevator(i));
            listOfElevatorDoors.add(ED);
        }
    }
    
    public int getFloorNumber(){
        return floorNumber;
    }
    
    public Building getBuilding(){
        return parentBuilding;
    }
    
    public void addKeypad(){
        Keypad K = new Keypad(this);
        listOfKeypads.add(K);
    }
    
    public void addElevatorDoor(int i){
        ElevatorDoor ED = new ElevatorDoor(this,this.getBuilding().getElevator(i));
        listOfElevatorDoors.add(ED);
    }
}
