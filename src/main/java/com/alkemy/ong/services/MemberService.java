package com.alkemy.ong.services;

import com.alkemy.ong.dtos.responses.MemberResponseDTO;
import com.alkemy.ong.dtos.requests.createAndUpdate.MemberRequest;
import com.alkemy.ong.entities.Member;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;
import org.springframework.data.domain.Slice;

public interface MemberService {

    MemberResponseDTO create(MemberRequest member) throws DataAlreadyExistException, NotFoundException;
    MemberResponseDTO update(MemberRequest member, Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    Member findById(Long id) throws NotFoundException;
    Slice<Member> findAll(int page,int size);

}
