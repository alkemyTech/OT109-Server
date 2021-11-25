package com.alkemy.ong.services.impl;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import com.alkemy.ong.entities.Member;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.repositories.MemberRepository;
import com.alkemy.ong.services.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Member create(Member member) throws DataAlreadyExistException{

        if ((memberRepository.findByName(member.getName()).isPresent())) {
            throw new DataAlreadyExistException("Member already exists");
        }
       return memberRepository.save(member);

    }

    @Override
    public Member update(Member member, Long id) throws NotFoundException {
        Member newMember = memberRepository.findById(id).orElseThrow(() -> new NotFoundException("Member does not exist"));
        newMember.setName(member.getName());
        newMember.setFacebookUrl(member.getFacebookUrl());
        newMember.setInstagramUrl(member.getInstagramUrl());
        newMember.setLinkedinUrl(member.getLinkedinUrl());
        newMember.setImage(member.getImage());
        newMember.setDescription(member.getDescription());
        newMember.setUpdatedAt(Date.valueOf(LocalDate.now()));
        
        return memberRepository.save(newMember);

    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Member with id %id was not found", id)));
        member.setDeletedAt((Date.valueOf(LocalDate.now())));
        memberRepository.save(member);
    }

    @Override
    public Member findById(Long id) throws NotFoundException {
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Member with id %id was not found", id)));
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

}
