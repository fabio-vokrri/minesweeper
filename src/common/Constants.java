package common;

import common.enums.Color;
import common.enums.Direction;
import common.enums.Operation;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final int minRows = 5;
    public static final int maxRows = 30;
    public static final int minColumns = 5;
    public static final int maxColumns = 30;


    public static final String square = "  ";
    public static final String pointerSymbol = Console.coloredString(square, Color.BLUE_BG);
    public static final String unopenedSymbol = Console.coloredString(square, Color.WHITE_BG);
    public static final String flagSymbol = Console.coloredString(square, Color.GREEN_BG);
    public static final String bombSymbol = Console.coloredString(square, Color.RED_BG);

    public static final List<String> operationKeys = Arrays.stream(Operation.values()).map(Operation::getKey).toList();
    public static final List<String> directionKeys = Arrays.stream(Direction.values()).map(Direction::getKey).toList();
}
