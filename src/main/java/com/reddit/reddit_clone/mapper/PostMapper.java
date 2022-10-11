package com.reddit.reddit_clone.mapper;

import com.reddit.reddit_clone.dto.PostRequest;
import com.reddit.reddit_clone.dto.PostResponse;
import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.model.SubReddit;
import com.reddit.reddit_clone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subReddit", source = "subReddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Post map(PostRequest postRequest, SubReddit subReddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "subredditName", source = "subReddit.name")
    PostResponse mapToDTO(Post post);
}
