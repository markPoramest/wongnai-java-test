package com.example.wongnaijavatest.Controller;

import com.example.wongnaijavatest.Helper.CSVHelper;
import com.example.wongnaijavatest.Helper.ResponseMessage;
import com.example.wongnaijavatest.Model.FoodReview;
import com.example.wongnaijavatest.Service.FoodReviewService;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping("/reviews")
public class FoodReviewController {
    @Autowired
    FoodReviewService foodReviewService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                foodReviewService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/food")
    public ResponseEntity<List<FoodReview>> getAllFoodReview() {
        try {
            List<FoodReview> foodReviews = foodReviewService.getAllFoods();

            if (foodReviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foodReviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public String getFoodbyID(ModelMap modelMap, @PathVariable("id") String id){
        try{
            FoodReview fd = foodReviewService.getFoodByID(Integer.parseInt(id));
            modelMap.addAttribute("title","รีวิวลำดับที่ ");
            modelMap.addAttribute("listfood",fd);
            modelMap.addAttribute("keyword",id);
            return "showFoodReview";
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @GetMapping()
    public String getFoodbyKeyword(ModelMap modelMap, @RequestParam("query") String review){
        try{
            List<FoodReview> FD =  foodReviewService.getFoodByReview(review);
            modelMap.addAttribute("title","รวม Review ที่มี Keyword คำว่า ");
            modelMap.addAttribute("listfood",FD);
            modelMap.addAttribute("keyword",review);
            return "showFoodReview";
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @PutMapping("/{id}")
    public String editReview(ModelMap modelMap,@ModelAttribute(value="editReview") FoodReview foodReview){

    }

}
