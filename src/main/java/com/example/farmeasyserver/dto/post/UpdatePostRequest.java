package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.CropCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class UpdatePostRequest {
    private String title;
    private CropCategory cropCategory;
    private String content;
    private List<MultipartFile> addedImages = new ArrayList<>();
    private List<MultipartFile> deletedImages = new ArrayList<>();

}
