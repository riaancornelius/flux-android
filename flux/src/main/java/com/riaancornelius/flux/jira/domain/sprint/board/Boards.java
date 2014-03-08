package com.riaancornelius.flux.jira.domain.sprint.board;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by riaan.cornelius on 2014/03/06.
 */
public class Boards {

    @JsonProperty("views")
    private List<Board> boards;

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }
}
