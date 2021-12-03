package com.alkemy.ong.services;

import com.alkemy.ong.entities.OrganizationEntity;
import java.util.List;

public interface OrganizationService {
    
    OrganizationEntity create(OrganizationEntity organization);
    void update(Long id, OrganizationEntity organizationEntity);
    void delete(Long id);
    OrganizationEntity findById(Long id);
    List<OrganizationEntity> findAll();
    
}
