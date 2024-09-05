package tourism.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("")
    public ResponseEntity<List<TouristAttraction>> getAllAttractions(){
        return new ResponseEntity<>(touristService.getAllAttractions(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> findAttractionByName(@PathVariable String name){
        return new ResponseEntity<>(touristService.findAttractionByName(name), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction touristAttraction){
        return new ResponseEntity<>(touristService.addAttraction(touristAttraction), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<TouristAttraction> updateAttraction(@RequestBody TouristAttraction touristAttraction){
        return new ResponseEntity<>(touristService.updateAttraction(touristAttraction), HttpStatus.OK);
    }

    @PostMapping("/delete/{name}")
    public ResponseEntity<String> deleteAttraction(@PathVariable String name){
        return new ResponseEntity<>(touristService.deleteAttraction(name), HttpStatus.OK);
    }

}
