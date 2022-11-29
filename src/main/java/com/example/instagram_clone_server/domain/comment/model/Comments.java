package com.example.instagram_clone_server.domain.comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    private List<Comment> comments;

    public static Comments of(List<Comment> comments){
        return new Comments(comments);
    }
}
