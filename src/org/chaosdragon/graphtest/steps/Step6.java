/*
 * Group informational contents (Slide - 41) NOT FINISHED!!!
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import java.util.ArrayList;
import java.util.*;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step6 extends Command {
    WizardForm w;
    ArrayList<Matrix> requirements; //Bx
    ArrayList<Set<String>> informationalElements; //DxF
    ArrayList<Set<String>> requirementGroups;     //DxG       
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<String[]> ids;
    ArrayList<Matrix> reachabilityMatrices;
    ArrayList<Matrix> submatrices;
    ArrayList<ArrayList<Set<String>>> groupLevels;
    
    //Current requirement -> Group id from D-> Content
    ArrayList<Map<String, Set<String>>> groupInformation;

    public Step6(Step5 old) {
        w = old.w;
        requirements = old.requirements;
        informationalElements = old.informationalElements;
        requirementGroups = old.requirementGroups;
        precedentSets = old.precedentSets;
        reachabilitySets = old.reachabilitySets;
        ids = old.ids;
        reachabilityMatrices = old.reachabilityMatrices;
        submatrices = old.submatrices;
        groupLevels = old.groupLevels;
    }

    @Override
    public void execute() {
        w.clearText();
        groupInformation = new ArrayList<>();

        //DELETE ELEMENTS FROM Bk
        for (int current = 0; current < requirements.size(); current++) {
            w.printText("Requirement " + (current + 1) + ":\n");
            groupInformation.add(new TreeMap<String, Set<String>>(new NaturalOrderComparator()));

            Matrix b = new Matrix(requirements.get(current));
            int size = b.getConnections().length;
            Set<String> D = new TreeSet<String>(requirementGroups.get(current));

            for (String S1 : D) {
                int s1 = Step5.getNumberFromIDS(ids.get(current), S1);

                for (String S2 : D) {
                    int s2 = Step5.getNumberFromIDS(ids.get(current), S2);
                    b.getConnections()[s1][s2] = 0;
                }
            }

            for (String S1 : D) {
                Set<String> H = new TreeSet<String>(new NaturalOrderComparator());
                for (int i = 0; i < size; i++) {
                    String e = ids.get(current)[i];
                    if (b.isConnected(e, S1)) {
                        H.add(e);
                    }
                }
                w.printText("H" + S1 + "=" + H + "\n");
                groupInformation.get(current).put(S1, H);
                
                if (H.size()==0) {
                    JOptionPane.showMessageDialog(w,
                        "Group H"+S1+" is empty for requirement "+(current+1)+
                            "!\nThat can lead to errors while selecting group keys."
                            + "\nPlease check your input data again.",
                        "WARNING",
                        JOptionPane.WARNING_MESSAGE);
                }
                
            }

            w.printText("\n");
        }        
                
    }

    @Override
    public Command getNext() {
        return new Step7(this);
    }

}
