package com.itsmine.itsmine.publicFoundItem.config;

import com.itsmine.itsmine.publicFoundItem.domain.PublicFoundItem;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublicFoundItemQueueConfig {

    @Bean
    public BlockingQueue<PublicFoundItem> publicFoundItemQueue(){
        return new LinkedBlockingDeque<>(10_000);
    }

    @Bean
    public ExecutorService publicFoundItemExecutor(){
        return Executors.newFixedThreadPool(5);
    }

}
