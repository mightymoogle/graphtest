/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.gui;

/**
 *
 * @author Mighty
 */
public class Matrix {
    
    private String[] ids;
    private int[][] connections;
    //Coordinates???

    
    public Matrix() {
        
    }
    
    public Matrix(String[] ids, int[][] connections) {
        this.ids = ids;
        this.connections = connections;
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
        
    
    public void print() {
                //CUT HERE
                
        System.out.println("Base matrix");             
        MatrixTools.printMatrix(connections, ids);
        
      
      int counter = 0;      
      int[][]m2 = connections;
      
      //At first it is the base matrix, will add other later
      int[][] end = connections;
      
      //FIX 10 to "is equals to the last one or numbers just grow"
      while (counter<10 && !MatrixTools.isMatrixZeroOnly(m2)) {
         System.out.println("Matrix "+counter);
         m2=MatrixTools.multiply(m2, connections);              
         
         MatrixTools.printMatrix(m2, ids);     
         
         //Add all 1
         end = MatrixTools.specialAdd(end, m2);
         counter++;       
                  
      }
      
        System.out.println("FINAL MATRIX");
        MatrixTools.printMatrix(end, ids);         
    }
    
    
    
}
