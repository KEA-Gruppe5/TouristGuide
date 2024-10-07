package tourism.util;

public enum City {
    COPENHAGEN(1),
    AARHUS(2),
    ODENSE(3),
    AALBORG(4);

    private final int id;
    City(int id){
        this.id  = id;
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
