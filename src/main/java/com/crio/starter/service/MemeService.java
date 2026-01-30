package com.crio.starter.service;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeRequest;
import com.crio.starter.repository.MemeRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MemeService {

  private final MemeRepository memeRepository;

  public MemeService(MemeRepository memeRepository) {
    this.memeRepository = memeRepository;
  }

  public MemeEntity createMeme(MemeRequest request) {

    if (request.getName() == null ||
        request.getCaption() == null ||
        request.getUrl() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (memeRepository.existsByNameAndCaptionAndUrl(
        request.getName(), request.getCaption(), request.getUrl())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    MemeEntity meme = new MemeEntity();
    meme.setName(request.getName());
    meme.setCaption(request.getCaption());
    meme.setUrl(request.getUrl());
    meme.setCreatedAt(Instant.now());

    return memeRepository.save(meme);
  }

  public List<MemeEntity> getLatestMemes() {
    return memeRepository.findTop100ByOrderByCreatedAtDesc();
  }

  public MemeEntity getMemeById(String id) {
    return memeRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
