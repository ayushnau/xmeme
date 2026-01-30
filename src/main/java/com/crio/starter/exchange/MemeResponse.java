package com.crio.starter.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemeResponse {
  private String id;
  private String name;
  private String caption;
  private String url;
}
