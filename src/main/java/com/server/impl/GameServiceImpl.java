package com.server.impl;

import com.common.Card;
import com.common.PlayerAction;
import com.gameprotocol.FinishRoundRequest;
import com.gameprotocol.PlayerActionRequest;
import com.gameprotocol.PlayerActionResponse;
import com.gameprotocol.StartRoundRequest;
import com.server.api.GameService;
import com.server.api.SetBaseCardRequest;
import com.server.api.SetBaseCardResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;

public class GameServiceImpl implements GameService {
    private static ServerSocket server;
    private static int port = 4321;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Open port, socket, data streams
        server = new ServerSocket(port);
        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        GameService service = new GameServiceImpl();
        //Create deck
        List<Card> deck = Card.generateDeck();
        int actionRoundDuration = 10;
        long roundId = 1;
        System.out.println("Server started.");

        while(deck.size() > 1){
            socket = server.accept();
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            //Get current timestamp
            Date today = new java.util.Date();
            java.sql.Timestamp ts1 = new java.sql.Timestamp(today.getTime());
            long tsTime = ts1.getTime();
            //Get base card's and the next card's values
            int baseCardValue = deck.get(0).returnNumberValue(deck.get(0));
            int nextCardValue = deck.get(1).returnNumberValue(deck.get(1));
            SetBaseCardRequest baseCardReq = new SetBaseCardRequest(deck.get(0));

            //Create a StartRoundRequest and send to client
            StartRoundRequest startRoundRequest = new StartRoundRequest(actionRoundDuration, tsTime, roundId, baseCardReq.getBaseCard());
            oos.writeObject(startRoundRequest);

            //Read player input from client
            Object playerAction = ois.readObject();
            PlayerAction act = (PlayerAction) playerAction;

            //Compare player input to possible options, decide if the guess was correct
            //and send the result string and FinishRoundRequest to client
            if (act.equals(PlayerAction.HIGHER)) {
                if (baseCardValue < nextCardValue) {
                    String result = ("Correct! The next card is " + deck.get(1).getValue().toString() + " of " + deck.get(1).getSuit().toString());
                    oos.writeObject(result);
                    deck.remove(0);
                    FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), true);
                    oos.writeObject(finishRoundRequest);
                } else if (baseCardValue > nextCardValue) {
                    String result = ("Incorrect. The next card is " + deck.get(1).getValue().toString() + " of " + deck.get(1).getSuit().toString() + ", which is lower.");
                    oos.writeObject(result);
                    deck.remove(0);
                    FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), false);
                    oos.writeObject(finishRoundRequest);
                } else {
                    String result = ("Incorrect. The next card is " + deck.get(1).getValue().toString() + " of " + deck.get(1).getSuit().toString() + ", which is equal.");
                    oos.writeObject(result);
                    deck.remove(0);
                    FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), false);
                    oos.writeObject(finishRoundRequest);
                }
                roundId++;
            } else if (act.equals(PlayerAction.LOWER)) {
                if (baseCardValue > nextCardValue) {
                    String result = ("Correct! The next card is " + deck.get(1).getValue().toString() + " of " + deck.get(1).getSuit().toString());
                    oos.writeObject(result);
                    deck.remove(0);
                    FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), true);
                    oos.writeObject(finishRoundRequest);
                } else if (baseCardValue < nextCardValue) {
                    String result = ("Incorrect. The next card is " + deck.get(1).getValue().toString() + " of " + deck.get(1).getSuit().toString() + ", which is higher.");
                    oos.writeObject(result);
                    deck.remove(0);
                    FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), false);
                    oos.writeObject(finishRoundRequest);
                } else {
                    String result = ("Incorrect. The next card is " + deck.get(1).getValue().toString() + " of " + deck.get(1).getSuit().toString() + ", which is equal.");
                    oos.writeObject(result);
                    deck.remove(0);
                    FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), false);
                    oos.writeObject(finishRoundRequest);
                }
                roundId++;
            } else if (act.equals(PlayerAction.EQUALS)) {
                if (baseCardValue == nextCardValue) {
                    String result = ("Correct! The next card is " + deck.get(1).getValue().toString() + " of " + deck.get(1).getSuit().toString());
                    oos.writeObject(result);
                    deck.remove(0);
                    FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), true);
                    oos.writeObject(finishRoundRequest);
                } else if (baseCardValue < nextCardValue) {
                    String result = ("Incorrect. The next card is " + deck.get(1).getValue().toString() + " of " + deck.get(1).getSuit().toString() + ", which is higher.");
                    oos.writeObject(result);
                    deck.remove(0);
                    FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), false);
                    oos.writeObject(finishRoundRequest);
                } else {
                    String result = ("Incorrect. The next card is " + deck.get(1).getValue().toString() + " of " + deck.get(1).getSuit().toString() + ", which is lower.");
                    oos.writeObject(result);
                    deck.remove(0);
                    FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), false);
                    oos.writeObject(finishRoundRequest);
                }
                roundId++;
            } else if (act.equals(PlayerAction.FAIL)) { //Failed to input a correct value, moving on to next round
                deck.remove(0);
                FinishRoundRequest finishRoundRequest = new FinishRoundRequest(startRoundRequest.getRoundId(), false);
                oos.writeObject(finishRoundRequest);
                roundId++;
            }
        }
        //Close resources
        ois.close();
        oos.close();
        socket.close();
    }

    @Override
    public PlayerActionResponse playerAction(PlayerActionRequest playerActionRequest) {
        return new PlayerActionResponse("");
    }

    @Override
    public SetBaseCardResponse setBaseCard(SetBaseCardRequest setBaseCardRequest) {
        return new SetBaseCardResponse("");
    }
}
