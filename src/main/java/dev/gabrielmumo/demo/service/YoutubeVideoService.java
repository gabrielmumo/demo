package dev.gabrielmumo.demo.service;


import io.github.thoroldvix.api.TranscriptRetrievalException;

public interface YoutubeVideoService {

    public String getVideoTranscript(String videoUrl) throws TranscriptRetrievalException;
}
