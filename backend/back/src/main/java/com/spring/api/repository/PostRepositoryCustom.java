package com.spring.api.repository;

import com.spring.api.domain.Post;
import com.spring.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
