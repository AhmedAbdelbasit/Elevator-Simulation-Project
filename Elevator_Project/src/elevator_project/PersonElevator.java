/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import static elevator_project.Elevator_Project.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;


/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class PersonElevator extends Elevator {
    private final int personsCapacity;
    
//    private Rectangle guiElevator;
    private Pane guiElevator;
    
    public PersonElevator(Building B, int eNumber, int capacity){
        setElevatorNumber(eNumber);
        personsCapacity = capacity;
        setParentBuilding(B);
        
//        guiElevator = new Rectangle(getElevatorX(eNumber),getElevatorY(0),elevatorWidth, elevatorHeight);
        guiElevator = new Pane();
        guiElevator.setLayoutX(getElevatorX(eNumber));
        guiElevator.setLayoutY(getElevatorY(0));
        Rectangle R = new Rectangle(0,0,elevatorWidth, elevatorHeight);
        R.setFill(Color.BLUE);
        R.setOpacity(0.6);
        guiElevator.getChildren().add(R);
//        guiElevator.setFill(Color.BLUE);
    }
    
    public Pane getGuiContainer(){
        return this.guiElevator;
    }
    
    public int getCapacity(){
        return personsCapacity;
    }
    
    private static int getElevatorX(int i){
        return (10+(floorWidth/(numOfPersonElevators+1))*(i+1)-elevatorWidth/2);
    }
    
    private int getElevatorY(int f){
        return (10+(numOfFloors)*floorHeight-elevatorHeight);
    }
    
    public static int getElevatorFloor(int y){
        int floor = numOfFloors - (y/floorHeight) - 1;
        return floor;
    }
}
