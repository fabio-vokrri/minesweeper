package common.message;

import common.enums.Direction;

public class MoveMessage implements Message {
    private final Direction direction;

    public MoveMessage(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}

