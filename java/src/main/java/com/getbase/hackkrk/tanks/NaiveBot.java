package com.getbase.hackkrk.tanks;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getbase.hackkrk.tanks.api.Command;
import com.getbase.hackkrk.tanks.api.GameSetup;
import com.getbase.hackkrk.tanks.api.TanksClient;
import com.getbase.hackkrk.tanks.api.TurnResult;

public class NaiveBot {
    private static final Logger log = LoggerFactory.getLogger(NaiveBot.class);
    private Random rand = new Random();

    public volatile Command nextCommand = null;

    private boolean movedAlready = false;

    public static void main(String... args) throws Exception {
        new NaiveBot().run();
    }

    public void run() throws Exception {
//        TanksClient client = new TanksClient("http://10.12.202.144:9999/", "sandbox-1", "NobleGainsboroDinosaurGuanaco");
        TanksClient client = new TanksClient("http://10.12.202.141:9999/", "master", "NobleGainsboroDinosaurGuanaco");

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
            TurnResult result = client.submitMove(generateCommand());

            gameFinished = result.last;

        }
    }

    public Command generateCommand() {
        if (!movedAlready) {
            movedAlready = true;
            return Command.move(1000000);
        } else {
//            return Command.fire(rand.nextInt(50) - 45, rand.nextInt(100) + 30);
            return Command.fire(45, rand.nextInt(100) + 30);
        }
    }
}
