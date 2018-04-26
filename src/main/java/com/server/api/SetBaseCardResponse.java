package com.server.api;

import java.io.Serializable;

public class SetBaseCardResponse implements Serializable {
    private final String errorText;

    public SetBaseCardResponse(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }

    @Override
    public String toString() {
        return "SetBaseCardResponse{" +
                "errorText='" + errorText + '\'' +
                '}';
    }
}
