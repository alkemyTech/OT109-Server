package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.MemberDescriptionDTO;
import com.alkemy.ong.dtos.MemberRequestDTO;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(memberService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody MemberRequestDTO request){

        try{
            MemberDescriptionDTO response = memberService.create(request);
            return ResponseEntity.ok(response);

        }catch (DataAlreadyExistException ex){
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            memberService.delete(id);
        }catch(NotFoundException ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Member successfuly deleted");
    }
}
