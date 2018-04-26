package com.server.api;

import com.common.Card;
import java.io.Serializable;

public class SetBaseCardRequest implements Serializable {
    private final Card baseCard;

    public SetBaseCardRequest(Card baseCard) {
        this.baseCard = baseCard;
    }

    public Card getBaseCard() {
        return baseCard;
    }

    @Override
    public String toString() {
        return "SetBaseCardRequest{" +
                "baseCard=" + baseCard +
                '}';
    }
}
