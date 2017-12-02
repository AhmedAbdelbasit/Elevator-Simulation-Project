/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public abstract class Elevator implements IObservable{
    
    private Building parentBuilding;
    private int elevatorNumber;
    private float currentPosition = 0;
    private int motionStatus = 0;
    
    public Elevator(){
        
    }
    
    public void setElevatorNumber(int n){
        elevatorNumber = n;
    }
    public int getElevatorNumber(){
        return elevatorNumber;
    }
    
    public void setParentBuilding(Building B){
        parentBuilding = B;
    }
    public Building getParentBuilding(){
        return parentBuilding;
    }
    
    @Override
    public void add(IObserver O){
        
    }
    
    @Override
    public void remove(IObserver O){
        
    }
    
    @Override
    public void notifyObservers(){
        
    }
    
    public void moveTo(int floorNumber){
        
    }
    
    public void openDoor(){
        
    }
    
    public void enable(){
        
    }
    
    public void disable(){
        
    }
    
    
    
}
