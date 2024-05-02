package com.example.farmeasyserver.entity.board;

import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String originName;
    @Column(nullable = false)
    private String uniqueName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    /*@ManyToOne(fetch = FetchType.LAZY)
    private MarketPost m_post;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommunityPost c_post;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExperiencePost e_post;*/


    private final static String supportedExtension[] = {"jpg","jpeg","gif","bmp","png"};

    public Image(String imageName){
        this.originName = imageName;
        this.uniqueName = generatedUniqueName(extractExtension(imageName));
    }

    public void setPost(CommunityPost post) {
        if(this.c_post == null) {
            this.c_post = post;
        }
    }

    public void setPost(MarketPost post) {
        if(this.m_post == null) {
            this.m_post = post;
        }
    }

    public void setPost(ExperiencePost post) {
        if(this.e_post == null) {
            this.e_post = post;
        }
    }

    private String generatedUniqueName(String extension){
        return UUID.randomUUID().toString()+"."+extension;
    }

    private String extractExtension(String imageName){
        try {
            String ext = imageName.substring(imageName.lastIndexOf(".")+1);
            if(isSupportedFormat(ext)) return ext;
        }catch (StringIndexOutOfBoundsException e){}
        throw new UnsupportedOperationException("잘못된 이미지 형식입니다.");
    }

    private boolean isSupportedFormat(String ext){
        return Arrays.stream(supportedExtension).anyMatch(e -> e.equalsIgnoreCase(ext));
    }

}
