package com.getbase.hackkrk.tanks;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getbase.hackkrk.tanks.api.Command;
import com.getbase.hackkrk.tanks.api.GameSetup;
import com.getbase.hackkrk.tanks.api.TanksClient;
import com.getbase.hackkrk.tanks.api.TurnResult;

public class NaiveBot {
    private static final Logger log = LoggerFactory.getLogger(NaiveBot.class);
    private Random rand = new Random();

    public static volatile Command nextCommand = null;

    private boolean movedAlready = false;

    public static volatile int shotDirection = 1;

    public static void main(String... args) throws Exception {
        new NaiveBot().run();
    }

    public void run() throws Exception {
        TanksClient client = new TanksClient("http://10.12.202.144:9999/", "sandbox-1", "NobleGainsboroDinosaurGuanaco");
//        TanksClient client = new TanksClient("http://10.12.202.141:9999/", "master", "NobleGainsboroDinosaurGuanaco");

        ReaderKeyboard readerKeyboard = new ReaderKeyboard();

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(readerKeyboard);

        while (true) {
            log.info("Waiting for the next game...");
            GameSetup gameSetup = client.getMyGameSetup();
            log.info("Playing {}", gameSetup);

            playGame(client);
        }
    }

    private void playGame(TanksClient client) {
        boolean gameFinished = false;
        while (!gameFinished) {

            Command thisTurnCommand;
            if (nextCommand != null) {
                thisTurnCommand = nextCommand;
                nextCommand = null;
            } else {
                thisTurnCommand = fireShot();
            }
            TurnResult result = client.submitMove(thisTurnCommand);

            gameFinished = result.last;

        }
    }

    private Command fireShot() {
        return Command.fire(shotDirection, rand.nextInt(100) + 30);
    }
}
