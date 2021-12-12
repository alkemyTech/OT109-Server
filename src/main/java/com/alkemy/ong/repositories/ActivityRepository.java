package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByName(String name);

    List<Activity> findByDeletedAtIsNull();
    boolean existsByName(String name);
}
