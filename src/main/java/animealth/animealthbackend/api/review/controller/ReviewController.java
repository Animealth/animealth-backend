package animealth.animealthbackend.api.review.controller;

import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewRequestDTO;
import animealth.animealthbackend.api.review.service.ReviewService;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/api/reviews")
@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(value = "/save")
    public ModelAndView saveReview(@RequestBody CreateReviewRequestDTO request) {
        ModelAndView modelAndView = new ModelAndView("dummyPage");
        modelAndView.addObject("response", reviewService.saveReview(request));
        return modelAndView;
    }

    @GetMapping(value = "/read/{reviewId}}")
    public ModelAndView getReviewsById(@PathVariable Long reviewId) {
        ModelAndView modelAndView = new ModelAndView("dummyPage");
        modelAndView.addObject("response", reviewService.getReviewByReviewId(reviewId));
        return modelAndView;
    }

    @GetMapping(value = "/read/{vetId}}")
    public ModelAndView getReviewsByVetId(
            @PathVariable Long vetId,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "createdTime", value = "criteria") String criteria
    ) {
        ModelAndView modelAndView = new ModelAndView("dummyPage");
        modelAndView.addObject("response", reviewService.getReviewByVetId(pageNo, criteria, vetId));
        return modelAndView;
    }

    @GetMapping(value = "/read}")
    public ModelAndView getReviewsByWriterId(
            HttpSession session,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "createdTime", value = "criteria") String criteria
    ) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView("dummyPage");
        modelAndView.addObject("response", reviewService.getReviewByWriterId(pageNo, criteria, principal.getId()));
        return modelAndView;
    }

    @PatchMapping(value = "/update")
    public ModelAndView updateReview(@RequestBody UpdateReviewRequestDTO request) {
        ModelAndView modelAndView = new ModelAndView("dummyPage");
        modelAndView.addObject("response", reviewService.updateReview(request));
        return modelAndView;
    }

    @DeleteMapping("/delete/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);
    }

}
