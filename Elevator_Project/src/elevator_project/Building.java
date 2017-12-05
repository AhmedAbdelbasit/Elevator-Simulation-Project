/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import static elevator_project.Elevator_Project.*;
import java.util.ArrayList;
import java.util.Timer;

import javafx.scene.shape.*;
import javafx.scene.layout.Pane;


/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class Building {
    private int buildingNumber;
    private ArrayList<Floor> floorsList;
    private ArrayList<PersonElevator> personElevatorsList;
    private final Brain brain;
    
    private Pane guiContainer;
    private Rectangle guiBuilding ;
    
    private BuildingTimer timerTask;
    private Timer timer;
    
    public Building(Brain b){
        brain = b;
        floorsList = new ArrayList();
        personElevatorsList = new ArrayList();
        /**
         * here, register the building in the brain
         */ 
        
        /**
         * GUI Code
         */
        guiContainer = new Pane();
        guiContainer.setLayoutX(10);
        guiContainer.setLayoutY(10);
        guiBuilding = new Rectangle(10,10,buildingWidth, buildingHeight);
        guiContainer.getChildren().add(guiBuilding);
    }
    
    public Building(Brain b, int num, int nOfFloors, int nOfKeypads, int nOfElevators, int eCapacity){
        brain = b;
        
        personElevatorsList = new ArrayList();
        PersonElevator PE;
        for(int i=0 ; i< nOfElevators ; i++){
            PE = new PersonElevator(this, i, eCapacity);
            this.addElevator(PE);
        }
        
        floorsList = new ArrayList();
        Floor F;
        for(int i=0 ; i< nOfFloors ; i++){
            F = new Floor(this, i, nOfElevators, 1);
            this.addFloor(F);
        }
        
        
        /**
         * GUI CODE
         */
        guiContainer = new Pane();
        guiContainer.setLayoutX(10 + (buildingWidth + 10)* num);
        guiContainer.setLayoutY(10 + 20 * num);
        guiBuilding = new Rectangle(0,0,buildingWidth, buildingHeight);
        guiContainer.getChildren().add(guiBuilding);
        
        // Adding floors to GUI
        for(int i=0 ; i<numOfFloors ; i++){
            guiContainer.getChildren().add(floorsList.get(i).getGuiContainer());
        }
        
        // Ading elevators to GUI
        for(int i=0 ; i<nOfElevators ; i++){
            guiContainer.getChildren().add(personElevatorsList.get(i).getGuiContainer());
        }
    
        timerTask = new BuildingTimer(this);
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 40);
        for(int i=0 ; i< numOfPersonElevators ; i++){
//            timerTask.addTarget(1,i);
//            timerTask.addTarget(0,i);
//            timerTask.addTarget(2,i);
        }
    }
    
    public Floor getFloor(int i){
        return floorsList.get(numOfFloors - i - 1);
    }
    
    public void addElevatorTask(int eNum, int fNum){
        timerTask.addTarget(fNum, eNum);
    }
    
    public void finishElevatorTask(int eNum){
        timerTask.applyNextOrder(eNum);
    }
    
    public Pane getBuildingPane(){
        return this.guiContainer;
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
//        return personElevatorsList.size();
        return numOfPersonElevators;
    }
    
    public int getNumOfFloors(){
        return numOfFloors;
    }
    
    public int getElevatorHeight(){
        return elevatorHeight;
    }
    
    public int getFloorHeight(){
        return floorHeight;
    }
}
