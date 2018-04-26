package com.gameprotocol;

import java.io.Serializable;

public class PlayerActionResponse implements Serializable {
    private final String errorText;

    public PlayerActionResponse(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }

    @Override
    public String toString() {
        return "PlayerActionResponse{" +
                "errorText='" + errorText + '\'' +
                '}';
    }
}
