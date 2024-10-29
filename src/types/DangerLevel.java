package types;

import ecxeptions.WrongDataException;

import java.util.Arrays;

public enum DangerLevel {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int dngLvlInt;

    DangerLevel(int dngLvlInt) {
        this.dngLvlInt = dngLvlInt;
    }

    public int getDngLvlInt() {
        return dngLvlInt;
    }

    public static DangerLevel valueOfInt(int i) throws WrongDataException {
        return Arrays.stream(DangerLevel.values()).filter(dangerLevel -> dangerLevel.dngLvlInt == i)
                .findFirst().orElseThrow(() -> new WrongDataException("No such Danger Level for " + i));
    }
}
