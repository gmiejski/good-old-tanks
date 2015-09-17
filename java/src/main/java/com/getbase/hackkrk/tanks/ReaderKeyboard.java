package com.getbase.hackkrk.tanks;

import com.getbase.hackkrk.tanks.api.Command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

/**
 * Created by grzegorz.miejski on 17/09/15.
 */
public class ReaderKeyboard  implements Runnable{


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String nextMove = scanner.nextLine();
            if (nextMove.contains("j")) {
                NaiveBot.nextCommand = Command.move(-50);
            } else if (nextMove.contains("k")) {
                NaiveBot.nextCommand = Command.move(50);
            }

        }
    }
}
