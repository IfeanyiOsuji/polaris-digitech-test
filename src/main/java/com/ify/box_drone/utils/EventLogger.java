package com.ify.box_drone.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public  class EventLogger {
    public static final Logger LOGGER = LogManager.getLogger(EventLogger.class);


}
