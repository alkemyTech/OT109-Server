package com.alkemy.ong.services.impl;

import com.alkemy.ong.dtos.requests.SlideRequest;
import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.entities.Member;
import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.entities.Slide;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.repositories.OrganizationRepository;
import com.alkemy.ong.repositories.SlideRepository;
import com.alkemy.ong.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    SlideRepository slideRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public OrganizationEntity create(OrganizationEntity entity) {
        return organizationRepository.save(entity);
    }

    @Override
    public void update(Long id, OrganizationEntity organizationEntity) throws ParamNotFound {
        Optional<OrganizationEntity> entity = organizationRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("Error: invalid organization id");
        }
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        OrganizationEntity organization = entity.get();
        modelMapper.map(organizationEntity, organization);
        organizationRepository.save(organization);
    }

    @Override
    public void delete(Long id) {
        organizationRepository.deleteById(id);
    }

    @Override
    public OrganizationEntity findById(Long id) throws ParamNotFound {
        Optional<OrganizationEntity> opt = organizationRepository.findById(id);
        if (opt.isPresent()) {
            opt.get().setSlide(slideRepository.getSlidesSortedAsc(id));
            return opt.get();
        }
        throw new ParamNotFound("Organization not found");
    }

    @Override
    public List<OrganizationEntity> findAll() {
        List<OrganizationEntity> entities = organizationRepository.findAll();
        return entities;
    }

}
