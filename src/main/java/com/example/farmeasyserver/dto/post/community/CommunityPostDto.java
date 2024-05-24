package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CommunityPostDto {
    private Long postId;
    private CommunityAuthorDto author;
    private String title;
    private int postLike;
    private CommunityType communityType;
    private CropCategory cropCategory;
    private String content;
    private List<ImageDto> imageList;

    public static CommunityPostDto toDto(CommunityPost post){
        return new CommunityPostDto(
                post.getId(),
                CommunityAuthorDto.toDto(post.getAuthor()),
                post.getTitle(),
                post.getPostLike(),
                post.getCommunityType(),
                post.getCropCategory(),
                post.getContent(),
                post.getImageList().stream().map(i->ImageDto.toDto(i, i.getId())).collect(Collectors.toList())
        );
    }
}
