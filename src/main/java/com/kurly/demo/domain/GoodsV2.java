package com.kurly.demo.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("GoodsInfoV2")
@Setter
//@JsonIgnoreProperties(value={"description"})
public class GoodsV2 {

    @Id
    @GeneratedValue
    @Column(name = "goods_id")
    private Long id;

    private String img;
    private int price;

    @Size(min=2, message = "Name은 2글자 이상 입력해주세요")
    private String name;

    //@JsonIgnore
    private String description;

    private String storage;

}
