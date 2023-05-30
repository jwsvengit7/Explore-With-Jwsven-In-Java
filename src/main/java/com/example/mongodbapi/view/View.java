package com.example.mongodbapi.view;

import com.example.mongodbapi.service.Impl.ServiceApiImplementation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class View implements Runnable {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new View());

        executor.shutdown();
    }

    @Override
    public void run()  {
        ServiceApiImplementation serviceApiImplementation = new ServiceApiImplementation(new ModelMapper(), new RestTemplate(), new HttpHeaders());
        System.out.println(serviceApiImplementation.getCoinByName("bitcoin"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println(serviceApiImplementation.getValueByRank(4));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(serviceApiImplementation.getCoinByName("ethereum"));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(serviceApiImplementation.getCoinByHighestPrice());
    }
}
