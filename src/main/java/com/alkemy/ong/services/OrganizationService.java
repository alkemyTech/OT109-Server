package com.alkemy.ong.services;

import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    public void create(OrganizationEntity entity) {
        organizationRepository.save(entity);
    }

    public void update(Long id, OrganizationEntity organizationEntity) {
        Optional<OrganizationEntity> entity = organizationRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("Error: invalid organization id");
        }
        entity.get().setName(organizationEntity.getName());
        entity.get().setImage(organizationEntity.getImage());
        entity.get().setAddress(organizationEntity.getAddress());
        entity.get().setPhone(organizationEntity.getPhone());
        entity.get().setEmail(organizationEntity.getEmail());
        entity.get().setWelcomeText(organizationEntity.getWelcomeText());
        entity.get().setAboutUsText(organizationEntity.getAboutUsText());
        organizationRepository.save(entity.get());
    }

    public void delete(Long id) {
        organizationRepository.deleteById(id);
    }

    public OrganizationEntity findById(Long id) {
        return organizationRepository.getById(id);
    }

    public List<OrganizationEntity> findAll() {
        List<OrganizationEntity> entities = organizationRepository.findAll();
        return  entities;
    }
}
