package com.capgemini.tdd.services.game.impl;

import com.capgemini.tdd.core.data.ValidationResult;
import com.capgemini.tdd.core.enums.GameError;
import com.capgemini.tdd.services.game.GameValidatorService;
import org.springframework.stereotype.Service;

@Service
public class GameValidatorServiceImpl implements GameValidatorService {

    @Override
    public ValidationResult validatePlayers(String playerOneName, String playerTwoName) {
        if (playerOneName.equals(playerTwoName)) {
            return new ValidationResult(false, GameError.DUPLICATE_PLAYER_NAMES);
        }

        return ValidationResult.validResult();
    }
}
