/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.gui;

import java.util.Arrays;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Mighty
 */
public class Matrix {

    private String[] ids;
    private int[][] connections;
    private String name = "S";
    //Coordinates???

    public static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    //Copy constructor
    public Matrix(Matrix m) {
        //Must not forget this...
        this.connections = cloneArray(m.connections);
        this.ids = Arrays.copyOf(m.ids, m.ids.length);
        this.name = new String(m.getName());
    }

    public Matrix() {
        ids = new String[0];
        connections = new int[0][0];
    }

    public void sort() {
        String[] old = ids;
        Arrays.sort(ids);        
        

        for (int i = 0; i < old.length; i++) {
            for (int j = 0; i < old.length; j++) {
                if (old[i].equals(ids[j])) {
                    
                    //Write to new array or move columns and rows
                    System.out.println(ids[j]);
                }
            }
        }

    }

    public Matrix(String[] ids, int[][] connections) {
        this.ids = ids;
        this.connections = connections;
    }

    public Matrix(String[] ids, int[][] connections, String name) {
        this(ids, connections);
        this.name = name;
    }

    /**
     * @return the ids
     */
    public String[] getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(String[] ids) {
        this.ids = ids;
    }

    /**
     * @return the connections
     */
    public int[][] getConnections() {
        return connections;
    }

    /**
     * @param connections the connections to set
     */
    public void setConnections(int[][] connections) {
        this.connections = connections;
    }

    public String print() {
        return MatrixTools.printMatrix(connections, ids);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
