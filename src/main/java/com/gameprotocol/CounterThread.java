package com.gameprotocol;

public class CounterThread implements Runnable{

    public volatile boolean keepRunning = true;
    @Override
    public void run() {
        int secondsLeft = 10;
        while (keepRunning && secondsLeft > 0) {
            System.out.print("\rTime left: " + secondsLeft + " ");
            secondsLeft--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
