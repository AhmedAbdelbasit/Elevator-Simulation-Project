/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import static elevator_project.Elevator_Project.numOfPersonElevators;
import java.util.ArrayList;
/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */
public class Brain implements IObserver {
    private ArrayList<Request> requestsQueue;
    private ArrayList<BrainBuilding> buildingsList;
    
    private int eState = 0;
    
    public Brain(){
        requestsQueue = new ArrayList();
        buildingsList = new ArrayList();
    }
    
    public void request(Request R){
        requestsQueue.add(R);
        manageRequests();
    }
    
    private void manageRequests(){
        Request R;
        while( requestsQueue.size() > 0){
            R = requestsQueue.get(0);
            requestsQueue.remove(0);
            applyRequest(R);
        }
    }
    
    private void applyRequest(Request R){
        
        // get request details
        Keypad k = R.getSourceKeypad();
        int buildingNumber = k.getBuilding().getBuildingNumber();
        int s = R.getSource();
        int d = R.getDestination();
        
        // get best fit elevator
        int eNum = getBestFit(buildingNumber, s,  d);
        
        // send response
//        eNum = eState;
//        eState += 1;
//        if(eState == numOfPersonElevators){
//            eState = 0;
//        }

        k.displayResponse(eNum);
        ((PersonElevator)(k.getBuilding().getElevator(eNum))).addTarget(s);
        ((PersonElevator)(k.getBuilding().getElevator(eNum))).addTarget(d);
//        k.getBuilding().addElevatorTask(eNum, s);
//        k.getBuilding().addElevatorTask(eNum, d);
    }
    
    private int getBestFit(int b, int s, int d){
        BrainBuilding BB = buildingsList.get(b);
        int bestE = 0;
        int min_path = 1000;
        int temp;
        
        for(int e=0 ; e<BB.getNumOfElevators() ; e++){
            temp = BB.registeredElevators.get(e).getFitness(s,d);
            if(temp < min_path){
                min_path = temp;
                bestE = e;
            }
        }
        return bestE;
    }
    
    public void registerBuilding(Building mB){
        BrainBuilding B = new BrainBuilding(mB);
        buildingsList.add(B);
        System.out.println("Building number " + mB.getBuildingNumber() + " with " + mB.getNumOfElevators() + " Elevators");
    }
    
    @Override
    public void addStation(IObservable S){
        PersonElevator E = (PersonElevator)S;
        int Bnum = E.getParentBuilding().getBuildingNumber();
        buildingsList.get(Bnum).addElevator(E);
    }
    
    @Override
    public void removeStation(IObservable S){
        
    }
    
    @Override
    public void update(IObservable S){
        Elevator E = (PersonElevator)S;
        int Bnum = E.getParentBuilding().getBuildingNumber();
        buildingsList.get(Bnum).updateElevator(E);
    }
    
    public void updateElevatorLocation(Elevator E, int f){
        int b = E.getParentBuilding().getBuildingNumber();
        int e = E.getElevatorNumber();
        buildingsList.get(b).registeredElevators.get(e).setLocation(f);
    }
    
    private class BrainBuilding{
        public ArrayList<BrainElevator> registeredElevators;
        private final int numOfFloors;
        private int numOfElevators;
        
        public BrainBuilding(Building B){
            numOfFloors = B.getNumOfFloors();
            numOfElevators = B.getNumOfElevators();
            registeredElevators = new ArrayList();
            for(int i=0 ; i<numOfElevators ; i++){
                addElevator(B.getElevator(i));
                registeredElevators.get(i).printData();
            }
        }
        
        public void addElevator(Elevator E){
            char s = E.getStatus();
            int l = E.getLocation();
            int n = E.getNumOfPersonsInside();
            int eN = E.getElevatorNumber();
            ArrayList r = ((PersonElevator)E).getRequestsList();
            
            BrainElevator BE = new BrainElevator(eN,s, l, n, r);
            registeredElevators.add(BE);
        }
        
