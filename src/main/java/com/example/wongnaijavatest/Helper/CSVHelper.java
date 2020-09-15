package com.example.wongnaijavatest.Helper;

import com.example.wongnaijavatest.Model.FoodReview;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "Title", "Description", "Published" };

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<FoodReview> csvToTutorials(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader("reviewID","review"));) {

            List<FoodReview> foodReviews = new ArrayList<FoodReview>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            int i=1;
            for (CSVRecord csvRecord : csvRecords) {
                FoodReview foodReview = new FoodReview(
                       i++,
                        csvRecord.get("review"));
                foodReviews.add(foodReview);
            }

            return foodReviews;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    public static ByteArrayInputStream foodReviewtoCSV(List<FoodReview> foodReviews) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (FoodReview foodReviewial : foodReviews) {
                List<String> data = Arrays.asList(
                        String.valueOf(foodReviewial.getReviewID()),
                        foodReviewial.getReview());
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
