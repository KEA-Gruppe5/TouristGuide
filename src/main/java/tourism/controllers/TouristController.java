package tourism.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("")
    public String getAllAttractions(Model model){
        model.addAttribute("attractions", touristService.getAllAttractions());
        return "attractionList";
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> findAttractionByName(@PathVariable String name){
        return new ResponseEntity<>(touristService.findAttractionByName(name), HttpStatus.OK);
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTags(@PathVariable String name, Model model){
        model.addAttribute("tags", null);
        return "tags";
        // TODO: write service and repo, add service method to attributeValue to render on html
    }

    @GetMapping("/add")
    public String addAttraction(){
        return "addAttraction"; //TODO: make html form
    }

    @PostMapping("/save")
    public ResponseEntity<TouristAttraction> saveAttraction(@RequestBody TouristAttraction touristAttraction){
        return new ResponseEntity<>(touristService.saveAttraction(touristAttraction), HttpStatus.CREATED);
    }

    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name){
        return null;
        // TODO: make html form
    }

    @PostMapping("/update")
    public ResponseEntity<TouristAttraction> updateAttraction(@RequestBody TouristAttraction touristAttraction){
        return new ResponseEntity<>(touristService.updateAttraction(touristAttraction), HttpStatus.OK);
    }

    @PostMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name){
        touristService.deleteAttraction(name);
        return "redirect:/attractions";
    }

}