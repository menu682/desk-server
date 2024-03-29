package pp.ua.lomax.desk.controller;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import pp.ua.lomax.desk.dto.post.PhotoDto;
import pp.ua.lomax.desk.dto.post.PostCreateDto;
import pp.ua.lomax.desk.dto.post.PostDeleteDto;
import pp.ua.lomax.desk.dto.post.PostPaginationDto;
import pp.ua.lomax.desk.dto.post.PostPutDto;
import pp.ua.lomax.desk.dto.post.PostResponseDto;
import pp.ua.lomax.desk.service.PostService;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping
    public PostResponseDto createPost(@RequestBody PostCreateDto postCreateDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return postService.createPost(postCreateDto, userDetailsImpl);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping
    public PostResponseDto putPost(@RequestBody PostPutDto postPutDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return postService.putPost(postPutDto, userDetailsImpl);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping
    public MessageResponseDto deletePost(@RequestBody PostDeleteDto postDeleteDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){

        return postService.deletePost(postDeleteDto, userDetailsImpl);
    }

    @GetMapping("/region/{regionId}")
    public PostPaginationDto getPostsListByRegion(@PathVariable Long regionId,
                                          @RequestParam Integer page,
                                          @RequestParam Integer size){

        return postService.getPostsPaginationByRegion(regionId, page, size);
    }

    @GetMapping("/category/{categoryId}")
    public PostPaginationDto getPostsListByCategory(@PathVariable Long categoryId,
                                          @RequestParam Integer page,
                                          @RequestParam Integer size){

        return postService.getPostsPaginationByCategory(categoryId, page, size);
    }

    @GetMapping("/{regionId}/{categoryId}")
    public PostPaginationDto getPostsList(@PathVariable Long regionId,
                                          @PathVariable Long categoryId,
                                          @RequestParam Integer page,
                                          @RequestParam Integer size){

        return postService.getPostsPaginationByRegionAndCategory(regionId, categoryId, page, size);
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping (value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDto uploadPostPhoto(@RequestPart("post") @Valid @NotNull @NotBlank PostPutDto postPutDto,
                                           @RequestParam("file") @Valid @NotNull @NotBlank MultipartFile file,
                                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){

        return postService.uploadPhoto(postPutDto, file, userDetailsImpl);
    }

    @GetMapping("/getphoto/{fileName}")
    public ResponseEntity getPhoto(@PathVariable String fileName){

        BufferedImage bufferedImage = postService.downloadPhoto(fileName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/removephoto",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDto removePhoto(@RequestPart("post") @Valid @NotNull @NotBlank PostPutDto postPutDto,
                                       @RequestPart("photo") @Valid @NotNull @NotBlank PhotoDto photoDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){

        return postService.removePhoto(postPutDto, photoDto, userDetailsImpl);
    }

}