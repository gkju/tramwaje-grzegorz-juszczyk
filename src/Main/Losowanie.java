package Main;

import java.util.Random;

public class Losowanie {
    private static Random random = new Random();

    /**
     * @param dolna
     * @param gorna
     * @return Losowa liczba z przedziału [dolna, gorna]
     */
    public static int losuj(int dolna, int gorna) {
        return random.nextInt(dolna, gorna + 1);
    }
}
