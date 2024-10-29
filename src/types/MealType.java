package types;

import ecxeptions.WrongDataException;

import java.util.Arrays;

public enum MealType {
    CARNIVOROUS(1),
    OMNIVOROUS(2),
    HERBIVOROUS(3);

    private final int mealTypeInt;

    MealType(int mealTypeInt) {
        this.mealTypeInt = mealTypeInt;
    }

    public int getMealTypeInt() {
        return mealTypeInt;
    }

    public static MealType valueOfInt(int i) throws WrongDataException {
        return Arrays.stream(MealType.values()).filter(mealType -> mealType.mealTypeInt == i)
                .findFirst().orElseThrow(() -> new WrongDataException("No such Meal Type for " + i));
    }
}
