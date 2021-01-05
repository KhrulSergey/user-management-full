package com.khsa.usermanagement.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ArrayUtils;

import static java.util.Objects.isNull;

@Component
public class SearchUtil {
//    private final AuthorizedUserService authorizedUserService;

    @Autowired
    public SearchUtil() {
//        this.authorizedUserService = authorizedUserService;
    }


    private static Sort sort(Sort.Direction direction, String... properties) {
        Sort sort;
        if (isNull(direction)) direction = Sort.Direction.DESC;
        if (isNull(properties)) properties = new String[]{"id"};
        return Sort.by(direction, properties);
    }

    public static PageRequest pageable(Integer page, Integer size, Sort.Direction direction, String... properties) {
            return PageRequest.of(page, size, sort(direction, properties));
    }

    public static PageRequest pageable(Integer page,
                                       Integer size,
                                       Sort.Direction direction,
                                       Sort.Direction defaulDirection,
                                       String defaultProperty,
                                       String... properties) {
        if (isNull(direction)) {
            direction = defaulDirection;
        }
        if (ArrayUtils.isEmpty(properties)) {
            properties = new String[]{defaultProperty};
        }
        return PageRequest.of(page, size, sort(direction, properties));
    }
}
