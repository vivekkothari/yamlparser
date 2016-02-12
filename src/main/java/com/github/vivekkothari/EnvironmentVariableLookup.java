package com.github.vivekkothari;

import org.apache.commons.lang3.text.StrLookup;

/**
 * @author vivek.kothari on 12/02/16.
 */
public class EnvironmentVariableLookup
        extends StrLookup<Object> {
    private final boolean strict;

    /**
     * Create a new instance with strict behavior.
     */
    public EnvironmentVariableLookup() {
        this(true);
    }

    /**
     * Create a new instance.
     *
     * @param strict
     *         {@code true} if looking up undefined environment variables should throw a
     *         {@link RuntimeException}, {@code false} otherwise.
     *
     * @throws RuntimeException
     *         if the environment variable doesn't exist and strict behavior
     *         is enabled.
     */
    public EnvironmentVariableLookup(boolean strict) {
        this.strict = strict;
    }

    /**
     * {@inheritDoc}
     *
     * @throws RuntimeException
     *         if the environment variable doesn't exist and strict behavior
     *         is enabled.
     */
    @Override
    public String lookup(String key) {
        String value = System.getenv(key);

        if (value == null && strict) {
            throw new RuntimeException("The environment variable '" + key + "' is not defined; could not substitute the expression '${" + key + "}'.");
        }

        return value;
    }
}
