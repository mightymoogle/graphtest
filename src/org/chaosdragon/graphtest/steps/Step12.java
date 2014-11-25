/*
 * Remove unneeded links (75-76)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import org.chaosdragon.graphtest.gui.GraphEditor;
import org.chaosdragon.graphtest.matrix.MatrixTools;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step12 extends Command {
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
    ArrayList<Matrix> newMatrices;
    ArrayList<Map<String, Set<String>>> groupInformation;
    ArrayList<Step9.ElementTable> elementTableList; //Step9 tables
    ArrayList<ArrayList<String>> keys;    //W1 
    ArrayList<ArrayList<String>> notKeys; //W2
    ArrayList<ArrayList<String>> fakeKeys;    //for doubling
    ArrayList<String> elements; //W2 subset  
    ArrayList<Matrix> bStarMatrix; //New matrices
    //The global ones
    ArrayList<String> W1;
    ArrayList<String> W2;
    ArrayList<String> W0;
    //Global matrix
    Matrix global;
    boolean PRINTALL = false;
    Map<String,String> repeatingKeys;

    public Step12(Step11 old) {
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
        newMatrices = old.newMatrices;
        elementTableList = old.elementTableList;
        //skippable = false;
        keys = old.keys;
        notKeys = old.notKeys;
        fakeKeys = old.fakeKeys;
        bStarMatrix = old.bStarMatrix;
        W1 = old.W1;
        W0 = old.W0;
        W2 = old.W2;
        
        //Will be changed!!!
        global = new Matrix(old.global);
    }
          
    public Matrix askRepeatingKeys(GraphEditor backGraph, Matrix newGlobal,
            Map<String,String> repeatingKeys) {
           
        newGlobal = new Matrix(newGlobal);        
        for (Map.Entry<String,String> e: repeatingKeys.entrySet()) {
            backGraph.updateGraph(newGlobal);
                if (backGraph.showLinkQuestion(e.getKey(), e.getValue())) {             
                    newGlobal.setValue(e.getKey(), e.getValue(), 0);
                    w.printText("Removed link from " + e.getKey()
                            + " to " + e.getValue() + "!\n");
                }
                backGraph.setVisible(false);
        }
        return newGlobal;
        
    }

    //Copy paste from step 8
    public Matrix removeExtraConnections() {
        Matrix subBase = new Matrix(global.getLeftMatrix());
        Matrix newGlobal = new Matrix(global);
        
        repeatingKeys = new HashMap<>();
        
        if (PRINTALL) 
            w.printText("Base SubMatrix:\n" + subBase);        
        Matrix multi = new Matrix(subBase);

        //s=2 may be incorret! see slide 46
        for (int s = 2; s < subBase.getConnections().length; s++) {
            int[][] contentMatrix
                    = MatrixTools.multiply(multi.getConnections(),
                            subBase.getConnections());
            multi.setConnections(contentMatrix);
            if (PRINTALL) {
                w.printText("\nB0-λ" + (s) + ":\n");
                w.printText(new Matrix(multi.getIds(),
                        contentMatrix).toString() + "\n");
            }

            //Compare first one with content matrix
            ArrayList<int[]> found = MatrixTools.remainingOnes(
                    multi.getConnections(), subBase.getConnections());

            if (found.size() > 0) {
                for (int[] item : found) {
                    //Get strings from values recieved
                    String s1 = multi.getIds()[item[0]];
                    String s2 = multi.getIds()[item[1]];                   
                    repeatingKeys.put(s1, s2);
                }
            }

            if (MatrixTools.isMatrixZeroOnly(multi.getConnections())) {
                return newGlobal;
            }
        }
        return newGlobal;
    }

    public Matrix getSubMatrix(Matrix in) {
        Matrix m = new Matrix(in); //Get a copy to avoid editing      
        String[] ids = m.getIds();
        int counter = 0;
        int[][] m2 = m.getConnections();
        //At first it is the base matrix, will add other later
        int[][] end = m2;
        //FIX 10 to "is equals to the last one or numbers just grow"
        while (counter < 10 && !MatrixTools.isMatrixZeroOnly(m2)) {           
            m2 = MatrixTools.multiply(m2, m.getConnections());
            //Add all 1
            end = MatrixTools.specialAdd(end, m2);
            counter++;
        }
        for (int i = 0; i < end.length; i++) {
            for (int j = 0; j < end[0].length; j++) {
                if (i == j) {
                    end[i][j] = 1;
                }
            }
        }
        if (PRINTALL) {
            w.printText("\n" + "Reachability Matrix A0-1\n");
            w.printText(MatrixTools.printMatrix(end, ids) + "\n");
        }
        Matrix ma = new Matrix(ids, end);
        return ma;
    }

    public ArrayList<Set<String>> getReachabilitySets(Matrix m) {
        String[] ids = m.getIds();
        int[][] connections = m.getConnections();
        ArrayList<Set<String>> result = new ArrayList<>();
        //By rows
        for (int i = 0; i < connections.length; i++) {
            Set<String> x = new TreeSet<>(new NaturalOrderComparator());
            result.add(x);
            for (int j = 0; j < connections.length; j++) {
                if (connections[i][j] == 1) {
                    x.add(ids[j]);
                }
            }
        }
        return result;
    }

    //Copy paste from above
    public ArrayList<Set<String>> getAncestorSets(Matrix m) {
        String[] ids = m.getIds();
        int[][] connections = m.getConnections();
        ArrayList<Set<String>> result = new ArrayList<>();
        //By columns
        for (int i = 0; i < connections.length; i++) {
            Set<String> x = new TreeSet<>(new NaturalOrderComparator());
            result.add(x);
            for (int j = 0; j < connections.length; j++) {
                if (connections[j][i] == 1) {//CHANGED!
                    x.add(ids[j]);
                }
            }
        }
        return result;
    }

    public Set<String> intersect(Set<String> a, Set<String> b) {
        Set<String> s = new TreeSet<>(new NaturalOrderComparator());
        s.addAll(a);
        s.retainAll(b);
        return s;
    }

    public ArrayList<Set<String>> removeFromLists(ArrayList<Set<String>> R, Set<String> Remove) {
        ArrayList<Set<String>> temp = new ArrayList<>();
        for (int i = 0; i < R.size(); i++) {
            Set<String> x = new TreeSet<>(R.get(i));
            temp.add(x);
            x.removeAll(Remove);
        }
        return temp;
    }

    public ArrayList<Set<String>> getSortedKeys(
            ArrayList<Set<String>> R,
            ArrayList<Set<String>> S,
            Matrix m) {

        ArrayList<Set<String>> result = new ArrayList<>();
        String[] keyList = m.getIds();
        Set<String> level0 = new TreeSet<>(new NaturalOrderComparator());
        result.add(level0);

        for (int keyNum = 0; keyNum < keyList.length; keyNum++) {

            Set<String> RR = R.get(keyNum);
            Set<String> SS = S.get(keyNum);

            //Level 0 (top level)
            Set<String> l0 = intersect(RR, SS);
            if (l0.equals(RR)) {
                level0.add(keyList[keyNum]);
            }
        }
        //For each level
        int level = 1;
        Set<String> tempList = new TreeSet<>(Arrays.asList(keyList));
        tempList.removeAll(level0);
        R = removeFromLists(R, level0);
        S = removeFromLists(S, level0);

        while (level < R.size()) {
            Set<String> levelN = new TreeSet<>(new NaturalOrderComparator());

            for (int keyNum = 0; keyNum < keyList.length; keyNum++) {

                Set<String> RR = R.get(keyNum);
                Set<String> SS = S.get(keyNum);

                //Level 0 (top level)
                Set<String> l0 = intersect(RR, SS);
                if (l0.equals(RR) && !l0.isEmpty()) {
                    levelN.add(keyList[keyNum]);
                }
            }

            level++;
            tempList.removeAll(levelN);
            R = removeFromLists(R, levelN);
            S = removeFromLists(S, levelN);
            if (!levelN.isEmpty()) {
                result.add(levelN);
            }
        }

        return result;
    }

    public Matrix sortMatrixByKeys(Matrix subMatrix, ArrayList<Set<String>> sortedKeys) {

        String ids[] = new String[subMatrix.getIds().length];
        int i = 0;
        for (Set<String> set : sortedKeys) {

            for (String key : set) {
                ids[i] = key;
                i++;
            }
        }

        int[][] conn = subMatrix.getConnections();
        int len = conn.length;
        int len2 = conn[0].length;
        int[][] data = new int[len][len2];

        for (i = 0; i < len; i++) {
            for (int j = 0; j < len2; j++) {

                if (subMatrix.isConnected(ids[i], ids[j])) {

                    data[i][j] = 1;
                }

            }
        }

        return new Matrix(ids, data);
    }

    private class findBossKey implements Comparator<String> {
        ArrayList<Set<String>> sortedKeys;
        
        public findBossKey(ArrayList<Set<String>> sortedKeys) {
            this.sortedKeys = sortedKeys;
        }

        @Override
        public int compare(String o1, String o2) {
            for (Set<String> S : sortedKeys) {
                for (String SS : S) {
                    if (SS.equals(o1)) {
                        return -1;
                    }
                    if (SS.equals(o2)) {
                        return 1;
                    }
                }
            }
            return 0;
        }
    }

    public Matrix fixDoublingElements(Matrix m, ArrayList<String> W2,
            ArrayList<Set<String>> sortedKeys) {
        Matrix p = new Matrix(m);
        String ids[] = p.getIds();
        int[][] conn = p.getConnections();
        int startPosition = ids.length - W2.size(); //CHECK!
        int[] count = new int[conn[0].length - startPosition];

        for (int i = 0; i < conn.length; i++) {
            for (int j = startPosition; j < conn[0].length; j++) {
                if (conn[i][j] == 1) {
                    count[j - startPosition]++;
                }
            }
        }

        Set<String> fixMe = new TreeSet<>(new NaturalOrderComparator());
        for (int i = 0; i < count.length; i++) {
            if (count[i] >= 2) {
                fixMe.add(W2.get(i));
            }
        }

        ArrayList<String[]> toAdd = new ArrayList<>();
        ArrayList<String[]> toRemove = new ArrayList<>();

        for (String element : fixMe) {
            //Number in the array
            int num = Step10.numberInArrayList(W2, element) + startPosition;
            ArrayList<String> candidates = new ArrayList<>();
            //For keys - find candidates
            for (int i = 0; i < conn.length; i++) {
                if (conn[i][num] == 1) {
                    candidates.add(ids[i]);
                }
            }
            candidates.sort(new findBossKey(sortedKeys));
            //Connect fixMe ot the first candidate
            p.setValue(candidates.get(0), element, 1);
            //Remove links to all other candidates
            for (int i = 1; i < candidates.size(); i++) {
                p.setValue(candidates.get(i), element, 0);
            }
        }
        return p;
    }

    public void doStep() {
        GraphEditor backGraph = new GraphEditor(w, false, null,true);  
        global = removeExtraConnections(); //Remove extra links - needs fixing when cycles!
        global = askRepeatingKeys(backGraph, global, repeatingKeys);
        //Remove doubling elements?
        Matrix subMatrix = getSubMatrix(global.getLeftMatrix()); //A01
        ArrayList<Set<String>> reachabilitySetsR = getReachabilitySets(subMatrix); //R
        ArrayList<Set<String>> ancestorSetsS = getAncestorSets(subMatrix); //S
        
        ArrayList<Set<String>> sortedKeys
                = getSortedKeys(reachabilitySetsR, ancestorSetsS, subMatrix);

        Matrix resortedSubMatrix = sortMatrixByKeys(subMatrix, sortedKeys);
        w.printText("\nResorted reachability matrix A0-1:\n" + resortedSubMatrix + "\n");
        global = fixDoublingElements(global, W2, sortedKeys);
        w.printText("Global matrix B0 (final):\n" + global + "\n");
        w.addToMatrixBox("B0 - final", global);
    }

    @Override
    public void execute() {
        w.clearText();
        doStep();
    }

    @Override
    public Command getNext() {
        return new FinalCommand();
    }
}
