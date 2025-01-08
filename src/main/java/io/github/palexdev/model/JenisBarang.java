package io.github.palexdev.model;

public enum JenisBarang {
    Baru("Baru"),
    Bekas("Bekas");

    private final String displayName;

    JenisBarang(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
