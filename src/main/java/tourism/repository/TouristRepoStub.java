package tourism.repository;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;
import tourism.util.City;
import tourism.util.Tag;

import java.sql.SQLException;
import java.util.List;

@Repository("TOURIST_REPO_STUB")
@Lazy
public class TouristRepoStub implements ITouristRepo {

    private List<TouristAttraction> stub = List.of(
            new TouristAttraction("strøget", "shopping street", City.COPENHAGEN, List.of(Tag.FREE)),
            new TouristAttraction("botanist_have", "green garden", City.COPENHAGEN, List.of(Tag.FREE)),
            new TouristAttraction("Glyptotek", "Musem", City.COPENHAGEN, List.of(Tag.ART,Tag.CHILD_FRIENDLY),120)
    );


    @Override
    public List<TouristAttraction> findAllAttractions() {
        return null;
    }

    @Override
    public TouristAttraction findAttractionByID(int id) {
        return null;
    }

    @Override
    public TouristAttraction addAttraction(TouristAttraction touristAttraction) {
        return null;
    }

    @Override
    public TouristAttraction updateAttraction(TouristAttraction touristAttraction, int id) {
        return null;
    }

    @Override
    public boolean deleteAttraction(int attractionID) {
        return false;
    }

    @Override
    public TouristAttraction displayEditAttraction(String name) {
        return null;
    }

    @Override
    public List<Tag> findTag(int attractionId) {
        return null;
    }

    @Override
    public List<Tag> findPrevSelectedTags(int id) {
        return null;
    }
}
