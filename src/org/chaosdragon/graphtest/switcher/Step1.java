/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.switcher;

import org.chaosdragon.graphtest.gui.Matrix;
import java.util.ArrayList;

/**
 *
 * @author Mighty
 */
public class Step1 implements Command{

    ArrayList<Matrix> requirements;
    
    public void startup() {
        //Initialize stuff
        requirements=new ArrayList<>();
    }
    
    
    public Step1() {
        //Startup
        startup();
    }
    
    
    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undo() {  
        //Just start all over
        startup();
    }
    
}
