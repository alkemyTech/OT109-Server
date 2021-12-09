package com.alkemy.ong.repositories;

import com.alkemy.ong.dtos.responses.CommentListDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CommentRepositoryImpl {
    @Autowired
    private EntityManager em;


    public List<CommentListDTO> findCommentsByNewsId(Long id){
        String query = "SELECT NEW com.alkemy.ong.dtos.responses.CommentListDTO(c.id, c.body, c.user.email) FROM Comment c WHERE c.news.id = :id";
        TypedQuery<CommentListDTO> typedQuery = em.createQuery(query, CommentListDTO.class);
        typedQuery.setParameter("id",id);
        return typedQuery.getResultList();
    }

}
