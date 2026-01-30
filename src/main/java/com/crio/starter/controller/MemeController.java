package com.crio.starter.controller;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeIdResponse;
import com.crio.starter.exchange.MemeRequest;
import com.crio.starter.exchange.MemeResponse;
import com.crio.starter.service.MemeService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/memes")
public class MemeController {

  private final MemeService memeService;

  public MemeController(MemeService memeService) {
    this.memeService = memeService;
  }

  // GET /memes/
  @GetMapping("/")
  public List<MemeResponse> getMemes() {
    return memeService.getLatestMemes()
        .stream()
        .map(m -> new MemeResponse(
            m.getId(),
            m.getName(),
            m.getCaption(),
            m.getUrl()))
        .collect(Collectors.toList());
  }

  // POST /memes/
  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public MemeIdResponse createMeme(@RequestBody MemeRequest request) {
    MemeEntity saved = memeService.createMeme(request);
    return new MemeIdResponse(saved.getId());
  }

  // GET /memes/{id}
  @GetMapping("/{id}")
  public MemeResponse getMeme(@PathVariable String id) {
    MemeEntity meme = memeService.getMemeById(id);
    return new MemeResponse(
        meme.getId(),
        meme.getName(),
        meme.getCaption(),
        meme.getUrl());
  }
}
