package com.project.kcookserver.util.location;

import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NaverGeocode {

    @Value("${naver.API_KEY_ID}")
    private String X_NCP_APIGW_API_KEY_ID;

    @Value("${naver.API_KEY}")
    private String X_NCP_APIGW_API_KEY;

    public String getCoordinate(String address) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-NCP-APIGW-API-KEY-ID", X_NCP_APIGW_API_KEY_ID);
        headers.add("X-NCP-APIGW-API-KEY", X_NCP_APIGW_API_KEY);
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity =
                rest.exchange("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+address, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        JSONArray addressesArray = new JSONObject(response).getJSONArray("addresses");
        if (addressesArray.length() == 0) throw new CustomException(CustomExceptionStatus.LOCATION_NOT_VALID);
        JSONObject addresses = new JSONObject(addressesArray.get(0).toString());
        String x = addresses.get("x").toString();
        String y = addresses.get("y").toString();
        return x + "," + y;
    }


}
