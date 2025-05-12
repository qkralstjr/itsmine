package com.itsmine.itsmine.global.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public DefaultUriBuilderFactory uriBuilderFactory() {
//        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
//        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
//        return factory;
//    }
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        Jaxb2RootElementHttpMessageConverter xmlConverter = new Jaxb2RootElementHttpMessageConverter();// 알 수 없는 프로퍼티 무시
//        return builder.messageConverters(xmlConverter).build();
//    }

//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        //add 스프링이 등록해주는 컨버터도 그대로 사용
//        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
//        return restTemplate;
//    }

}