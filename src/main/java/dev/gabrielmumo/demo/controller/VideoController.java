package dev.gabrielmumo.demo.controller;

import dev.gabrielmumo.demo.dto.VideoLecture;
import dev.gabrielmumo.demo.service.YoutubeVideoService;
import io.github.thoroldvix.api.TranscriptRetrievalException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private final YoutubeVideoService youtubeVideoService;

    @Autowired
    public VideoController(YoutubeVideoService youtubeVideoService) {
        this.youtubeVideoService = youtubeVideoService;
    }

    @PostMapping
    public ResponseEntity<String> saveVideoLecture(@Valid @RequestBody VideoLecture.Request videoLecture)
            throws TranscriptRetrievalException {
        return ResponseEntity.ok(youtubeVideoService.getVideoTranscript(videoLecture.videoUrl()));
    }
}
