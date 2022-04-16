package lk.bsc212.pdsa.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Queens {

    static String[][] possiblePlaces = new String[8][8];
    //    static List<String> possiblePs = Arrays.asList(new String[4]);
    static List<String> possiblePs = new ArrayList<>();


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
        Arrays.stream(possiblePlaces).forEach(x -> Arrays.fill(x, "0"));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (q[i] == j) {
                    possiblePlaces[i][j] = "1";


                } else {
                    possiblePlaces[i][j] = "0";
                }
            }
        }
        List<String> add = Arrays.stream(possiblePlaces)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        possiblePs.add(add.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));

    }


    /***************************************************************************
     *  Try all permutations using backtracking
     ***************************************************************************/
    public static List<String> enumerate(int n) {
        int[] a = new int[n];
        return enumerate(a, 0);
    }


    public static List<String> enumerate(int[] q, int k) {
        int n = q.length;


        if (k == n) {
            printQueens(q);
        } else {
            for (int i = 0; i < n; i++) {
                q[k] = i;
                if (isConsistent(q, k))
                    enumerate(q, k + 1);
            }
        }
        if (possiblePs.size() == 92)
            return possiblePs;
        return null;

    }


}
