const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/battleship-server'
});

function disconnect() {
    stompClient.deactivate();
    setConnected(false, false);
    console.log("Disconnected");
}

function connect() {
    setConnected(true, true);
    stompClient.activate();
}

function setConnecting(connecting, connected) {
    $("#join").prop("disabled", false);

}

stompClient.onWebSocketClose = (event) => {
    console.log(event);
    disconnect();
    if (event.code == "1001") {
        showUserMessage("Server disconnected.")
    }
}

stompClient.onConnect = (frame) => {
    setConnected(true, false);

    console.log('Connected: ' + frame);

    let handleIncomingControlMessage = function(response) {
        let responseJson = JSON.parse(response.body);
        showControlMessage(JSON.stringify(responseJson));

        switch (responseJson.eventType) {
            case "LOBBY_UPDATE":
                handleLobbyUpdate(responseJson);
                break;
            case "MATCH_SETUP_UPDATE":
                handleMatchSetupUpdate(responseJson);
                break;
            case "OPPONENT_LEFT":
                handleOpponentLeft();
                break;
        }
    }

    let handleLobbyUpdate = function(responseJson) {
        let playerName = responseJson.responseBody.playerName;
        switch (responseJson.responseBody.status) {
            case "PLAYER_ENTERED":
                showUserMessage(playerName + " joined.");
                break;
            case "PLAYER_LEFT":
                showUserMessage(playerName + " left.");
                break;
            case "PLAYER_ALREADY_EXISTS":
                showUserMessage("Name \"" + playerName + "\" is already in use.");
                disconnect();
                break;
            case "LOBBY_FULL":
                showUserMessage("Lobby is full, cannot join game.");
                disconnect();
                break;
        }
    }

    let handleMatchSetupUpdate = function(responseJson) {
        switch (responseJson.responseBody.status) {
            case "MATCH_STARTED":
                showUserMessage("Starting match against " + responseJson.responseBody.opponentName + ". Place your ships.")
                $("#setup-section").show();
                $("#match-id").val(responseJson.responseBody.matchId);
                updateListWithUnplacedShips(responseJson.responseBody.unplacedShips);
                break;
            case "VALID_SHIP_PLACEMENT":
                showUserMessage("Ship placed! " + responseJson.responseBody.unplacedShips.length + " more to go.");
                updateListWithUnplacedShips(responseJson.responseBody.unplacedShips);
                break;
            case "INVALID_SHIP_PLACEMENT":
                showUserMessage("Invalid ship placement!");
                break;
        }
    }

    let updateListWithUnplacedShips = function(unplacedShips) {
        $("#ship-id > option").remove();
        for (let i = 0; i < unplacedShips.length; i++) {
            let option = document.createElement("option");
            option.text = unplacedShips[i].name;
            option.value = unplacedShips[i].id;
            $("#ship-id").append(option);
        }
    }

    let handleOpponentLeft = function() {
        showUserMessage("Match ended, because your opponent left the game.");
        endMatch();
    }

    stompClient.subscribe('/topic/control-messages', handleIncomingControlMessage);

    stompClient.subscribe('/user/topic/control-messages', handleIncomingControlMessage);

    stompClient.publish({
        destination: "/join",
        body: JSON.stringify({'playerName': $("#name").val()})
    });
};

stompClient.onWebSocketError = (error) => {
    showUserMessage("Cannot establish connection to server.")
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected, connecting) {
    $("#join").prop("disabled", connected || connecting);
    $("#name").prop("disabled", connected || connecting);
    $("#disconnect").prop("disabled", !connected || connecting);
    if (connecting) {
        $("#connecting").show();
    } else {
        $("#connecting").hide();
    }
}

function endMatch() {
    $("#setup-section").hide();
}

function showUserMessage(response) {
    $("#notifications").append("<tr><td>" + response + "</td></tr>");
}

function showControlMessage(response) {
    $("#game-data").append("<tr><td>" + response + "</td></tr>");
}

function placeShip() {
    stompClient.publish({
        destination: "/place-ship",
        body: JSON.stringify({
            'matchId': $("#match-id").val(),
            'position': $("#position").val(),
            'shipId': $("[name=ship-id]").val(),
            'direction': $("[name=direction]:checked").val()
        })
    });
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#join" ).click(() => {
        if ($("#name").val().length > 0) {
            connect();
        } else {
            showUserMessage("No user name entered.");
        }
    });
    $( "#disconnect" ).click(() => {
        showUserMessage($("#name").val() + " left.");
        endMatch();
        disconnect();
    });
    $( "#place-ship" ).click(() => {
        placeShip();
    });
});
