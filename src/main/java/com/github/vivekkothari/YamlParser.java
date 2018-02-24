package com.github.vivekkothari;

import org.apache.commons.text.StrLookup;
import org.apache.commons.text.StrSubstitutor;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author vivek.kothari on 12/02/16.
 */
public class YamlParser {

    public static <T> T load(final File yaml, final Class<T> type) {
        try {
            return load(convertFileToString(yaml), type);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File with path " + yaml.getAbsolutePath() + " does not exists", e);
        }
    }

    public static <T> T load(final String yaml, final Class<T> type) {
        final Yaml yml = new Yaml();
        return yml.loadAs(yaml, type);
    }

    public static <T> T load(final InputStream is, final Class<T> type) {
        final Yaml yml = new Yaml();
        return yml.loadAs(is, type);
    }

    public static <T> T loadWithEnvironmentResolution(final File yaml, final Class<T> type) {
        try {
            return loadWithEnvironmentResolution(convertFileToString(yaml), type);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File with path " + yaml.getAbsolutePath() + " does not exists", e);
        }
    }

    public static <T> T loadWithEnvironmentResolution(final String yaml, final Class<T> type) {
        return loadWithEnvironmentResolution(yaml, type, new EnvironmentVariableLookup());
    }

    public static <T> T loadWithEnvironmentResolution(final InputStream is, final Class<T> type) {
        return loadWithEnvironmentResolution(convertStreamToString(is), type);
    }

    private static String convertStreamToString(final InputStream is) {
        try (final Scanner s = new Scanner(is).useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "";
        }
    }

    private static String convertFileToString(final File file) throws FileNotFoundException {
        try (final Scanner s = new Scanner(file).useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "";
        }
    }

    public static <T> T loadWithEnvironmentResolution(final String yaml, final Class<T> type, final StrLookup lookup) {
        final Yaml yml = new Yaml();
        StrSubstitutor substitutor = new StrSubstitutor(lookup);
        String replacedConfig = substitutor.replace(yaml);
        return yml.loadAs(replacedConfig, type);
    }

}
