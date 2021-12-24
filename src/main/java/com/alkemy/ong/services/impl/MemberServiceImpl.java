package com.alkemy.ong.services.impl;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.dtos.responses.MemberResponseDTO;
import com.alkemy.ong.dtos.requests.MemberRequest;
import com.alkemy.ong.entities.Member;
import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.dtos.responses.ListOrganizationDTO;
import com.alkemy.ong.repositories.MemberRepository;
import com.alkemy.ong.services.MemberService;

import com.alkemy.ong.services.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MemberResponseDTO create(MemberRequest request) throws DataAlreadyExistException, NotFoundException {
        Optional<Member> optionalMember = memberRepository.findByName(request.getName());
        if (optionalMember.isPresent()) {
            throw new DataAlreadyExistException("Member already exists");
        }

        OrganizationEntity organization = organizationService.findById(request.getOrganizationId());

        if(organization == null) throw new NotFoundException(String.format("Organization Id: %d not found",request.getOrganizationId()));

        Member newMember = modelMapper.map(request, Member.class);

        newMember.setId(null);
        newMember.setCreatedAt(new java.util.Date());
        newMember.setOrganization(organization);

        newMember = memberRepository.save(newMember);

        MemberResponseDTO response = modelMapper.map(newMember, MemberResponseDTO.class);
        response.setOrganization(modelMapper.map(organization,ListOrganizationDTO.class));

       return response;

    }

    @Override
    public MemberResponseDTO update(MemberRequest member, Long id) throws NotFoundException {
        Member newMember = memberRepository.findById(id).orElseThrow(() -> new NotFoundException("Member does not exist"));
        OrganizationEntity organization = organizationService.findById(member.getOrganizationId());

        newMember.setId(id);
        newMember.setName(member.getName());
        newMember.setFacebookUrl(member.getFacebookUrl());
        newMember.setInstagramUrl(member.getInstagramUrl());
        newMember.setLinkedinUrl(member.getLinkedinUrl());
        newMember.setImage(member.getImage());
        newMember.setDescription(member.getDescription());
        newMember.setUpdatedAt(Date.valueOf(LocalDate.now()));
        newMember.setOrganization(organization);

        newMember = memberRepository.save(newMember);

        return modelMapper.map(newMember, MemberResponseDTO.class);

    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Member with id %d was not found", id)));
        memberRepository.deleteById(id);
    }

    @Override
    public Member findById(Long id) throws NotFoundException {
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Member with id %d was not found", id)));
    }

    @Override
    public Slice<Member> findAll(int page,int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Slice<Member> memberSlice = memberRepository.findBy(pageRequest);
        if(memberSlice.isEmpty()){
            throw new NotFoundException("Page not found, try with other Page.");
        }
        return memberSlice;
    }

    private List<ListMemberDTO> convertListToDTO(List<Member> members){

        if(members.isEmpty())
            return new ArrayList<ListMemberDTO>();

        return members
                .stream()
                .map(member -> modelMapper.map(member,ListMemberDTO.class))
                .collect(Collectors.toList());
    }

    private OrganizationEntity keepId(OrganizationEntity organization){
        organization.setName(null);
        organization.setImage(null);
        organization.setAddress(null);
        organization.setAboutUsText(null);
        organization.setEmail(null);
        organization.setMembers(null);
        organization.setPhone(null);
        organization.setWelcomeText(null);
        organization.setUpdatedAt(null);
        organization.setCreatedAt(null);
        organization.setDeletedAt(null);
        return organization;
    }
}
