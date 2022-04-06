package com.example.SimplifiedReddit.data_config;

import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.enums.UserRole;
import com.example.SimplifiedReddit.repository.PostRepository;
import com.example.SimplifiedReddit.repository.SubredditRepository;
import com.example.SimplifiedReddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {
    private UserRepository userRepository;
    private SubredditRepository subredditRepository;
    private PostRepository postRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, SubredditRepository subredditRepository, PostRepository postRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.subredditRepository = subredditRepository;
        this.postRepository = postRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void run(ApplicationArguments args) {

        UserRole role = UserRole.USER;
        User user1 = User.builder().email("abc1@gmail.com").username("abc1").password(bCryptPasswordEncoder.encode("123")).role(role).enabled(Boolean.TRUE).locked(Boolean.FALSE).build();
        User user2 = User.builder().email("abc2@gmail.com").username("abc2").password(bCryptPasswordEncoder.encode("123")).role(role).enabled(Boolean.TRUE).locked(Boolean.FALSE).build();
        List<User> users = userRepository.saveAll(List.of(user1, user2));

        Subreddit subreddit1 = Subreddit.builder().user(users.get(0)).description("description1").name("subreddit1").build();
        Subreddit subreddit2 = Subreddit.builder().user(users.get(1)).description("description2").name("subreddit2").build();
        Subreddit subreddit3 = Subreddit.builder().user(users.get(1)).description("description3").name("subreddit3").build();

        List<Subreddit> subreddits = subredditRepository.saveAll(List.of(subreddit1, subreddit2, subreddit3));

        Post post1 = Post.builder().user(users.get(0)).subreddit(subreddits.get(0)).title("title1").text("text1").voteCount(0).build();
        Post post2 = Post.builder().user(users.get(0)).subreddit(subreddits.get(1)).title("title2").text("text2").voteCount(0).build();

        Post post3 = Post.builder().user(users.get(1)).subreddit(subreddits.get(0)).title("title1").text("text1").voteCount(0).build();
        Post post4 = Post.builder().user(users.get(1)).subreddit(subreddits.get(1)).title("title2").text("text2").voteCount(0).build();
        Post post5 = Post.builder().user(users.get(1)).subreddit(subreddits.get(1)).title("title1").text("text1").voteCount(0).build();
        List<Post> posts = postRepository.saveAll(List.of(post1, post2, post3, post4, post5));
    }
}

