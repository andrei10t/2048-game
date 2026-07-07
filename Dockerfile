FROM maven:3.9-eclipse-temurin-21
WORKDIR /app

# i need to install and start ollama so i can pull the model.
RUN apt-get update && apt-get install -y curl zstd \
    && curl -fsSL https://ollama.com/install.sh | sh \
    && (ollama serve & sleep 5 && ollama pull qwen2.5:0.5b)

COPY . .
RUN mvn -q -Dmaven.test.skip=true package

ENTRYPOINT ["java", "-jar", "target/game-2048-1.0-SNAPSHOT.jar"]
