/*
 * Placeholder for FINAL operation
 */
package org.chaosdragon.graphtest.steps;

/**
 *
 * @author Mighty
 */
public class FinalCommand extends Command{
      
    @Override
    public void execute() {      
    }

    //Last one is not skippable
    public FinalCommand() {
        super();     
        skippable = false;    
    }    

    @Override
    public Command getNext() {
        return new FinalCommand();
    }
    
}
