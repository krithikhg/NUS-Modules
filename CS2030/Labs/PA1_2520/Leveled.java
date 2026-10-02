abstract class Leveled implements Comparable<Leveled> {
    private final int level;
    private final String leveledType;

    Leveled(int level, String leveledType) {
        this.level = level;
        this.leveledType = leveledType;
    }

    @Override
    public int compareTo(Leveled other) {
        return this.level - other.level;
    }

    @Override
    public String toString() {
        int level = this.level < 0 ? -this.level : this.level;
        return leveledType + " at L" + level;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Leveled leveled) {
            return this.level == leveled.level;
        }

        return false;
    }
}
