package com.kurly.demo.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("GoodsInfo")
//@JsonIgnoreProperties(value={"description"})
@ApiModel(description = "판매상품")
public class Goods {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "상품 번호")
    @Column(name = "goods_id")
    private Long id;

    @ApiModelProperty(notes = "상품 이미지 경로")
    @NotNull(message = "이미지는 필수값입니다.")
    private String img;

    @ApiModelProperty(notes = "상품 가격")
    @NotNull(message = "가격은 필수값입니다.")
    private int price;

    @ApiModelProperty(notes = "상품명")
    @Size(min=2, message = "상품명은 2글자 이상 입력해주세요")
    private String name;

    //@JsonIgnore
    @ApiModelProperty(notes = "상품 설명")
    private String description;

}
