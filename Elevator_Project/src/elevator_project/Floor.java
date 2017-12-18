/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import static elevator_project.Elevator_Project.*;
import java.util.ArrayList;
import java.util.Timer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class Floor {
    private final Building parentBuilding;
    private final int floorNumber;
    private ArrayList<Keypad> listOfKeypads;
    private ArrayList<ElevatorDoor> listOfElevatorDoors;
    
//    private DoorTimer timerTask;
//    private Timer timer;
    
    private Pane guiFloor;
//    private Pane guiKeypad;
    
    
    public Floor(Building B, int floorNum){
        parentBuilding = B;
        floorNumber = floorNum;
        listOfKeypads = new ArrayList();
        listOfElevatorDoors = new ArrayList();
        
//        guiFloor = new Rectangle(20,20+floorNum*floorHeight,floorWidth, floorHeight-2);
        guiFloor = new Pane();
        Rectangle R = new Rectangle(20,20+floorNum*floorHeight,floorWidth, floorHeight-2);
        R.setLayoutX(10);
        R.setFill(Color.WHITE);
//        guiFloor.setFill(Color.WHITE);

    }    
    
    public Floor(Building B, int floorNum,int NumOfElevators, int numOfKeypads){
        parentBuilding = B;
        floorNumber = numOfFloors - floorNum - 1;
        listOfKeypads = new ArrayList();
        listOfElevatorDoors = new ArrayList();
        ElevatorDoor ED ;
        for(int i=0; i<NumOfElevators ; i++){
            ED = new ElevatorDoor(this,this.getBuilding().getElevator(i),i);
            listOfElevatorDoors.add(ED);
        }
        
        for(int i=0; i<numOfKeypads ; i++){
            listOfKeypads.add(new Keypad(this));
        }
        
        /**
         * GUI CODE
         */
        guiFloor = new Pane();
        guiFloor.setLayoutX(10);
        guiFloor.setLayoutY(10 + floorNum*floorHeight);
        Rectangle R = new Rectangle(0,0,floorWidth, floorHeight-2);
        R.setFill(Color.WHITE);
        guiFloor.getChildren().add(R);
        
        for(int i=0; i<NumOfElevators ; i++){
            guiFloor.getChildren().add(listOfElevatorDoors.get(i).getGuiContainer());
        }
        
        for(int i=0; i<numOfKeypads ; i++){
            guiFloor.getChildren().add(listOfKeypads.get(i).getGuiContainer());
        }
        
//        timerTask = new DoorTimer(this);
//        timer = new Timer(true);
//        timer.scheduleAtFixedRate(timerTask, 0, 40);
//        for(int i=0 ; i< NumOfElevators ; i++){
//            timerTask.addTarget(1,i);
//            timerTask.addTarget(0,i);
//        }
//        if(floorNum == 1){
//            timerTask.addTarget(1,0);
//            timerTask.addTarget(0,0);
//        }

        // adding rectangles to hide doors when openning
        for(int i=0; i<(NumOfElevators+1) ; i++){
            R = new Rectangle(2*i*elevatorWidth,floorHeight-elevatorHeight,elevatorWidth,elevatorHeight-2);
            R.setFill(Color.WHITE);
            guiFloor.getChildren().add(R);
        }
    }
    
    public Pane getGuiContainer(){
        return guiFloor;
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
        ElevatorDoor ED = new ElevatorDoor(this,this.getBuilding().getElevator(i),i);
        listOfElevatorDoors.add(ED);
    }
    
    public ElevatorDoor getElevatorDoor(int i){
        return listOfElevatorDoors.get(i);
    }
    
    public void openDoor(int dNum){
        // Timer
//        timerTask.addTarget(1, dNum);
        // Threads
        listOfElevatorDoors.get(dNum).addTarget(1, dNum);
    }
    
    public void reOpenDoor(int dNum){
        // Timer
//        timerTask.addTarget(0,1, dNum);
        // Threads
        listOfElevatorDoors.get(dNum).addTarget(1, dNum);
    }
    
//    private static int getElevatorX(int i){
//        return (10+(floorWidth/(numOfPersonElevators+1))*(i+1)-elevatorWidth/2);
//    }
//    
//    private static int getElevatorY(){
//        return (floorHeight - elevatorHeight);
//    }
}
