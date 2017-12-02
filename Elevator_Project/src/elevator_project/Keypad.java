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

public class Keypad {
    private final Brain brain;
    private final Floor locatedFloor;
    private final Screen attachedScreen;
    
    public Keypad(Floor F){
        locatedFloor = F;
        Screen S = new Screen(this);
        attachedScreen = S;
        brain = this.locatedFloor.getBuilding().getBrain();
    }
    
    public void sendRequest(Keypad K, Request R){
        
    }
    
    public void displayResponse(int R){
        attachedScreen.display(R);
    }
}
