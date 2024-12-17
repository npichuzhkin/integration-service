package com.example.demo.remote.clients;

import com.example.demo.dto.DictionaryRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CONTENT-ADMIN")
public interface ContentAdminClient {

    @GetMapping({"/dictionary/{name}"})
    ResponseEntity<DictionaryRequestDTO> getDictionaryByName(@PathVariable String name);
}
