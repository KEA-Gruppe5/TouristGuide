package tourism.repository;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;
import tourism.util.City;
import tourism.util.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Repository
public class TouristRepository {

    private List<TouristAttraction> touristAttractions = new ArrayList<>();

    private static final Logger logger = Logger.getLogger("RepLogger");

    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;

    public List<TouristAttraction> findAllAttractions() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM TOURIST_ATTRACTION" +
                    " LEFT JOIN CITY ON TOURIST_ATTRACTION.CITYID = CITY.ID";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Integer, TouristAttraction> map = new HashMap<>();
            while (resultSet.next()) {
                int attractionId = resultSet.getInt("id");
                TouristAttraction touristAttraction = map.get(attractionId);
                if (touristAttraction == null) {
                    touristAttraction = new TouristAttraction();
                    touristAttraction.setId(attractionId);
                    touristAttraction.setName(resultSet.getString("name"));
                    touristAttraction.setDescription(resultSet.getString("description"));
                    touristAttraction.setPriceInDkk(resultSet.getDouble("price"));
                    touristAttraction.setCity(City.getEnumFromId(resultSet.getInt("cityId")));
                    map.put(attractionId, touristAttraction);
                }
            }
            touristAttractions = new ArrayList<>(map.values());
        }
        return touristAttractions;
    }

    public TouristAttraction findAttractionByName(String name) {
        for (TouristAttraction touristAttraction : touristAttractions) {
            if (touristAttraction.getName().equalsIgnoreCase(name)) {
                return touristAttraction;
            }
        }
        return null;
    }

    public TouristAttraction addAttraction(TouristAttraction touristAttraction) throws SQLException {

        String insertQuery = "INSERT INTO TOURIST_ATTRACTION (name, description, price, convertedPrice, cityID) " +
                "VALUES (?, ?, ?, ?, ?)";
        String insertTags = "INSERT INTO ATTRACTIONS_TAGS (attractionID, tagID) VALUES (?, ?)";


        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertAttractionTagStmt = connection.prepareStatement(insertTags)) {

            // Set the parameters for the tourist attraction
            preparedStatement.setString(1, touristAttraction.getName());
            preparedStatement.setString(2, touristAttraction.getDescription());
            preparedStatement.setDouble(3, touristAttraction.getPriceInDkk());
            preparedStatement.setDouble(4, touristAttraction.getConvertedPrice());
            preparedStatement.setInt(5, touristAttraction.getCityId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        touristAttraction.setId(generatedId);

                    }
                    insertAttractionTagStmt.setInt(1, touristAttraction.getId());
                    for (Tag tag : Tag.values()) {
                        if (touristAttraction.getTags().contains(tag)) {
                            insertAttractionTagStmt.setInt(2, tag.getId());
                            insertAttractionTagStmt.executeUpdate();
                        }
                    }
                }
            }
            return touristAttraction;
        }
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction, String originalName) {
        for (TouristAttraction t : touristAttractions) {
            if (t.getName().equals(originalName)) {
                List<Tag> tags = touristAttraction.getTags();
                t.setName(touristAttraction.getName());
                t.setDescription(touristAttraction.getDescription());
                t.setTags(tags);
                t.setCity(touristAttraction.getCity());
                if (tags.contains(Tag.FREE)) {
                    t.setPriceInDkk(0);
                } else {
                    t.setPriceInDkk(touristAttraction.getPriceInDkk());
                }
                logger.info("attraction " + originalName + " edited.");
                return t;
            }
        }
        return null;
    }



    public boolean deleteAttraction(String name) throws SQLException {
        //SQL delete
        String query = "DELETE FROM TOURIST_ATTRACTION WHERE LOWER(name) = LOWER(?)";
        //connect
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             //the statement
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             //set parameter
             preparedStatement.setString(1, name);
             //execute
             int rowsAffected = preparedStatement.executeUpdate();

             return rowsAffected > 0;
            }
  

        }


    public TouristAttraction editAttraction(String name) {
        for (TouristAttraction t : touristAttractions) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }


    public List<Tag> findTag(int attractionId) throws SQLException {
        List<Tag> tags = new ArrayList<>();
        String query = "SELECT tag.id FROM TAG JOIN attractions_tags on tag.ID = attractions_tags.tagID WHERE attractions_tags.attractionID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, attractionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int tagId = resultSet.getInt("id");
                Tag tag = Tag.getEnumFromId(tagId);
                tags.add(tag);
            }
        }
        return tags;
    }

}
