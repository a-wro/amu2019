package com.capgemini.tdd.services.game;

import com.capgemini.tdd.core.data.ValidationResult;

public interface GameValidatorService {

    ValidationResult validatePlayers(String playerNameOne, String playerNameTwo);
}
