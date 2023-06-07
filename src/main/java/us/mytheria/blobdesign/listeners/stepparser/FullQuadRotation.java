package us.mytheria.blobdesign.listeners.stepparser;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.events.TransformationStepParseEvent;
import us.mytheria.bloblib.entities.display.RotationAxis;
import us.mytheria.bloblib.entities.display.TransformationStep;
import us.mytheria.bloblib.entities.display.TransformationStepFactory;

public class FullQuadRotation extends StepParser implements Listener {
    public FullQuadRotation(BlobDesign plugin) {
        super(plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onParse(TransformationStepParseEvent event) {
        String[] args = event.getArgs();
        String type = args[0];
        if (!type.equalsIgnoreCase("fullquadrotation"))
            return;
        if (args.length != 3) {
            plugin.getAnjoLogger().singleError("Invalid arguments for FullQuadRotation step");
            return;
        }
        String readAxis = args[1];
        String readDuration = args[2];
        RotationAxis axis;
        int duration;
        try {
            axis = RotationAxis.valueOf(readAxis.toUpperCase());
            duration = Integer.parseInt(readDuration);
        } catch (Exception e) {
            plugin.getAnjoLogger().singleError("Invalid arguments for FullQuadRotation step");
            return;
        }
        TransformationStep lastStep = event.getLastStep();
        event.setSteps(new TransformationStepFactory(lastStep)
                .fullQuadRotation(axis, duration));

    }
}
