package com.test.nab.service;

import com.test.nab.util.StackMachineUtil.CMD;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public interface CommandHandler {
    void handle(Stack<Double> dataStack, Stack<Map.Entry<CMD, LinkedList<Double>>> operationStack);
}
