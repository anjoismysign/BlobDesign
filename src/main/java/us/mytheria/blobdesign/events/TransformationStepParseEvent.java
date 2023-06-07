package us.mytheria.blobdesign.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.bloblib.entities.display.TransformationStep;

import java.util.List;

public class TransformationStepParseEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final String[] args;
    private final TransformationStep lastStep;
    private List<TransformationStep> steps;

    public TransformationStepParseEvent(String[] args, TransformationStep lastStep) {
        super(true);
        this.args = args;
        this.lastStep = lastStep;
        this.steps = null;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    /**
     * Using these args, you need to parse the transformation step.
     * Then return it using {@link #setSteps(List<TransformationStep)}
     * If no step is provided, it will be skipped.
     *
     * @return The args
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Will get the last step that has been parsed.
     * This is useful for step chaining.
     * If no step has been provided, initial transformation will be used
     * to get the last step.
     *
     * @return The last step
     */
    @NotNull
    public TransformationStep getLastStep() {
        return lastStep;
    }

    /**
     * Will get the steps that will be returned.
     * Null if no steps have been provided.
     *
     * @return The steps
     */
    @Nullable
    public List<TransformationStep> getSteps() {
        return steps;
    }

    /**
     * Will set the steps that will be used.
     *
     * @param steps The steps
     */
    public void setSteps(@Nullable List<TransformationStep> steps) {
        this.steps = steps;
    }

    /**
     * Will set the step that will be used.
     *
     * @param step The step
     */
    public void setStep(@Nullable TransformationStep step) {
        if (step == null)
            this.steps = null;
        else
            this.steps = List.of(step);
    }
}
