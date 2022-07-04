package com.example.demo.application.imageApplication;
import com.example.demo.domain.imageDomain.Image;
import com.example.demo.infraestructure.redisInfraestructure.RedisRepository;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ImageApplicationImp implements ImageApplication{
    private final RedisRepository<byte[], String> redisRepository;
	private final ModelMapper modelMapper;

    @Autowired
	public ImageApplicationImp(final RedisRepository<byte[], String> redisRepository, final ModelMapper modelMapper) {
		this.redisRepository = redisRepository;
    	this.modelMapper = modelMapper;
  	}

      public Mono<ImageDTO> add(CreateOrUpdateImageDTO dto){
    	Image image = modelMapper.map(dto, Image.class);
    	image.setId(UUID.randomUUID());
    	image.setThisNew(true);
    	return this.redisRepository
						.set(image.getId().toString(), image.getContent(), 24)
                        .then(Mono.just(this.modelMapper.map(image,ImageDTO.class)));
  	}
      public Mono<ImageDTO> getImageRedis(String id){
    	return this.redisRepository
						.getFromID(id)
						.map(bytes -> {
							ImageDTO imageDTO = new ImageDTO();
							imageDTO.setContent(bytes);
							imageDTO.setId(UUID.fromString(id));
							return imageDTO;
						});
  	}
}
