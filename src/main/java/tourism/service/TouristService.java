package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;
import tourism.util.Tag;

import java.io.IOException;
import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;
    private final CurrencyService currencyService;

    public TouristService(TouristRepository touristRepository, CurrencyService currencyService) {
        this.touristRepository = touristRepository;
        this.currencyService = currencyService;
    }

    public List<TouristAttraction> getAllAttractions(String currency) throws IOException {
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


    public TouristAttraction findAttractionByName(String name) {
        return touristRepository.findAttractionByName(name);
    }

    public TouristAttraction saveAttraction(TouristAttraction touristAttraction) {
        return touristRepository.addAttraction(touristAttraction);
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction, String originalName) {
        return touristRepository.updateAttraction(touristAttraction, originalName);
    }

    public TouristAttraction editAttraction(String name){
        return touristRepository.editAttraction(name);
    }

    public String deleteAttraction(String name) {
        if(touristRepository.deleteAttraction(name)){
            return name + " was successfully deleted!";
        } else {
            return "Could not find " + name;
        }
    }

    public List<Tag> findTag(String name){
        return touristRepository.findTag(name);
    }


}
