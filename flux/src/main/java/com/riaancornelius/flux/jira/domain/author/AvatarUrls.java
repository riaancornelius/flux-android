package com.riaancornelius.flux.jira.domain.author;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: riaan.cornelius
 */
public class AvatarUrls {

    @JsonProperty(value = "16x16")
    private String sixteenSquareUrl;

    @JsonProperty(value = "24x24")
    private String twentyFourSquareUrl;

    @JsonProperty(value = "32x32")
    private String thirtyTwoSquareUrl;

    @JsonProperty(value = "48x48")
    private String fortyEightSquareUrl;

    public String getSixteenSquareUrl() {
        return sixteenSquareUrl;
    }

    public void setSixteenSquareUrl(String sixteenSquareUrl) {
        this.sixteenSquareUrl = sixteenSquareUrl;
    }

    public String getTwentyFourSquareUrl() {
        return twentyFourSquareUrl;
    }

    public void setTwentyFourSquareUrl(String twentyFourSquareUrl) {
        this.twentyFourSquareUrl = twentyFourSquareUrl;
    }

    public String getThirtyTwoSquareUrl() {
        return thirtyTwoSquareUrl;
    }

    public void setThirtyTwoSquareUrl(String thirtyTwoSquareUrl) {
        this.thirtyTwoSquareUrl = thirtyTwoSquareUrl;
    }

    public String getFortyEightSquareUrl() {
        return fortyEightSquareUrl;
    }

    public void setFortyEightSquareUrl(String fortyEightSquareUrl) {
        this.fortyEightSquareUrl = fortyEightSquareUrl;
    }
}
