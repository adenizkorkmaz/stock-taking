package com.petfishco.stocktaking.exception;

import lombok.Getter;

@Getter
public class FishNotFoundException extends RuntimeException {

    private Integer gameId;

    public FishNotFoundException(String message, Integer gameId) {
        super(message);
        this.gameId = gameId;
    }


}
