package com.capgemini.tdd.core.data;

import com.capgemini.tdd.core.enums.GameError;

public class ValidationResult {

    private boolean isValid;
    private GameError validationError;

    public ValidationResult() {
    }

    public ValidationResult(boolean isValid, GameError validationError) {
        this.isValid = isValid;
        this.validationError = validationError;
    }

    public boolean isValid() {
        return isValid;
    }

    public static ValidationResult validResult() {
        return new ValidationResult(true, null);
    }

    public GameError getValidationError() {
        return validationError;
    }
}
