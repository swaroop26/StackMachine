package com.test.nab.service;

import com.test.nab.util.StackMachineUtil.CMD;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class UndoService {

    private static final Map<String, UndoHandler> undoMap = new HashMap<>();

    @PostConstruct
    void initCommandMap() {
        undoMap.put(CMD.push.name(), undoPush());
        undoMap.put(CMD.pop.name(), undoPop());
        undoMap.put(CMD.clear.name(), undoClear());
        undoMap.put(CMD.add.name(),  undoAddOrMul());
        undoMap.put(CMD.mul.name(),  undoAddOrMul());
        undoMap.put(CMD.neg.name(), undoNegateOrInverse());
        undoMap.put(CMD.inv.name(), undoNegateOrInverse());
    }

    public void processUndo(Stack<Double> dataStack, Stack<Map.Entry<CMD, LinkedList<Double>>> operationStack){
        Map.Entry<CMD, LinkedList<Double>> lastOperation = operationStack.pop();
        UndoHandler undoHandler = undoMap.get(lastOperation.getKey().name());
        undoHandler.undo(dataStack, lastOperation.getValue());
    }


    private UndoHandler undoPush() {
        return (commandStack, undoData) -> commandStack.pop();
    }
    private UndoHandler undoPop() {
        return (commandStack, undoData) -> commandStack.push(undoData.get(0));
    }

    private UndoHandler undoClear() {
        return (commandStack, undoData) ->  undoData.forEach(commandStack::push);
    }

    private UndoHandler undoAddOrMul() {
        return (commandStack, undoData) -> {
            commandStack.pop();
            Iterator<Double> iterator = undoData.descendingIterator();
            while (iterator.hasNext()){
                commandStack.push(iterator.next());
            }
        };
    }

    private UndoHandler undoNegateOrInverse() {
        return (commandStack, undoData) -> {
            commandStack.pop();
            commandStack.push(undoData.get(0));
        };
    }
}
