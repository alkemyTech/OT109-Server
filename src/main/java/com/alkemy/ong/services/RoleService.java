package com.alkemy.ong.services;

import com.alkemy.ong.entities.Role;
import com.alkemy.ong.exceptions.RoleServiceException;
import com.alkemy.ong.repositories.RoleRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepo;
    
    @Transactional
    public Role create(@NonNull Role role){
        role.setCreatedAt(new Date());
        return roleRepo.save(role);
    }
    
    @Transactional
    public Role create(String name, String description){
        return create(Role.builder()
                .name(name)
                .description(description)
                .build());
    }
    
    public List<Role> findAll(){
        return roleRepo.findAll();
    }
    
    public Role findById(@NonNull Long id) throws RoleServiceException{
        Optional<Role> opt = roleRepo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new RoleServiceException("Role not found");
    }
    
    public List<Role> findByName(@NonNull String name) {
        return roleRepo.findByName(name);
    }
    
    @Transactional
    public void update(@NonNull Long id, @NonNull Role newRole) throws RoleServiceException {
        Optional<Role> opt = roleRepo.findById(id);
        if (opt.isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT).setFieldAccessLevel(AccessLevel.PRIVATE);;
            Role role = opt.get();
            modelMapper.map(newRole, role);
            role.setUpdatedAt(new Date());
            roleRepo.save(role);
        } else {
            throw new RoleServiceException("User not found");
        }
    }
    
    @Transactional
    public void put(@NonNull Long id, @NonNull Role newRole) {
        Optional<Role> opt = roleRepo.findById(id);
        if (opt.isPresent()) {
            Role role = opt.get();
            newRole.setCreatedAt(role.getCreatedAt());
            BeanUtils.copyProperties(newRole, role);
            role.setUpdatedAt(new Date());
            roleRepo.save(role);
        } else {
            roleRepo.putId(id);
            newRole.setCreatedAt(new Date());
            roleRepo.save(newRole);
        }
    }
    
    @Transactional
    public void delete(@NonNull Long id) throws RoleServiceException {
        if(roleRepo.findById(id).isPresent()){
            roleRepo.deleteById(id);
        } else {
            throw new RoleServiceException("User not found");
        }
    }
    
}
