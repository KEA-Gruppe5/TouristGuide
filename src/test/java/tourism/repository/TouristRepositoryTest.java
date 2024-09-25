package tourism.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;
import tourism.util.City;
import tourism.util.Tag;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristRepository.class)
class TouristRepositoryTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristAttraction touristAttraction;

    @MockBean
    TouristRepository touristRepository;

    @MockBean
    TouristService touristService;


    @Test
    void findAllAttractions() throws Exception {

    }

    @Test
    void findAttractionByName() {
    }

    @Test
    void addAttraction() {
    }

    @Test
    void updateAttraction() {
    }

    @Test
    void deleteAttraction() {
    }

    @Test
    void editAttraction() {
    }

    @Test
    void findTag() {
    }
}