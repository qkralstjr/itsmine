package com.itsmine.itsmine.global.config;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
/*
XMl 메세지컨버터 등록
        // Jaxb2RootElementHttpMessageConverter "text/xml;charset=UTF-8"
        Jaxb2RootElementHttpMessageConverter xmlConverter = new Jaxb2RootElementHttpMessageConverter();
        xmlConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.TEXT_XML,
                MediaType.APPLICATION_XML,
                new MediaType("text", "xml", StandardCharsets.UTF_8)
        ));
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        converters.add(xmlConverter);
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(xmlConverter);
        converters.addAll(restTemplate.getMessageConverters());
        restTemplate.setMessageConverters(converters);*/
        return restTemplate;
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