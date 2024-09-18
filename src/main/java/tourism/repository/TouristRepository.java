package tourism.repository;

import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;
import tourism.util.City;
import tourism.util.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> touristAttractions = new ArrayList<>();
    private static Logger logger = Logger.getLogger("RepLogger");

    public TouristRepository() {
        touristAttractions.add(new TouristAttraction("Tivoli", "entertainment park", City.COPENHAGEN, List.of(Tag.CHILD_FRIENDLY, Tag.ART)));
        touristAttractions.add(new TouristAttraction("Christiansborg", "Parliament", City.COPENHAGEN, List.of(Tag.MUSEUM)));
        touristAttractions.add(new TouristAttraction("Nyhavn", "Main street", City.COPENHAGEN, List.of(Tag.OPEN_AIR, Tag.CHILD_FRIENDLY)));
        touristAttractions.add(new TouristAttraction("AroS", "Art museum", City.AARHUS, List.of(Tag.MUSEUM, Tag.ART));
    }

    public List<TouristAttraction> findAllAttractions() {
        return touristAttractions;
    }

    public TouristAttraction findAttractionByName(String name) {
        for(TouristAttraction touristAttraction : touristAttractions){
            if(touristAttraction.getName().equalsIgnoreCase(name)){
                return touristAttraction;
            }
        }
        return null;
    }

    public TouristAttraction addAttraction(TouristAttraction touristAttraction) {
        touristAttractions.add(touristAttraction);
        logger.info("added new attraction: " + touristAttraction.getName());
        return touristAttraction;
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction) {
        for(int i =0; i < touristAttractions.size(); i++){
            if(touristAttractions.get(i).getName().equalsIgnoreCase(touristAttraction.getName())){
                touristAttractions.set(i, touristAttraction);
            }
        }
        return touristAttraction;
    }

    public boolean deleteAttraction(String name) {
        for(TouristAttraction touristAttraction : touristAttractions){
            if(touristAttraction.getName().equalsIgnoreCase(name)){
                return touristAttractions.remove(touristAttraction);
            }
        }
        return false;
    }
    public List<Tag> findTag(String name) {
        for(TouristAttraction touristAttraction : touristAttractions){
            if(touristAttraction.getName().contains(name)){
                return touristAttraction.getTags();
            }
        }
        return null;
    }



}
