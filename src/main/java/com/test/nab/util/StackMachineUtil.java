package com.test.nab.util;

import java.util.LinkedList;
import java.util.Stack;

public class StackMachineUtil {

    public static String WELCOME_MESSAGE = "*** Welcome to Stack Machine ***";
    public static String ENTER_COMMAND = "Please enter command:";
    public static String EXIT_MESSAGE ="*** Thanks for using Stack Machine. Quitting now. ***";
    public static String PRINT_MSG = "Stack Elements : {}";

    public static String UNKNOWN_CMD_ERROR = "Can not recognise the command : '{}' . Enter 'Help' to get the list of commands.";
    public static String POP_ERROR = "Stack is empty. Nothing to pop.";
    public static String CLEAR_ERROR = "Stack is empty. Nothing to clear.";
    public static String ADD_ERROR = "Not enough elements in stack to add.";
    public static String MULTIPLY_ERROR = "Not enough elements in stack to multiply.";
    public static String NEGATE_ERROR = "Stack is empty. Cannot negate.";
    public static String INVERT_ERROR = "Stack is empty. Cannot invert.";
    public static String UNDO_ERROR = "Nothing to undo.";

    public static String  HELP_TEXT = new StringBuilder(" Stack Machine supports below list of commands ")
            .append("\n").append("\n")
            .append("PUSH <xyz> or <xyz>   - Pushes the numeric value <xyz> to the top of the stack ( <xyz> is a valid decimal number ).").append("\n")
            .append("POP     - Removes the top element from the stack.").append("\n")
            .append("CLEAR   - Removes all elements from the stack.").append("\n")
            .append("ADD     - Adds the top 2 elements on the stack and pushes the result back to the stack.").append("\n")
            .append("MUL     - Multiplies the top 2 elements on the stack and pushes the result back to the stack.").append("\n")
            .append("NEG     - Negates the top element on the stack and pushes the result back to the stack.").append("\n")
            .append("INV     - Inverts the top element on the stack and pushes the result back to the stack.").append("\n")
            .append("UNDO    - The last instruction is undone leaving the stack in the same state as before that instruction.").append("\n")
            .append("PRINT   - Prints all elements that are currently on the stack.").append("\n")
            .append("QUIT    - Exits the program.").append("\n")
            .append("\n").append("\n").toString()
            ;

    public static boolean isDouble(String doubleSting) {
        try {
            Double.parseDouble(doubleSting);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String input) {
        return input == null || input.length() == 0;
    }

    public static LinkedList<Double> copyToArray(Stack<Double> stack) {
        return new LinkedList<>(stack);
    }

    public static enum CMD {
        push, pop, clear, add, mul, neg, inv, undo, print, quit, help;
    }
}
