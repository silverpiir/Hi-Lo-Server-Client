package com.client.impl;

import com.client.api.GameClient;
import com.common.PlayerAction;
import com.gameprotocol.FinishRoundRequest;
import com.gameprotocol.StartRoundRequest;
import com.gameprotocol.CounterThread;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class GameClientImpl implements GameClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        //Initialize socket and data streams, initialize variables
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        BufferedReader in = null;

        GameClient client = new GameClientImpl();
        int roundCounter = 51;
        int winCounter = 0;
        int lossCounter = 0;
        String startRoundChoice;

        while (roundCounter > 0) {
            socket = new Socket(host.getHostName(), 4321);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("--------------------------------");
            System.out.println("Start a round? 1 - yes, 2 - exit");

            while(true){
                Scanner reader = new Scanner(System.in);
                startRoundChoice = reader.nextLine();
                if (startRoundChoice.equals("1")) {
                    break;
                } else if (startRoundChoice.equals("2")){
                    break;
                } else {
                    //Asks the user again
                    System.out.println("Invalid input. Try again.");
                    continue;
                }
            }
            if(startRoundChoice.equals("2")){
                break;
            }

            //Read StartRoundRequest sent by server
            Object roundStart = ois.readObject();
            StartRoundRequest startRoundRequest = (StartRoundRequest) roundStart;
            client.startRound(startRoundRequest);
            String baseCardString = (startRoundRequest.getBaseCard().getValue().toString() + " of " + startRoundRequest.getBaseCard().getSuit().toString());
            System.out.println("Basecard: " + baseCardString);
            System.out.println("Next card: 1 - higher, 2 - lower, 3 - equals");

            //The following is not ideal for running in Intellij, as input has to be entered and Enter key pressed in the space of a second.
            //The alternatives would be to use Java Curses Library for detecting keystrokes or to make a GUI for the program.
            long startTime = System.currentTimeMillis();

            CounterThread counterThread = new CounterThread();
            Thread cThread = new Thread(counterThread);
            cThread.start();

            while ((System.currentTimeMillis() - startTime) < 10000 && !in.ready());
            counterThread.keepRunning = false;
            cThread.interrupt();

            //If some input is received before time is up
            if (in.ready()) {
                String action = in.readLine();

                PlayerAction act = PlayerAction.FAIL; //Default

                switch(action){
                    case("1"):
                        act = PlayerAction.HIGHER;
                        break;
                    case("2"):
                        act = PlayerAction.LOWER;
                        break;
                    case("3"):
                        act = PlayerAction.EQUALS;
                        break;
                    default:
                        break;
                }
                //Send input data to server
                if (act.equals(PlayerAction.HIGHER) || act.equals(PlayerAction.LOWER) || act.equals(PlayerAction.EQUALS)) {
                    oos.writeObject(act);
                    System.out.println(act);
                    //If the player enters an invalid input, send FAIL to server
                } else {
                    oos.writeObject(act);
                    System.out.println("Invalid input. Try again.");

                    FinishRoundRequest finishRoundRequest = (FinishRoundRequest) ois.readObject();
                    client.finishRound(finishRoundRequest);
                    if (finishRoundRequest.isWin()) {
                        winCounter++;
                    } else {
                        lossCounter++;
                    }

                    if (roundCounter == 2){
                        System.out.println(roundCounter -1 + " round left. Wins: " + winCounter + " | Losses: " + lossCounter);
                    } else {
                        System.out.println(roundCounter - 1 + " rounds left. Wins: " + winCounter + " | Losses: " + lossCounter);
                    }
                    roundCounter--;
                    continue;
                }
                //If the player doesn't enter any input in 10 seconds, send FAIL to server
            } else {
                System.out.println("\rYou did not enter a choice in time.");
                PlayerAction act = PlayerAction.FAIL;
                oos.writeObject(act);

                FinishRoundRequest finishRoundRequest = (FinishRoundRequest) ois.readObject();
                client.finishRound(finishRoundRequest);
                if (finishRoundRequest.isWin()) {
                    winCounter++;
                } else {
                    lossCounter++;
                }

                if (roundCounter == 2){
                    System.out.println(roundCounter -1 + " round left. Wins: " + winCounter + " | Losses: " + lossCounter);
                } else {
                    System.out.println(roundCounter - 1 + " rounds left. Wins: " + winCounter + " | Losses: " + lossCounter);
                }
                //The following is a hacky way of clearing input stream in case
                //user typed something in, but didn't press enter in time
                System.out.print("PRESS ENTER");
                in.readLine();

                roundCounter--;
                continue;
            }

            //If data sent to server was HIGHER, LOWER or EQUALS, receive round result string and FinishRoundRequest
            String result = (String) ois.readObject();
            System.out.println(result);
            FinishRoundRequest finishRoundRequest = (FinishRoundRequest) ois.readObject();
            client.finishRound(finishRoundRequest);
            if (finishRoundRequest.isWin()) {
                winCounter++;
            } else {
                lossCounter++;
            }

            if (roundCounter == 2){
                System.out.println(roundCounter -1 + " round left. Wins: " + winCounter + " | Losses: " + lossCounter);
            } else {
                System.out.println(roundCounter - 1 + " rounds left. Wins: " + winCounter + " | Losses: " + lossCounter);
            }
            roundCounter--;
        }
        //Close resources
        ois.close();
        oos.close();
        socket.close();
        in.close();
    }

    @Override
    public void startRound(StartRoundRequest startRoundRequest) {
        System.out.println("Round started.");
    }

    @Override
    public void finishRound(FinishRoundRequest finishRoundRequest) {
        System.out.println("Round finished.");
    }
}

