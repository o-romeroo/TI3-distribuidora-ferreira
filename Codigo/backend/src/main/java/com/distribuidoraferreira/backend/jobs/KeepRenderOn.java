package com.distribuidoraferreira.backend.jobs;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KeepRenderOn {

    @Scheduled(fixedRate = (1000 * 60 * 14))
    public void executeTask() throws ClientProtocolException, IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet("https://pmg-es-2024-1-ti3-9577100-distribuidora.onrender.com/keeprenderon");
        HttpResponse response = httpClient.execute(request);

        System.out.println(response.getEntity());
    }
}
