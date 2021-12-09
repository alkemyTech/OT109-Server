package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByOrderByCreatedAtDesc();

    @Query(value = "select case when (u.email LIKE ?1) then true else false end " +
            "from comments c join users u on u.id = c.user_id " +
            "where c.comments_id = ?2", nativeQuery = true)
    Boolean isOwner(String email, Long commentId);

}
