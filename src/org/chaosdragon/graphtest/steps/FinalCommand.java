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
    public void undo() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Command getNext() {
        return new FinalCommand();
    }
    
}
