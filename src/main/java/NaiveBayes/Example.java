package NaiveBayes;

import java.util.List;

/**
 * <p>Class representing an example in the training set or a query. It contains a category name (empty for a query)
 * and a list of attribute:value pairs.</p>
 */
public class Example {
    private String category;
    private final List<String> attributes;

    /**
     * <p>Creates a new example setting a category name and a list of pair attribute:value.</p>
     *
     * @param category   the category name for this example
     * @param attributes all the attribute:value pairs in this example
     */
    public Example(String category, List<String> attributes) {
        this.category = category;
        this.attributes = attributes;
    }

    /**
     * <p>Sets the category name of this example.</p>
     *
     * @param category category name of this example
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the category name of this example
     */
    public String getCategoryName() {
        return category;
    }

    /**
     * @return all the attribute:value pairs in this example
     */
    public List<String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "NavieBayes.Example{" +
                "category='" + category + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
