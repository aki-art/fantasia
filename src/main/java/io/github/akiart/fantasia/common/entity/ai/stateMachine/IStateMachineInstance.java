package io.github.akiart.fantasia.common.entity.ai.stateMachine;

public interface IStateMachineInstance {

    public void initializeStates();
    public StateMachine<? extends IStateMachineInstance> getSM();


    public default void setState(State state) {
        getSM().setState(state);
    }

    public default void setState(String stateName) {
        getSM().setState(stateName);
    }
}
