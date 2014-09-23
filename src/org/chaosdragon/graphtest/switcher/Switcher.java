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
      this.history.add(cmd); // optional 
      cmd.execute();        
   }
   
   public void undo() {       
       //Undo the last
       Command victim = history.get(history.size());       
       victim.undo();
       //Remove from list
       history.remove(victim);       
   }
   
}