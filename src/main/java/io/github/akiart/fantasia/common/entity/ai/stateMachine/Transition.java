package io.github.akiart.fantasia.common.entity.ai.stateMachine;

import java.util.function.Predicate;

public class Transition{
    State target;
    Predicate<IStateMachineInstance> predicate;

    public Transition(State target, Predicate<IStateMachineInstance> predicate) {
        this.target = target;
        this.predicate = predicate;
    }

    public State getTarget() { return target; }
    public boolean test(IStateMachineInstance smi) { return predicate.test(smi); }
}
