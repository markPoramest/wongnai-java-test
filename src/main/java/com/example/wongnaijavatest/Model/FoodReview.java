package com.example.wongnaijavatest.Model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;

@Data
@Entity
@Table(name = "Food")
public class FoodReview {
    @Id
    @Column(name = "reviewID")
    private int reviewID;

    @Column(name = "review",length = 1_000_000)
    private String review;
    public FoodReview(){

}
    public FoodReview(int reviewID, String review) {
        this.reviewID = reviewID;
        this.review = review;
    }
}
