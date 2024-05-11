package com.example.farmeasyserver.repository.post.community;

import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommunityFilter {
    private CommunityType type;
    private CropCategory crop;
    private String search;

}
