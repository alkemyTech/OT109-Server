package com.alkemy.ong.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class MembersPageResponseDTO {
    private List<ListMemberDTO> membersDto;
    private String previousPage;
    private String nextPage;
}

