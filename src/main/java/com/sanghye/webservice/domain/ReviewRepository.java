package com.sanghye.webservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Iterable<Review> findByDeleted(boolean deleted);
}
