package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.math.BigDecimal;

@RestController

public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clienId = requestDTO.getClientId();
            ResponseDTO responseDTO = new ResponseDTO();
            char firstDigit = clienId.charAt(0);
            BigDecimal maxLimit;
            String rqUID = requestDTO.getRqUID();
            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000.00);
                responseDTO.setCurrency("US");
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000.00);
                responseDTO.setCurrency("EU");
            } else {
                maxLimit = new BigDecimal(10000.00);
                responseDTO.setCurrency("RU");
            }
            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clienId);
            responseDTO.setAccount(requestDTO.getAccount());
            BigDecimal result = BigDecimal.valueOf(Math.round(Math.random() * maxLimit.doubleValue() * 100.0) / 100.0) ;
            responseDTO.setBalance(result);
            responseDTO.setMaxLimit(maxLimit);

            log.error("********** RequestDTO ***********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("********** ResponseDTO ***********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
            return responseDTO;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
