package vttp.day26_workshop.service;

import java.util.Date;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.day26_workshop.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    public JsonObject getAllGames(Integer limit, Integer offset) {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (Document doc : repository.getAllGames(limit, offset)) {

            JsonObject game = Json.createObjectBuilder()
                    .add("game_id", doc.getObjectId("_id").toString())
                    .add("name", doc.getString("name"))
                    .build();

            arrBuilder.add(game);
        }

        JsonArray gamesArr = arrBuilder.build();

        JsonObject json = Json.createObjectBuilder()
                .add("games", gamesArr)
                .add("offset", offset)
                .add("limit", limit)
                .add("total", repository.getGamesCount())
                .add("timestamp", new Date().toString())
                .build();

        return json;
    }

    public JsonObject getGamesByRank(Integer limit, Integer offset) {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (Document doc : repository.getGamesByRank(limit, offset)) {

            JsonObject game = Json.createObjectBuilder()
                    .add("game_id", doc.getObjectId("_id").toString())
                    .add("name", doc.getString("name"))
                    .build();

            arrBuilder.add(game);
        }

        JsonArray gamesArr = arrBuilder.build();

        JsonObject json = Json.createObjectBuilder()
                .add("games", gamesArr)
                .add("offset", offset)
                .add("limit", limit)
                .add("total", repository.getGamesCount())
                .add("timestamp", new Date().toString())
                .build();

        return json;
    }

    public Optional<JsonObject> getGameById(String id) {

        Optional<Document> opt = repository.getGameById(id);

        if (opt.isEmpty())
            return Optional.empty();

        Document doc = opt.get();

        return Optional.of(Json.createObjectBuilder()
                .add("game_id", doc.getObjectId("_id").toString())
                .add("name", doc.getString("name"))
                .add("year", doc.getInteger("year"))
                .add("ranking", doc.getInteger("ranking"))
                .add("users_rated", doc.getInteger("users_rated"))
                .add("url", doc.getString("url"))
                .add("thumbnail", doc.getString("image"))
                .add("timestamp", new Date().toString())
                .build());
    }

}
