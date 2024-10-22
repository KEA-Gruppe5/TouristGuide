package tourism.repository;


import tourism.model.TouristAttraction;
import tourism.util.Tag;

import java.sql.SQLException;
import java.util.List;

public interface ITouristRepo {
    List<TouristAttraction> findAllAttractions() throws SQLException;
    TouristAttraction findAttractionByID(int id);
    TouristAttraction addAttraction(TouristAttraction touristAttraction) throws SQLException;
    TouristAttraction updateAttraction(TouristAttraction touristAttraction, int id);
    boolean deleteAttraction(int attractionID) throws SQLException;
    TouristAttraction displayEditAttraction(String name);
    List<Tag> findTag(int attractionId)throws SQLException;
    List<Tag> findPrevSelectedTags(int id)throws SQLException;

}

