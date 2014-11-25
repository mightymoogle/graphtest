/*
 * 
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import org.chaosdragon.graphtest.gui.GraphEditor;
import org.chaosdragon.graphtest.matrix.MatrixTools;
import org.chaosdragon.graphtest.gui.WizardForm;

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
    ArrayList<ArrayList<Set<String>>> groupLevels;
    ArrayList<Map<String, Set<String>>> groupInformation;
    
    ArrayList<Matrix> newMatrices;

    public Step8(Step7 old) {
        w = old.w;
        requirements = old.requirements;
        newRequirements = old.newRequirements;
        informationalElements = old.informationalElements;
        requirementGroups = old.requirementGroups;
        precedentSets = old.precedentSets;
        reachabilitySets = old.reachabilitySets;
        ids = old.ids;
        reachabilityMatrices = old.reachabilityMatrices;
        submatrices = old.submatrices;
        groupLevels = old.groupLevels;
        groupInformation = old.newGroupInformation; //WATCH OUT!
    }

    public boolean groupsConnected() {
        return false;
    }

    @Override
    public void execute() {
        newMatrices = new ArrayList<Matrix>();
        w.clearText();
        w.printText("Step 8\n");
        for (int current = 0; current < requirements.size(); current++) {
            //Formejam Matricu
            int size = requirementGroups.get(current).size();
            int[][] matr = new int[size][size];
            //WARNING
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
            w.printText("Matrix B" + (current + 1) + "g:\n");
            Matrix m = new Matrix(newIds, matr);
            Matrix b = new Matrix(newRequirements.get(current));
            w.printText(m.toString());
            w.printText("\n");
            Matrix m2 = new Matrix(m);

            //s=2 may be incorret! see slide 46
            for (int s = 2; s < size; s++) {
                int[][] contentMatrix
                        = MatrixTools.multiply(m.getConnections(),
                                m2.getConnections());
                m2.setConnections(contentMatrix);
                w.printText("λ" + (s) + ":\n");
                w.printText(new Matrix(m.getIds(),
                        contentMatrix).toString() + "\n");

                //Compare first one with content matrix
                ArrayList<int[]> found = MatrixTools.remainingOnes(
                        m.getConnections(), m2.getConnections());

                if (found.size() > 0) {
                    for (int[] item : found) {
                        //Get strings from values recieved
                        String s1 = m.getIds()[item[0]];
                        String s2 = m.getIds()[item[1]];
                         
                GraphEditor backGraph = new GraphEditor(w, false, b, true);                
                if (backGraph.showLinkQuestion(s1, s2)) {
                    b.setValue(s1, s2, 0);
                    w.printText("Removed link from " + s1 + " to " + s2 + "!\n\n");
                }    
                backGraph.dispose();
                        
                        
                    }
                }
            }
            newMatrices.add(b);
            ///PUT MATRIX HERE
            w.printText("Matrix B" + (current + 1) + "*" + "\n" + b + "\n");
            w.addToMatrixBox("B" + (current + 1) + "*", b);
        }
        //Reizinam un citi brinumi
        //Gala jaizdod Bk no pilna
        //////////???????????????????????????????????????????????????????????????
    }

    @Override
    public Command getNext() {
        return new Step9(this);
    }
}
