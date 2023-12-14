package tt.hashtranslator.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tt.hashtranslator.entity.Application;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, Long> {

    @Query("{id:'?0'}")
    Optional<Application> findApplicationById(ObjectId id);

}
