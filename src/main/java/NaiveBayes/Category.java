package NaiveBayes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Class to represent a category of the Naive Bayes Classifier, here prior probabilities are calculated and recalculated if needed</p>
 */
public class Category {
    private final String category_name;                             //category name
    private final Set<Example> example_set;                               //example for the category
    private final double category_probability;                            //probability for this category (documents.size()/Tr.length)
    private int number_of_terms;                                    //number of terms = attribute:value couples in the category
    private HashMap<String, Double> attribute_probabilities;        //probability of each term in the general vocabulary(TR), in this category

    /**
     * <p>Takes in input the training set, for each document d, if d category is equal to the category name than d will be added.
     * This calculates the prior probability for the category and for each attribute:value pair in the attribute set.</p>
     *
     * @param str           name for this category
     * @param training_set  input training set
     * @param attribute_set all the unique attribute:value pair
     */
    public Category(String str, Set<Example> training_set, Set<String> attribute_set) {
        assert training_set != null : "Training set not initialized.";
        assert attribute_set != null : "Attribute set is not initialized";
        category_name = str;

        //populating example_set with examples in input training set
        this.example_set = new HashSet<>();
        this.number_of_terms = 0;
        for (Example e : training_set) {
            if (e.getCategoryName().equals(this.getCategoryName())) {
                example_set.add(e);
                //calculating the number of couples attribute:value in this category
                this.number_of_terms += e.getAttributes().size();
            }
        }

        //calculating probability for this category
        category_probability = (double) example_set.size() / (double) training_set.size();

        //calculating probabilities for each attribute in attribute set
        attribute_probabilities = new HashMap<>();

        //first calculation of probabilities
        calculateProbabilities(attribute_set);

    }

    /**
     * @param attribute attribute:value pair
     * @return the number of occurrences for the instance attribute
     */
    private int countOccurrences(String attribute) {
        int counter = 0;
        for (Example d : example_set) {
            for (String s : d.getAttributes()) if (s.equals(attribute)) counter++;
        }
        return counter;
    }

    /**
     * @return the prior probability for this category
     */
    public Double getCategoryProbability() {
        return category_probability;
    }

    /**
     * @param s attribute:value pair to get prior probability
     * @return prior probability for s in this category
     */
    public double getAttributeProbability(String s) {
        return attribute_probabilities.get(s);
    }

    /**
     * @return the name of the category
     */
    public String getCategoryName() {
        return category_name;
    }

    /**
     * <p>Calculates the prior probability for each pair attribute:value in the attribute set.</p>
     *
     * @param attribute_set all the unique attribute:value pair
     */
    protected void calculateProbabilities(Set<String> attribute_set) {
        for (String s : attribute_set) {
            //calculating prior probability for s in this category using Laplace smoothing
            double prob_s_c = (double) (countOccurrences(s) + 1) / (double) (number_of_terms + attribute_set.size());
            if (attribute_probabilities.containsKey(s) && attribute_probabilities.get(s) != prob_s_c) {
                attribute_probabilities.replace(s, prob_s_c);
            } else {
                attribute_probabilities.put(s, prob_s_c);
            }
        }
        /*
        !!! The sum of probabilities in each category must be 1, however there is a precision error summing double values.
                1.0000000000000007 is obtained summing edible probabilities
                0.9999999999999997 is obtained summing poisonous probabilities*/
    }

    @Override
    public String toString() {
        return "NavieBayes.Category{" +
                "category_name='" + category_name + '\'' +
                ", example_set=" + example_set +
                ", category_probability=" + category_probability +
                ", number_of_terms=" + number_of_terms +
                ", attribute_probabilities=" + attribute_probabilities +
                '}';
    }
}


