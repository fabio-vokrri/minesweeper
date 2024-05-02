package common.printable;

import common.enums.Color;

public class Console {

    public static String coloredString(String string, Color color) {
        return color.getCode() + string + Color.RESET.getCode();
    }
}
