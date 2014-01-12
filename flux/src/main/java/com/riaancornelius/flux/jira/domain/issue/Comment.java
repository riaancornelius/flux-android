package com.riaancornelius.flux.jira.domain.issue;

import com.riaancornelius.flux.jira.domain.author.Author;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * User: riaan.cornelius
 */
public class Comment {

    private Long id;

    private String key;

    @JsonProperty(value = "self")
    private String restUrl;

    private String body;

    private Date created;

    private Date updated;

    private Author author;

    private Author updateAuthor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(Author updateAuthor) {
        this.updateAuthor = updateAuthor;
    }
}
