package com.alkemy.ong.services;

import java.util.List;

import com.alkemy.ong.dtos.ListMemberDTO;
import com.alkemy.ong.dtos.MemberDescriptionDTO;
import com.alkemy.ong.dtos.MemberRequestDTO;
import com.alkemy.ong.entities.Member;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;

public interface MemberService {

    MemberDescriptionDTO create(MemberRequestDTO member) throws DataAlreadyExistException;
    Member update(Member member, Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    Member findById(Long id) throws NotFoundException;
    List<ListMemberDTO> findAll();

    
}
