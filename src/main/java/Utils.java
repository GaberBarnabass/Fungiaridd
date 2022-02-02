import NaiveBayes.Example;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * <p>Class of utility to handle csv, making test sets and printing sets</p>
 */
public class Utils {
    /**
     * @param training_set      input training set
     * @param number_of_example number of example to put into test set
     * @param class_            class to take
     * @return test set
     */
    static Set<Example> getTestData(Set<Example> training_set, int number_of_example, String class_) {
        Set<Example> test_set = new HashSet<>();
        for (Example e : training_set) {
            if (e.getCategoryName().equals(class_) && number_of_example != 0) {
                test_set.add(e);
                number_of_example--;
            } else if (number_of_example == 0) {
                break;
            }
        }
        for (Example e : test_set) {
            //e.setCategory("query");
            training_set.remove(e);
        }
        return test_set;
    }

    /**
     * <p>Takes the csv file, transform any tuple to an example and returns the training set.</p>
     *
     * @param path path to data
     * @return the training set
     */
    static Set<Example> editCsvAndGetData(String path) {
        List<String[]> to_edit = null;
        Set<Example> training_set = new HashSet<>();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            to_edit = reader.readAll();
        } catch (CsvException | IOException e) {
            System.err.println("Error while reading data.");
        }

        assert to_edit != null;
        String[] labels = to_edit.remove(0);


        for (int i = 0; i < to_edit.size(); i++) {
            for (int j = 0; j < labels.length; j++) {
                String tmp = labels[j] + ":" + to_edit.get(i)[j];
                to_edit.get(i)[j] = tmp.toLowerCase();
            }
        }
        for (String[] tuple : to_edit) {
            List<String> tmp = new LinkedList<>(Arrays.asList(tuple));
            String tmp_category = tmp.remove(0);
            training_set.add(new Example(tmp_category, tmp));
        }
        return training_set;
    }

    /**
     * @param set to b printed
     */
    private static void printSet(Set<Example> set) {
        for (Example e : set) {
            for (String s : e.getAttributes()) {
                System.out.printf("%-40s", s);
            }
            System.out.println(e);
        }
    }
}
