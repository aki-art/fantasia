package io.github.akiart.fantasia.common.entity.ai.stateMachine;

import io.github.akiart.fantasia.Fantasia;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StateMachine<T extends IStateMachineInstance> {

    private State currentState;
    private State defaultState;
    private final List<State> states = new ArrayList<>();
    T smi;

    public StateMachine(T smi) {
        this.smi = smi;
    }

    private Transition getNextTransition() {

        for (Transition item : currentState.transitions) {
            if (item.test(smi)) return item;
        }

        return null;
    }

    private boolean running = false;

    public void start() {
        running = true;
        if (currentState == null && defaultState != null) setState(defaultState);
    }

    public void setDefaultState(State state) {
        defaultState = state;
    }

    public void stop() {
        running = false;
    }

    public void tick() {
        if (!running) return;

        Transition transition = getNextTransition();
        if (transition != null)
            setState(transition.getTarget());
        Fantasia.LOGGER.info("sm tick");
        executeActions(currentState.tickActions);
    }

    public State create(String stateName) {
        return add(new State(stateName));
    }

    public State add(State state) {
        states.add(state);
        return state;
    }

    public void setState(State state) {
        if (state == null) { return; }

        Fantasia.LOGGER.info("setting state: " + state.getName());
        if (state == currentState) return;

        if (currentState != null) {
            Fantasia.LOGGER.info("executing exit: " + currentState.getName());
            executeActions(currentState.exitActions);
        }

        currentState = state;

        Fantasia.LOGGER.info("executing enter: " + currentState.getName());
        executeActions(currentState.enterActions);
    }

    private void executeActions(List<Runnable> actions) {
        if (actions == null || !running) return;
        for (Runnable action : actions) {
            action.run();
        }
    }

    public State getState() {
        return currentState;
    }

    public void setState(String stateName) {
        setState(states.stream()
                .filter(s -> s.getName().equals(stateName))
                .findFirst()
                .orElse(null));
    }
}
