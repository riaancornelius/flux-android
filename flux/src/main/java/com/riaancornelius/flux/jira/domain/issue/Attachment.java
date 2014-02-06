package com.riaancornelius.flux.jira.domain.issue;

import com.riaancornelius.flux.jira.domain.author.Author;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Elsabe
 */
public class Attachment {
    @JsonProperty(value = "self")
    private String restUrl;

    private Long id;

    private String filename;

    private String created;

    private Long size;

    private String mimeType;

    private String content;

    private Author author;

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
