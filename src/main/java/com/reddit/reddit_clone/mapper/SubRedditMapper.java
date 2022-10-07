package com.reddit.reddit_clone.mapper;

import com.reddit.reddit_clone.dto.SubRedditDTO;
import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.model.SubReddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subReddit.getPosts()))")
    SubRedditDTO mapSubRedditToDTO(SubReddit subReddit);

    default Integer mapPosts(List<Post> numberOfPosts){
        return numberOfPosts.size();
    }

    @Mapping(target = "posts", ignore = true)
    @InheritInverseConfiguration
    SubReddit mapDTOToSubreddit(SubRedditDTO subRedditDTO);
}
