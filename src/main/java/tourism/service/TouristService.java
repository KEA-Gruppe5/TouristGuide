package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;
import tourism.util.Tag;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;
    private final CurrencyService currencyService;

    public TouristService(TouristRepository touristRepository, CurrencyService currencyService) {
        this.touristRepository = touristRepository;
        this.currencyService = currencyService;
    }

    public List<TouristAttraction> getAllAttractions(String currency) throws IOException, SQLException {
        List<TouristAttraction> touristAttractions = touristRepository.findAllAttractions();
        if (!currency.equalsIgnoreCase("dkk")) {
            setConvertedPrice(touristAttractions);
        }
        return touristAttractions;
    }

    public void setConvertedPrice( List<TouristAttraction> touristAttractions) throws IOException {
        for(TouristAttraction touristAttraction: touristAttractions){
            touristAttraction.setConvertedPrice(currencyService.getPriceInEuro(touristAttraction.getPriceInDkk()));
        }
    }


    public TouristAttraction findAttractionByID(int id) {
        return touristRepository.findAttractionByID(id);
    }

    public TouristAttraction saveAttraction(TouristAttraction touristAttraction) throws SQLException {
        return touristRepository.addAttraction(touristAttraction);
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction, int id) {
        return touristRepository.updateAttraction(touristAttraction, id);
    }

    public TouristAttraction displayEditAttraction(String name){
        return touristRepository.displayEditAttraction(name);
    }

    public String deleteAttraction(int attractionID) throws SQLException {
        if(touristRepository.deleteAttraction(attractionID)){
            return attractionID + " was successfully deleted!";
        } else {
            return "Could not find " + attractionID;
        }
    }

    public List<Tag> findTag(int attractionId) throws SQLException {
        return touristRepository.findTag(attractionId);
    }

    public List<Tag> findPrevSelectedTags(int id) throws SQLException {
        return touristRepository.findPrevSelectedTags(id);
    }

}
