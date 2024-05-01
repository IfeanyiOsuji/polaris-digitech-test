package com.ify.box_drone.controller;

import com.ify.box_drone.exceptions.BoxCannotBeLoadedException;
import com.ify.box_drone.models.dtos.BoxRequest;
import com.ify.box_drone.models.entities.Box;
import com.ify.box_drone.models.entities.Item;
import com.ify.box_drone.services.box.BoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/box")
public class BoxController {

    private final BoxService boxService;
    public BoxController(BoxService boxService){
        this.boxService = boxService;

    }

    @PostMapping("/create-box")
    public ResponseEntity<Box> registerDrone(@RequestBody BoxRequest request){
            Box box =  boxService.createBox(request);
        return new ResponseEntity<Box>((box), null, HttpStatus.CREATED);
    }

    @PutMapping("/items/id/{id}")
    public ResponseEntity<String> loadBoxWithItems(@PathVariable String id, @RequestBody List<Item> items) throws BoxCannotBeLoadedException {
        String response = boxService.loadBoxWithItems(id, items);
        return new ResponseEntity<String>((response), null, HttpStatus.OK);
    }

    @GetMapping("/available-boxes")
    public ResponseEntity<List<Box>>checkAvailableBoxesForLoading(){
        List<Box> boxRespnses = boxService.getAvailableBoxesForLoading();
        return new ResponseEntity<List<Box>>((boxRespnses), null, HttpStatus.OK);
    }

    @GetMapping("/box/batterylevel/id/{id}")
    public ResponseEntity<String>checkBoxBatteryLevel(@PathVariable String id){
        double batteryLevel = boxService.getBoxBatteryLevel(id);
        return new ResponseEntity<>(("Battery level for Box with txref "+id+" is " +batteryLevel+"%"), null, HttpStatus.OK);
    }

    @GetMapping("/items/box/id/{id}")
    public ResponseEntity<List<Item>>checkLoadedItemsForBox(@PathVariable String id){
        return new ResponseEntity<>((boxService.checkLoadedItemsForAGivenBox(id)), null, HttpStatus.OK);
    }


}
