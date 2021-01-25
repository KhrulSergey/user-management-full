package com.khsa.usermanagement.util;

import com.khsa.usermanagement.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


/**
 * Implement comparator for 'User' model with comparing (id, UserName, Name and Gender)
 */
@Component
public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        if (isNull(o1) || isNull(o2)) {
            return -1;
        }

        if (o1.getId() == o2.getId() && o1.getId() != 0) {
            return 0;
        }

        HashMap<String, Integer> usernameEq = new HashMap<>();
        if (nonNull(o1.getUsername()) && nonNull(o2.getUsername())) {
            usernameEq.put("username", o1.getUsername().compareTo(o2.getUsername()));
        }
        if (nonNull(o1.getName()) && nonNull(o2.getUsername())) {
            usernameEq.put("name", o1.getName().compareTo(o2.getName()));
        }
        if (nonNull(o1.getGender()) && nonNull(o2.getGender())) {
            usernameEq.put("gender", o1.getGender().compareTo(o2.getGender()));
        }

        HashMap<Integer, Integer> repeatSignMap = new HashMap<>();
        for (Integer compValue : usernameEq.values()) {
            int value =  compValue != 0 ? Integer.signum(compValue) : 0;
            Integer repeatSign = repeatSignMap.get(value);
            if (isNull(repeatSign)) {
                repeatSignMap.put(value, 1);
            } else {
                repeatSignMap.put(value, repeatSign + 1);
            }
        }
        int result = Collections.max(repeatSignMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        if(result == 0 && repeatSignMap.get(result) != usernameEq.size()){
            result = -1;
        }

        return result;
    }
}
