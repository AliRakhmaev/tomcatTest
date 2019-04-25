package model;

/**
 * Класс для объединения двух сущностей в одну (корабля и ячейки)
 */
public interface Entity {

    public int getState();
    public void setState(int state);
}
