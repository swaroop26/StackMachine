package com.test.nab.service;

import com.test.nab.util.StackMachineUtil.CMD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.test.nab.util.StackMachineUtil.*;

@Component
public class StackCommandRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(StackCommandRunner.class);

    @Autowired
    StackMachineService stackMachineService;

    @Override
    public void run(String... args) {
        log.info(WELCOME_MESSAGE);
        log.info(ENTER_COMMAND);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine().toLowerCase();
            if (input.equals(CMD.quit.name())) {
                break;
            }
            stackMachineService.processCommand(input);
        }
        log.info(EXIT_MESSAGE);
    }
}
