package com.symbio.blog.domain.post;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@DynamicUpdate
@Entity
@Table(name = "POST")
public class Post implements Serializable {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "BLOG-POST-ID-GENERATOR", sequenceName = "SEQ_POST", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BLOG-POST-ID-GENERATOR")
    private Long id;


    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BODY")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE", insertable = false)
    private Date modifiedDate;


    public Post(Long id) {
        this.id = id;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
