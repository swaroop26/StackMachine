package com.test.nab.service

import com.test.nab.util.StackMachineUtil.CMD
import org.junit.jupiter.api.Assertions
import spock.lang.Specification

class UndoServiceSpec extends Specification {
    UndoService undoService;

    void setup(){
        undoService = new UndoService();
        undoService.initCommandMap();
    }

    void "Testing undo push and pop"(){
        Stack<Double> dataStack = new Stack<>();
        Stack<Map.Entry<CMD, LinkedList<Double>>> operationStack = new Stack<>();

        given: "'push 123' and 'push 345' commands entered"
        double value1 = 123.0
        double value2 = 345.0
        dataStack.push(value1)
        operationStack.push(new AbstractMap.SimpleEntry<>(CMD.push, new LinkedList<>(Arrays.asList(value1))))
        operationStack.push(new AbstractMap.SimpleEntry<>(CMD.push, new LinkedList<>(Arrays.asList(value2))))
        operationStack.push(new AbstractMap.SimpleEntry<>(CMD.pop, new LinkedList<>(Arrays.asList(value2))))

        when: "'undo' command in entered"
        undoService.processUndo(dataStack, operationStack);

        then: "Thank stack undo last pop adds back '345'"
        Assertions.assertEquals( 2, dataStack.size())
        Assertions.assertEquals( value2, dataStack.peek())
        and:

        when: "'undo' command in entered"
        undoService.processUndo(dataStack, operationStack);

        then: "Thank stack undo last push and removes last element '345'"
        Assertions.assertEquals( 1, dataStack.size())
        Assertions.assertEquals( value1, dataStack.peek())
    }

    void "Testing undo add"(){
        Stack<Double> dataStack = new Stack<>();
        Stack<Map.Entry<CMD, LinkedList<Double>>> operationStack = new Stack<>();

        given: "'push 123' and 'push 345' commands entered"
        double value1 = 10.0
        double value2 = 20.0
        double value3 = 200.0
        dataStack.push(value3)
        operationStack.push(new AbstractMap.SimpleEntry<>(CMD.push, new LinkedList<>(Arrays.asList(value1))))
        operationStack.push(new AbstractMap.SimpleEntry<>(CMD.push, new LinkedList<>(Arrays.asList(value2))))
        operationStack.push(new AbstractMap.SimpleEntry<>(CMD.add, new LinkedList<>(Arrays.asList(value2, value1))))

        when: "'undo' command in entered"
        undoService.processUndo(dataStack, operationStack);

        then: "Thank stack undo last push and removes last element '345'"
        Assertions.assertEquals( 2, dataStack.size())
        Assertions.assertEquals( value2, dataStack.pop())
        Assertions.assertEquals( value1, dataStack.pop())
    }

    void "Testing undo inverse"(){
        Stack<Double> dataStack = new Stack<>();
        Stack<Map.Entry<CMD, LinkedList<Double>>> operationStack = new Stack<>();

        given: "'push 123' commands entered"
        double value1 = 10.0
        double inv1 = -10.0
        dataStack.push(value1)
        operationStack.push(new AbstractMap.SimpleEntry<>(CMD.push, new LinkedList<>(Arrays.asList(value1))))

        when: "'inv' command in entered"
        operationStack.push(new AbstractMap.SimpleEntry<>(CMD.inv, new LinkedList<>(Arrays.asList(value1))))
        undoService.processUndo(dataStack, operationStack);

        then: "Thank stack undo last push and removes last element '345'"
        Assertions.assertEquals( 1, dataStack.size())
        Assertions.assertEquals( inv1, dataStack.peek())
    }
}
