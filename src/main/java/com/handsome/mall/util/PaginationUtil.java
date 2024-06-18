package com.handsome.mall.util;

import com.handsome.mall.valueobject.SortField;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {

    public static Pageable createPageRequest(int page, int size, String sort, String orderBy) {
           Sort.Direction direction = Sort.Direction.fromString(orderBy);
           return PageRequest.of(page, size, Sort.by(direction, SortField.valueOf(sort).getField()));


    }
}
