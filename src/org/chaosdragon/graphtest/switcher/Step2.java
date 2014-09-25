/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.switcher;

import org.chaosdragon.graphtest.gui.Matrix;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.chaosdragon.graphtest.gui.WizardForm;

/**
 *
 * @author Mighty
 */
public class Step2 implements Command{

    ArrayList<Matrix> requirements;
    WizardForm w;   
   
    public Step2(ArrayList<Matrix> requirements, WizardForm p) {
        w=p;
        this.requirements=requirements;
    }
    
    
    @Override
    public boolean execute() {        
    
     //requirements.get(0).print();               
     return false;
      
    }

    @Override
    public void undo() {          
        
      //  w.clearText();        
        
    }

    @Override
    public Command getNext() {
        return new Step1(requirements,w);
    }
 
    
}
