package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.post.UpdatePostRequest;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import lombok.Data;


@Data
public class UpdateCommPostReq extends UpdatePostRequest {
    private CommunityType type;
}
