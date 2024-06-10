package com.zerobase.rdv.domain.model.validator;

import com.zerobase.rdv.domain.model.Accommodation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeCheckValidator implements ConstraintValidator<RangeCheck,
        Accommodation> {

    @Override
    public boolean isValid(Accommodation accommodation,
                           ConstraintValidatorContext context) {
        return accommodation.getCurrentAvailability() <=
                accommodation.getTotalAvailability();
    }

}
