import NaiveBayes.Example;
import NaiveBayes.NaiveBayesClassifier;

import java.util.*;

/**
 * <p>Unit to test Naive Bayes Classifier</p>
 */
public class KFoldCrossValidation extends NaiveBayesClassifier {
    public static void main(String[] args) {
        String class_edible = "class:edible";
        String class_poisonous = "class:poisonous";

        //8124 examples for the whole training set
        Set<Example> training_set = Utils.editCsvAndGetData("res/mushroomsupdated.csv");

        //shuffling the example in the training set
        List<Example> list = new LinkedList<>(training_set);
        Collections.shuffle(list);
        training_set.clear();
        training_set.addAll(list);
        list.clear();

        //dividing the training set in k = 12 subsets with  677 examples for each
        //                          or k = 6  subsets with 1354 examples for each
        int k = 12;
        int size = 8124 / k;

        double accuracy_avg = 0;
        List<HashSet<Example>> subsets_list = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            HashSet<Example> set = new HashSet<>();
            int j = 0;
            for (Example e : training_set) {
                if (j < size) {
                    set.add(e);
                    j++;
                }
            }
            for (Example e : set) training_set.remove(e);
            subsets_list.add(set);
        }
        training_set.clear();
        System.out.println(subsets_list.size() + " subsets created!");


        //index + 1 of the subset to use for test
        int for_test = 1;
        double accuracy = 0;
        for (int i = 0; i < subsets_list.size(); i++) {
            System.out.println("\n\nTest set number _________________ " + for_test);
            Set<Example> test_set = subsets_list.get(for_test - 1);
            HashSet<Example> ts = new HashSet<>();

            for (HashSet<Example> subset : subsets_list) {
                ts.addAll(subset);
            }
            ts.removeAll(test_set);

            for_test++;

            NaiveBayesClassifier bc = new NaiveBayesClassifier(ts, Arrays.asList(class_edible, class_poisonous));

            int n = 1;
            int true_positive = 0,          //correctly predicted positive
                    false_positive = 0,     //wrongly positive prediction.
                    false_negative = 0,     //wrongly negative prediction.
                    true_negative = 0;      //correctly predicted negative

            for (Example q : test_set) {
                //System.out.println("\n" + n + ") " + q.getCategoryName() + "\n");
                String tmp = bc.handleQuery(q);

                if (tmp.contains(q.getCategoryName())) {
                    if (tmp.contains(class_edible)) {
                        true_positive++;
                    } else if (tmp.contains(class_poisonous)) {
                        true_negative++;
                    }
                    //System.out.println(tmp);
                } else {
                    if (tmp.contains(class_edible)) {
                        false_positive++;
                    } else if (tmp.contains(class_poisonous)) {
                        false_negative++;
                    }
                    //wrongly predicted example will be printed in red
                    //System.err.println(tmp);
                }
                n++;
            }

            //Accuracy = (TP + TN) / (TP + TN + FP + FN)
            accuracy = (double) (true_negative + true_positive) / (double) (true_negative + true_positive + false_negative + false_positive) * 100;
            accuracy_avg += accuracy;

            System.out.println("Correctly predicted Edible ______ " + true_positive +
                    "\nCorrectly predicted Poisonous ___ " + true_negative +
                    "\nWrong predicted Positive ________ " + false_positive +
                    "\nWrong predicted Negative ________ " + false_negative +
                    "\nAccuracy ________________________ " + String.format("%.2f", accuracy) + "%");
        }
        accuracy_avg = accuracy_avg / k;
        System.out.println("\n\n\nAverage accuracy: _______________ " + String.format("%.2f", accuracy_avg) + "%");
    }
}
