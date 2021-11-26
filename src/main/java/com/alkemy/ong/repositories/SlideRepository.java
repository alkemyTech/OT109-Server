package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<Slide,Long> {

    @Query("update Slide s set s.orderNum = s.orderNum-1 where s.orderNum>:orderNum")
    void decreaseHigherThan(Long orderNum);

    Slide getByOrderNum(int orderNum);

    @Query("select max(s.orderNum) from Slide s ")
    Slide getByMaxOrderNum();
}
