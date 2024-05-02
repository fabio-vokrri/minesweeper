package common.message;

import common.enums.Command;

public class CommandMessage implements Message {
    private final Command command;
    private final int selectedRow;
    private final int selectColumn;

    public CommandMessage(Command command, int selectedRow, int selectColumn) {
        this.command = command;
        this.selectedRow = selectedRow;
        this.selectColumn = selectColumn;
    }

    public Command getCommand() {
        return command;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectColumn() {
        return selectColumn;
    }

}
