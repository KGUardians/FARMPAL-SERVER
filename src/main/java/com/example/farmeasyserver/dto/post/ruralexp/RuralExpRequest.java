package com.example.farmeasyserver.dto.post.ruralexp;

import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.PostType;
import com.example.farmeasyserver.repository.jpa.UserJpaRepository;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Data
public class RuralExpRequest {

    private PostType postType = PostType.RURAL_EXP;

    @ApiModelProperty(value = "게시글 제목", notes = "게시글 제목을 입력해주세요", required = true, example = "my title")
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "게시글 본문", notes = "게시글 본문을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private String content;

    @ApiModelProperty(hidden = true)
    @Null
    private Long userId;

    @ApiModelProperty(value = "이미지", notes = "이미지를 첨부해주세요.")
    private List<MultipartFile> imageList = new ArrayList<>();

    public static Post RuralExpToEntity(RuralExpRequest req, UserJpaRepository userJpaRepository) {
        return new Post(
                req.postType,
                req.title,
                req.content,
                userJpaRepository.findById(req.getUserId()).orElseThrow(()->new NoSuchElementException("사용자를 찾을 수 없습니다.")),
                req.imageList.stream().map(i -> new Image(i.getOriginalFilename())).collect(toList())
        );
    }
}