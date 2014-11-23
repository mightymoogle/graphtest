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

    public NullCommand(){
       skippable = false; 
    }
    
    @Override
    public void execute() {

    }



    @Override
    public Command getNext() {
        return new NullCommand();
    }
    
}
