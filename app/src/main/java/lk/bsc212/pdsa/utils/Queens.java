package lk.bsc212.pdsa.utils;

import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lk.bsc212.pdsa.MainActivity;
import lk.bsc212.pdsa.model.QueenPlace;

public class Queens {

    static String[][] possiblePlaces = new String[8][8];


    /***************************************************************************
     * Return true if queen placement q[n] does not conflict with
     * other queens q[0] through q[n-1]
     ***************************************************************************/
    public static boolean isConsistent(int[] q, int n) {
        for (int i = 0; i < n; i++) {
            if (q[i] == q[n]) return false;   // same column
            if ((q[i] - q[n]) == (n - i)) return false;   // same major diagonal
            if ((q[n] - q[i]) == (n - i)) return false;   // same minor diagonal
        }
        return true;
    }

    /***************************************************************************
     * Prints n-by-n placement of queens from permutation q in ASCII.
     ***************************************************************************/
    public static void printQueens(int[] q) {
        int n = q.length;
        Arrays.stream(possiblePlaces).forEach(x -> Arrays.fill(x, null));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (q[i] == j) {
                    possiblePlaces[i][j] = "1";
                    System.out.print("Q " + possiblePlaces[i][j] + "  ");


                } else {
                    possiblePlaces[i][j] = "0";
                    System.out.print("* " + possiblePlaces[i][j] + "  ");
                }
            }
            System.out.println();
        }
        System.out.println();

        List<String> collection = Arrays.stream(possiblePlaces)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

//        AsyncTask.execute(() -> MainActivity.placeDao.insertAll(new QueenPlace(collection.stream().map(Object::toString)
//                .collect(Collectors.joining(", ")))));
        MainActivity.placeDao.insertAll(new QueenPlace(collection.stream().map(Object::toString)
                .collect(Collectors.joining(", "))));


    }


    /***************************************************************************
     *  Try all permutations using backtracking
     ***************************************************************************/
    public static void enumerate(int n) {
        int[] a = new int[n];
        enumerate(a, 0);
    }

    public static void enumerate(int[] q, int k) {
        int n = q.length;
        if (k == n) printQueens(q);
        else {
            for (int i = 0; i < n; i++) {
                q[k] = i;
                if (isConsistent(q, k)) enumerate(q, k + 1);
            }
        }
    }


}
