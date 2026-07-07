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

  // optional: when set, the board starts from this grid instead of a random one.
  // null cells represent empty tiles. Missing from config -> null -> random setup.
  private final Integer[][] startingGrid;

}
