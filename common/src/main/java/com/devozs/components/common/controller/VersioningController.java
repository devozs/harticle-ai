package com.devozs.components.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("version")
public class VersioningController {

    @Autowired
    public VersioningController(){
    }

    @GetMapping()
    public String getReleaseVersion() {
        return "0.0.1";
    }

    @GetMapping("/{imageName}")
    public String getServiceVersion(@PathVariable String imageName) throws Exception {
        return imageName;
    }

}
