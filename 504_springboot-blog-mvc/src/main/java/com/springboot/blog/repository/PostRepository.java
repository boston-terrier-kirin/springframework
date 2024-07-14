package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public Optional<Post> findByUrl(String url);

    @Query("""
            select p
              from Post p
             where p.title like concat('%', :query, '%')
                or p.shortDescription like concat('%', :query, '%')
          """)
    public List<Post> searchPosts(String query);
}
