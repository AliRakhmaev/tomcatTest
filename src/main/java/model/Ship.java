package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 0 - alive, 1 - died
 */
public class Ship implements Entity{
    @JsonProperty ("cells") private List<Cell> cells;
    @JsonProperty ("state") private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Ship() {
    }

    public Ship(List<Cell> cells, int state) {
        this.cells = cells;
        this.state = state;
    }
}
