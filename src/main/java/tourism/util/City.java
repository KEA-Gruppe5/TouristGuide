package tourism.util;

public enum City {
    COPENHAGEN("Copenhagen"),
    AARHUS("Århus"),
    ODENSE("Odensen"),
    AALBORG("Ålborg");

    private String displayName;
    City(String displaName){
        this.displayName = displaName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
