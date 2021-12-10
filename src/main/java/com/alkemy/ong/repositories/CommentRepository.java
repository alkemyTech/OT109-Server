package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByOrderByCreatedAtDesc();

    @Query(value = "select u.email " +
            "from comments c join users u on u.id = c.user_id " +
            "where c.comments_id = ?1", nativeQuery = true)
    Optional<String> findOwnerEmail(Long commentId);

}
