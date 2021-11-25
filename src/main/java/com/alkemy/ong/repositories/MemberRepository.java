package com.alkemy.ong.repositories;

import java.util.Optional;

import com.alkemy.ong.entities.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
        Optional<Member> findByFacebookUrl(String facebookUrl);
}
