package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByOrderByCreatedAtDesc();

    @Query(value = "select case when (u.email is null) then false else true end " +
            "from comments c join users u on u.id = c.user_id " +
            "where u.email like ?1 and c.comments_id = ?2", nativeQuery = true)
    boolean isOwner(String email, Long commentId);

}
