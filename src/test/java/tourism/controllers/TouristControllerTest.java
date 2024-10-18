package tourism.controllers;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;


import tourism.model.TouristAttraction;
import tourism.service.TouristService;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    private TouristAttraction touristAttraction = new TouristAttraction();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService touristService;

    @Test
    void showIndex() {

    }
    @Test
    void findAttractionByName() {
    }

    @Test
    void getAllAttractions()throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"));
    }


    @Test
    //TODO needs fixing, only test redirect to tags, not the tags itself
    void getAttractionTags()throws Exception {
        mockMvc.perform(get("/attractions/1/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"));
    }

    //used attributedExists
    @Test
    void addAttraction() throws Exception {
        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("touristAttraction"))
                .andExpect(view().name("addAttraction"));
    }

    @Test
    void saveAttraction() throws Exception {
        mockMvc.perform(post("/attractions/save").sessionAttr("touristAttraction", this.touristAttraction))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));
    }

    @Test
    void showEditForm() {
    }

    @Test
    void updateAttraction() {
    }

    @Test
    void deleteAttraction() {
    }
}