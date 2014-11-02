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
    public boolean execute() {        
        return false;
    }


    @Override
    public Command getNext() {
        return new FinalCommand();
    }
    
}
