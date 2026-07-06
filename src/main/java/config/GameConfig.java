package config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "game")
public class GameConfig {
  private final int boardSize;
  private final int winValue;
  private final double fourSpawnProbability;

}
