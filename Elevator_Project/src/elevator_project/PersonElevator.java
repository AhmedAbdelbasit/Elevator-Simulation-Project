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
    
    private PersonElevator me;
    private final int personsCapacity;
    private Pane guiElevator;
    private Thread elevatorMotor;
    private ArrayList<Integer> targetsFloor;
    private ArrayList<Integer> targetsY;
    private int currentTarget;
    private int currentFloor = 0;
    private final int motionStep = 2;
    private final int delayTime = 50;
    
    public PersonElevator(Building B, int eNumber, int capacity){
        me = this;
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
                try {
                    while(targetsFloor.size() > 0){
                        System.out.print('.');
                        if((targetsY.size() == targetsFloor.size())){
                            double p = guiElevator.getLayoutY();
                            double targetY = p;
                            if(targetsY.size()>0){
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
                                    getParentBuilding().getBrain().update(me);
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
                                    getParentBuilding().getBrain().update(me);
                                }
                            // Case Reached
                            }else{
                                if(targetsY.size() > 0){
                                    setStatus('s');
                                    getParentBuilding().getFloor(targetsFloor.get(0)).openDoor(getElevatorNumber());
                                    targetsY.remove(0);
                                    getParentBuilding().getBrain().update(me);
                                }
                            }
                            Thread.sleep(delayTime);
                        }
                    }
                } catch (InterruptedException ex) {
//                        Logger.getLogger(PersonElevator.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public int addTarget(int f, int sInd){
        int s = targetsFloor.size();
        int rIndex = 0;
        boolean SET = false;
        
        if(s > sInd){
            for(int i=1 ; i<s ; i++){
                if( (f<=targetsFloor.get(i) && f>=targetsFloor.get(i-1)) || (f>=targetsFloor.get(i) && f<=targetsFloor.get(i-1)) ){
                    targetsFloor.add(i,f);
                    SET = true;
                    rIndex = i;
                }
            }
        }else if(s == 1 && sInd ==0){
            if( (f<=targetsFloor.get(0) && f>=currentFloor) || (f>=targetsFloor.get(0) && f<=currentFloor) ){
                rIndex = 0;
                SET = true;
                targetsFloor.add(0,f);
            }
        }
        
        if(!SET){
            rIndex = s;
            targetsFloor.add(f);
        }
            
        Building b = getParentBuilding();
        if(rIndex == s){
            targetsY.add( 10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
        }else{
            if(targetsY.size() == s){
                targetsY.add(rIndex, 10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
            }else if(targetsY.size() == 0){
                targetsY.add(10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
            }else if(rIndex == 0){
                targetsY.add(rIndex, 10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
            }else{
                targetsY.add(rIndex-1, 10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
            }
        }
        
        if (elevatorMotor.isAlive()){
            System.out.println("Alive");
        }else{
            System.out.println("Dead");
            initiateThread();
            elevatorMotor.start();
        }
        
        trimRequests();
        System.out.println("#######");
        System.out.println(targetsFloor.size());
        System.out.println("#######");
        System.out.println(targetsY.size());
        System.out.println("#######");
        for(int i=0 ; i<targetsY.size() ; i++){
            System.out.print(targetsFloor.get(i) + " >> " + targetsY.get(i) + " ,, ");
        }
        
        return (rIndex+1);
        
//        
//        System.out.println("#######");
//            if(targetsFloor.get(s - 1) != f){
//                targetsFloor.add(f);
//                Building b = getParentBuilding();
//                targetsY.add( 10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
//                if (elevatorMotor.isAlive()){
//                    System.out.println("Alive");
//                }else{
//                    System.out.println("Dead");
//        //            elevatorMotor.start();
//                    initiateThread();
//                    elevatorMotor.start();
//                }
//            }
//        }
//        else{
//            targetsFloor.add(f);
//            Building b = getParentBuilding();
//            targetsY.add( 10+ ((b.getNumOfFloors() - f)*(b.getFloorHeight())) - b.getElevatorHeight() );
//            if (elevatorMotor.isAlive()){
//                System.out.println("Alive");
//            }else{
//                System.out.println("Dead");
//    //            elevatorMotor.start();
//                initiateThread();
//                elevatorMotor.start();
//            }
//        }
    }
    
    public void trimRequests(){
        for(int i=1 ; i<targetsFloor.size() ; i++ ){
            if(targetsFloor.get(i) == targetsFloor.get(i-1)){
                if(targetsFloor.size() == targetsY.size()){
                    targetsY.remove(i);
                }else{
                    targetsY.remove(i-1);
                }
                targetsFloor.remove(i);
                
            }
        }
    }
    
    public ArrayList getRequestsList(){
        return this.targetsFloor;
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
