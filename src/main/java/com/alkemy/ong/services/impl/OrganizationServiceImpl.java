package com.alkemy.ong.services.impl;

import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.repositories.OrganizationRepository;
import com.alkemy.ong.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

@Service
public class OrganizationServiceImpl implements OrganizationService{

    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public OrganizationEntity create(OrganizationEntity entity) {
        return organizationRepository.save(entity);
    }

    @Override
    public void update(Long id, OrganizationEntity organizationEntity) {
        Optional<OrganizationEntity> entity = organizationRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("Error: invalid organization id");
        }
        ModelMapper modelMapper = new ModelMapper();
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
    public OrganizationEntity findById(Long id) {
        return organizationRepository.getById(id);
    }

    @Override
    public List<OrganizationEntity> findAll() {
        List<OrganizationEntity> entities = organizationRepository.findAll();
        return  entities;
    }
    
}
