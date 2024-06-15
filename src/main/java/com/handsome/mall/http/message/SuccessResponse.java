package com.handsome.mall.http.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessResponse<T> {
    private String status;
    private String message;
    private T data;

}
