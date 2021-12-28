package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.MemberRequest;
import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.dtos.responses.MemberResponseDTO;
import com.alkemy.ong.entities.Member;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.dtos.responses.PageDTO;
import com.alkemy.ong.services.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private ModelMapper modelMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PageDTO<ListMemberDTO> findAll(@RequestParam(defaultValue = "0")  @Min(value = 0, message = "Page must be 0 or greater.") int page,
                                     @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size cannot be less than one.") int size
                                    ) {
        Slice<Member> membersSlice = memberService.findAll(page,size);

        List<ListMemberDTO> membersResponseDTOS = membersSlice.stream()
                .map(member -> modelMapper.map(member,ListMemberDTO.class))
                .collect(Collectors.toList());

        String url = "http://localhost:9800/members?page=";
        Page<ListMemberDTO> outputPage = new PageImpl(membersResponseDTOS, PageRequest.of(membersSlice.getNumber(), membersSlice.getSize()), membersSlice.getNumberOfElements());
        return new PageDTO<>(outputPage, url);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MemberRequest request) throws DataAlreadyExistException, NotFoundException{
        MemberResponseDTO response = memberService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Min(value = 1, message = "Id cannot be less than one.") Long id) throws NotFoundException{
        memberService.delete(id);
        return ResponseEntity.ok("Member successfully deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable @Min(value = 1, message = "Id value cannot be less than 1") Long id, @Valid @RequestBody MemberRequest request) throws NotFoundException{

        memberService.update(request,id);
        return ResponseEntity.ok().build();
    }

}
