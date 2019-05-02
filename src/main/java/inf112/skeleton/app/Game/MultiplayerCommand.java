package inf112.skeleton.app.Game;

import java.io.Serializable;

public class MultiplayerCommand implements Serializable {
    Action action;
    byte[] params;

    MultiplayerCommand() {
        this(null);
    }

    MultiplayerCommand(Action cmd) {
        action = cmd;
    }

    @Override
    public String toString() {
        switch (action) {
            case JOIN:
                return "join action";
            case STATE:
                return "state action";
            default:
                return "unknown";
        }
    }

    enum Action {
        STATE,
        JOIN
    }
}
