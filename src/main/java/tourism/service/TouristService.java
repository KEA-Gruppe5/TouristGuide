package tourism.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<TouristAttraction> getAllAttractions() {
        return touristRepository.findAllAttractions();
    }

    public TouristAttraction findAttractionByName(String name) {
        return touristRepository.findAttractionByName(name);
    }

    public TouristAttraction addAttraction(TouristAttraction touristAttraction) {
        return touristRepository.addAttraction(touristAttraction);
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction) {
        return touristRepository.updateAttraction(touristAttraction);
    }

    public String deleteAttraction(String name) {
        if(touristRepository.deleteAttraction(name)){
            return name + " was successfully deleted!";
        } else {
            return "Could not find " + name;
        }
    }
}
