package com.reddit.reddit_clone.service;

import com.reddit.reddit_clone.dto.SubRedditDTO;
import com.reddit.reddit_clone.exception.ApplicationException;
import com.reddit.reddit_clone.mapper.SubRedditMapper;
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
    public final SubRedditMapper subRedditMapper;

    @Transactional
    public SubRedditDTO save(SubRedditDTO subredditDto) {
        SubReddit save = subRedditRepository.save(subRedditMapper.mapDTOToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubRedditDTO> getAll() {
        return subRedditRepository.findAll()
                .stream()
                .map(subRedditMapper::mapSubRedditToDTO)
                .collect(toList());
    }

    public SubRedditDTO get(Long id) {
        SubReddit subReddit = subRedditRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Subreddit not found"));
        return subRedditMapper.mapSubRedditToDTO(subReddit);

    }
}
