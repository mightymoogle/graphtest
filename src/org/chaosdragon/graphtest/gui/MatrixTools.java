/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.gui;

/**
 *
 * @author Mighty
 */
public class MatrixTools {
    public static int[][] multiply(int[][] A, int[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        int[][] C = new int[aRows][bColumns];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                C[i][j] = 0;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
    
    public static boolean isMatrixZeroOnly(int[][] matrix) {
        
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix.length; j++) {
                
                if (matrix[i][j] == 1) return false;
                
            }
        }        
        
        return true;
        
    }
    
    
    //Check if equals (2=1=3=4=5=6 != 0)
    public static boolean matrixEquals(int[][]a,int[][]b) {
        
        //Implement me
        
        return false;
    }
    
    
    //Adss all 1 and above as 1
    public static int[][] specialAdd(int[][]source,int[][]addition) {
        for (int i=0; i<source.length; i++)  {
            for (int j=0; j<source.length; j++)  {
            
                if (addition[i][j]!=0) source[i][j]=1;
                
            }    
        }
        return source;
    }
    
    public static String printMatrix(int[][] matrix, String[] resultNames) {
        
        String r = "   ";
        
        int len = matrix.length;        
       
        
        for (int i=0; i<len;i++) {
            r=r+String.format("%3s",resultNames[i]);
        }
        r=r+("")+"\n";
        r=r+("------------------------------")+"\n";
        
        for (int i=0; i<len; i++) {
             r=r+String.format("%4s",resultNames[i]+"|");

            for (int j=0; j<len; j++) {
                r=r+String.format("%3d",matrix[i][j]);                
            }
            
            r=r+""+"\n";
        }
       
        return r;
        
    }
    
}
