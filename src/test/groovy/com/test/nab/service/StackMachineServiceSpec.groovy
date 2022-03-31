package com.test.nab.service

import org.junit.jupiter.api.Assertions
import spock.lang.Specification

class StackMachineServiceSpec extends Specification {
    StackMachineService stackMachineService;

    void setup(){
        stackMachineService = new StackMachineService();
        stackMachineService.initCommandMap();
    }

    void "Testing pushing elements and add scenario"(){
        given: "'push 123' command entered"
        stackMachineService.processCommand("push 123");

        and: "'push 334' command entered"
        stackMachineService.processCommand("push 334");

        when: "'add' command entered"
        stackMachineService.processCommand("add")

        then: "top 2 elements in the stack will be added"
        Stack<Double> dataStack = stackMachineService.getDataStack();
        Assertions.assertEquals( 1, dataStack.size())
        Assertions.assertEquals( 457.0, dataStack.peek())
    }

    void "Testing clear command "(){
        given: "'push 123' command entered"
        stackMachineService.processCommand("push 123");

        and: "'push 334' command entered"
        stackMachineService.processCommand("push 334");

        when: "'clear' command entered"
        stackMachineService.processCommand("clear")

        then: "stack will be entered"
        Stack<Double> dataStack = stackMachineService.getDataStack();
        Assertions.assertEquals( 0, dataStack.size())
    }

    void "Testing neg, inv and mul command "(){
        given: "'push 10' command entered"
        stackMachineService.processCommand("push 10");

        when: "'neg' command entered"
        stackMachineService.processCommand("neg")

        then: "top element in the stack will be negated"
        Stack<Double> dataStack = stackMachineService.getDataStack();
        Assertions.assertEquals( -10.0, dataStack.peek())

        and:
        when: "'inv' command entered"
        stackMachineService.processCommand("inv")

        then: "top element in the stack will be negated"
        Stack<Double> dataStack1 = stackMachineService.getDataStack();
        Assertions.assertEquals( -0.1, dataStack1.peek())

        and:
        when: "'push 123' command entered"
        stackMachineService.processCommand("push 123")
        and:"'mul' command entered"
        stackMachineService.processCommand("mul")

        then: "top element in the stack will be negated"
        Stack<Double> dataStack2 = stackMachineService.getDataStack();
        Assertions.assertEquals( -12.3, dataStack2.peek())
    }

    void "Testing invalid commands "(){
        given: "Stack is empty"

        when: "'pop' command entered"
        stackMachineService.processCommand("pop")

        then: "Then the command will be ignored"
        Assertions.assertEquals( 0, stackMachineService.getDataStack().size())

        when: "'mul' command entered"
        stackMachineService.processCommand("mul")

        then: "Then the command will be ignored"
        Assertions.assertEquals( 0, stackMachineService.getDataStack().size())

        when: "'neg' command entered"
        stackMachineService.processCommand("neg")

        then: "Then the command will be ignored"
        Assertions.assertEquals( 0, stackMachineService.getDataStack().size())

        when: "'inv' command entered"
        stackMachineService.processCommand("inv")

        then: "Then the command will be ignored"
        Assertions.assertEquals( 0, stackMachineService.getDataStack().size())

        when: "'add' command entered"
        stackMachineService.processCommand("add")

        then: "Then the command will be ignored"
        Assertions.assertEquals( 0, stackMachineService.getDataStack().size())

        when: "'wrong' command entered"
        stackMachineService.processCommand("wrong")

        then: "Then the command will be ignored"
        Assertions.assertEquals( 0, stackMachineService.getDataStack().size())

    }

}
