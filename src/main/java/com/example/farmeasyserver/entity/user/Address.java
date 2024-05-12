package com.example.farmeasyserver.entity.user;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long zipcode;//우편번호
    private String address;//경기 수원시 장안구 창훈로52번길 22
    private String sido;//경기도
    private String sigungu;//수원시 장안구
}
