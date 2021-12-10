package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<Slide,Long> {

    @Transactional
    @Modifying
    @Query("update Slide s set s.orderNum = s.orderNum-1 where s.orderNum>:orderNum")
    void decreaseHigherThan(int orderNum);

    Slide getByOrderNum(int orderNum);

    @Query("select max(s.orderNum) from Slide s ")
    int getByMaxOrderNum();

    List<Slide> findByOrderByOrderNumAsc();

    boolean existsByOrderNum(int orderNum);
}
