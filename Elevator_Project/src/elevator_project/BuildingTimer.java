/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

/**
 *
 * @author Poto
 */
import java.util.ArrayList;

//import java.util.Date;
//import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

public class BuildingTimer extends TimerTask {
    private ArrayList<Integer>[] targetsY;
    private ArrayList<Integer>[] targetsFloor;
    private Building building;
    private ArrayList<Pane> guiElevatorList;
    private int nOfElevators;
    
    public BuildingTimer(Building B){
        building  = B;
        nOfElevators = building.getNumOfElevators();
        guiElevatorList = new ArrayList();
        
        for(int i=0 ; i<nOfElevators ; i++){
            guiElevatorList.add(((PersonElevator)building.getElevator(i)).getGuiContainer());
        }
        targetsY = new ArrayList[nOfElevators];
        targetsFloor = new ArrayList[nOfElevators];
        for(int i=0 ; i<nOfElevators ; i++){
            targetsY[i] = new ArrayList();
            targetsFloor[i] = new ArrayList();
        }
        //targets = new ArrayList[E.length];
    }
    public void addTarget(int x, int e){
        targetsY[e].add( 10+ ((building.getNumOfFloors() - x)*(building.getFloorHeight())) - building.getElevatorHeight() );
        targetsFloor[e].add(x);
    }
    
    @Override
    public void run() {
        completeTask();
    }

    private void completeTask() {
        try {
            for(int elevatorNum=0 ; elevatorNum<nOfElevators ; elevatorNum++){
                
                if((targetsY[elevatorNum].size() > 0) && (targetsY[elevatorNum].size() == targetsFloor[elevatorNum].size())){
                    double p = guiElevatorList.get(elevatorNum).getLayoutY();
                    
                    if(targetsY[elevatorNum].get(0) > p){
                        guiElevatorList.get(elevatorNum).setLayoutY(p + 1);
                    }else if(targetsY[elevatorNum].get(0) < p){
                    guiElevatorList.get(elevatorNum).setLayoutY(p-1);
                    }else{
                        building.getFloor(targetsFloor[elevatorNum].get(0)).openDoor(elevatorNum);
                        targetsY[elevatorNum].remove(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void applyNextOrder(int eNum){
        targetsFloor[eNum].remove(0);
    }
}

//TimerTask timerTask = new MyTimerTask();
//        //running timer task as daemon thread
//        Timer timer = new Timer(true);
//        timer.scheduleAtFixedRate(timerTask, 0, 10*1000);
//        System.out.println("TimerTask started");
//        //cancel after sometime
//        try {
//            Thread.sleep(120000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        timer.cancel();
//        System.out.println("TimerTask cancelled");
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }