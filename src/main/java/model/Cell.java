package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cell {
    @JsonProperty private int x;
    @JsonProperty private int y;
    @JsonProperty private int state;

    public Cell(int state, int x, int y){
        this.state=state;
        this.x=x;
        this.y=y;
    }
    public Cell(){

    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getState() {
        return state;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setState(int state) {
        this.state = state;
    }
}

