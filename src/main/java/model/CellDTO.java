package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CellDTO {
    @JsonProperty private int x;
    @JsonProperty private int y;
    @JsonProperty private int state;
    @JsonProperty private String userId;

    public CellDTO() {
    }

    public CellDTO(int x, int y, int state, String userId) {
        this.x = x;
        this.y = y;
        this.state = state;
        this.userId = userId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
        return "CellDTO{" +
                "x=" + x +
                ", y=" + y +
                ", state=" + state +
                ", userId='" + userId + '\'' +
                '}';
    }
}
