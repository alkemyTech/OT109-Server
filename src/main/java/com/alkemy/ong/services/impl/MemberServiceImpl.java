package com.alkemy.ong.services.impl;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alkemy.ong.dtos.ListMemberDTO;
import com.alkemy.ong.dtos.MemberDescriptionDTO;
import com.alkemy.ong.dtos.MemberRequestDTO;
import com.alkemy.ong.entities.Member;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.repositories.MemberRepository;
import com.alkemy.ong.services.MemberService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    @Qualifier("Member-Mapper")
    private ModelMapper memberMapper;

    @Override
    public MemberDescriptionDTO create(MemberRequestDTO request) throws DataAlreadyExistException{
        Optional<Member> optionalMember = memberRepository.findByName(request.getName());
        if (optionalMember.isPresent()) {
            throw new DataAlreadyExistException("Member already exists");
        }

        Member newMember = memberMapper.map(request, Member.class);

       return memberMapper.map(memberRepository.save(newMember), MemberDescriptionDTO.class);

    }

    @Override
    public MemberDescriptionDTO update(MemberRequestDTO member, Long id) throws NotFoundException {
        Member newMember = memberRepository.findById(id).orElseThrow(() -> new NotFoundException("Member does not exist"));

        newMember.setName(member.getName());
        newMember.setFacebookUrl(member.getFacebookUrl());
        newMember.setInstagramUrl(member.getInstagramUrl());
        newMember.setLinkedinUrl(member.getLinkedinUrl());
        newMember.setImage(member.getImage());
        newMember.setDescription(member.getDescription());
        newMember.setUpdatedAt(Date.valueOf(LocalDate.now()));
        newMember = memberRepository.save(newMember);

        return memberMapper.map(newMember,MemberDescriptionDTO.class);

    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Member with id %id was not found", id)));
        memberRepository.deleteById(id);

        member.setDeletedAt((Date.valueOf(LocalDate.now())));
        memberRepository.save(member);
    }

    @Override
    public Member findById(Long id) throws NotFoundException {
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Member with id %id was not found", id)));
    }

    @Override
    public List<ListMemberDTO> findAll() {
        return convertListToDTO(memberRepository.findAll());
    }


    private List<ListMemberDTO> convertListToDTO(List<Member> members){

        if(members.isEmpty())
            return new ArrayList<ListMemberDTO>();

        return members
                .stream()
                .map(member -> memberMapper.map(member,ListMemberDTO.class))
                .collect(Collectors.toList());
    }
}
