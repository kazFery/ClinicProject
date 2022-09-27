package com.medical.clinic.constraint;

import com.medical.clinic.repository.ClinicUserRepository;
import com.medical.clinic.service.ClinicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

    @Autowired
    ClinicUserRepository userRepository;

    @Override
    public void initialize(final UniqueUserName arg0) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
      //return userRepository.findByUsername(username).isEmpty();
        return ! userRepository.existsByUsername(username);
    }
}