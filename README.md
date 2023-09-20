# Battleship server

*This application is written as a demo solution to a technical assignment. This is not a working game (yet).*

## Description

This is a spring boot project that creates a websocket message broker so we can have real time interaction between the players and the server.

When the demo page opens, it does not immediately connect. First the user can enter a name and hit the "Join" button. 

This will:
    
1. Let the demo page set up a websocket connection to the server.
2. Once connected, the page will send the username to the server. 
3. The battleship server will try to place the player in the "lobby". 
4. When doing so, the server will check that the lobby is not full and the chosen name is not yet in use by another player. 
5. Next, the server will check if there is a player who is not in a game yet
    - If so a match will be started, between the player that just connected and the player that was waiting in the lobby.
    - Otherwise, the player is just added to the list and will keep waiting until another player joins.
    - If either of the players in the match leave the game, the match will be ended and the opponent will be notified.
6. When a match is started, it enters the "setup" phase in which both players can place their ships on the board.
    - The server will let the client know which ships still have to be placed on the board
    - The user can indicate which ship to place, the coordinates of the placement and the direction.
    - The server will then check if the specified ship is indeed in the player's list of unplaced ships, check if there 
      is room on the board for place the ship at the specified coordinates in the specified direction.
    - The server will let the client know if the placement is valid or not, and if so, update the list of unplaced ships.

TODO:\
If all ships are placed, the game advances to the next stage in which the players take turns guessing where the 
opponent's ships are placed. The player who first sunk all the opponents boats (i.e. guessed all the coordinates of 
the opponent's ships) wins the game.

## Endpoint

The endpoint the client can connect to is `/battleship-server`

## Topics

The server will send updates about the game to the user through the following topics:

* `/topic/control-messages`:\
  By subscribing to this topic the client can receive messages that are sent to all connected clients.
* `/user/topic/control-messages`:\
  By subscribing to this topic the client can receive message that are sent only to the specific user.

The messages published to these topics have the format:

```json
{
   "eventType": "LOBBY_UPDATE | MATCH_SETUP_UPDATE | OPPONENT_LEFT | ERROR",
   "responseBody": { 
     "...": "..."
   }
}
```

## Destinations

The following destinations are available for performing operations on the server:

### Joining a game: `/join`

A message to this destination can be sent to let a player join the game.

#### Message format:
```json
{
  "playerName": "..."
}
```

#### Responses to join request:
Once a join request is received, the server will publish a response after processing the join request. Also, the server 
will monitor websockets closing. If a websockets closes, the server will also publish a response indicating that the
corresponding player left. The `eventType` of the response will be `"LOBBY_UPDATE"`.

The `responseBody` of these messages will have the form:
```json
{
   "status": "LOBBY_FULL | PLAYER_ALREADY_EXISTS | PLAYER_ENTERED | PLAYER_MATCHED | PLAYER_LEFT",
   "playerName": "..."
}
```
Explanation of the status:
* `LOBBY_FULL`: The server accepts a maximum amount of players (which for the sake of this demo has been set to 4). 
  If another player tries to join while the maximum has already been reached, a private message is sent to that user 
  with this status.
* `PLAYER_ALREADY_EXISTS`: If a player tries to join with a name that is already used by another player that has 
  already joined, a private message is sent to that user with this status.
* `PLAYER_ENTERED`: If a player has successfully joined the game, a global message with this status is published.
* `PLAYER_MATCHED`: If a player has been matched to another player, a private message is sent to both of these users.
* `PLAYER_LEFT`: If a player disconnects from the game, a global message is published with this status.

Note that if a player is matched, additionally a control message is sent to both players with eventType 
`MATCH_SETUP_UPDATE` indicating that a match has started. See more about the [section "Game setup responses"](#game-setup-responses).
### Placing a ship on the board: `/place-ship`
A message can be sent to this destination to let the player place a ship on the grid during the setup phase of the match.

#### Message format:
```json
{
    "matchId": "...",
    "position": "...",
    "shipId": "1 - 5",
    "direction": "H | V" // Horizontal or Vertical
}
```

#### Game setup responses:
Updates about the setup phase of the game will be sent as message with eventType `MATCH_SETUP_UPDATE`. These messages
are only sent to players who participate in the specific match. 

This responseBody of the message contains the following info:

```json
{
  "playerName": "...",
  "opponentName": "...",
  "matchId": "...",
  "status": "MATCH_STARTED | VALID_SHIP_PLACEMENT | INVALID_SHIP_PLACEMENT",
  "unplacedShips": [
    {
      "id": "...",
      "name": "...",
      "length": "..."
    }
  ]
}
```
* `playerName`: the name of the player to whom the message is directed
* `opponentName`: the name of the opponent
* `status`:
    * `MATCH_STARTED`: A message with this status is sent once to both players at the start of the match. 
      The purpose of this message of this message to let both players know that they have entered a match, 
      who their opponent is and which ships they need to place on the board.
    * `VALID_SHIP_PLACEMENT`: A message with this status is sent to a player who has placed a ship at a ship on the
      board at a valid location. This message will also let the player know which ships are left to be placed.
    * `INVALID_SHIP_PLACEMENT`: A message with this status is sent to a player who tried to place a ship at a location
      that is not available, or a ship that has already been placed.
* `unplacedShips`: A list with the ships that have to be placed by the user.\
  For each ship the following data is provided:
   * `id`: The id of the ship
   * `name`: The name of the ship
   * `length`: The length of the ship (i.e. number of coordinates that will be occupied by the ship)
