package com.ify.box_drone.services.box;

import com.ify.box_drone.exceptions.BoxCannotBeLoadedException;
import com.ify.box_drone.models.dtos.BoxRequest;
import com.ify.box_drone.models.entities.Box;
import com.ify.box_drone.models.entities.Item;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoxService {
    Box createBox(BoxRequest request);
    @Transactional
    String loadBoxWithItems(String txref, List<Item> items) throws BoxCannotBeLoadedException;

    public List<Item> checkLoadedItemsForAGivenBox(String txref);
    List<Box> getAvailableBoxesForLoading();
    double getBoxBatteryLevel(String txref);
}
