/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.gui.Matrix;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JPanel;
import org.chaosdragon.graphtest.gui.WizardForm;

/**
 *
 * @author Mighty
 */
public class Step2 extends Command{

    private ArrayList<Matrix> requirements;
    private WizardForm w;   
   
    
    //Must be put inside of a map or something...
    private ArrayList<Set<String>> precedentSets;
    private ArrayList<Set<String>> reachabilitySets;
    String[] ids;
    
    public Step2(ArrayList<Matrix> requirements, WizardForm p) {
        w=p;
        this.requirements=requirements;
    }
    
    
    @Override
    public boolean execute() {        
    
      w.clearText();
            
      Matrix m = new Matrix(requirements.get(0)); //Get a copy to avoid editing
                      
          ids = m.getIds();        
          int [][]table = m.getConnections();
          int rows = table.length;
          int columns = table[0].length;          
          
          
        precedentSets= new ArrayList<>();
        reachabilitySets= new ArrayList<>();
        
        for (int i=0; i<rows; i++) {
            precedentSets.add(new TreeSet<String>());
            reachabilitySets.add(new TreeSet<String>());        
        }
        
        //Precedent set
        for (int i=0; i<rows; i++)  {
            //reachability set();
            for (int j=0; j<columns; j++) {
                
                
                //If is 1 then add to both sets
                if (table[i][j]==1) {
                    
                    precedentSets.get(j).add(ids[i]);
                    
                    //i for F
                    //j for C
                    reachabilitySets.get(i).add(ids[j]);                    
                    
                }
                
            }
                }
                
                w.printText("Precedence matrices:");
                w.printText("\n");
                for (int i=0; i<precedentSets.size(); i++) {
                    w.printText(" Cx(d"+ids[i]+")="+precedentSets.get(i).toString());
                    w.printText("\n");
                }
                
                w.printText("\n");
                
                w.printText("Reachability matrices:");
                w.printText("\n");                
                for (int i=0; i<reachabilitySets.size(); i++) {
                    w.printText(" Fx(d"+ids[i]+")="+reachabilitySets.get(i).toString());
                    w.printText("\n");
                }
                
                //w.printText(""+table[i][j]);
                
            
    
        
      
      
     return false;
      
    }

    @Override
    public void undo() {          
        
      //  w.clearText();        
        
    }

    @Override
    public Command getNext() {
        //return new Step1(requirements,w);
        return new Step3(w,precedentSets,reachabilitySets,ids);
        
    }
 
    
}
