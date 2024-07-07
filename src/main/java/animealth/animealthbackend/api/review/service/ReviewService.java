package animealth.animealthbackend.api.review.service;

import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewResponseDTO;
import animealth.animealthbackend.api.review.dto.GetReviewResponseDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewResponseDTO;
import animealth.animealthbackend.domain.review.Review;
import animealth.animealthbackend.domain.review.ReviewRepository;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewService {

    private static final int PAGE_SIZE = 10;

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public CreateReviewResponseDTO saveReview(CreateReviewRequestDTO request) {
        User writer = userRepository.findById(request.getWriterId()).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );

        Review result = reviewRepository.save(
                Review.of(
                        request.getContent(),
                        request.getRating(),
                        writer,
                        request.getVetId()
                )
        );
        return CreateReviewResponseDTO.fromEntity(result);
    }

    @Transactional(readOnly = true)
    public GetReviewResponseDTO getReviewByReviewId(Long reviewId) {
        Review result = reviewRepository.findById(reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review Not Found")
        );
        return GetReviewResponseDTO.fromEntity(result);
    }

    @Transactional(readOnly = true)
    public Page<GetReviewResponseDTO> getReviewByVetId(int pageNo, String criteria, Long vetId) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));
        return reviewRepository.findAllByVetId(pageable, vetId).map(GetReviewResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<GetReviewResponseDTO> getReviewByWriterId(int pageNo, String criteria, Long writerId) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));
        return reviewRepository.findAllByWriterId(pageable, writerId).map(GetReviewResponseDTO::fromEntity);
    }

    public UpdateReviewResponseDTO updateReview(UpdateReviewRequestDTO request) {
        Review result = reviewRepository.findById(request.getReviewId()).orElseThrow(
                () -> new EntityNotFoundException("Review Not Found")
        );

        if (request.getContent() != null) {
            result.updateContent(request.getContent());
        }

        if (request.getRating() != null) {
            result.updateRating(request.getRating());
        }

        return UpdateReviewResponseDTO.fromEntity(result);
    }

    public void deleteReviewById(Long reviewId) {
        try {
            reviewRepository.deleteById(reviewId);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Review Not Found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
