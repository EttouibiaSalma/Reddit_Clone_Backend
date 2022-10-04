package com.reddit.reddit_clone.service;

import com.reddit.reddit_clone.dto.SubRedditDTO;
import com.reddit.reddit_clone.model.SubReddit;
import com.reddit.reddit_clone.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubRedditService {

    private final SubRedditRepository subRedditRepository;

    @Transactional
    public SubRedditDTO save(SubRedditDTO subredditDto) {
        SubReddit save = subRedditRepository.save(mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    private SubReddit mapDtoToSubreddit(SubRedditDTO subRedditDTO){
        return SubReddit.builder().name(subRedditDTO.getName())
                .description(subRedditDTO.getDescription())
                .build();
    }

    @Transactional(readOnly = true)
    public List<SubRedditDTO> getAll() {
        return subRedditRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(toList());
    }

    private SubRedditDTO mapToDTO(SubReddit subReddit) {
        return SubRedditDTO.builder().name(subReddit.getName())
                .description(subReddit.getDescription())
                .build();
    }
}
