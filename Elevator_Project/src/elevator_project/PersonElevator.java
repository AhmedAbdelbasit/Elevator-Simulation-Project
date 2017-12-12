package elevator_project;

import static elevator_project.Elevator_Project.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;


/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class PersonElevator extends Elevator {
    private final int personsCapacity;
    private Pane guiElevator;
    
    public PersonElevator(Building B, int eNumber, int capacity){
        setElevatorNumber(eNumber);
        personsCapacity = capacity;
        setParentBuilding(B);
        
        guiElevator = new Pane();
        guiElevator.setLayoutX(getElevatorX(eNumber));
        guiElevator.setLayoutY(getElevatorY(0));
        Rectangle R = new Rectangle(0,0,elevatorWidth, elevatorHeight);
        R.setFill(Color.BLUE);
        R.setOpacity(0.1);
        guiElevator.getChildren().add(R);

        guiElevator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                if(getStatus() == 's'){
                    getParentBuilding().getFloor(getLocation()).reOpenDoor(getElevatorNumber());
                }
            }
        });
    }
    
    public Pane getGuiContainer(){
        return this.guiElevator;
    }
    
    public int getCapacity(){
        return personsCapacity;
    }
    
    private static int getElevatorX(int i){
        return (10+(2*elevatorWidth)*(i)+elevatorWidth);
    }
    
    private int getElevatorY(int f){
        return (10+(numOfFloors)*floorHeight-elevatorHeight);
    }
}
