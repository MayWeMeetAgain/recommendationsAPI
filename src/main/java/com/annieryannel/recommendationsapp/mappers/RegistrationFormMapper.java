package com.annieryannel.recommendationsapp.mappers;

import com.annieryannel.recommendationsapp.DTO.RegistrationFormDto;
import com.annieryannel.recommendationsapp.models.User;
import com.annieryannel.recommendationsapp.repositories.RoleRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RegistrationFormMapper {
    final ModelMapper modelMapper;
    final RoleRepository roleRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationFormMapper(ModelMapper modelMapper, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(RegistrationFormDto.class, User.class)
                .addMappings(m -> m.skip(User::setId))
                .addMappings(m -> m.skip(User::setPassword))
                .addMappings(m -> m.skip(User::setRoles))
                .setPostConverter(toEntityConverter());
    }

    public User toEntity(RegistrationFormDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public Converter<RegistrationFormDto, User> toEntityConverter() {
        return context -> {
            RegistrationFormDto source = context.getSource();
            User destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(RegistrationFormDto source, User destination) {
        destination.setPassword(bCryptPasswordEncoder.encode(source.getPassword()));
        destination.addRole(roleRepository.findByRole("ROLE_USER"));
    }
}
