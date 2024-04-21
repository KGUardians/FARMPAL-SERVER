package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.post.PostDto;
import com.example.farmeasyserver.entity.board.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommunityPostDto extends PostDto {

    public static CommunityPostDto toDto(Post post){
        return new CommunityPostDto();
    }
}
