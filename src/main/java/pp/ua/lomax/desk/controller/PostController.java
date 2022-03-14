package pp.ua.lomax.desk.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.post.CreatePostRequestDto;
import pp.ua.lomax.desk.dto.post.DeletePostRequestDto;
import pp.ua.lomax.desk.dto.post.PostResponseDto;
import pp.ua.lomax.desk.service.PostServise;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/post")
public class PostController {

    private PostServise postServise;

    public PostController(PostServise postServise) {
        this.postServise = postServise;
    }

    @PostMapping
    public MessageResponseDto createPost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                         @RequestBody CreatePostRequestDto createPostRequestDto){
        return postServise.create(userDetailsImpl.getUser(), createPostRequestDto);
    }

    @PutMapping
    public MessageResponseDto putPost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                         @RequestBody CreatePostRequestDto createPostRequestDto){
        return null;
    }

    @DeleteMapping
    public MessageResponseDto deletePost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                         @RequestBody DeletePostRequestDto deletePostRequestDto){
        return null;
    }

    @GetMapping
    public PostResponseDto getPostsList(){
        return null;
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId){
        return null;
    }

}
