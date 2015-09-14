package com.example.samson.smpr_lab_1.utils;

import java.util.ArrayList;

/**
 * Created by samson on 10.09.15.
 */
public class Property {

    public static boolean reflexive(int[][] matrix, int n){
        for(int i = 0; i < n; ++i){
            if(matrix[i][i] == 0)
                return false;
        }
        return true;
    }

    public static boolean irreflexive(int[][] matrix, int n){
        for(int i = 0; i < n; ++i){
            if(matrix[i][i] == 1)
                return false;
        }
        return true;
    }

    public static boolean symmetry(int[][] matrix, int n){
        for(int row = 0; row < n; ++row){
            for(int col = row; col <  n; ++col){
                if(matrix[row][col] != matrix[col][row])
                    return false;
            }
        }
        return true;
    }

    public static boolean asymmetry(int[][] matrix, int n){
        for(int row = 0; row < n; ++row){
            for(int col = row; col <  n; ++col){
                if(matrix[row][col] == 1 && col == row
                        || matrix[row][col] == matrix[col][row] && col != row)
                    return false;
            }
        }
        return true;
    }

    public static boolean antisymmetry(int[][] matrix, int n){
        for(int row = 0; row < n; ++row){
            for(int col = row; col <  n; ++col){
                if(matrix[row][col] == 0 && col == row
                        || matrix[row][col] == matrix[col][row] && col != row)
                    return false;
            }
        }
        return true;
    }

    public static boolean transitive(int[][] matrix, int n){
        for(int row = 0; row < n; ++row){
            for(int col = 0; col < n; ++col){
                if(matrix[row][col] == 1){
                    for(int k = 0; k < n; ++k){
                        if(matrix[col][k] == 1 && matrix[row][k] == 0)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean total(int[][] matrix, int n){
        for(int row = 0; row < n - 1; ++row){
            for(int col = row + 1; col <  n; ++col){
                if(matrix[row][col] == 0 && matrix[col][row] == 0)
                    return false;
            }
        }
        return true;
    }

    public static ArrayList<Integer> sendRmax(int[][] matrix, int n){
        ArrayList<Integer> result = new ArrayList<>();
        int s;
        for(int row = 0; row < n; ++row){
            s = 0;
            for(int col = 0; col < n; ++col){
                s += matrix[row][col];
            }
            if(s == n)
                result.add(row + 1);
        }
        return result;
    }

    public static ArrayList<Integer> sendRmin(int[][] matrix, int n){
        ArrayList<Integer> result = new ArrayList<>();
        int s;
        for(int col = 0; col < n; ++col){
            s = 0;
            for(int row = 0; row < n; ++row){
                s += matrix[row][col];
            }
            if(s == n)
                result.add(col + 1);
        }
        return result;
    }
}
