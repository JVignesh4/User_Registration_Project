package com.bridgelabz.userregistration.batch;

import com.bridgelabz.userregistration.model.UserData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<UserData, UserData> {

    public Processor() {

    }

    @Override
    public UserData process(UserData userData) throws Exception {
        return userData;
    }
}
