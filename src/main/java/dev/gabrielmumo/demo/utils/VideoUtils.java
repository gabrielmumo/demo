package dev.gabrielmumo.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VideoUtils {

    @Value("${dev.gabrielmumo.utils.youtube-url-pattern}")
    private String YOUTUBE_URL_PATTERN;

    public String getVideoId(String videoUrl) {
        String videoId = "";
        Pattern pattern = Pattern.compile(YOUTUBE_URL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);
        if(matcher.find()){
            videoId = matcher.group(1);
        }
        return videoId;
    }
}
