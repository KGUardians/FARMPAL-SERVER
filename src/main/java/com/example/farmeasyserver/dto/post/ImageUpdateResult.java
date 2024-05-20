package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImageUpdateResult {
    private List<MultipartFile> addedImageFileList;
    private List<Image> addedImageList;
    private List<Image> deletedImageList;
}
