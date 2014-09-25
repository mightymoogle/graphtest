/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.switcher;

import org.chaosdragon.graphtest.gui.*;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.chaosdragon.graphtest.gui.WizardForm;

/**
 *
 * @author Mighty
 */
public class Step1 implements Command{

    ArrayList<Matrix> requirements;
    WizardForm w;          
        
    public Step1(ArrayList<Matrix> requirements, WizardForm p) {
        this.w = p;
        this.requirements = requirements;
    }
    
    
    //Does nothing
    @Override
    public boolean execute() {       
        w.clearText();
       // w.printText("FUCK YOU ALL!");
      //w.printText(requirements.get(0).print());
      
      Matrix m = new Matrix(requirements.get(0)); //Get a copy to avoid editing
      
      w.printText("BASE MATRIX "+"\n");
      w.printText(MatrixTools.printMatrix(m.getConnections(), m.getIds())); 
      
      
      String[] ids = m.getIds();        
      int counter = 0;      
      int[][]m2 = m.getConnections();      
      
      //At first it is the base matrix, will add other later
      int[][] end  = m2; 
            
      //FIX 10 to "is equals to the last one or numbers just grow"
      while (counter<10 && !MatrixTools.isMatrixZeroOnly(m2)) {
                  
         //result=result+"\n"+ "Matrix "+counter;
          
                  
         m2=MatrixTools.multiply(m2, m.getConnections());              
         
         w.printText("\n"+"Matrix step "+counter+"\n");
         w.printText(MatrixTools.printMatrix(m2, ids));     
         
         //Add all 1
         end = MatrixTools.specialAdd(end, m2);
         counter++;       
                  
      }
      
        w.printText("\n"+"Reachability Matrix:"+"\n");
        w.printText(MatrixTools.printMatrix(end, ids)+"\n");
           
        
        //A beautiful dialog box with matrix
         //JOptionPane.showMessageDialog(
         //null, new JLabel( "<html><pre>" + MatrixTools.printMatrix(end, ids)));
        
        return true;
    }
    
    //Resets the List
    
    @Override
    public void undo() {  
                
                
    }
    
    public Command getNext() {
        
        return new Step2(requirements,w);
        
    }
  
    
}
