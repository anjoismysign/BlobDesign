package io.github.anjoismysign.blobdesign.listeners.stepparser;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.events.TransformationStepParseEvent;
import io.github.anjoismysign.blobdesign.listeners.DesignListener;
import io.github.anjoismysign.bloblib.entities.display.RotationAxis;
import io.github.anjoismysign.bloblib.entities.display.TransformationStep;
import io.github.anjoismysign.bloblib.entities.display.TransformationStepFactory;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import java.util.Locale;

public class FullQuadRotation extends DesignListener {
    public FullQuadRotation(DesignManagerDirector director) {
        super(director);
        Bukkit.getPluginManager().registerEvents(this, getPlugin());
    }

    @EventHandler
    public void onParse(TransformationStepParseEvent event) {
        String[] args = event.getArgs();
        int length = args.length;
        if (length != 3) {
            getPlugin().getAnjoLogger().singleError("Invalid arguments for FullQuadRotation step: \nlength: " + length);
            return;
        }
        String type = args[0];
        if (!type.equalsIgnoreCase("fullquadrotation"))
            return;
        String readAxis = args[1];
        String readDuration = args[2];
        RotationAxis axis;
        int duration;
        try {
            axis = RotationAxis.valueOf(readAxis.toUpperCase(Locale.ROOT));
            duration = Integer.parseInt(readDuration);
        } catch (Exception e) {
            getPlugin().getAnjoLogger().singleError("Invalid arguments for FullQuadRotation step: \nreadAxis:" +
                    readAxis + "\nreadDuration: "
                    + readDuration);
            return;
        }
        TransformationStep lastStep = event.getLastStep();
        event.setSteps(new TransformationStepFactory(lastStep)
                .fullQuadRotation(axis, duration));

    }
}
