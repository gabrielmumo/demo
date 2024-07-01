package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.utils.VideoUtils;
import io.github.thoroldvix.api.TranscriptContent;
import io.github.thoroldvix.api.TranscriptFormatter;
import io.github.thoroldvix.api.TranscriptFormatters;
import io.github.thoroldvix.api.TranscriptRetrievalException;
import io.github.thoroldvix.api.YoutubeTranscriptApi;
import io.github.thoroldvix.internal.TranscriptApiFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class YoutubeVideoServiceImpl implements YoutubeVideoService {

    private final TranscriptFormatter textFormatter;

    @Autowired
    public YoutubeVideoServiceImpl(@Qualifier("textFormatter") TranscriptFormatter textFormatter) {
        this.textFormatter = textFormatter;
    }

    public String getVideoTranscript(String videoUrl) throws TranscriptRetrievalException {
        String videoId = VideoUtils.getVideoId(videoUrl);
        YoutubeTranscriptApi youtubeTranscriptApi = TranscriptApiFactory.createDefault();
        TranscriptContent transcriptContent = youtubeTranscriptApi.getTranscript(videoId, "en");
        return textFormatter.format(transcriptContent);
    }
}
