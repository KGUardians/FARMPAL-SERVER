package com.example.farmeasyserver.dto.post.experience;

import lombok.Data;

@Data
public class ExpApplicationRequest {

    private Long postId;
    private String name;
    private String phoneNumber;
    private int participants;
}
