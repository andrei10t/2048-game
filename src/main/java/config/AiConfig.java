package config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "ollama")
public class AiConfig {

  private final String url;
  private final String model;
  private final String promptTemplate;
  private final boolean showLogs;
}
