package tourism.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import tourism.model.TouristAttraction;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TouristRepositoryTest {

    @Autowired
    private TouristRepository touristAttractionRepository;


    @Test
    public void testFindAll() throws SQLException {
        List<TouristAttraction> attractions = touristAttractionRepository.findAllAttractions();
        assertEquals(4, attractions.size());
    }
}