        public void updateElevator(Elevator E){
            char s = E.getStatus();
            int l = E.getLocation();
            int n = E.getNumOfPersonsInside();
            registeredElevators.get(E.getElevatorNumber()).updateData(s, l, n);
        }
        
        public int getNumOfElevators(){
            return numOfElevators;
        }
    }
    
    private class BrainElevator{
        private char status;
        private int location;
        private int numberOfPersonsInside;
        private int elevatorNumber;
        public ArrayList<Integer> requests;
        
        public BrainElevator(int eNum, char s, int l, int nP, ArrayList req){
            elevatorNumber = eNum;
            status = s;
            location = l;
            numberOfPersonsInside = nP;
            requests = req;
        }
        
        private int getFitness(int s, int d){
            int numOfFloorsTravelled = 0;
            int rSize = requests.size();
            int sIndex = 0;
            boolean SET = false;
            
            if(rSize == 0){
                numOfFloorsTravelled = (Math.abs(d-s) + Math.abs(s-location));
                SET = true;
            }else{
                if((s>=location && s<=requests.get(0)) || (s<=location && s>=requests.get(0)) ){
                    sIndex = 0;
                    SET = true;
                    numOfFloorsTravelled += Math.abs(s-location);
                }else{
                    numOfFloorsTravelled += Math.abs(requests.get(0)-location);
                }
                
                for(int i=1 ; i<rSize ; i++){
                    if((s>=requests.get(i) && s<=requests.get(i-1)) || (s<=requests.get(i) && s>=requests.get(i-1)) ){
                        sIndex = i;
                        numOfFloorsTravelled += Math.abs(s-requests.get(i-1));
                        SET = true;
                        break;
                    }else{
                        numOfFloorsTravelled += Math.abs(requests.get(i-1)-requests.get(i));
                    }
                }
                
                if(!SET){
                    numOfFloorsTravelled += Math.abs(s-requests.get(rSize-1));
                    sIndex = rSize;
                }
                
                
                
                // destination calculation
                SET = false;
                if(sIndex >= rSize-1){
                     numOfFloorsTravelled += Math.abs(s-d);
                     SET = true;
                }else{
                    if((d>=s && d<=requests.get(sIndex)) || (d<=s && d>=requests.get(sIndex)) ){
                        numOfFloorsTravelled += Math.abs(s-d);
                        SET = true;
                    }else{
                        numOfFloorsTravelled += Math.abs(requests.get(sIndex)-s);
                    }
                    
                    for(int i=sIndex+1 ; i<rSize ; i++){
                        if((d>=requests.get(i) && d<=requests.get(i-1)) || (d<=requests.get(i) && d>=requests.get(i-1)) ){
                            numOfFloorsTravelled += Math.abs(d-requests.get(i-1));
                            SET = true;
                            break;
                        }else{
                            numOfFloorsTravelled += Math.abs(requests.get(i-1)-requests.get(i));
                        }
                    }
                }
                if(!SET){
                    numOfFloorsTravelled += Math.abs(d-requests.get(rSize-1));
                }
            }
            System.out.println();
            System.out.println("##################");
            System.out.println("Num Of Travelled Floors = " + numOfFloorsTravelled);
            System.out.println("##################");
            System.out.println();
            return numOfFloorsTravelled;
        }
        
        public void updateData(char s, int l, int n){
            status = s;
            location = l;
            numberOfPersonsInside = n;
            System.out.println();
            System.out.println("######");
            System.out.println(s + " " + l + " " + n);
            System.out.println("######");
            System.out.println();   
        }

        public void setNumberOfPersonsInside(int n) {
            numberOfPersonsInside = n;
        }
        public void setStatus(char s){
            status = s;
        }
        public char getStatus(){
            return status;
        }
        public void setLocation(int f){
            location = f;
        }
        public int getLocation(){
            return location;
        }
        public void printData(){
            System.out.print("\nRegistered Elevator " + elevatorNumber);
            System.out.print("\n\t\tStatus : " + status + "\n\t\tlocation : " + location + "\n\t\tnumberOfPersonsInside : " + numberOfPersonsInside);
            System.out.println();
        }
    }
}
