package io.github.anjoismysign.blobdesign.entities;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DisplayController<T extends Display> {
    private final T display;

    private static final NamespacedKey TRANSLATION_SCALE_FACTOR_KEY = new NamespacedKey(Bukkit.getPluginManager()
            .getPlugin("BlobDesign"), "translation_scale_factor");

    public static NamespacedKey getTranslationScaleFactorKey() {
        return TRANSLATION_SCALE_FACTOR_KEY;
    }

    public static <T extends Display> DisplayController<T> of(T display) {
        return new DisplayController<>(display);
    }

    private DisplayController(T display) {
        this.display = display;
    }

    public T call() {
        return display;
    }

    public void uniformScale(float scale) {
        T call = call();
        Transformation transformation = call.getTransformation();
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                transformation.getLeftRotation(),
                new Vector3f(scale),
                transformation.getRightRotation()));
    }

    public void scaleX(float x) {
        T call = call();
        Transformation transformation = call.getTransformation();
        Vector3f scale = transformation.getScale();
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                transformation.getLeftRotation(),
                new Vector3f(x, scale.y, scale.z),
                transformation.getRightRotation()));
    }

    public void scaleY(float y) {
        T call = call();
        Transformation transformation = call.getTransformation();
        Vector3f scale = transformation.getScale();
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                transformation.getLeftRotation(),
                new Vector3f(scale.x, y, scale.z),
                transformation.getRightRotation()));
    }

    public void scaleZ(float z) {
        T call = call();
        Transformation transformation = call.getTransformation();
        Vector3f scale = transformation.getScale();
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                transformation.getLeftRotation(),
                new Vector3f(scale.x, scale.y, z),
                transformation.getRightRotation()));
    }

    public void leftX(float degrees) {
        T call = call();
        degrees = (float) Math.toRadians(degrees);
        Transformation transformation = call.getTransformation();
        Quaternionf rotation = transformation.getLeftRotation();
        Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                new Quaternionf().rotationXYZ(degrees, vector3f.y, vector3f.z),
                transformation.getScale(),
                transformation.getRightRotation()));
    }

    public void leftY(float degrees) {
        T call = call();
        degrees = (float) Math.toRadians(degrees);
        Transformation transformation = call.getTransformation();
        Quaternionf rotation = transformation.getLeftRotation();
        Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                new Quaternionf().rotationXYZ(vector3f.x, degrees, vector3f.z),
                transformation.getScale(),
                transformation.getRightRotation()));
    }

    public void leftZ(float degrees) {
        T call = call();
        degrees = (float) Math.toRadians(degrees);
        Transformation transformation = call.getTransformation();
        Quaternionf rotation = transformation.getLeftRotation();
        Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                new Quaternionf().rotationXYZ(vector3f.x, vector3f.y, degrees),
                transformation.getScale(),
                transformation.getRightRotation()));
    }

    public void rightX(float degrees) {
        T call = call();
        degrees = (float) Math.toRadians(degrees);
        Transformation transformation = call.getTransformation();
        Quaternionf rotation = transformation.getRightRotation();
        Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                transformation.getLeftRotation(),
                transformation.getScale(),
                new Quaternionf().rotationXYZ(degrees, vector3f.y, vector3f.z)));
    }

    public void rightY(float degrees) {
        T call = call();
        degrees = (float) Math.toRadians(degrees);
        Transformation transformation = call.getTransformation();
        Quaternionf rotation = transformation.getRightRotation();
        Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                transformation.getLeftRotation(),
                transformation.getScale(),
                new Quaternionf().rotationXYZ(vector3f.x, degrees, vector3f.z)));
    }

    public void rightZ(float degrees) {
        T call = call();
        degrees = (float) Math.toRadians(degrees);
        Transformation transformation = call.getTransformation();
        Quaternionf rotation = transformation.getRightRotation();
        Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
        call.setTransformation(new Transformation(
                transformation.getTranslation(),
                transformation.getLeftRotation(),
                transformation.getScale(),
                new Quaternionf().rotationXYZ(vector3f.x, vector3f.y, degrees)));
    }

    public Float translationScaleFactor() {
        T call = call();
        PersistentDataContainer container = call.getPersistentDataContainer();
        if (!container.has(getTranslationScaleFactorKey(), PersistentDataType.FLOAT))
            return 1.0f;
        return container.get(getTranslationScaleFactorKey(), PersistentDataType.FLOAT);
    }

    public void translationScaleFactor(float scaleFactor) {
        T call = call();
        PersistentDataContainer container = call.getPersistentDataContainer();
        container.set(getTranslationScaleFactorKey(), PersistentDataType.FLOAT, scaleFactor);
    }

    public void uniformTranslation(float translation) {
        T call = call();
        call.setTransformation(new Transformation(
                new Vector3f(translation),
                call.getTransformation().getLeftRotation(),
                call.getTransformation().getScale(),
                call.getTransformation().getRightRotation()));
    }

    private Vector3f translation() {
        return call().getTransformation().getTranslation();
    }

    public Float translationX() {
        return translation().x / translationScaleFactor();
    }

    public Float translationY() {
        return translation().y / translationScaleFactor();
    }

    public Float translationZ() {
        return translation().z / translationScaleFactor();
    }

    public void translationX(float x) {
        T call = call();
        Transformation transformation = call.getTransformation();
        Vector3f Translation = transformation.getTranslation();
        x *= translationScaleFactor();
        call.setTransformation(new Transformation(
                new Vector3f(x, Translation.y, Translation.z),
                transformation.getLeftRotation(),
                transformation.getScale(),
                transformation.getRightRotation()));
    }

    public void translationY(float y) {
        T call = call();
        Transformation transformation = call.getTransformation();
        Vector3f Translation = transformation.getTranslation();
        y *= translationScaleFactor();
        call.setTransformation(new Transformation(
                new Vector3f(Translation.x, y, Translation.z),
                transformation.getLeftRotation(),
                transformation.getScale(),
                transformation.getRightRotation()));
    }

    public void translationZ(float z) {
        T call = call();
        Transformation transformation = call.getTransformation();
        Vector3f Translation = transformation.getTranslation();
        z *= translationScaleFactor();
        call.setTransformation(new Transformation(
                new Vector3f(Translation.x, Translation.y, z),
                transformation.getLeftRotation(),
                transformation.getScale(),
                transformation.getRightRotation()));
    }
}
