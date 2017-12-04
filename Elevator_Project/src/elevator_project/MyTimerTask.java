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
import static elevator_project.Elevator_Project.*;
import java.util.ArrayList;

//import java.util.Date;
//import java.util.Timer;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private ArrayList<Integer>[] targets;
    
    public MyTimerTask(){
        targets = new ArrayList[E.length];
        for(int i=0 ; i<E.length ; i++){
            targets[i] = new ArrayList();
        }
        //targets = new ArrayList[E.length];
    }
    public void addTarget(int x, int e){
        targets[e].add( 20 + (numOfFloors - x)*floorHeight - elevatorHeight );
    }
    
    @Override
    public void run() {
        completeTask();
    }

    private void completeTask() {
        try {
            for(int elevatorNum=0 ; elevatorNum<E.length ; elevatorNum++){
                double p = E[elevatorNum].getY();
                if(targets[elevatorNum].size() > 0){
                    if(targets[elevatorNum].get(0) > p){
                        E[elevatorNum].setY(p + 1);
                    }else if(targets[elevatorNum].get(0) < p){
                        E[elevatorNum].setY(p - 1);
                    }else{
                        targets[elevatorNum].remove(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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