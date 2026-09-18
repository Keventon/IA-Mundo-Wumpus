package pdwumpus;

/** Mistura determinística SplitMix64, para não usar sementes sequenciais diretamente no Random. */
public final class Sementes {
    private Sementes() { }
    public static long misturar(long x) {
        x+=0x9E3779B97F4A7C15L;
        x=(x^(x>>>30))*0xBF58476D1CE4E5B9L;
        x=(x^(x>>>27))*0x94D049BB133111EBL;
        return x^(x>>>31);
    }
}
