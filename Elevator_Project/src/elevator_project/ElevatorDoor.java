/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import static elevator_project.Elevator_Project.elevatorHeight;
import static elevator_project.Elevator_Project.elevatorWidth;
import static elevator_project.Elevator_Project.floorHeight;
import static elevator_project.Elevator_Project.floorWidth;
import static elevator_project.Elevator_Project.numOfPersonElevators;
import java.util.Timer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Poto
 */
public class ElevatorDoor {
    private Floor locatedFloor;
    private Elevator relatedElevator;

    
    private static int elevatorDoorWidth = elevatorWidth/2;
    
    private Pane guiElevatorDoor;
    private Rectangle leftDoor;
    private Rectangle rightDoor;
    
    public ElevatorDoor(Floor F, Elevator E, int eNum){
        locatedFloor = F;
        relatedElevator = E;
        
        guiElevatorDoor = new Pane();
        guiElevatorDoor.setLayoutX(getElevatorX(eNum));
        guiElevatorDoor.setLayoutY(getElevatorY());
        Rectangle R = new Rectangle(0,0,elevatorWidth, elevatorHeight);
        R.setFill(Color.BLUE);
        leftDoor = new Rectangle(0,0,elevatorWidth/2, elevatorHeight);
        leftDoor.setFill(Color.BROWN);
//        leftDoor.setOpacity(0.5);
        rightDoor = new Rectangle(getRightDoorX(),0,elevatorWidth/2, elevatorHeight);
        rightDoor.setFill(Color.BROWN);
        guiElevatorDoor.getChildren().add(R);
        guiElevatorDoor.getChildren().add(leftDoor);
        guiElevatorDoor.getChildren().add(rightDoor);
        
        
    }
    
    public Pane getGuiContainer(){
        return guiElevatorDoor;
    }
    
    private static int getRightDoorX(){
        return (elevatorWidth - elevatorDoorWidth);
    }
    
    private static int getElevatorX(int i){
        return ((floorWidth/(numOfPersonElevators+1))*(i+1)-elevatorWidth/2);
    }
    
    private static int getElevatorY(){
        return (floorHeight - elevatorHeight);
    }
    
    
    public void openDoor(){
        System.out.println("open door");
    }
    
    public void closeDoor(){
        System.out.println("close door");
    }
    
    public double getLeftDoorX(){
//        System.out.println(leftDoor.getLayoutX());
        return leftDoor.getLayoutX();
    }
    
    public void closeStep(){
        leftDoor.setLayoutX(leftDoor.getLayoutX()+1);
        rightDoor.setLayoutX(rightDoor.getLayoutX()-1);
    }
    
    public void openStep(){
        leftDoor.setLayoutX(leftDoor.getLayoutX()-1);
        rightDoor.setLayoutX(rightDoor.getLayoutX()+1);
    }
}
