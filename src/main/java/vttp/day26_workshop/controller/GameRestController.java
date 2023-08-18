package vttp.day26_workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import vttp.day26_workshop.service.GameService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class GameRestController {

    @Autowired
    private GameService service;
    
    @GetMapping("/games")
    public ResponseEntity<String> getAllGames(
        @RequestParam(name = "limit", defaultValue = "25") Integer limit,
        @RequestParam(name = "offset", defaultValue = "0") Integer offset) {

        return ResponseEntity.ok(service.getAllGames(limit, offset).toString());
    }
    
    @GetMapping("/games/rank")
    public ResponseEntity<String> getGamesByRank(
        @RequestParam(name = "limit", defaultValue = "25") Integer limit,
        @RequestParam(name = "offset", defaultValue = "0") Integer offset) {

        return ResponseEntity.ok(service.getGamesByRank(limit, offset).toString());
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<String> getGameById(@PathVariable(name = "id") String id) {

        Optional<JsonObject> opt = service.getGameById(id);

        if (opt.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(opt.get().toString());
    }
}
