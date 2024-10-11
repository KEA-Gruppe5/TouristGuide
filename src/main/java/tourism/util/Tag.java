package tourism.util;

public enum Tag {
    CHILD_FRIENDLY(1, "Child Friendly"),
    FREE(2, "Free"),
    ART(3, "Art"),
    MUSEUM(4, "Museum"),
    OPEN_AIR (5, "Open air");

    private final String displayName;
    private final int id;

    Tag(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }

    public int getId() {
        return id;
    }

    public static Tag getEnumFromId(int id){
        for(Tag tag: Tag.values()){
            if(tag.getId() == id){
                return tag;
            }
        }
        return null;
    }

}
