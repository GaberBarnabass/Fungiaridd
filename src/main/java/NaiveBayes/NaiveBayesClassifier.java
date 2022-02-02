package NaiveBayes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>Class representing a Naive Bayes classifier.</p>
 */
public class NaiveBayesClassifier extends Category {
    private final Set<Example> training_set;
    private Set<Category> categories = new HashSet<>();
    private Set<String> attribute_set = new TreeSet<>();

    /**
     * <p>Take in input the training set, divide alle the example in their category.</p>
     *
     * @param training_set    input training set
     * @param categories_name a name list for the example in the training set
     */
    public NaiveBayesClassifier(Set<Example> training_set, List<String> categories_name) {
        this.training_set = training_set;

        for (Example e : training_set) attribute_set.addAll(e.getAttributes());
        for (String s : categories_name) categories.add(new Category(s, this.training_set, attribute_set));
        training_set.clear();
    }

    /**
     * @param query example to classify
     * @return a string indicating the predicted class and the probability
     */
    public String handleQuery(Example query) {
        attribute_set.addAll(query.getAttributes());

        //calculation of probabilities in case of missing terms in category
        for (Category c : categories) c.calculateProbabilities(attribute_set);

        String best_match = "";
        double probability = 0;
        for (Category c : categories) {
            //prior category probability
            double tmp = c.getCategoryProbability();
            for (String s : query.getAttributes()) {
                //multiplying for the probability of s
                tmp = tmp * c.getAttributeProbability(s);
            }
            if (tmp > probability) {
                probability = tmp;
                best_match = c.getCategoryName();
            }
        }
        return "Classified as " + best_match + " with probability: " + probability + ".";
    }

    @Override
    public String toString() {
        return "NavieBayes.BayesClassifier\n{" +
                "training_set=\n" + training_set +
                ", categories\n=" + categories +
                ", attribute_set\n=" + attribute_set +
                '}';
    }
}
