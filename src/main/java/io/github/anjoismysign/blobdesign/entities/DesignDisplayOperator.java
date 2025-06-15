package io.github.anjoismysign.blobdesign.entities;

import io.github.anjoismysign.bloblib.entities.display.DisplayOperator;

/**
 * Represents an object that can hold Display entities
 * using initial displayData and a transformation.
 */
public interface DesignDisplayOperator extends DisplayOperator {
    default DesignDisplayOperator getOperator() {
        return this;
    }
}
