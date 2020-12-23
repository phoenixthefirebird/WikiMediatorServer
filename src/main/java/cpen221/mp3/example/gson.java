package cpen221.mp3.example;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class gson {

    //sample program
    public static void main(String[] args) {
        String jsonString = "{ \"id\": \"two\", \"type\": \"trending\", \"limit\": \"5\" }";
        Request request = new Gson().fromJson(jsonString, Request.class);
        System.out.println(request);
        String[] seed = {"Barack Obama", "Barack Obama in comics", "Barack Obama Sr.", "List of things named after Barack Obama", "Speeches of Barack Obama"};
        List<String> result = Arrays.asList(seed);
        Response<List<String>> response = new Response<>("2", "success", result);
        String json = new Gson().toJson(response);
        System.out.println(json);
    }

    /**
     * this class holds the information parsed from json in the requests
     */
    private static class Request {
        String id;
        String type;
        String query;
        String getPage;
        int limit;
        int timeout;
    }

    /**
     * this class holds the information parsed from response object
     */
    private static class Response<T> {
        String id;
        String status;
        T response;

        public Response(String id, String status, T response) {
            this.id = id;
            this.status = status;
            this.response = response;
        }

        public Response(String id, T response) {
            this.id = id;
            this.response = response;
        }
    }

}
