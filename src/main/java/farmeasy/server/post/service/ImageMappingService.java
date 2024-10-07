package farmeasy.server.post.service;

import farmeasy.server.main.dto.PostListDto;

import java.util.List;

public interface ImageMappingService {
    <T extends PostListDto> void imageMapping(List<T> mainPageDto);
}
