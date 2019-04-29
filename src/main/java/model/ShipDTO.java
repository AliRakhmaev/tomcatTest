package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ShipDTO {
    @JsonProperty ("cells") private List<CellDTO> cells;
    @JsonProperty ("state") private int state;
    @JsonProperty private String userId;

    public ShipDTO() {
    }

    public ShipDTO(List<CellDTO> cells, int state, String userId) {
        this.cells = cells;
        this.state = state;
        this.userId = userId;
    }

    public List<CellDTO> getCells() {
        return cells;
    }

    public void setCells(List<CellDTO> cells) {
        this.cells = cells;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ShipDTO{" +
                "cells=" + cells +
                ", state=" + state +
                ", userId='" + userId + '\'' +
                '}';
    }
}
