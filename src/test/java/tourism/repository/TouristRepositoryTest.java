package tourism.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import tourism.model.TouristAttraction;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("h2")
public class TouristRepositoryTest {

    @Autowired
    private TouristRepositoryJDBC touristAttractionRepository;


    @Test
    public void testFindAll() throws SQLException {
        List<TouristAttraction> attractions = touristAttractionRepository.findAllAttractions();
        assertEquals(4, attractions.size());
    }
}