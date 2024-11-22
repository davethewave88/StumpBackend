package net.javaguides.springboot_backend.controller;

import net.javaguides.springboot_backend.domain.Post;
import net.javaguides.springboot_backend.domain.PostRepository;
import net.javaguides.springboot_backend.domain.User;
import net.javaguides.springboot_backend.dto.PostDTO;
import net.javaguides.springboot_backend.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<PostDTO> findLatestPosts() {

        List<Post> posts = postRepository.findAllByOrderByIdAsc();
        List<PostDTO> postDTO_list = new ArrayList<>();
        for (Post p: posts) {
            postDTO_list.add(new PostDTO(p.getId(), p.getCreator_id(), p.getCreate_time(), p.getLink()));
        }
        return postDTO_list;
    }


    @PostMapping("/posts")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public PostDTO createPost(
            @RequestBody PostDTO dto) {

        Post p = new Post();
        p.setCreator_id(dto.creator_id());
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        p.setCreate_time(currentTime);
        p.setLink(dto.link());
        postRepository.save(p);

        return new PostDTO(
                p.getId(),
                p.getCreator_id(),
                p.getCreate_time(),
                p.getLink()
        );
    }

    @DeleteMapping("/posts/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void  updateUser(@PathVariable("id") int id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post!=null) {
            postRepository.delete(post);
        }

    }
}
