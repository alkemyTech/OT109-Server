package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
        Optional<Member> findByName(String name);
        Slice<Member> findBy(Pageable pageable);
}
