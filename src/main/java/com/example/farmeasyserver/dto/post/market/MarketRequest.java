package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.entity.board.PostType;
import com.example.farmeasyserver.entity.board.item.ItemCategory;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class MarketRequest {
    @ApiModelProperty(value = "카테고리", notes = "카테고리를 입력해주세요", required = true, example = "MARKET")
    @NotNull(message = "카테고리 아이디를 입력해주세요.")
    @PositiveOrZero(message = "올바른 카테고리 아이디를 입력해주세요.")
    private PostType postType = PostType.MARKET;

    @ApiModelProperty(value = "게시글 제목", notes = "게시글 제목을 입력해주세요", required = true, example = "my title")
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "게시글 본문", notes = "게시글 본문을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private String content;

    @ApiModelProperty(value = "물건 이름", notes = "물건 이름을 입력해주세요", required = true, example = "item")
    @NotBlank(message = "물건 이름을 입력해주세요.")
    private String itemName;

    @ApiModelProperty(value = "가격", notes = "가격을 입력해주세요", required = true, example = "50000")
    @NotNull(message = "가격을 입력해주세요.")
    @PositiveOrZero(message = "0원 이상을 입력해주세요")
    private int price;

    @ApiModelProperty(value = "무게", notes = "무게를 입력해주세요", required = true, example = "50000")
    @NotNull(message = "작물 무게를 입력해주세요.")
    @PositiveOrZero(message = "0 이상을 입력해주세요")
    private int gram;

    @ApiModelProperty(value = "카테고리", notes = "카테고리를 입력해주세요", required = true, example = "MARKET")
    @NotNull(message = "카테고리 아이디를 입력해주세요.")
    @PositiveOrZero(message = "올바른 카테고리 아이디를 입력해주세요.")
    private ItemCategory itemCategory;

    @ApiModelProperty(hidden = true)
    @Null
    private Long userId;

    @ApiModelProperty(value = "이미지", notes = "이미지를 첨부해주세요.")
    private List<MultipartFile> imageList = new ArrayList<>();
}
