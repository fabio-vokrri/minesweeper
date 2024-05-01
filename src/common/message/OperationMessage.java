package common.message;


import common.Coordinates;
import common.enums.Operation;

public class OperationMessage implements Message {
    private final Operation operation;
    private final Coordinates coordinates;

    public OperationMessage(Operation operation, Coordinates coordinates) {
        this.operation = operation;
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getRow() {
        return coordinates.getColumn();
    }

    public int getColumn() {
        return coordinates.getRow();
    }

    public Operation getOperation() {
        return operation;
    }
}
