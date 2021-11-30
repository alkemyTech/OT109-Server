package com.alkemy.ong.configurations;

import com.alkemy.ong.dtos.ListMemberDTO;
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

        return modelMapper;
    }
}
