package controllers;


import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.concurrent.Future;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HttpController {

    private static final String base_url = "/api/v1/";

    @RequestMapping(value = base_url + "searchitems", method = GET)
    public String elasticSearchItems(@RequestParam String query, @RequestParam(value="type", defaultValue="") String type) {
        final String uri = "http://localhost:8081/items/searchitems";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("query", query);
        if(!type.isEmpty()){
            builder.queryParam("type", type);
        }
        ResponseEntity<String> result = getUrl(builder);
        return result.getBody();
    }

    private ResponseEntity<String> getUrl(   UriComponentsBuilder builder){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        System.out.println(builder.build().encode().toUri());
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
    }

}