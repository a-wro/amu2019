package com.capgemini.tdd.services.game;

import com.capgemini.tdd.core.data.ValidationResult;
import com.capgemini.tdd.dao.entities.MoveBE;

import java.util.List;

public interface MoveValidatorService {

    static ValidationResult validResult() {
        return new ValidationResult(true, null);
    }

    ValidationResult validate(List<MoveBE> movesOnBoard, Long x, Long y, String playerName, String value);

    ValidationResult validatePlayerTurn(List<MoveBE> movesOnBoard, String playerName);

    ValidationResult validateMoveValue(List<MoveBE> movesOnBoard, String move);

    ValidationResult validateMoveCoordinates(List<MoveBE> movesOnBoard, Long x, Long y);

}
