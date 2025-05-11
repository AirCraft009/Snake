package main.java.tile;

public enum TileType {
    Ground1("Ground-basic.png"),
    Ground2("Ground-basic-var2.png"),
    Ground3("Ground-basic-var2.png"),
    Wall1("wall.png");

    private final String fileName;

    TileType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public static String getFileNameByIndex(int i) {
        TileType[] values = TileType.values();
        if (i >= 0 && i < values.length) {
            return values[i].getFileName();
        } else {
            throw new IndexOutOfBoundsException("Invalid TileType index: " + i);
        }
    }
}
