package com.example.demo.remoteClients;

import com.example.demo.dto.DictionaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CONTENT-ADMIN")
public interface ContentAdminClient {

    @GetMapping({"/dictionary/{name}"})
    ResponseEntity<DictionaryDTO> getDictionaryByName(@PathVariable String name);
}
