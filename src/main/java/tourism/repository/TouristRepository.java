package tourism.repository;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.metrics.StartupStep;
import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;
import tourism.util.City;
import tourism.util.Tag;

import java.sql.*;
import java.util.*;
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
        String insertTagsQuery = "INSERT INTO ATTRACTIONS_TAGS (attractionID, tagID) VALUES (?, ?)";


        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement prepareStatementTags = connection.prepareStatement(insertTagsQuery)) {

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
                }

                for (Tag tag : touristAttraction.getTags()) {
                    prepareStatementTags.setInt(1, touristAttraction.getId());
                    prepareStatementTags.setInt(2, tag.getId());
                    prepareStatementTags.executeUpdate();

                }
            }
            return touristAttraction;
        }
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction, String name) {
        String query = "UPDATE TOURIST_ATTRACTION SET name = ?, description = ?, price = ?, convertedPrice = ?, cityID = ? WHERE id = ?";
        String updateTags = "INSERT INTO ATTRACTIONS_TAGS (attractionID, tagID) VALUES (?, ?) ";
        String deleteTags = "DELETE FROM ATTRACTIONS_TAGS WHERE attractionID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement updateTagsStmt = connection.prepareStatement(updateTags);

            for (TouristAttraction t : touristAttractions) {
                if (t.getName().equals(name)) {

                    //Updat
                    preparedStatement.setString(1, touristAttraction.getName());
                    preparedStatement.setString(2, touristAttraction.getDescription());
                    preparedStatement.setDouble(3, touristAttraction.getPriceInDkk());
                    preparedStatement.setDouble(4, touristAttraction.getConvertedPrice());
                    preparedStatement.setInt(5, touristAttraction.getCityId());
                    preparedStatement.setInt(6, touristAttraction.getId());
                    preparedStatement.executeUpdate();

                    //Delete all tags to start fresh
                    PreparedStatement delTags = connection.prepareStatement(deleteTags);
                    delTags.setInt(1, touristAttraction.getId());
                    System.out.println(touristAttraction.getId());
                    delTags.executeUpdate();
                }
            }
            for (Tag tag : touristAttraction.getTags()) {
                updateTagsStmt.setInt(1, touristAttraction.getId());
                updateTagsStmt.setInt(2, tag.getId());
                updateTagsStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return touristAttraction;
    }


    public boolean deleteAttraction(int attractionID) throws SQLException {
        //SQL delete
        String deleteAttractions = "DELETE FROM tourist_attraction WHERE ID = ?";
        String deleteTags = "DELETE FROM attractions_tags WHERE attractionID = ?";
        //connect
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
             //the statement
             PreparedStatement delTags = connection.prepareStatement(deleteTags);

            PreparedStatement delAttractions = connection.prepareStatement(deleteAttractions);

            //set parameter
            delTags.setInt(1, attractionID);
            delTags.executeUpdate();

            delAttractions.setInt(1, attractionID);
            delAttractions.executeUpdate();

        }
        return true;
    }


    public TouristAttraction displayEditAttraction(String name) {
        for (TouristAttraction t : touristAttractions) {
            if (t.getName().trim().equalsIgnoreCase(name)) {
                return t;
            }
        }
        throw new NoSuchElementException("No tourist attraction found with the name " + name);
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

    public List<Tag> findPrevSelectedTags(int id) throws SQLException {
        List<Tag> tags = new ArrayList<>();
        String query = "SELECT tagID FROM attractions_tags WHERE attractionID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int tagId = resultSet.getInt("tagID");
                Tag tag = Tag.getEnumFromId(tagId);

                if (tag != null) {
                    tags.add(tag);
                } else {
                    System.out.println("No Tag found for ID: " + tagId);
                }
            }
        }
        return tags;
    }
}
