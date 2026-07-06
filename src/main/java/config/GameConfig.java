package config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameConfig {
  private final int boardSize;
  private final int winValue;
  private final double fourSpawnProbability;

}
