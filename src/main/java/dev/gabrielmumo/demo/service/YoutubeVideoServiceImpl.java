package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.utils.VideoUtils;
import io.github.thoroldvix.api.TranscriptContent;
import io.github.thoroldvix.api.TranscriptFormatter;
import io.github.thoroldvix.api.TranscriptRetrievalException;
import io.github.thoroldvix.api.YoutubeTranscriptApi;
import io.github.thoroldvix.internal.TranscriptApiFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class YoutubeVideoServiceImpl implements YoutubeVideoService {

    private final TranscriptFormatter textFormatter;
    private final VideoUtils videoUtils;

    @Autowired
    public YoutubeVideoServiceImpl(@Qualifier("textFormatter") TranscriptFormatter textFormatter,
                                   VideoUtils videoUtils) {
        this.textFormatter = textFormatter;
        this.videoUtils = videoUtils;
    }

    public String getVideoTranscript(String videoUrl) throws TranscriptRetrievalException {
        String videoId = videoUtils.getVideoId(videoUrl);
        YoutubeTranscriptApi youtubeTranscriptApi = TranscriptApiFactory.createDefault();
        TranscriptContent transcriptContent = youtubeTranscriptApi.getTranscript(videoId, "en");
        return textFormatter.format(transcriptContent);
    }
}
