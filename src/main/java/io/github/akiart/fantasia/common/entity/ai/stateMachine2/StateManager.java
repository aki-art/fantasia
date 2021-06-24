package io.github.akiart.fantasia.common.entity.ai.stateMachine2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class StateManager {
    private EntityState currentState;
    private final Map<Integer, EntityState> states = new HashMap<>();
    int lastTick = 0;

    public <T extends EntityState> void add(T state, String name) {
        state.setName(name);
        states.put(name.hashCode(), state);
    }

    public EntityState getById(int id) {
        return states.get(id);
    }

    public void tick() {
        if(lastTick++ > currentState.getTickFrequency()) {
            checkTransitions();

            if(currentState.entity.level.isClientSide())
                currentState.clientTick();
            else
                currentState.serverTick();

            lastTick = 0;
        }
    }

    protected void checkTransitions() {
        EntityState next = currentState.getNextTransition();
        if(next != null) {
            setState(next);
        }
    }

    public void setState(int id) {
        setState(getById(id));
    }

    public EntityState getCurrentState() { return currentState; }

    public void setState(EntityState state) {
        if(state == null || currentState.equals(state)) return;

        currentState.exit();
        state.enter();

        currentState = state;
    }

    public void resumeState(EntityState state) {
        if(state == null) return;
        currentState = state;
    }

    public void resumeState(int id) {
        resumeState(getById(id));
    }
}
