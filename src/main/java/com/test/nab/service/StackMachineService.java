package com.test.nab.service;

import com.test.nab.util.StackMachineUtil;
import com.test.nab.util.StackMachineUtil.CMD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.test.nab.util.StackMachineUtil.*;

@Service
public class StackMachineService {

    @Autowired
    UndoService undoService;

    private static final Logger log = LoggerFactory.getLogger(StackMachineService.class);

    private static final Stack<Double> dataStack = new Stack<>();
    private static final Stack<Map.Entry<CMD, LinkedList<Double>>> operationStack = new Stack<>();
    private static final Map<String, CommandHandler> commandMap = new HashMap<>();

    @PostConstruct
    void initCommandMap() {
        commandMap.put(CMD.pop.name(), popHandler());
        commandMap.put(CMD.clear.name(), clearHandler());
        commandMap.put(CMD.add.name(), addHandler());
        commandMap.put(CMD.mul.name(), multiplyHandler());
        commandMap.put(CMD.neg.name(), negateHandler());
        commandMap.put(CMD.inv.name(), inverseHandler());
        commandMap.put(CMD.undo.name(), undoHandler());
        commandMap.put(CMD.print.name(), printHandler());
        commandMap.put(CMD.help.name(), helpHandler());
    }

    public void processCommand(String command) {
        CommandHandler commandHandler = null;

        if (commandMap.containsKey(command)) {
            commandHandler = commandMap.get(command);
        } else if (isPushCommand(command)) {
            commandHandler = pushHandler(command);
        }

        if (commandHandler != null) {
            commandHandler.handle(dataStack, operationStack);
        } else {
            log.error(UNKNOWN_CMD_ERROR, command);
            log.info(ENTER_COMMAND);
        }

    }

    private boolean isPushCommand(String command) {
        return isDouble(command)
                || (command.startsWith(CMD.push.name())
                && command.split(" ").length == 2 && !isEmpty(command.split(" ")[1]) && isDouble(command.split(" ")[1]));
    }


    private CommandHandler pushHandler(String command) {
        return (dataStack, operationStack) -> {
            double data = isDouble(command) ? Double.parseDouble(command) : Double.parseDouble(command.split(" ")[1]);
            dataStack.push(data);
            operationStack.push(new AbstractMap.SimpleEntry<>(CMD.push, new LinkedList<>(Arrays.asList(data))));
        };
    }

    private CommandHandler popHandler() {
        return (dataStack, operationStack) -> {
            if (dataStack.isEmpty()) {
                log.error(POP_ERROR);
            } else {
                double data = dataStack.pop();
                operationStack.push(new AbstractMap.SimpleEntry<>(CMD.pop, new LinkedList<>(Arrays.asList(data))));
            }
        };
    }

    private CommandHandler clearHandler() {
        return (dataStack, operationStack) -> {
            if (dataStack.isEmpty()) {
                log.error(CLEAR_ERROR);
            } else {
                operationStack.push(new AbstractMap.SimpleEntry<>(CMD.clear, StackMachineUtil.copyToArray(dataStack)));
                dataStack.clear();
            }
        };
    }

    private CommandHandler addHandler() {
        return (dataStack, operationStack) -> {
            if (dataStack.size() < 2) {
                log.error(ADD_ERROR);
            } else {
                double element1 = dataStack.pop();
                double element2 = dataStack.pop();
                dataStack.push(element1 + element2);
                operationStack.push(new AbstractMap.SimpleEntry<>(CMD.add, new LinkedList<>(Arrays.asList(element1, element2))));
            }
        };
    }

    private CommandHandler multiplyHandler() {
        return (dataStack, operationStack) -> {
            if (dataStack.size() < 2) {
                log.error(MULTIPLY_ERROR);
            } else {
                double element1 = dataStack.pop();
                double element2 = dataStack.pop();
                dataStack.push(element1 * element2);
                operationStack.push(new AbstractMap.SimpleEntry<>(CMD.mul, new LinkedList<>(Arrays.asList(element1, element2))));
            }
        };

    }

    private CommandHandler negateHandler() {
        return (dataStack, operationStack) -> {
            if (dataStack.isEmpty()) {
                log.error(NEGATE_ERROR);
            } else {
                double element = dataStack.pop();
                dataStack.push(element * -1);
                operationStack.push(new AbstractMap.SimpleEntry<>(CMD.neg, new LinkedList<>(Arrays.asList(element))));
            }
        };

    }

    private CommandHandler inverseHandler() {
        return (dataStack, operationStack) -> {
            if (dataStack.isEmpty()) {
                log.error(INVERT_ERROR);
            } else {
                double element = dataStack.pop();
                dataStack.push(1 / element);
                operationStack.push(new AbstractMap.SimpleEntry<>(CMD.inv, new LinkedList<>(Arrays.asList(element))));
            }
        };
    }

    private CommandHandler printHandler() {
        return (dataStack, operationStack) -> log.info( PRINT_MSG, dataStack);
    }

    private CommandHandler helpHandler() {
        return (dataStack, operationStack) -> {
            log.info(HELP_TEXT);
            log.info(ENTER_COMMAND);
        };
    }

    private CommandHandler undoHandler() {
        return (dataStack, operationStack) -> {
            if(operationStack.isEmpty()){
                log.error(UNDO_ERROR);
            }else{
                undoService.processUndo(dataStack, operationStack);
            }
        };
    }

    public Stack<Double> getDataStack() {
        return dataStack;
    }
}
