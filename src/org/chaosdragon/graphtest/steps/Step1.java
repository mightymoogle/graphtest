/*
 * STARTUP AND FIND REACHABILITY MATRICES (Slide 26-30)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import org.chaosdragon.graphtest.matrix.MatrixTools;
import java.util.ArrayList;
import org.chaosdragon.graphtest.gui.WizardForm;

/**
 *
 * @author Mighty
 */
public class Step1 extends Command {

    ArrayList<Matrix> requirements;
    WizardForm w;
    //One for List each requirement
    ArrayList<Matrix> reachabilityMatrices = new ArrayList<>();

    public Step1(ArrayList<Matrix> requirements, WizardForm p) {
        this.w = p;
        this.requirements = requirements;
    }

    public Step1(Step1 old) {
        this(old.requirements, old.w);
    }

    //Does nothing
    @Override
    public void execute() {

        final boolean PRINTALL = false;
        int current = 0;
        w.clearText();

        for (int cc = 0; cc < requirements.size(); cc++) {
            if (PRINTALL) {
                w.printText("**** REQUIREMENT " + (cc + 1) + " ****\n");
            }
            Matrix m = new Matrix(requirements.get(current)); //Get a copy to avoid editing

            if (PRINTALL) {
                w.printText("BASE MATRIX " + "\n");
                w.printText(MatrixTools.printMatrix(m.getConnections(), m.getIds()));
            }

            String[] ids = m.getIds();
            int counter = 0;
            int[][] m2 = m.getConnections();

            //At first it is the base matrix, will add other later
            int[][] end = m2;

            //FIX 10 to "is equals to the last one or numbers just grow"
            while (counter < 10 && !MatrixTools.isMatrixZeroOnly(m2)) {

                m2 = MatrixTools.multiply(m2, m.getConnections());

                if (PRINTALL) {
                    w.printText("\n" + "Matrix step " + counter + "\n");
                    w.printText(MatrixTools.printMatrix(m2, ids));
                }

                //Add all 1
                end = MatrixTools.specialAdd(end, m2);
                counter++;

            }

            w.printText("\n" + "Reachability Matrix A" + (current + 1) + ":\n");
            w.printText(MatrixTools.printMatrix(end, ids) + "\n");

            Matrix ma = new Matrix(ids, end);
            w.addToMatrixBox("A" + (current + 1), ma);
            reachabilityMatrices.add(ma);
            current++;
        }
    }

    public Command getNext() {
        return new Step2(this);
    }

}
