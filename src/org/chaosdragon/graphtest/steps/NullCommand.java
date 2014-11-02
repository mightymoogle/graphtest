/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.steps;

/**
 *
 * @author Mighty
 */
public class NullCommand extends Command{

    @Override
    public boolean execute() {
        return false;
    }



    @Override
    public Command getNext() {
        return null;
    }
    
}
