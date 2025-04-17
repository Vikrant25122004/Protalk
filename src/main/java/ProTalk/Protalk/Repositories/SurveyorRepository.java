package ProTalk.Protalk.Repositories;

import ProTalk.Protalk.Entity.Surveyors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyorRepository extends MongoRepository<Surveyors, ObjectId> {
    Surveyors findByusername( String username);
}
