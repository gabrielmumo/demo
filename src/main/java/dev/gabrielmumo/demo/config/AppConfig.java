package dev.gabrielmumo.demo.config;

import io.github.thoroldvix.api.TranscriptFormatter;
import io.github.thoroldvix.api.TranscriptFormatters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "textFormatter")
    public TranscriptFormatter textFormatter() {
        return TranscriptFormatters.textFormatter();
    }
}
