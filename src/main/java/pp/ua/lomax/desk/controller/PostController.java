package pp.ua.lomax.desk.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.post.PostCreateDto;
import pp.ua.lomax.desk.dto.post.PostDeleteDto;
import pp.ua.lomax.desk.dto.post.PostPutDto;
import pp.ua.lomax.desk.dto.post.PostResponseDto;
import pp.ua.lomax.desk.service.PostServise;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/post")
public class PostController {

    private final PostServise postServise;

    public PostController(PostServise postServise) {
        this.postServise = postServise;
    }

    //TODO Create services for controllers
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping
    public PostResponseDto createPost(@RequestBody PostCreateDto postCreateDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return postServise.createPost(postCreateDto, userDetailsImpl);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping
    public PostResponseDto putPost(@RequestBody PostPutDto postPutDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return postServise.putPost(postPutDto, userDetailsImpl);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping
    public MessageResponseDto deletePost(@RequestBody PostDeleteDto postDeleteDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return null;
    }
    //??????? pagination
    @GetMapping
    public PostResponseDto getPostsList(){
        return null;
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId){
        return postServise.getPostById(postId);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping (value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDto uploadPhoto(@RequestPart("post") @Valid PostPutDto postPutDto,
                                       @RequestParam("file") @Valid @NotNull @NotBlank MultipartFile file,
                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){

        return postServise.uploadPhoto(postPutDto, file, userDetailsImpl);
    }

}