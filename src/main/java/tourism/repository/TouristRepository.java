package tourism.repository;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;
import tourism.model.Enum.City;
import tourism.model.Enum.Tag;

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

    public TouristAttraction findAttractionByID(int id) {
        for (TouristAttraction touristAttraction : touristAttractions) {
            if (touristAttraction.getId() ==id) {
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

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction, int id) {
        String updateAttractionQuery = "UPDATE TOURIST_ATTRACTION SET name = ?, description = ?, price = ?, convertedPrice = ?, cityID = ? WHERE id = ?";
        String deleteTagsQuery = "DELETE FROM ATTRACTIONS_TAGS WHERE attractionID = ?";
        String insertTagQuery = "INSERT INTO ATTRACTIONS_TAGS (attractionID, tagID) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // 1. Update the TouristAttraction
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateAttractionQuery)) {
                preparedStatement.setString(1, touristAttraction.getName());
                preparedStatement.setString(2, touristAttraction.getDescription());
                preparedStatement.setDouble(3, touristAttraction.getPriceInDkk());
                preparedStatement.setDouble(4, touristAttraction.getConvertedPrice());
                preparedStatement.setInt(5, touristAttraction.getCityId());
                preparedStatement.setInt(6, id);  // Use passed ID to ensure correct record is updated
                preparedStatement.executeUpdate();
            }

            // 2. Delete all existing tags for this attraction
            try (PreparedStatement deleteTagsStmt = connection.prepareStatement(deleteTagsQuery)) {
                deleteTagsStmt.setInt(1, id);  // Delete tags by attraction ID
                deleteTagsStmt.executeUpdate();
            }

            // 3. Insert new tags associated with the attraction
            try (PreparedStatement insertTagStmt = connection.prepareStatement(insertTagQuery)) {
                for (Tag tag : touristAttraction.getTags()) {
                    insertTagStmt.setInt(1, id);  // Insert attractionID
                    insertTagStmt.setInt(2, tag.getId());  // Insert tagID
                    insertTagStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update tourist attraction and tags for ID: " + id, e);
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
