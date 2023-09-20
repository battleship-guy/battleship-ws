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
                let playerName = responseJson.responseBody.playerName;
                switch (responseJson.responseBody.status) {
                    case "PLAYER_ENTERED":
                        showUserMessage(playerName + " joined.");
                        return;
                    case "PLAYER_LEFT":
                        showUserMessage(playerName + " left.");
                        return;
                    case "PLAYER_ALREADY_EXISTS":
                        showUserMessage("Name \"" + playerName + "\" is already in use.");
                        disconnect();
                        return;
                    case "LOBBY_FULL":
                        showUserMessage("Lobby is full, cannot join game.");
                        disconnect();
                        return;
                }
                return;
        }
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

function showUserMessage(response) {
    $("#notifications").append("<tr><td>" + response + "</td></tr>");
}

function showControlMessage(response) {
    $("#game-data").append("<tr><td>" + response + "</td></tr>");
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
        disconnect();
    });
});
