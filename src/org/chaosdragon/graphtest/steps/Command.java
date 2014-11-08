/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.steps;

/**
 *
 * @author Mighty
 */
public abstract class Command {      
   abstract public boolean execute();
//   abstract public void undo();
   abstract public Command getNext();
   
   protected boolean skippable = true;
   
   private Command previousCommand;

    /**
     * @return the previousCommand
     */
    public Command getPreviousCommand() {
        return previousCommand;
    }

    /**
     * @param previousCommand the previousCommand to set
     */
    public void setPreviousCommand(Command previousCommand) {
        this.previousCommand = previousCommand;
    }   
     
//    public class CommandBuilder{
//        
//        public CommandBuilder() {
//            
//        }
//        
//        public Command buildCommand() {
//            
//            return null;
//            
//        }        
        
//    }

    /**
     * @return the skippable
     */
    public boolean isSkippable() {
        return skippable;
    }

    /**
     * @param skippable the skippable to set
     */
    public void setSkippable(boolean skippable) {
        this.skippable = skippable;
    }
    
    
   
}
