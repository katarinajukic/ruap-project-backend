package com.example.ruap.machine_learning;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.util.concurrent.CompletableFuture;

public class CallRequestResponseService {

    public static void main(String[] args) {
        try {
            invokeRequestResponseService().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static CompletableFuture<Void> invokeRequestResponseService() {
        HttpClient client = HttpClient.newBuilder().build();

        URI uri = URI.create("http://d8e263fa-13c0-4f0e-b374-b7e4e66be6e2.westeurope.azurecontainer.io/score");

        String requestBody = getPredictionInputs();

        String apiKey = "o84N8NkdpKnrOBeeeKca53K82pFJD1sP";

        if (apiKey.isEmpty()) {
            throw new RuntimeException("A key should be provided to invoke the endpoint");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        String result = response.body();
                        System.out.println("Result: " + result);
                    } else {
                        System.out.println("The request failed with status code: " + response.statusCode());
                        HttpHeaders headers = response.headers();
                        System.out.println(headers);
                        String responseContent = response.body();
                        System.out.println(responseContent);
                    }
                });
    }

    public static String getPredictionInputs() {
        return "{\n" +
                "  \"Inputs\": {\n" +
                "    \"input1\": [\n" +
                "      {\n" +
                "        \"Age\": \"\",\n" +
                "        \"Gender\": \"\",\n" +
                "        \"Air Pollution\": \"\",\n" +
                "        \"Alcohol use\": \"\",\n" +
                "        \"Dust Allergy\": \"\",\n" +
                "        \"OccuPational Hazards\": \"\",\n" +
                "        \"Genetic Risk\": \"\",\n" +
                "        \"Chronic Lung Disease\": \"\",\n" +
                "        \"Balanced Diet\": \"\",\n" +
                "        \"Obesity\": \"\",\n" +
                "        \"Smoking\": \"\",\n" +
                "        \"Passive Smoker\": \"\",\n" +
                "        \"Chest Pain\": \"\",\n" +
                "        \"Coughing of Blood\": \"\",\n" +
                "        \"Fatigue\": \"\",\n" +
                "        \"Weight Loss\": \"\",\n" +
                "        \"Shortness of Breath\": \"\",\n" +
                "        \"Wheezing\": \"\",\n" +
                "        \"Swallowing Difficulty\": \"\",\n" +
                "        \"Clubbing of Finger Nails\": \"\",\n" +
                "        \"Frequent Cold\": \"\",\n" +
                "        \"Dry Cough\": \"\",\n" +
                "        \"Snoring\": \"\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"GlobalParameters\": {}\n" +
                "}";
    }
}


