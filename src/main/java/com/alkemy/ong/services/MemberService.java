package com.alkemy.ong.services;

import java.util.List;

import com.alkemy.ong.entities.Member;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;

public interface MemberService {

    Member create(Member member) throws DataAlreadyExistException;
    Member update(Member member, Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    Member findById(Long id) throws NotFoundException;
    List<Member> findAll();

    
}
