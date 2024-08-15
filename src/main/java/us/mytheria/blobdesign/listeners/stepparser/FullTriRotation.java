package us.mytheria.blobdesign.listeners.stepparser;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.events.TransformationStepParseEvent;
import us.mytheria.blobdesign.listeners.DesignListener;
import us.mytheria.bloblib.entities.display.RotationAxis;
import us.mytheria.bloblib.entities.display.TransformationStep;
import us.mytheria.bloblib.entities.display.TransformationStepFactory;

import java.util.Locale;

public class FullTriRotation extends DesignListener {
    public FullTriRotation(DesignManagerDirector director) {
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
        if (!type.equalsIgnoreCase("fulltrirotation"))
            return;
        String readAxis = args[1];
        String readDuration = args[2];
        RotationAxis axis;
        int duration;
        try {
            axis = RotationAxis.valueOf(readAxis.toUpperCase(Locale.ROOT));
            duration = Integer.parseInt(readDuration);
        } catch (Exception e) {
            getPlugin().getAnjoLogger().singleError("Invalid arguments for FullTriRotation step");
            return;
        }
        TransformationStep lastStep = event.getLastStep();
        event.setSteps(new TransformationStepFactory(lastStep)
                .fullTriRotation(axis, duration));

    }
}
