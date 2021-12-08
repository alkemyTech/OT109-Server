package com.alkemy.ong.repositories;

import com.alkemy.ong.dtos.responses.CommentListDTO;
import com.alkemy.ong.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<CommentListDTO> findCommentsByNewsId(Long id);
}
