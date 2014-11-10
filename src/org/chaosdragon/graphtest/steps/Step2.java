/*
 * FINDS PRECEDENCE AND REACHABILITY SETS (Slide 31-32)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import java.util.ArrayList;
import java.util.HashSet;
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
public class Step2 extends Command {

    ArrayList<Matrix> requirements;
    ArrayList<Matrix> reachabilityMatrices;
    WizardForm w;

    //Must be put inside of a map or something...
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<String[]> newIds = new ArrayList<>();

    public Step2(ArrayList<Matrix> reach, WizardForm p,ArrayList<Matrix> requirements) {
        w = p;
        this.reachabilityMatrices = requirements;
        this.requirements=requirements;
    }
    
    public Step2(Step1 old) {        
        w = old.w;
        reachabilityMatrices = old.reachabilityMatrices;
        requirements = old.requirements;        
    }
    

    @Override
    public boolean execute() {

        w.clearText();
        precedentSets = new ArrayList<>();
            reachabilitySets = new ArrayList<>();

                //Each requirement gets its own array list
            for (int i = 0; i < reachabilityMatrices.size(); i++) {
                precedentSets.add(new ArrayList());
                reachabilitySets.add(new ArrayList());
            }
            
           
            
            
        for (int current = 0; current < reachabilityMatrices.size(); current++) {

            

        

            Matrix m = new Matrix(reachabilityMatrices.get(current)); //Get a copy to avoid editing

            String[] ids = m.getIds();
            newIds.add(ids);
            int[][] table = m.getConnections();
            int rows = table.length;
            int columns = table[0].length;

            for (int i = 0; i < rows; i++) {
                precedentSets.get(current).add(new TreeSet<String>(new NaturalOrderComparator()));
                reachabilitySets.get(current).add(new TreeSet<String>(new NaturalOrderComparator()));
            }

            //Precedent set
            for (int i = 0; i < rows; i++) {
                //reachability set();
                for (int j = 0; j < columns; j++) {

                    //If is 1 then add to both sets
                    if (table[i][j] == 1) {

                        precedentSets.get(current).get(j).add(newIds.get(current)[i]);

                    //i for F
                        //j for C
                        reachabilitySets.get(current).get(i).add(newIds.get(current)[j]);

                    }

                }
            }

        }
        
                w.printText("Precedence sets:");
                w.printText("\n");
                for (int cc=0; cc<reachabilityMatrices.size();cc++) {
                    for (int i=0; i<precedentSets.get(cc).size(); i++) {
                        w.printText(" C"+(cc+1)+"(d"+newIds.get(cc)[i]+")="+precedentSets.get(cc).get(i).toString());
                        w.printText("\n");
                    }
                    w.printText("*********\n");
                }
                
                w.printText("\n");                
                w.printText("Reachability sets:");
                w.printText("\n");                
               for (int cc=0; cc<reachabilityMatrices.size();cc++) {
                    for (int i=0; i<reachabilitySets.get(cc).size(); i++) {
                        w.printText(" F"+(cc+1)+"(d"+newIds.get(cc)[i]+")="+reachabilitySets.get(cc).get(i).toString());
                        w.printText("\n");
                    }
                      w.printText("*********\n");
                }
        
        

        return true;

    }



    @Override
    public Command getNext() {
        //return new Step1(requirements,w);
       // return new Step3(w, null, null, ids);
        return new Step3(this);

    }

}
