package com.example.wongnaijavatest.Dao;

import com.example.wongnaijavatest.Model.FoodReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodReviewRepository extends JpaRepository<FoodReview,Integer> {

    @Query("Select r from FoodReview r where Review like %?1%")
    public List<FoodReview> findByReview(String review);


}
