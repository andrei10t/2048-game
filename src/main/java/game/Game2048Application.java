package game;

import config.GameConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(GameConfig.class)
public class Game2048Application {

  public static void main(String[] args) {
    SpringApplication.run(Game2048Application.class, args);
  }
}
