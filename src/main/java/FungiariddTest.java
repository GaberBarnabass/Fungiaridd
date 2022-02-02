import NaiveBayes.Example;
import NaiveBayes.NaiveBayesClassifier;

import java.util.Arrays;
import java.util.Set;

/**
 * <p>Simple and rapid test for the Naive Bayes Classifier.</p>
 */
public class FungiariddTest extends NaiveBayesClassifier {

    public static void main(String[] args) {
        Set<Example> training_set;
        NaiveBayesClassifier bc;

        String class_edible = "class:edible";
        String class_poisonous = "class:poisonous";

        training_set = Utils.editCsvAndGetData("res/mushroomsupdated.csv");

        //edible_test_set = Utils.getTestData(training_set, 250, class_edible);
        //poisonous_test_set = Utils.getTestData(training_set, 250, class_poisonous);

        //bc = new NaiveBayesClassifier(training_set, Arrays.asList(class_edible, class_poisonous));

        //proof taking n positive examples and m negative examples
        /*for (Example q : edible_test_set) {
            String tmp = bc.handleQuery(q);
            if (tmp.contains("Poisonous")) {
                System.err.println(tmp);
            } else {
                System.out.println(tmp);
            }
        }

        for (Example q : poisonous_test_set) {
            String tmp = bc.handleQuery(q);
            if (tmp.contains("Edible")) {
            }
            else System.out.println(tmp);
        }*/

        bc = new NaiveBayesClassifier(training_set, Arrays.asList(class_edible, class_poisonous));

        //edible
        Example cardoncello = new Example("", Arrays.asList("cap-shape:flat", "cap-surface:smooth", "cap-color:brown", "odor:bread", "gill-spacing:crowded", "gill-size:broad", "gill-color:brown", "stalk-shape:enlarging", "stalk-root:club", "ring-number:none", "ring-type:none", "habitat:grasses"));
        //edible
        Example prataiolo = new Example("", Arrays.asList("cap-shape:convex", "cap-surface:fibrous", "veil-color:white", "gill-attachment:free", "gill-attachment:free", "gill-color:pink", "stalk-shape:enlarging", "stalk-color-below-ring:white", "stalk-color-above-ring:white", "stalk-surface-below-ring:fibrous", "stalk-surface-above-ring:fibrous", "cap-color:white", "habitat:grasses", "population:numerous"));
        //edible
        Example lardaro = new Example("", Arrays.asList("cap-surface:fibrous", "cap-surface:convex", "veil-color:orange", "cap-color:gray", "gill-color:buff", "stalk-shape:enlarging", "odor:almond", "gill-attachment:descending", "gill-spacing:close", "population:numerous"));
        //poisonous
        Example falsa_famigliola = new Example("", Arrays.asList("cap-shape:conical", "cap-surface:smooth", "cap-color:yellow", "odor:none", "gill-attachment:attached", "gill-spacing:crowded", "gill-size:narrow", "gill-color:buff", "stalk-shape:tapering", "ring-number:one", "habitat:paths"));


        System.out.println("Cardoncello     : " + bc.handleQuery(cardoncello));
        System.out.println("Prataiolo       : " + bc.handleQuery(prataiolo));
        System.out.println("Lardaro         : " + bc.handleQuery(lardaro));
        System.out.println("Falsa Famigliola: " + bc.handleQuery(falsa_famigliola));


    }


}
