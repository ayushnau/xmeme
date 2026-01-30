package com.crio.starter.data;

import java.time.Instant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "memes")
public class MemeEntity {

  @Id
  private String id;

  private String name;
  private String caption;
  private String url;

  private Instant createdAt;
}
