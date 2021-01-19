package com.khsa.usermanagement.util;

import com.khsa.usermanagement.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Comparator;

import static java.util.Objects.nonNull;

@Component
public class UserComparator implements Comparator<User> {

    //TODO fix method and implement using
    @Override
    public int compare(User o1, User o2) {
        if (nonNull(o1.getUsername()) && nonNull(o2.getUsername())) {
            int result =  o1.getUsername().compareTo(o2.getUsername()) +
                    o1.getName().compareTo(o2.getName());

          return result;
        }
        return -1;
    }
}
