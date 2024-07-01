package dev.gabrielmumo.demo.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoUtils {

    @Value("${dev.gabrielmumo.utils.youtube-url-pattern}")
    private static String YOUTUBE_URL_PATTERN;

    public static String getVideoId(String videoUrl) {
        String videoId = "";
        Pattern pattern = Pattern.compile(YOUTUBE_URL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);
        if(matcher.find()){
            videoId = matcher.group(1);
        }
        return videoId;
    }
}
