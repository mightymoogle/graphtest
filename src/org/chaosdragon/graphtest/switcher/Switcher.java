/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.switcher;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mighty
 */
/* The Invoker class */
public class Switcher {
   private List<Command> history = new ArrayList<Command>();
 
   public void storeAndExecute(Command cmd) {
      
       //Add the command if it executes
       this.history.add(cmd);
       cmd.execute();    
      
       }     
         
   
   public void undo() {       
       //Undo the last
       if (history.size()==0) return;
       
       Command victim = history.get(history.size()-1);       
       victim.undo();
       //Remove from list
       history.remove(victim);              
       
   }
   
   
   public Command getLastStep() {       
         
       if (history.size()==0) return new NullCommand(); 
       
       return history.get(history.size()-1);
       
   } 
      
   
}