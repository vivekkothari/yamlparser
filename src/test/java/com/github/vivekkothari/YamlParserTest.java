package com.github.vivekkothari;

import com.google.common.truth.Truth;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

public class YamlParserTest {

  @Rule
  public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

  @Test
  public void test() {
    var config = YamlParser
        .load(YamlParserTest.class.getResourceAsStream("/test.yaml"), Config.class);
    Truth.assertThat(config.key1).isEqualTo("value");
    Truth.assertThat(config.key2).isEqualTo("${ENV_VALUE}");
  }

  @Test
  public void testEnvResolution() {
    environmentVariables.set("ENV_VALUE", "value2");
    var config = YamlParser
        .loadWithEnvironmentResolution(YamlParserTest.class.getResourceAsStream("/test.yaml"),
            Config.class);
    Truth.assertThat(config.key1).isEqualTo("value");
    Truth.assertThat(config.key2).isEqualTo("value2");
  }

}
