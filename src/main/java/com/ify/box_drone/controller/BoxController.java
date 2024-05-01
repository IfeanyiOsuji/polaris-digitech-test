package com.ify.box_drone.controller;

import com.ify.box_drone.models.dtos.BoxRequest;
import com.ify.box_drone.models.entities.Box;
import com.ify.box_drone.services.box.BoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/box")
public class BoxController {

    private final BoxService boxService;
    public BoxController(BoxService boxService){
        this.boxService = boxService;

    }

    @PostMapping("/create-box")
    public ResponseEntity registerDrone(@RequestBody BoxRequest request){
            Box box =  boxService.createBox(request);
        return new ResponseEntity<Box>((box), null, HttpStatus.CREATED);
    }


}
