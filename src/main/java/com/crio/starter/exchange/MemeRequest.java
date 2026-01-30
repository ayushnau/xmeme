package com.crio.starter.exchange;

import lombok.Data;

@Data
public class MemeRequest {
  private String name;
  private String caption;
  private String url;
}
