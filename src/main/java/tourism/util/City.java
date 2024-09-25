package tourism.util;

public enum City {
    COPENHAGEN("Copenhagen"),
    AARHUS("Århus"),
    ODENSE("Odensen"),
    AALBORG("Ålborg");

    private final String displayName;
    City(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
