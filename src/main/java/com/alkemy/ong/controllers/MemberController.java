package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.createAndUpdate.MemberRequest;
import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.dtos.responses.MemberResponseDTO;
import com.alkemy.ong.dtos.responses.MembersPageResponseDTO;
import com.alkemy.ong.entities.Member;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;
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

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private ModelMapper modelMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public MembersPageResponseDTO findAll(@RequestParam(defaultValue = "0")  @Min(value = 0, message = "Page must be 0 or greater.") int page,
                                     @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size cannot be less than one.") int size
                                    ) {
        Slice<Member> membersSlice = memberService.findAll(page,size);

        List<ListMemberDTO> membersPageResponseDTOS = membersSlice.stream()
                .map(member -> modelMapper.map(member,ListMemberDTO.class))
                .collect(Collectors.toList());

        String url = "http://localhost:9800/members?page=";
        MembersPageResponseDTO membersPageResponseDTO = new MembersPageResponseDTO();
        membersPageResponseDTO.setMembersDto(membersPageResponseDTOS);
        if(membersSlice.hasPrevious()) {
            membersPageResponseDTO.setPreviousPage(url + membersSlice.previousPageable().getPageNumber());
        }
        if(membersSlice.hasNext()) {
            membersPageResponseDTO.setNextPage(url + membersSlice.nextPageable().getPageNumber());
        }
        //membersSlice.previousOrFirstPageable().getPageNumber();
        //membersSlice.nextOrLastPageable().getPageNumber();
        return membersPageResponseDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MemberResponseDTO save(@Valid @RequestBody MemberRequest request) throws DataAlreadyExistException, NotFoundException{
//        try{
        return memberService.create(request);
            /*
        }catch (ConstraintViolationException ex){
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            throw new InvalidParameterException(errorMessage);

        }catch (DataIntegrityViolationException ex){
            throw new InvalidParameterException(ex.getLocalizedMessage());
        }*/
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Min(value = 1, message = "Id cannot be less than one.") Long id) throws NotFoundException{
        //try{
            memberService.delete(id);
            /*
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }*/
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable @Min(value = 1, message = "Id value cannot be less than 1") Long id, @Valid @RequestBody MemberRequest request) throws NotFoundException{
        //if(id == null || id.equals(0L)) throw new InvalidParameterException("Invalid id");

        //try{
            memberService.update(request,id);
            /*
        }catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            throw new InvalidParameterException(errorMessage);

        }catch (DataIntegrityViolationException ex){
            throw new InvalidParameterException("Image can't be null");
        }
        }*/
    }
    /*
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handle(NotFoundException notFoundException) {
        return notFoundException.getMessage();
    }*/

}
