package com.riaancornelius.flux.jira.domain.author;

import android.util.Log;

import com.google.common.base.Predicate;
import com.octo.android.robospice.SpiceManager;
import com.riaancornelius.flux.jira.domain.author.AvatarUrls;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Comparator;

/**
 * User: riaan.cornelius
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author implements Comparable<Author> {

    private String key;

    private String name;

    private String emailAddress;

    private String displayName;

    private boolean active;

    @JsonProperty(value = "self")
    private String restUrl;

    private AvatarUrls avatarUrls;

    public static Predicate<Author> notEmptyPredicate() {
        return new Predicate<Author>() {
            @Override
            public boolean apply(Author input) {
                return hasKeyAndNameFields(input);
            }
        };
    }

    private static boolean hasKeyAndNameFields(Author input) {
        return input != null &&
                input.getKey() != null && !input.getKey().isEmpty() &&
                input.getName() != null && !input.getName().isEmpty() &&
                input.getDisplayName() != null && !input.getDisplayName().isEmpty();
    }

    public void putInCache(SpiceManager spiceManager) {
        if (hasKeyAndNameFields(this)) {
            Log.d("AUTHOR", "Caching author: " + this);
            spiceManager.putInCache("author_" + getName(), this);
        } else {
            Log.d("AUTHOR", "Not caching author because key or name fields empty: " + this);
        }
    }

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

    @Override
    public int compareTo(Author another) {
        return getDisplayName().compareTo(another.getDisplayName());
    }
}
