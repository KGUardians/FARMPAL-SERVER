package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.dto.post.UpdatePostRequest;

import lombok.Data;

@Data
public class UpdateMarPostReq extends UpdatePostRequest {
    private String itemName;
    private int price;
    private int gram;
}
