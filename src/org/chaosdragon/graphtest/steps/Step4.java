/*
 * GENEREATES SUBMATRIXES (SLIDE 34)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import java.util.ArrayList;
import java.util.Set;
import org.chaosdragon.graphtest.gui.WizardForm;

/**
 *
 * @author Mighty
 */
public class Step4 extends Command {
    WizardForm w;
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<String[]> ids;
    ArrayList<Matrix> reachabilityMatrices;
    ArrayList<Matrix> requirements;
    ArrayList<Set<String>> informationalElements;
    ArrayList<Set<String>> requirementGroups;

    ArrayList<Matrix> submatrices;

    public Step4(Step3 old) {
        w = old.w;
        requirements = old.requirements;
        informationalElements = old.informationalElements;
        requirementGroups = old.requirementGroups;
        precedentSets = old.precedentSets;
        reachabilitySets = old.reachabilitySets;
        ids = old.ids;
        reachabilityMatrices = old.reachabilityMatrices;
    }

    @Override
    public void execute() {
        w.clearText();
        submatrices = new ArrayList<>();

        for (int current = 0; current < requirementGroups.size(); current++) {
            Set<String> group = requirementGroups.get(current);
            int[][] matr = new int[group.size()][group.size()];
            int i = 0;
            int j = 0;
            //IDs for the new sub-matrix
            String[] newIds = new String[matr.length];

            for (String s1 : group) {
                //Setting of ids
                newIds[i] = s1;
                for (String s2 : group) {
                    //It is 1 if there is a connection in the big matrix OR if it is itself!
                    if (reachabilityMatrices.get(current).isConnected(s1, s2) || s1.equals(s2)) {
                        matr[i][j] = 1;
                    }
                    j++;
                }
                j = 0;
                i++;
            }

            w.printText("Submatrix A" + (current + 1) + "g:\n");
            Matrix m = new Matrix(newIds, matr);
            w.addToMatrixBox("A" + (current + 1) + "g", m);
            w.printText(m.toString());
            w.printText("\n");
            submatrices.add(m);
        }
    }

    @Override
    public Command getNext() {
        return new Step5(this);
    }
}
