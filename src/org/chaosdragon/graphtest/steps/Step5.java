/*
 * Groups levels??? NOT COMPLETE!!!!!!!! (Slide - 35)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step5 extends Command {
    WizardForm w;
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<Matrix> requirements;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<String[]> ids;
    ArrayList<Matrix> reachabilityMatrices;
    ArrayList<Matrix> submatrices;
    ArrayList<Set<String>> informationalElements;
    ArrayList<Set<String>> requirementGroups;
    
    //Current requirement -> Level-> Group
    ArrayList<ArrayList<Set<String>>> groupLevels;

    public Step5(Step4 old) {
        w = old.w;
        requirements = old.requirements;
        informationalElements = old.informationalElements;
        requirementGroups = old.requirementGroups;
        precedentSets = old.precedentSets;
        reachabilitySets = old.reachabilitySets;
        ids = old.ids;
        reachabilityMatrices = old.reachabilityMatrices;
        submatrices = old.submatrices;
    }

    public static int getNumberFromIDS(String[] ids, String description) {
        int num = 0;
        for (String s : ids) {
            if (s.equals(description)) {
                return num;
            }
            num++;
        }
        return -1;
    }

    @Override
    public void execute() {
        w.clearText();
        groupLevels = new ArrayList<>();

        //For each requirement
        for (int current = 0; current < requirementGroups.size(); current++) {

            groupLevels.add(new ArrayList<Set<String>>());
            //For each requirement group per requirement?

            Set<String> D = new TreeSet<String>(requirementGroups.get(current));
            Set<String> level1 = new TreeSet<String>(new NaturalOrderComparator());

            for (String SS : D) {

                //CurrentElement = 1 (2nd in Set, but SS = 4...)
                int currentElement = getNumberFromIDS(ids.get(current), SS);

                Set<String> F = new TreeSet<String>(reachabilitySets.get(current).get(currentElement));
                Set<String> C = new TreeSet<String>(precedentSets.get(current).get(currentElement));
                
                String thisElement;
                thisElement = SS;
                //Add the current element for some reason....?????
                F.add(thisElement);
                C.add(thisElement);
                Set<String> C2;
                //For m=1
                int level = 1;
                C2 = new TreeSet<String>(C);
                C2.retainAll(F);
                if (F.equals(C2)) {
                    level1.add(thisElement);
                    level++;
                }

            }
            w.printText("--------------\n");
            w.printText("P1 group = " + level1 + "\n");

            int level = 1;
            Set<String> level2 = new TreeSet<>(level1);

            //Add the first level
            groupLevels.get(current).add(level1);

            int sizeLimit = D.size();
            while (level1.size() < sizeLimit) {
                level++;
                //For the current LEVEL
                Set<String> levelX = new TreeSet<>();
                level1 = new TreeSet<>(level2);
                D.removeAll(level1);

                for (String SS : D) {

                    //CurrentElement = 1 (2nd in Set, but SS = 4...)
                    int currentElement = getNumberFromIDS(ids.get(current), SS);

                    Set<String> F = new TreeSet<String>(reachabilitySets.get(current).get(currentElement));
                    Set<String> C = new TreeSet<String>(precedentSets.get(current).get(currentElement));

                    F.add(SS);
                    C.add(SS);

                    Set<String> C2;
                    F.removeAll(level1);

                    C2 = new TreeSet<String>(C);

                    C2.retainAll(F);
                    if (F.equals(C2)) {
                        level2.add(SS); //The global one
                        levelX.add(SS); //Only current level
                    }
                }
                
                //Add current level to super array
                if (levelX.size() > 0) {
                    w.printText("P" + level + " group = " + levelX + "\n");
                    groupLevels.get(current).add(levelX);
                }
            }
        }
    }

    @Override
    public Command getNext() {
        return new Step6(this);
    }
}
