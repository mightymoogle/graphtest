/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.chaosdragon.graphtest.steps.Step5;

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
    
    public void setValue(int x, int y, int value) {        
        connections[x][y]=value;        
    }
    
    public void setValue(String x, String y, int value) {        
        int x1 = Step5.getNumberFromIDS(ids, x);
        int y1 = Step5.getNumberFromIDS(ids, y);
        setValue(x1,y1,value);        
    }
    
    public boolean isConnected(String one, String two) {
        
        for (int i=0; i<connections.length; i++) {
            
            for (int j=0; j<connections[i].length; j++) {
                
                if (ids[i].equals(one)&&ids[j].equals(two)&&connections[i][j]==1) {
                    
                    return true;                  
                    
                }                
            }            
        }      
        return false;        
    }

    public void sort() {
        String[] old = ids;
        Arrays.sort(ids);        
        

        for (int i = 0; i < old.length; i++) {
            for (int j = 0; i < old.length; j++) {
                if (old[i].equals(ids[j])) {
                    
                    //Write to new array or move columns and rows
                  //  System.out.println(ids[j]);
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
    
    @Override
    public String toString() {
        return print();
    }
    
    
    public int getColumnId(String name) {
        for (int i=0; i<ids.length; i++) {
            
            if (ids[i].equals(name)) return i;
            
        }
        
        return -1;
    }
    
    public void removeAttributeColumn(String name) {
        
        int[][] tem = new int[connections.length][connections[0].length-1];
        
        int skip = getColumnId(name);
        int step = 0;
        
        for (int i=0; i<connections.length; i++) {
            step=0;
            for (int j=0; j<connections[0].length; j++) {
            
                if (j==skip) { 
                    step++; 
                    continue;
                }
                
                tem[i][j-step]=connections[i][j];                
                
            }
        }
        
        connections = tem;
        
        ArrayList<String> idList= 
                       new ArrayList<>(Arrays.asList(getIds()));
                
        idList.remove(name);
        ids = idList.toArray(new String[0]);
        
        
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

    public static void main(String[] args) {
        
        String[] ids = {"2","6","19","20","3","5","8"};
        int[][] matr = {{0,0,0,1,1,0,0},{1,0,0,0,0,1,0},{0,1,0,1,0,0,1},{0,0,0,0,0,0,0}};
        
        Matrix m = new Matrix(ids,matr);
        System.out.println(m);
        
        m.removeAttributeColumn("8");
        System.out.println(m);
        

    }
    
}
