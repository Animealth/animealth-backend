package animealth.animealthbackend.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByVetId(Pageable pageable, Long vetId);
    Page<Review> findAllByWriter_Id(Pageable pageable, Long userId);
}
