# Game of Three :video_game: :space_invader:
This repository includes the source code for Game of Three client and server.

## Setup :gear:
* Make sure that [openjdk version ](https://openjdk.java.net/install/) version is at least 11.0
* Make sure that [make](https://en.wikipedia.org/wiki/Make_(software)) is installed

## Commands :computer:
make is used to orchestrate most development tasks. 

| Command | Description |
| --- | --- |
| `make build-server` | build server |
| `make start-server` | start game server  |
| `make build-client` | build game client  |
| `make start-client-manual` | start game client on manual mode |
| `make start-client-automatic` | start game client on automatic mode |


obs1.: you might need root access to run build at the first time so the wrapper can write on the file system. e.g. ```sudo make build-server``` ```sudo make build-client``` 

obs2.: the ```make``` build commands will run the gradlew wrapper with a shell command. for windows you need to go inside ```server``` and ```client``` folder and run ```gradlew.bat build```

## Game description
When a player starts, it incepts a random (whole) number and sends it to the second
player as an approach of starting the game. The receiving player can now always choose
between adding one of {-1, 0, 1} to get to a number that is divisible by 3. Divide it by three. The
resulting whole number is then sent back to the original sender.
The same rules are applied until one player reaches the number 1(after the division).

See example below.
![Screenshot from 2021-06-01 08-04-16](https://user-images.githubusercontent.com/57350932/120274348-ff1ba800-c2af-11eb-9f96-f67ba13168f8.png)



For each "move", a sufficient output should get generated (mandatory: the added, and
the resulting number). Both players should be able to play automatically without user input. The
type of input (manual, automatic) should be optionally adjustable by the player.

## Client Server connection
The client and server communicates trough a single websocket endpoint ```ws://localhost:8080/game``` and the players data is store in the server runtime memory for simplicity.

## Data Model
Objects are used for the web socket API: 
- ```ServerMessage``` (message sent from server to client)
- ```ClientMessage``` (message sent from client to server)
- ```Move``` (a move with information about the turn)
---
### ServerMessage
#### Properties
* `text` Simple message text
  * type: `string`,  *optional*
* `messageType` Type of the message
  * type: `enum`,  `MOVE` or `INFO` or `GAME_START` or `GAME_OVER` or `GAME_WIN` or `OPPONENT_DISCONNECTED`, **required**
* `move` Current move / Opponent move
  * type: `move`,  *optional*

example:
```json
{
  "text": "current move",
  "messageType": "MOVE",
  "move": {
    "resultingNumber": 56,
    "addedNumber": 0
  }
}
```
---
### ClientMessage
#### Properties
* `move` Current move / Opponent move
  * type: `move`,  **required**

example:
```json
{
  "move": {
    "resultingNumber": 56,
    "addedNumber": 0
  }
}
```
---
### Move
#### Properties
* `resultingNumber` Resulting number
  * type: `integer`, **required**
* `addedNumber` Added Number
  * type: `integer`,  **required**

example:
```json
"move": {
  "resultingNumber": 56,
  "addedNumber": 0
}
```
---
# GamePlay

![gameplay](https://user-images.githubusercontent.com/57350932/120353263-d7edc680-c301-11eb-83a8-b6ef6d90fc43.gif)

