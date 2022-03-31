package com.test.nab.service;

import com.test.nab.util.StackMachineUtil;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public interface UndoHandler {
    void undo(Stack<Double> commandStack, LinkedList<Double> undoData);
}
