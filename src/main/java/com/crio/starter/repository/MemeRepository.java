package com.crio.starter.repository;

import com.crio.starter.data.MemeEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemeRepository extends MongoRepository<MemeEntity, String> {

  boolean existsByNameAndCaptionAndUrl(String name, String caption, String url);

  List<MemeEntity> findTop100ByOrderByCreatedAtDesc();
}
