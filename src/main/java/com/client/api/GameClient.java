package com.client.api;

import com.gameprotocol.FinishRoundRequest;
import com.gameprotocol.StartRoundRequest;

public interface GameClient {

    void startRound(StartRoundRequest startRoundRequest);

    void finishRound(FinishRoundRequest finishRoundRequest);

}
