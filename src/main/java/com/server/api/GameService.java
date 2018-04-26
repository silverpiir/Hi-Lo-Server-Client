package com.server.api;

import com.gameprotocol.PlayerActionRequest;
import com.gameprotocol.PlayerActionResponse;

public interface GameService {

    PlayerActionResponse playerAction(PlayerActionRequest playerActionRequest);

    SetBaseCardResponse setBaseCard(SetBaseCardRequest setBaseCardRequest);
}
