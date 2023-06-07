package us.mytheria.blobdesign.listeners.stepparser;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.events.TransformationStepParseEvent;
import us.mytheria.bloblib.entities.display.RotationAxis;
import us.mytheria.bloblib.entities.display.TransformationStep;
import us.mytheria.bloblib.entities.display.TransformationStepFactory;

public class FullTriRotation extends StepParser implements Listener {
    public FullTriRotation(BlobDesign plugin) {
        super(plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onParse(TransformationStepParseEvent event) {
        String[] args = event.getArgs();
        String type = args[0];
        if (!type.equalsIgnoreCase("fulltrirotation"))
            return;
        if (args.length != 3) {
            plugin.getAnjoLogger().singleError("Invalid arguments for FullTriRotation step");
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
            plugin.getAnjoLogger().singleError("Invalid arguments for FullTriRotation step");
            return;
        }
        TransformationStep lastStep = event.getLastStep();
        event.setSteps(new TransformationStepFactory(lastStep)
                .fullTriRotation(axis, duration));

    }
}
