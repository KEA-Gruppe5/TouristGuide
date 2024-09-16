package tourism.model;

import tourism.util.Tag;

import java.util.List;

public class TouristAttraction {

    private String name;
    private String description;
    private List<Tag> tags;

    public TouristAttraction(String name, String description, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.tags = tags;
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
}
