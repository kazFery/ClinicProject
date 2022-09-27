package com.medical.clinic.constraint;

import com.medical.clinic.repository.ClinicUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    ClinicUserRepository userRepository;

    @Override
    public void initialize(final UniqueEmail arg0) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
          return userRepository.findByEmail(email).isEmpty();
    }
}