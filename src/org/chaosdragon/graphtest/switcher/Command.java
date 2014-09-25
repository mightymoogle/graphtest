/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.switcher;

/**
 *
 * @author Mighty
 */
public interface Command {      
   public boolean execute();
   public void undo();
   public Command getNext();
   
}
