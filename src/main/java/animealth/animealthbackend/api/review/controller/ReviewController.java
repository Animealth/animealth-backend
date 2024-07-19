package animealth.animealthbackend.api.review.controller;

import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewRequestDTO;
import animealth.animealthbackend.api.review.service.ReviewService;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/api/reviews")
@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(value = "/save")
    public String saveReview(@RequestBody CreateReviewRequestDTO request, Model model) {
        model.addAttribute("response", reviewService.saveReview(request));
        return "dummyPage";
    }

    @GetMapping(value = "/read/{reviewId}")
    public String getReviewsById(@PathVariable Long reviewId, Model model) {
        model.addAttribute("response", reviewService.getReviewByReviewId(reviewId));
        return "dummyPage";
    }

    @GetMapping(value = "/read/vet/{vetId}")
    public String getReviewsByVetId(
            @PathVariable Long vetId,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "createdTime", value = "criteria") String criteria,
            Model model
    ) {
        model.addAttribute("response", reviewService.getReviewByVetId(pageNo, criteria, vetId));
        return "dummyPage";
    }

    @GetMapping(value = "/read/my")
    public String getReviewsByWriterId(
            HttpSession session,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "createdTime", value = "criteria") String criteria,
            Model model
    ) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        model.addAttribute("response", reviewService.getReviewByWriterId(pageNo, criteria, principal.getId()));
        return "dummyPage";
    }

    @PostMapping(value = "/update")
    public String updateReview(@RequestBody UpdateReviewRequestDTO request, Model model) {
        model.addAttribute("response", reviewService.updateReview(request));
        return "dummyPage";
    }

    @DeleteMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId, Model model) {
        reviewService.deleteReviewById(reviewId);
        model.addAttribute("response", "Data successfully deleted");
        return "dummyPage";
    }

}
