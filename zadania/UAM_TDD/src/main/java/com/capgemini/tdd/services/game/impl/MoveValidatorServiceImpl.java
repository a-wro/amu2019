package com.capgemini.tdd.services.game.impl;

import com.capgemini.tdd.core.data.ValidationResult;
import com.capgemini.tdd.core.enums.GameError;
import com.capgemini.tdd.dao.entities.MoveBE;
import com.capgemini.tdd.dao.enums.MoveValueEnum;
import com.capgemini.tdd.services.game.MoveValidatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveValidatorServiceImpl implements MoveValidatorService {

    @Override
    public ValidationResult validate(List<MoveBE> movesOnBoard, Long x, Long y, String playerName, String value) {

        ValidationResult playerTurnValid = validatePlayerTurn(movesOnBoard, playerName);

        if (!playerTurnValid.isValid()) {
            return playerTurnValid;
        }

        ValidationResult moveValueValid = validateMoveValue(movesOnBoard, value);

        if (!moveValueValid.isValid()) {
            return moveValueValid;

        }

        ValidationResult moveCoordinatesValid = validateMoveCoordinates(movesOnBoard, x, y);

        if (!moveCoordinatesValid.isValid()) {
            return moveCoordinatesValid;
        }

        return ValidationResult.validResult();
    }

    @Override
    public ValidationResult validatePlayerTurn(List<MoveBE> movesOnBoard, String playerName) {
        // validate if player tries to move twice in a row
        int size = movesOnBoard.size();


        if (size > 0) {
            String lastMovePlayersName = movesOnBoard.get(size - 1).getPlayer().getName();

            if (lastMovePlayersName.equals(playerName)) {
                return new ValidationResult(false, GameError.NOT_PLAYERS_TURN);
            }
        }

        return ValidationResult.validResult();
    }

    @Override
    public ValidationResult validateMoveValue(List<MoveBE> movesOnBoard, String moveValue) {

        MoveValueEnum valueEnum;
        // validate enum
        try {
            valueEnum = MoveValueEnum.valueOf(moveValue);
        } catch (IllegalArgumentException e) {
            return new ValidationResult(false, GameError.ILLEGAL_MOVE);
        }

        // validate if player always picks the same value
        int size = movesOnBoard.size();

        if (size == 1) {
            MoveBE lastOpponentMove = movesOnBoard.get(size - 1);
            if (lastOpponentMove.getMoveValue().equals(valueEnum)) {
                return new ValidationResult(false, GameError.DUPLICATE_MOVE_VALUE);
            }
        }

        if (size > 1) {
            MoveBE lastPlayerMove = movesOnBoard.get(size - 2);

            if (!lastPlayerMove.getMoveValue().equals(valueEnum)) {
                return new ValidationResult(false, GameError.WRONG_VALUE_FOR_PLAYER);
            }
        }

        return ValidationResult.validResult();
    }

    @Override
    public ValidationResult validateMoveCoordinates(List<MoveBE> movesOnBoard, Long x, Long y) {

        // validate if (x, y) position is not already taken
        for (MoveBE moveBE : movesOnBoard) {
            if (moveBE.getX().equals(x) && moveBE.getY().equals(y)) {
                return new ValidationResult(false, GameError.POSITION_TAKEN);
            }
        }

        // validate if the move is in 3x3 board bounds
        boolean inBounds = x <= 2 && y <= 2;

        if (!inBounds) {
            return new ValidationResult(false, GameError.OUT_OF_BOUNDS);
        }

        return ValidationResult.validResult();
    }
}
