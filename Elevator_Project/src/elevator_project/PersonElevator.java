package elevator_project;

import static elevator_project.Elevator_Project.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Thread elevatorMotor;
    private ArrayList<Integer> targetsFloor;
    private ArrayList<Integer> targetsY;
    private int currentFloor = 0;
    private int motionStep = 2;
    
    public PersonElevator(Building B, int eNumber, int capacity){
        
        targetsY = new ArrayList();
        targetsFloor = new ArrayList();
                
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
            @Override
            public void handle(MouseEvent me) {
                if(getStatus() == 's'){
                    getParentBuilding().getFloor(getLocation()).reOpenDoor(getElevatorNumber());
                }
            }
        });
        
        initiateThread();
        
    }
    
    private void initiateThread(){
        elevatorMotor = new Thread(new Runnable(){
            @Override
            public void run() {
                

                while(targetsFloor.size() > 0){
//                    System.out.println(getElevatorNumber() + " : " + currentFloor);
                    System.out.print('.');
                    try {
                        if((targetsY.size() == targetsFloor.size())){
                            double p = guiElevator.getLayoutY();
                            double targetY = 0;
                            if(targetsY.size() > 0){
                                targetY = targetsY.get(0);
                            }
                            // Case Down
                            if(targetY > p){
                                if((targetY - p) >= motionStep){
                                    guiElevator.setLayoutY(p + motionStep);
                                }else{
                                    guiElevator.setLayoutY(p + (targetY - p));
                                }

                                int f = getElevatorFloor(p+1);
                                if (currentFloor != f){
                                    currentFloor = f;
                                    System.out.println("Elevator at floor : " + f);
                                    setLocation(f);
                                    setStatus('d');
                                }
    //                        System.out.println("Elevator at floor : " + getElevatorFloor(p+1));
                            // Case Up
                            }else if(targetY < p){
                                if((p - targetY) >= motionStep){
                                    guiElevator.setLayoutY(p-motionStep);
                                }else{
                                    guiElevator.setLayoutY(p - (p - targetY));
                                }

                                int f = (getElevatorFloor(p)+1);
                                if (currentFloor != f){
                                    currentFloor = f;
                                    System.out.println("Elevator at floor : " + f);
                                    setLocation(f);
                                    setStatus('u');
                                }
                            // Case Reached
                            }else{
                                setStatus('s');
                                getParentBuilding().getFloor(targetsFloor.get(0)).openDoor(getElevatorNumber());
                                targetsY.remove(0);
                                
                            }
//                            elevatorMotor.wait(500);
                            Thread.sleep(50);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PersonElevator.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
    
    public void addTarget(int f){
        if(targetsFloor.size() > 0){
            if(targetsFloor.get(targetsFloor.size() - 1) != f){
                targetsFloor.add(f);
                Building b = getParentBuilding();
                targetsY.add( 10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
                if (elevatorMotor.isAlive()){
                    System.out.println("Alive");
                }else{
                    System.out.println("Dead");
        //            elevatorMotor.start();
                    initiateThread();
                    elevatorMotor.start();
                }
            }
        }else{
            targetsFloor.add(f);
            Building b = getParentBuilding();
            targetsY.add( 10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
            if (elevatorMotor.isAlive()){
                System.out.println("Alive");
            }else{
                System.out.println("Dead");
    //            elevatorMotor.start();
                initiateThread();
                elevatorMotor.start();
            }
        }
    }
    
//    public void addTarget(int f){
//        targetFloor = f;
//        Building b = getParentBuilding();
//        targetY = ( 10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
//        
//        elevatorMotor.start();
//    }
//    
    @Override
    public void applyNext(){
        targetsFloor.remove(0);
    }
    
    private int getElevatorFloor(double y){
        int elevatorHeight = getParentBuilding().getElevatorHeight();
        y += (elevatorHeight - 10);
        double floor = numOfFloors - (y/floorHeight) ;
        return (int)floor;
    }
    
    public void startThread(){
        elevatorMotor.start();
    }
    
}
