package tourism.model;

import tourism.util.City;
import tourism.util.Tag;

import java.util.List;

public class TouristAttraction {

    private String name;
    private String description;
    private City city;
    private List<Tag> tags;

    public TouristAttraction(String name, String description, City city, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    public TouristAttraction() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        String str =  "TouristAttraction: " +
                "\nname: " + name  +
                "\ndescription: " + description;
        if(tags!= null && tags.size() > 0){
            for(Tag tag : tags){
                str += tag+"\n";
            }

        }

        return str;
    }
}
