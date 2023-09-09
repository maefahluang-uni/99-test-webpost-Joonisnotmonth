package lab.webpost.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lab.webpost.domain.Post;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    // TODO: get all Posts
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postRepository.findAll();

        return ResponseEntity.ok(posts);
    }

    // TODO: getting post by id
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable long id) {
        // TODO: find post by ID suing em.find(...
        Optional<Post> optPost = postRepository.findById(id);

        // TODO: Handle the case when no entity is found
        if (!optPost.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optPost.get());
    }

    // TODO: find by title
    @GetMapping("/posts/{title}")
    public ResponseEntity<List<Post>> getPostByTitle(@PathVariable String title) {
        // get post
        List<Post> posts = postRepository.findByTitle(title);

        // check if posts is empty
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // return success status
        return ResponseEntity.ok(posts);
    }

    // TODO: adding new post
    @PostMapping("/posts")
    public ResponseEntity<String> addPost(@RequestBody Post post) {

        postRepository.save(post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO: delete post by id
    @DeleteMapping("/posts")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {

        if (!postRepository.existsById(id)) {
            // return error message 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // TODO: delete all posts
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deleteAllPosts() {
        postRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
