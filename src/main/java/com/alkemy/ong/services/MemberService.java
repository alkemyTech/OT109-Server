package com.alkemy.ong.services;

import java.util.List;

import com.alkemy.ong.dtos.ListMemberDTO;
import com.alkemy.ong.dtos.MemberResponseDTO;
import com.alkemy.ong.dtos.MemberRequest;
import com.alkemy.ong.entities.Member;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;

public interface MemberService {

    MemberResponseDTO create(MemberRequest member) throws DataAlreadyExistException;
    MemberResponseDTO update(MemberRequest member, Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    Member findById(Long id) throws NotFoundException;
    List<ListMemberDTO> findAll();

    
}
