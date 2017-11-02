package controllers;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static tools.Constants.remote_db_base_url;
import static tools.Constants.remote_search_base_url;

@RestController
public class HttpController {

    private static final String base_url = "/api/v1/";

    @RequestMapping(value = base_url + "searchitems", method = GET)
    public String elasticSearchItems(@RequestParam String query, @RequestParam(value="type", defaultValue="") String type) {
        final String uri = remote_search_base_url +"/items/searchitems";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("query", query);
        if(!type.isEmpty()){
            builder.queryParam("type", type);
        }
        ResponseEntity<String> result = getUrl(builder);
        return result.getBody();
    }

    @RequestMapping(value = base_url + "item", method = GET)
    public String getCouchDBItem(@RequestParam String id) {
        final String uri = remote_db_base_url +"/product";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id", id);
        ResponseEntity<String> result = getUrl(builder);
        return result.getBody();
    }

    @RequestMapping(value = base_url + "items_all", method = GET)
    public String getCouchDBAllItems() {
        final String uri = remote_db_base_url +"/allProducts";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        ResponseEntity<String> result = getUrl(builder);
        return result.getBody();
    }


    private ResponseEntity<String> getUrl(   UriComponentsBuilder builder){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
    }

}