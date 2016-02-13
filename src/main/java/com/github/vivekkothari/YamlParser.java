package com.github.vivekkothari;

import org.apache.commons.lang3.text.StrLookup;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author vivek.kothari on 12/02/16.
 */
public class YamlParser {

    public static <T> T load(String yaml, Class<T> type) {
        Yaml yml = new Yaml();
        return yml.loadAs(yaml, type);
    }

    public static <T> T load(InputStream is, Class<T> type) {
        Yaml yml = new Yaml();
        return yml.loadAs(is, type);
    }

    public static <T> T loadWithEnvironmentResolution(InputStream is, Class<T> type) {
        return loadWithEnvironmentResolution(convertStreamToString(is), type);
    }

    public static <T> T loadWithEnvironmentResolution(String yaml, Class<T> type) {
        return loadWithEnvironmentResolution(yaml, type, new EnvironmentVariableLookup());
    }

    private static String convertStreamToString(InputStream is) {
        try (Scanner s = new Scanner(is).useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "";
        }
    }

    public static <T> T loadWithEnvironmentResolution(String yaml, Class<T> type, StrLookup lookup) {
        Yaml yml = new Yaml();
        StrSubstitutor substitutor = new StrSubstitutor(lookup);
        String replacedConfig = substitutor.replace(yaml);
        return yml.loadAs(replacedConfig, type);
    }

}
