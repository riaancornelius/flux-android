package com.riaancornelius.flux.jira.domain.author;

import com.riaancornelius.flux.jira.domain.author.AvatarUrls;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: riaan.cornelius
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {

    private String key;

    private String name;

    private String emailAddress;

    private String displayName;

    private boolean active;

    @JsonProperty(value = "self")
    private String restUrl;

    private AvatarUrls avatarUrls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public AvatarUrls getAvatarUrls() {
        return avatarUrls;
    }

    public void setAvatarUrls(AvatarUrls avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", displayName='" + displayName + '\'' +
                ", active=" + active +
                ", restUrl='" + restUrl + '\'' +
                ", avatarUrls=" + avatarUrls +
                '}';
    }
}
