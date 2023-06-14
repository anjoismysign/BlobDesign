package us.mytheria.blobdesign.entities;

import me.anjoismysign.anjo.entities.Uber;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Transformation;
import us.mytheria.blobdesign.events.TransformationStepParseEvent;
import us.mytheria.bloblib.entities.display.TransformationStep;

import java.util.ArrayList;
import java.util.List;

public class TransformationStepReader {
    public static List<TransformationStep> read(ConfigurationSection section,
                                                Transformation transformation,
                                                float shadowRadius,
                                                float shadowStrength) {
        if (!section.isList("TransformationSteps"))
            throw new IllegalArgumentException("No TransformationSteps list found");
        List<TransformationStep> steps = new ArrayList<>();
        Uber<TransformationStep> lastStep = Uber.drive(
                new TransformationStep(transformation,
                        shadowRadius,
                        shadowStrength,
                        20));
        List<String> list = section.getStringList("TransformationSteps");
        list.forEach(string -> {
            String[] split = string.split("-");
            if (split.length == 1)
                return;
            TransformationStepParseEvent event =
                    new TransformationStepParseEvent(split, lastStep.thanks());
            Bukkit.getPluginManager().callEvent(event);
            List<TransformationStep> eventSteps = event.getSteps();
            if (eventSteps == null)
                return;
            steps.addAll(eventSteps);
            lastStep.talk(eventSteps.get(eventSteps.size() - 1));
        });
        return steps;
    }
}
