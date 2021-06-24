package io.github.akiart.fantasia.common.entity.ai.stateMachine;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class State {

    private final String name;

    public List<Runnable> enterActions;
    public List<Runnable> tickActions;
    public List<Runnable> exitActions;

    public List<Transition> transitions;

    private State parent;

    boolean hasAnim;
    String animName;
    boolean animLoop;

    public State(String name) {
        this.name = name;
        transitions = new ArrayList<>();
    }

    public State subState(State parent) {
        if (parent.enterActions != null) enterActions = new ArrayList<>(parent.enterActions);
        if (parent.tickActions != null) tickActions = new ArrayList<>(parent.tickActions);
        if (parent.exitActions != null) exitActions = new ArrayList<>(parent.exitActions);
        if (parent.transitions != null) transitions = new ArrayList<>(parent.transitions);
        this.parent = parent;
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean is(State state) {
        return this.equals(state) || (parent != null && parent.is(state));
    }

    public State onEnter(Runnable consumer) {
        if (enterActions == null) enterActions = new ArrayList<>();
        enterActions.add(consumer);
        return this;
    }

    public State onTick(Runnable consumer) {
        if (tickActions == null) tickActions = new ArrayList<>();
        tickActions.add(consumer);
        return this;
    }

    public State onExit(Runnable consumer) {
        if (exitActions == null) exitActions = new ArrayList<>();
        exitActions.add(consumer);
        return this;
    }

    public State playAnim(String name, boolean loop) {
        this.hasAnim = true;
        this.animName = name;
        this.animLoop = loop;

        return this;
    }

    public void setAnimation(AnimationEvent<? extends IAnimatable> event, AnimationBuilder builder) {
        event.getController().setAnimation(builder.addAnimation(animName, animLoop));
    }

    public void setAnimation(AnimationEvent<? extends IAnimatable> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(animName, animLoop));
    }

    public State transition(State target, Predicate<IStateMachineInstance> predicate) {
        if (transitions == null) transitions = new ArrayList<>();
        transitions.add(new Transition(target, predicate));
        return this;
    }
}
