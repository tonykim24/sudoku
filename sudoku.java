import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

class Sudoku {
    
    public static void main(String[] args){
        Sudoku sudoku = new Sudoku();

        System.out.println("input pls");

        int[][] arr = new int[9][9];

        Scanner rows = new Scanner(System.in);

        int question = 0;
        
        for (int i = 8; i>=0; i--) {
            question = rows.nextInt();
            for (int j = 8; j>=0; j--) {

                //THIS IS THE PROBLEM OVER HERE
                arr[8-i][j] = question%10;
                
                question /= 10;
            }

        }
        sudoku.solve(arr);
    }




    public void solve(int[][] arr) {
        int[][] answer = new int[9][9];

        ArrayList<Integer> zeroList = new ArrayList<Integer>();
        ArrayList<Set<Integer>> PVList = new ArrayList<Set<Integer>>();
        ArrayList<List<Integer>> tempPV = new ArrayList<List<Integer>>();


        //Gives me the zero list and PVlist.
        for (int i = 0; i<9; i++) {
            for (int j = 0; j<9; j++) {
                if (arr[i][j] != 0) {
                    answer[i][j] = arr[i][j];
                }
                else if (arr[i][j] == 0) {
                    zeroList.add(i);
                    zeroList.add(j);
                    //pvListGen(arr, i, j);
                    PVList.add(pvListGen(arr, i, j));

                    List<Integer> list = new ArrayList<>(pvListGen(arr, i, j));
                    tempPV.add(list);

                }
            }
        }


        //adds value to zeroPos. create temp PV'.
        // if a value from PV' fails, remove from PV' try the next value. if no PV is left, go back a step.
        // keep track of PV' each time you progress.
        // reset PV' of B each time you back to A.
        
        int i = 0;
        while (i< zeroList.size()) {
            // output(answer);
            
            // System.out.println(zeroList.get(i) + ":" + zeroList.get(i+1));
            // System.out.println(tempPV.get(i/2));
            
            if (tempPV.get(i/2).isEmpty() == true && i != 0) {
                System.out.println(tempPV.get(i/2).size());
                answer[zeroList.get(i)][zeroList.get(i+1)] = 0;
                //needs work
                List<Integer> list = new ArrayList<>(PVList.get(i/2));

                tempPV.set(i/2, list);
                i = i - 2;
            }    

            else if (tempPV.get(i/2).isEmpty() == true && i == 0) {
                System.out.println("this is unsolvable");
                System.out.println(tempPV.get(i/2).size());
                break;
            }

            else {
                //System.out.println(pvListGen(answer, zeroList.get(i), zeroList.get(i+1)));

                //remove the first element from PV after using it
                //the code will use this to see the options it has.
                answer[zeroList.get(i)][zeroList.get(i+1)] = tempPV.get(i/2).get(0);
                tempPV.get(i/2).remove(0);


                //the next int cant have the same int as that one.
                if (i < zeroList.size()-3) {
                    List<Integer> list = new ArrayList<>(pvListGen(answer, zeroList.get(i+2), zeroList.get(i+3)));
                    tempPV.set(i/2 + 1, list);
                }


                // System.out.println(zeroList.get(i) + "," + zeroList.get(i+1));
                // System.out.println(tempPV.get(i/2).get(0));
                i = i+2;
            }

        } 

        output(answer);


    }

//Gives the possible values per zero value
    public Set<Integer> pvListGen (int[][] arr, int row, int col) {

    
        Integer list[] = {0,1,2,3,4,5,6,7,8,9};  
        Set<Integer> set = new HashSet<>(Arrays.asList(list));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(list));
        Set<Integer> set3 = new HashSet<>(Arrays.asList(list));

        for (int i = 0; i<9; i++) {
            if (set.contains(arr[row][i])) {
                set.remove(arr[row][i]);
            }
            if (set2.contains(arr[i][col])) {
                set2.remove(arr[i][col]);
            }
        }

        int rowdiv = (row/3) * 3;
        int coldiv = (col/3) * 3;

        for (int i = rowdiv; i < rowdiv+3; i++) {
            for (int j = coldiv; j < coldiv+3; j++) {
                if (set3.contains(arr[i][j])) {
                    set3.remove(arr[i][j]);
                }
            }
        }
        



        System.out.println(set3);
        System.out.println(set);
        // use retainAll() method to
        // find common elements
        set.retainAll(set2);
        set.retainAll(set3);
        // return PVList;
        //System.out.println(set);
        return set;
    }



    public void output(int[][] arr) {

        for (int[] miniArr:arr) {
            for (int element:miniArr) {
                if (element == 0){
                    System.out.print("");
                }
                else {
                    System.out.print("," + element);
                }
            }
            System.out.println("");
        }
        
    }

    
}