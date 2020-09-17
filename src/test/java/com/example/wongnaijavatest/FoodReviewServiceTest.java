package com.example.wongnaijavatest;

import com.example.wongnaijavatest.Dao.FoodReviewRepository;
import com.example.wongnaijavatest.Model.FoodReview;
import com.example.wongnaijavatest.Service.FoodReviewService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodReviewServiceTest  {
    @Mock
    FoodReviewRepository foodReviewRepository;
    @Spy
    @InjectMocks
    FoodReviewService foodReviewService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEmployeeByIdTest()
    {
        when(foodReviewRepository.findById(1)).thenReturn(java.util.Optional.of(new FoodReview(1, "ร้านนี้อร่อย")));
        FoodReview foodReview = foodReviewService.getFoodByID(1);
        assertEquals(1, foodReview.getReviewID());
        assertEquals("ร้านนี้อร่อย", foodReview.getReview());
    }

    @Test
    public void  getFoodByReviewTest(){
        List<FoodReview> foodReviewList = new ArrayList<>();
        foodReviewList.add(new FoodReview(1,"ร้านนี้ไม่อร่อยเทาไหร่ อย่ากินเลย"));
        foodReviewList.add(new FoodReview(2,"รสชาติอร่อยมาก กระเพาะปลา กับ ต้มยำกุ้งคือเดอะเบส"));
        foodReviewList.add(new FoodReview(3,"ไม่เคยกินร้านไหนอร่อยเท่านี้เลย แนะนำให้มาทานดูครับ"));
        foodReviewList.add(new FoodReview(4,"เบื่อรสชาติจำเจ ต้องมาลองร้านนี้แหละ อร่อยกว่าทุกที่ที่เคยไป"));
        when(foodReviewRepository.findByReview("อร่อย")).thenReturn(foodReviewList);
        List<FoodReview> foodList = foodReviewService.getFoodByReview("อร่อย");
        assertEquals(4,foodList.size());
    }

}
