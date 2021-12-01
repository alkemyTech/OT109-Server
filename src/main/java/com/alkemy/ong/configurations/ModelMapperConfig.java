package com.alkemy.ong.configurations;

import com.alkemy.ong.dtos.ListMemberDTO;
import com.alkemy.ong.dtos.MemberDescriptionDTO;
import com.alkemy.ong.dtos.MemberRequestDTO;
import com.alkemy.ong.entities.Member;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean("Member-Mapper")
    public ModelMapper memberMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Member, ListMemberDTO>(){
            @Override //source -> Member, Destination -> ListMemberDTO
            protected void configure(){
                map().setId(source.getId());
                map().setImage(source.getImage());
                map().setName(source.getName());
            }
        });

        modelMapper.addMappings(new PropertyMap<Member, MemberDescriptionDTO>() {
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

        modelMapper.addMappings(new PropertyMap<MemberRequestDTO, Member>() {
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
