package tourism.util;

public enum Tag {
    CHILD_FRIENDLY("Child Friendly"),
    FREE("Free"),
    ART("Art"),
    MUSEUM("Museum"),
    OPEN_AIR ("Open air");

    private String displayname;
    Tag(String displayname) {
        this.displayname = displayname;
    }
    public String getDisplayname() {
        return displayname;
    }
}
