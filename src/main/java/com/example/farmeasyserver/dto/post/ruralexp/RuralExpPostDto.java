package com.example.farmeasyserver.dto.post.ruralexp;

import com.example.farmeasyserver.dto.post.PostDto;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.user.Address;
import lombok.Data;

@Data
public class RuralExpPostDto extends PostDto {
    public static RuralExpPostDto toDto(Post post){
        return new RuralExpPostDto();
    }
}
