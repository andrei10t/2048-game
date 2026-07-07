## Setup and play
1. Building first time took me 4 min(even though the model is tiny).
   `docker build -t game-2048 .`

2. Run interactively.
   `docker run -it --rm game-2048`

3. You will be prompted the instructions, the board, and asked for a move
   `Use W/A/S/D to move, 'h' for an AI hint, 'q' to quit`


4. To play press w/a/s/d representing up/left/down/right movements and press ENTER after every key. 

## How to configure
in src/main/resources/application.yml you can configure the game.

### game
1. board-size: 4 -> standard board size
2. win-value: 2048 -> we will stop here, i know in the original game you can go further
3. four-spawn-probability: 0.1 -> checked online, this is the actual rate of 4s

### AI
1. show-logs: false -> if true, you will see the model's logs as you play and ask for hints.
2. prompt-template -> Payload passed to the model
3. starting-grid -> you can decomment it, if you don't want to start with a random position. I needed it for testing. 



## Choices/Assumptions:
1. Of course it would have been much easier if I implemented a deterministic logic for the helper, 
but I thought an LLM would be more interesting and I had some experience with it. It will NOT give the best answer. 
2. number of random 2s in the original setup: from 1 to all, even though original game doesn't have this.
I took the broadest range given no explicit requirement. We have game configuration if we wanted to select a specific
number or range.
3. Model can be a different container, right now it is sending a request to localhost. 

Extremely fun, lots of stuff that can be optimized, I just made it work for now. 
   


