package tourism.model.Enum;

public enum City {
    COPENHAGEN("Copenhagen", 1),
    AARHUS("Aarhus", 2),
    ODENSE("Odense", 3),
    AALBORG("Aalborg", 4);

    private final String displayName;
    private final int id;

    City(String displayName, int id){
        this.displayName = displayName;
        this.id  = id;
    }
    public String getDisplayName(){
        return displayName;
    }

    public int getId(){
        return id;
    }

    public static City getEnumFromId(int id){
        for(City city : City.values()){
            if(city.getId() == id){
                return city;
            }
        }
        return null;
    }
}
