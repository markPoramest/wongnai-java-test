package com.example.wongnaijavatest.Service;

import com.example.wongnaijavatest.Dao.FoodReviewRepository;
import com.example.wongnaijavatest.Helper.CSVHelper;
import com.example.wongnaijavatest.Model.FoodReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FoodReviewService {
    @Autowired
    FoodReviewRepository foodReviewRepository;
    public void save(FoodReview foodReview) {
        foodReviewRepository.save(foodReview);
    }
    public void save(MultipartFile file) {
        try {
            List<FoodReview> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
            foodReviewRepository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<FoodReview> foodReviews = foodReviewRepository.findAll();

        ByteArrayInputStream in = CSVHelper.foodReviewtoCSV(foodReviews);
        return in;
    }

    public FoodReview getFoodByID(int id){
        return foodReviewRepository.findById(id).get();
    }

    public List<FoodReview> getFoodByReview(String review){
        return foodReviewRepository.findByReview(review);
    }

    public void editReview(FoodReview foodReview){
        try {
            foodReviewRepository.save(foodReview);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}