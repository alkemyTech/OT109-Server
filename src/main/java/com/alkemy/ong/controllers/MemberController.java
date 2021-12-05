package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.dtos.responses.MemberResponseDTO;
import com.alkemy.ong.dtos.requests.MemberRequest;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.InvalidParameterException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<ListMemberDTO> members = memberService.findAll();
        return ResponseEntity.ok(members);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody MemberRequest request) throws DataAlreadyExistException{
        try{
            MemberResponseDTO response = memberService.create(request);
            return ResponseEntity.ok(response);
        }catch (ConstraintViolationException ex){
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            throw new InvalidParameterException(errorMessage);
        }catch (DataIntegrityViolationException ex){
            throw new InvalidParameterException(ex.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws NotFoundException{
        if(id == null || id.equals(0L)) throw new InvalidParameterException("Invalid id");
        try{
            memberService.delete(id);
            return ResponseEntity.ok("Member successfuly deleted");
        }catch (NotFoundException ex){
            throw new NotFoundException(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MemberRequest request) throws NotFoundException{
        if(id == null || id.equals(0L)) throw new InvalidParameterException("Invalid id");

        memberService.update(request,id);
        return ResponseEntity.ok().build();
    }
}
