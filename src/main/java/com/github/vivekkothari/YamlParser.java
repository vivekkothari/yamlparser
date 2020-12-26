package com.github.vivekkothari;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.yaml.snakeyaml.Yaml;

/**
 * @author vivek.kothari on 12/02/16.
 */
public class YamlParser {

  public static <T> T load(final File yaml, final Class<T> type) {
    try {
      return load(convertFileToString(yaml), type);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File with path " + yaml.getAbsolutePath() + " does not exists",
          e);
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
      throw new RuntimeException("File with path " + yaml.getAbsolutePath() + " does not exists",
          e);
    }
  }

  public static <T> T loadWithEnvironmentResolution(final String yaml, final Class<T> type) {
    return loadWithStringLookup(yaml, type,
        StringLookupFactory.INSTANCE.environmentVariableStringLookup());
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

  public static <T> T loadWithStringLookup(final String yaml, final Class<T> type,
      final StringLookup lookup) {
    final Yaml yml = new Yaml();
    var substitutor = new StringSubstitutor(lookup);
    String replacedConfig = substitutor.replace(yaml);
    return yml.loadAs(replacedConfig, type);
  }

}
