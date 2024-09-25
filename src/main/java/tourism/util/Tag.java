package tourism.util;

public enum Tag {
    CHILD_FRIENDLY("Child Friendly"),
    FREE("Free"),
    ART("Art"),
    MUSEUM("Museum"),
    OPEN_AIR ("Open air");

    private final String displayName;
    Tag(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
