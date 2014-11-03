/*
 * Remove duplicate elements (slides 43-45)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.gui.Matrix;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.JPanel;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step8 extends Command {

    WizardForm w;
    ArrayList<Matrix> requirements; //Bx       
    ArrayList<Matrix> newRequirements; //!!!! with deleted from step7
    ArrayList<Set<String>> informationalElements; //DxF
    ArrayList<Set<String>> requirementGroups;     //DxG       
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<String[]> ids;
    ArrayList<Matrix> reachabilityMatrices;
    ArrayList<Matrix> submatrices;
    //Current requirement -> Level-> Group
    ArrayList<ArrayList<Set<String>>> groupLevels;

    //Current requirement -> Group id from D-> Content
    ArrayList<Map<String, Set<String>>> groupInformation;

    public Step8(Step7 old) {
        w = old.w;
        requirements = old.requirements; ///!!!!!!!!!!
        newRequirements = old.newRequirements;
        informationalElements = old.informationalElements;
        requirementGroups = old.requirementGroups;
        precedentSets = old.precedentSets;
        reachabilitySets = old.reachabilitySets;
        ids = old.ids;
        reachabilityMatrices = old.reachabilityMatrices;
        submatrices = old.submatrices;
        groupLevels = old.groupLevels;
        groupInformation = old.groupInformation;
    }

    @Override
    public boolean execute() {

        ArrayList<Matrix> newMatrices = new ArrayList<Matrix>();
        w.clearText();
        w.printText("Step 8\n");

        for (int current = 0; current < requirements.size(); current++) {

            //Formejam Matricu
            int size = requirementGroups.get(current).size();
            int[][] matr = new int[size][size];
            Set<String> group = requirementGroups.get(current);

            int i = 0;
            int j = 0;

            //IDs for the new sub-matrix
            String[] newIds = new String[matr.length];

            for (String s1 : group) {

                //Setting of ids
                newIds[i] = s1;
                for (String s2 : group) {

                    //It is 1 if there is a connection in the big matrix
                    if (newRequirements.get(current).isConnected(s1, s2)) {
                        matr[i][j] = 1;
                    }

                    j++;
                }
                j = 0;
                i++;
            }

            w.printText("Submatrix A" + (current + 1) + "g:\n");
            Matrix m = new Matrix(newIds, matr);
            w.printText(m.toString());
            w.printText("\n");
            newMatrices.add(m);

        }

        //Reizinam un citi brinumi
        //Gala jaizdod Bk no pilna
        return false;

    }

    @Override
    public Command getNext() {

        return new FinalCommand();

    }

}
