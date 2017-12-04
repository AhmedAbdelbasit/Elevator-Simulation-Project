/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import static elevator_project.Elevator_Project.*;
import java.util.ArrayList;
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
    
    private Pane guiFloor;
    private Pane guiKeypad;
    
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
        
        //        guiFloor = new Rectangle(20,20+floorNum*floorHeight,floorWidth, floorHeight-2);
        guiFloor = new Pane();
        guiFloor.setLayoutX(10);
        guiFloor.setLayoutY(10 + floorNum*floorHeight);
        Rectangle R = new Rectangle(0,0,floorWidth, floorHeight-2);
//        R.setLayoutX(10);
        R.setFill(Color.WHITE);
        guiFloor.getChildren().add(R);
//        guiFloor.setFill(Color.WHITE);
        
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
        ElevatorDoor ED = new ElevatorDoor(this,this.getBuilding().getElevator(i));
        listOfElevatorDoors.add(ED);
    }
}
