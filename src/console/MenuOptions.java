package console;

public final class MenuOptions {

    private MenuOptions() {
    }

    public static final int EXIT = 0;

    //Ecosystem Menu
    public static final int SHORT_STATISTIC = 1;
    public static final int FULL_STATISTIC = 2;
    public static final int ECOSYSTEM_PARAMS = 3;
    public static final int CHANGE_ENTITIES = 4;
    public static final int ADD_NEW_ENTITY = 5;
    public static final int EVOLUTION_STEP = 6;
    public static final int PREDICTION = 7;
    public static final int AUTO_EVOLUTION = 8;

    //Ecosystem change params Menu
    public static final int HUMIDITY = 1;
    public static final int AMOUNT_OF_WATER = 2;
    public static final int SUNSHINE = 3;
    public static final int TEMPERATURE = 4;

    //Entities
    public static final int ANIMALS = 1;
    public static final int PLANTS = 2;

    public static final int DELETE = 1;
    public static final int CHANGE_COUNT = 2;
}
