package com.kurly.demo.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long userId;
    private String address;

}
