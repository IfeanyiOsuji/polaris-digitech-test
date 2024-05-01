package com.ify.box_drone.services.box;

import com.ify.box_drone.exceptions.BoxCannotBeLoadedException;
import com.ify.box_drone.models.dtos.BoxRequest;
import com.ify.box_drone.models.entities.Box;
import com.ify.box_drone.models.entities.Item;
import com.ify.box_drone.models.enums.State;
import com.ify.box_drone.repository.BoxRepository;
import com.ify.box_drone.repository.ItemRepository;
import com.ify.box_drone.utils.EventLogger;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BoxService {
    private final BoxRepository repository;
    private final ItemRepository itemRepository;

    public BookServiceImpl(BoxRepository repository, ItemRepository itemRepository){
        this.repository = repository;
        this.itemRepository = itemRepository;

    }
    @Override
    public Box createBox(BoxRequest request) {
        Box box = new Box(request.getWeight(), request.getBatteryCapacity());
        return repository.save(box);
    }

    @Override
    @Transactional
    public String loadBoxWithItems(String txref, List<Item> items) throws BoxCannotBeLoadedException {
        itemRepository.saveAll(items);

        Optional<Box>optional = repository.findById(txref);
        if(optional.isPresent()){
            Box box = optional.get();
            if(!isBoxAvailableForLoading(box.getTxref())){
                throw new BoxCannotBeLoadedException("Box with txref %s not available for loading", box.getTxref());
            };

            items.stream().forEach(x->{
                if((isBoxAvailableForLoading(box.getTxref())) && ((box.getWeight() + x.getWeight()) <= 500)) {
                    box.setState(State.LOADING);
                    box.getLoadedItems().add(x);
                    box.setWeight(box.getWeight() + x.getWeight());
                    if(box.getWeight() >= 500){
                        box.setState(State.LOADED);

                    }


                }else EventLogger.LOGGER.info("Box with txref {} cannot load this item with code {}", box.getTxref(), x.getCode());
            });
           repository.save(box);
           return "Items within loading weight range loaded successfully";
        }
        else throw new NullPointerException("Box with txref "+txref+" not found");


    }

    @Override
    public List<Item> checkLoadedItemsForAGivenBox(String txref) {
        Optional <Box> optional = repository.findById(txref);
        if(!optional.isPresent()){
            throw new NullPointerException("drone with id "+txref+" not found");
        }
        Box box = optional.get();
        return box.getLoadedItems();
    }

    @Override
    public List<Box> getAvailableBoxesForLoading() {
        List<Box>boxes = repository.findAll();
        return boxes.stream().filter(box -> (box.getState().equals(State.IDLE) && isBoxAvailableForLoading(box.getTxref())) || box.getState().equals(State.LOADING))
                .toList();
    }


    public boolean isBoxAvailableForLoading(String trxref){
        if(getBoxWeight(trxref) > 500.0 || checkBoxBatteryLevel(trxref) < 25.0) {
            EventLogger.LOGGER.info("Please check Box limit capacity and/or battery level");
            return false;
        }
        return true;
    }

    public double checkBoxBatteryLevel(String txref){
        return getBoxBatteryLevel(txref);
    }

    public double getBoxWeight(String txref){
        Optional<Box> optional = repository.findById(txref);
        if(!optional.isPresent()){
            throw new NullPointerException(String.format("Box with %s not found", txref));
        }
        return optional.get().getWeight();
    }

    public double getBoxBatteryLevel(String txref){
        Optional <Box>optional = repository.findById(txref);
        if(optional.isPresent()){
            Box box = optional.get();
            return box.getBatteryCapacity();

        }
        else throw new NullPointerException(String.format("Box with %s not found", txref));
    }
}
