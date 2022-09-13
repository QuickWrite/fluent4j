package net.quickwrite.fluent4j.util.bundle;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.exception.FluentParseException;

import java.util.List;

public interface FluentResource {
    /**
     * Returns all of the {@link FluentElement}s that
     * have been created at the parsing stage.
     *
     * @return A list of {@link FluentElement}s
     */
    List<FluentElement> getElements();

    /**
     * Checks if the FluentResource
     * has any exceptions itself.
     *
     * <p>
     * These exceptions are ones that have been thrown
     * at the parsing stage and they don't consist
     * of any runtime errors.
     *
     * @return If there are any exceptions
     */
    boolean hasExceptions();

    /**
     * Returns all of the exceptions that have been
     * thrown at the parsing stage in a {@link List}.
     *
     * @return The exceptions
     */
    List<FluentParseException> getExceptions();
}
