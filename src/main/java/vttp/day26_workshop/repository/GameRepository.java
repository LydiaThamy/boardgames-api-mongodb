package vttp.day26_workshop.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository {

    @Autowired
    private MongoTemplate template;

    public static final String C_GAMES = "games";
    public static final String F_ID = "_id";
    public static final String F_GID = "gid";
    public static final String F_NAME = "name";
    public static final String F_RANKING = "ranking";

    public List<Document> getAllGames(Integer limit, Integer offset) {

        Query query = Query.query(
                Criteria.where(F_ID).exists(true))
                .with(
                        Sort.by(Sort.Direction.ASC, F_NAME)
                );

        query
                .skip(offset)
                .limit(limit)
                .fields()
                .include(F_NAME);

        return template.find(query, Document.class, C_GAMES);
    }

    public long getGamesCount() {

        Query query = Query.query(
                Criteria.where(F_ID).exists(true));

        return template.count(query, Document.class, C_GAMES);
    }

    public List<Document> getGamesByRank(Integer limit, Integer offset) {

        Query query = Query.query(
                Criteria.where(F_ID).exists(true)).with(
                        Sort.by(Sort.Direction.ASC, F_RANKING));

        query
                .skip(offset)
                .limit(limit)
                .fields()
                .include(F_NAME);

        return template.find(query, Document.class, C_GAMES);
    }

    public Optional<Document> getGameById(String id) {
        return Optional.ofNullable(
                template.findById(
                        new ObjectId(id), Document.class, C_GAMES));
    }
}
