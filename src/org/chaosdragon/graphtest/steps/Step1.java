/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.steps;

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
public class Step1 extends Command{

    ArrayList<Matrix> requirements;
    WizardForm w;  
    
    //One for List each requirement
    ArrayList<Matrix>
            reachabilityMatrices = new ArrayList<>();
        
    
    public Step1(ArrayList<Matrix> requirements, WizardForm p) {
        this.w = p;
        this.requirements = requirements;
    }
        
    //Does nothing
    @Override
    public boolean execute() {     
        
        final boolean PRINTALL = false;
        
       int current = 0;
       w.clearText();
       for (int cc=0; cc<requirements.size();cc++) {
       
       if (PRINTALL)
           w.printText("**** REQUIREMENT "+(cc+1)+ " ****\n");       
           
//        for (int i=0; i<requirements.size(); i++) {
//            reachabilityMatrices.add(new ArrayList<Matrix>());
//        }
        
      
       
       
      //w.printText(requirements.get(0).print());
      
      Matrix m = new Matrix(requirements.get(current)); //Get a copy to avoid editing
      
       if (PRINTALL) {
      w.printText("BASE MATRIX "+"\n");      
      w.printText(MatrixTools.printMatrix(m.getConnections(), m.getIds())); 
       }            
      
      
      String[] ids = m.getIds();        
      int counter = 0;      
      int[][]m2 = m.getConnections();      
      
      //At first it is the base matrix, will add other later
      int[][] end  = m2; 
            
      //FIX 10 to "is equals to the last one or numbers just grow"
      while (counter<10 && !MatrixTools.isMatrixZeroOnly(m2)) {
                  
         //result=result+"\n"+ "Matrix "+counter;
          
                  
         m2=MatrixTools.multiply(m2, m.getConnections());              
         
          if (PRINTALL) {
         w.printText("\n"+"Matrix step "+counter+"\n");
         w.printText(MatrixTools.printMatrix(m2, ids));     
          }
         
         //Add all 1
         end = MatrixTools.specialAdd(end, m2);
         counter++;       
                  
      }
      
        w.printText("\n"+"Reachability Matrix A"+(current+1)+":\n");
        w.printText(MatrixTools.printMatrix(end, ids)+"\n");
           
        
        //A beautiful dialog box with matrix
         //JOptionPane.showMessageDialog(
         //null, new JLabel( "<html><pre>" + MatrixTools.printMatrix(end, ids)));
        reachabilityMatrices.add(new Matrix(ids,end));
       
        current++;
       }
       
        return true;
    }
    
    //Resets the List
    
    @Override
    public void undo() {  
                
                
    }
    
    public Command getNext() {
        
        return new Step2(reachabilityMatrices,w);
        
    }
  
    
}
