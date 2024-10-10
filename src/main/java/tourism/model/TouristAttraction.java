package tourism.model;

import tourism.util.City;
import tourism.util.Tag;

import java.util.List;

public class TouristAttraction {

    private int id;
    private String name;
    private String description;
    private City city;
    private List<Tag> tags;
    private double priceInDkk;
    private double convertedPrice;

    public TouristAttraction(String name, String description, City city, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    public TouristAttraction(String name, String description, City city, List<Tag> tags, double priceInDkk) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
        this.priceInDkk = priceInDkk;
    }

    public double getConvertedPrice() {
        return convertedPrice;
    }

    public void setConvertedPrice(double convertedPrice) {
        this.convertedPrice = convertedPrice;
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

    public int getCityId() {
        return city.getId();
    }

    public void setCity(City city) {
        this.city = city;
    }

    public double getPriceInDkk() {
        return priceInDkk;
    }

    public void setPriceInDkk(double priceInDkk) {
        this.priceInDkk = priceInDkk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
