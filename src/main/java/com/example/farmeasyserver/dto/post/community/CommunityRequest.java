package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.PostType;
import com.example.farmeasyserver.repository.jpa.UserJpaRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@ApiModel(value = "커뮤니티 게시글 생성 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityRequest {
    @ApiModelProperty(value = "게시글 제목", notes = "게시글 제목을 입력해주세요", required = true, example = "my title")
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "게시글 본문", notes = "게시글 본문을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private String content;

    @ApiModelProperty(hidden = true)
    @Null
    private Long userId;

    @ApiModelProperty(value = "카테고리", notes = "카테고리를 입력해주세요", required = true, example = "COMMUNITY")
    @NotNull(message = "카테고리를 입력해주세요.")
    @PositiveOrZero(message = "올바른 카테고리를 입력해주세요.")
    private String postType;

    @ApiModelProperty(value = "이미지", notes = "이미지를 첨부해주세요.")
    private List<MultipartFile> imageList = new ArrayList<>();

    public static Post CommunityToEntity(CommunityRequest req, UserJpaRepository userJpaRepository) {
        PostType type;
        if(req.postType.equals("자유")){
            type = PostType.FREE;
        } else if (req.postType.equals("공지")) {
            type = PostType.NOTICE;
        } else if (req.postType.equals("질문")) {
            type = PostType.QUESTION;
        } else {
            throw new EnumConstantNotPresentException(PostType.class,"올바른 카테고리가 아닙니다.");
        }

        return new Post(
                userJpaRepository.findById(req.getUserId()).orElseThrow(()-> new NoSuchElementException("사용자를 찾을 수 없습니다.")),
                req.title,
                type,
                req.imageList.stream().map(i -> new Image(i.getOriginalFilename())).collect(toList())
        );
    }
}
