package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.MemberRequest;
import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.dtos.responses.MemberResponseDTO;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.InvalidParameterException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<ListMemberDTO> members = memberService.findAll();
        return ResponseEntity.ok(members);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MemberRequest request) throws DataAlreadyExistException, NotFoundException{
        try{
            MemberResponseDTO response = memberService.create(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (ConstraintViolationException ex){
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            throw new InvalidParameterException(errorMessage);
        }catch (DataIntegrityViolationException ex){
            throw new InvalidParameterException(ex.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Min(value = 1, message = "Id cannot be less than one.") Long id) throws NotFoundException{
        try{
            memberService.delete(id);
            return ResponseEntity.ok("Member successfully deleted");
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable @Min(value = 1, message = "Id value cannot be less than 1") Long id, @Valid @RequestBody MemberRequest request) throws NotFoundException{
        //if(id == null || id.equals(0L)) throw new InvalidParameterException("Invalid id");

        try{
            memberService.update(request,id);
        }catch (ConstraintViolationException ex){
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            throw new InvalidParameterException(errorMessage);
        }catch (DataIntegrityViolationException ex){
            throw new InvalidParameterException("Image can't be null");
        }

        return ResponseEntity.ok().build();
    }
}
