/*
 * ????? (Slide - 35)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.gui.Matrix;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JPanel;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step5 extends Command{
   
    WizardForm w;   
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<String[]> ids;
    ArrayList<Matrix> reachabilityMatrices;
    
    
    //Must be put inside an arraylist...
    ArrayList<Set<String>> informationalElements;
    ArrayList<Set<String>> requirementGroups;
   
    public Step5(WizardForm p,
            ArrayList<Set<String>> informationalElements,
            ArrayList<Set<String>> requirementGroups,
            
            ArrayList<ArrayList<Set<String>>> precedentSets, 
            ArrayList<ArrayList<Set<String>>> reachabilitySets,ArrayList<String[]> ids,
            ArrayList<Matrix> reachabilityMatrices) {
        w=p;
        this.informationalElements=informationalElements;
        this.requirementGroups=requirementGroups;
        this.precedentSets=precedentSets;
        this.reachabilitySets=reachabilitySets;
        this.ids = ids;
        this.reachabilityMatrices=reachabilityMatrices;
    }
    
    
    @Override
    public boolean execute() {        
    
      w.clearText();
               
      for (int current=0; current<requirementGroups.size(); current++) {
          
          Set<String> group = requirementGroups.get(current);
          int[][] matr = new int[group.size()][group.size()];
          
          int i = 0;
          int j = 0;
          
         //IDs for the new sub-matrix
          String[] newIds = new String[matr.length];
          
        for(String s1:group) {
            
            //Setting of ids
            newIds[i] = s1;  
            for(String s2:group) {
                
                //It is 1 if there is a connection in the big matrix OR if it is itself!
                if (reachabilityMatrices.get(current).isConnected(s1, s2)||s1.equals(s2)) {
                    matr[i][j]=1;
                }
                
                j++;                
            }   
            j=0;
            i++;
        }
          
          w.printText("Submatrix A"+(current+1)+"g:\n");
          Matrix m = new Matrix(newIds, matr);
          w.printText(m.toString());          
          w.printText("\n");
          
      }

     return false;
    
    }

    @Override
    public void undo() {          
        
      //  w.clearText();        
        
    }

    @Override
    public Command getNext() {
        //return new Step1(requirements,w);
        //return new Step3();
        
        
        //MUST ALSO PROVIDE F FROM PREVIOUS (REACHABILITY)???????
        return new Step5(w, informationalElements, requirementGroups,precedentSets, reachabilitySets,ids,reachabilityMatrices);
    }
 
    
}
