package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /*
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */

        //n/50 omage n/2.5

        //No bucket has fewer than N / 50 oomages.
        //No bucket has more than N / 2.5 oomages. , where N is the number of oomages.
        //In other words, the number of oomages per bucket has to be within the range (N / 50, N / 2.5).
        //bucketNum = (o.hashCode() & 0x7FFFFFFF) % M

//        for (Oomage o: oomages) {
//            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
//            if (bucketNum > o.size()/50 && bucketNum < o.size()/2.5)
//
//
//
//        }


        return false;
    }
}
