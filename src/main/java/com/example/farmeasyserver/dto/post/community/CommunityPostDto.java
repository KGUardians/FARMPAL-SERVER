package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.post.PostDto;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CommunityPostDto extends PostDto {
    private Long postId;
    private CommunityAuthorDto author;
    private String title;
    private int postLike;
    private CommunityType communityType;
    private String content;
    private List<ImageDto> imageList;

    public static CommunityPostDto toDto(CommunityPost post){
        return new CommunityPostDto(
                post.getId(),
                CommunityAuthorDto.toDto(post.getAuthor()),
                post.getTitle(),
                post.getPostLike(),
                post.getCommunityType(),
                post.getContent(),
                post.getImageList().stream().map(i->ImageDto.toDto(i, i.getId())).collect(Collectors.toList())
        );
    }
}
