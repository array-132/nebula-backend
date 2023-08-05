package com.nebula.blogapp.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "post_title",length = 300,nullable = false)
    private String title;

    @Column(name = "post_content",length = 2500,nullable = false)
    private String content;

    @Column(name = "image_name",nullable = false)
    private String imageName;

    @Column(name = "creation_date",nullable = false)
    private Date addedDate;

    @Column(name = "last_edit_date",nullable = false)
    private Date lastEditDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

}
