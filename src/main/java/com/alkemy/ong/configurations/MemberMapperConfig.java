package com.alkemy.ong.configurations;

import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.dtos.responses.MemberResponseDTO;
import com.alkemy.ong.dtos.requests.MemberRequest;
import com.alkemy.ong.entities.Member;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberMapperConfig {
    @Bean("Member-Mapper")
    public ModelMapper memberMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Member, ListMemberDTO>(){
            @Override //source -> Member, Destination -> ListMemberDTO
            protected void configure(){
                map().setId(source.getId());
                map().setImage(source.getImage());
                map().setName(source.getName());
                map().setDescription(source.getDescription());
                map().setInstagramUrl(source.getInstagramUrl());
                map().setLinkedinUrl(source.getLinkedinUrl());
                map().setFacebookUrl(source.getFacebookUrl());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedAt(source.getUpdatedAt());
                map().setDeletedAt(source.getUpdatedAt());
                map().setOrganization(source.getOrganization());
            }
        });

        modelMapper.addMappings(new PropertyMap<Member, MemberResponseDTO>() {
            @Override
            protected void configure(){
                map().setId(source.getId());
                map().setName(source.getName());
                map().setImage(source.getImage());
                map().setDescription(source.getDescription());
                map().setFacebookUrl(source.getFacebookUrl());
                map().setInstagramUrl(source.getInstagramUrl());
                map().setLinkedinUrl(source.getLinkedinUrl());
            }
        });

        modelMapper.addMappings(new PropertyMap<MemberRequest, Member>() {
            @Override
            protected void configure(){
                map().setName(source.getName());
                map().setImage(source.getImage());
                map().setDescription(source.getDescription());
                map().setFacebookUrl(source.getFacebookUrl());
                map().setInstagramUrl(source.getInstagramUrl());
                map().setLinkedinUrl(source.getLinkedinUrl());
            }
        });

        modelMapper.addMappings(new PropertyMap<Member, MemberRequest>() {
            @Override
            protected void configure(){
                map().setName(source.getName());
                map().setImage(source.getImage());
                map().setDescription(source.getDescription());
                map().setFacebookUrl(source.getFacebookUrl());
                map().setInstagramUrl(source.getInstagramUrl());
                map().setLinkedinUrl(source.getLinkedinUrl());
            }
        });

        return modelMapper;
    }
}
