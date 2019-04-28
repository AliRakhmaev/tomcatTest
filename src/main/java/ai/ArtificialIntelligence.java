package ai;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtificialIntelligence {
    public List<Ship> shipsAtAI;
    public List<Cell> cellsAtPlayer;
    public List<Ship> shipsAtPlayer;
    private final Random random = new Random();

    /**
     * When the game is starting, AI create its own ships and empty field for future shouts on user ships
     */
    public ArtificialIntelligence() {
        formAIShips();
        cellsAtPlayer = initializePlayerCells();
        shipsAtPlayer = initializePlayerShips();
    }

    /**
     * @param ячейка искусственного интеллекта, по которой стреляет игрок
     * @return ячейку - в случае промаха или ранения корабля, корабль - в случае уничтожения корабля и победы игрока
     */
    public Entity checkTheResultOfPlayerShout (Cell cellUnderAttack){
        for( int i = 0; i < shipsAtAI.size(); i++){ // loop for ships
            for (int j = 0; j < shipsAtAI.get(i).getCells().size(); j++) { // loop for cells, which constitute certain ship
                if (cellUnderAttack.getX() == shipsAtAI.get(i).getCells().get(j).getX() && cellUnderAttack.getY() == shipsAtAI.get(i).getCells().get(j).getY()) { // shout reached the goal
                    shipsAtAI.get(i).getCells().get(j).setState(2);
                    // shout is done. Is this destroyed? Lets's make a loop for all cells at ship and check their state
                    boolean shipIsDestroyed = true;
                    boolean allShipsAreDestroyed = true;

                    for (int k = 0; k < shipsAtAI.get(i).getCells().size(); k++) {
                        if (shipsAtAI.get(i).getCells().get(k).getState() == 4) {
                            shipIsDestroyed = false;  // if at least one cell is still alive, ship is alive
                        }
                    }

                    // if ship is destroyed, we need to update the state of all cells
                    if (shipIsDestroyed) {
                        for (int k = 0; k < shipsAtAI.get(i).getCells().size(); k++) {
                            shipsAtAI.get(i).getCells().get(k).setState(3);
                        }
                        shipsAtAI.get(i).setState(1); // change the state of ship to "died"

                        // Is all ships at AI is destroyed?
                        for(int m = 0; m < shipsAtAI.size(); m++){
                            if(shipsAtAI.get(m).getState() == 0)
                                allShipsAreDestroyed = false;
                        }

                        // We return the state 2 of the Ship if AI is defeated
                        if(allShipsAreDestroyed){
                            shipsAtAI.get(i).setState(2);
                            return shipsAtAI.get(i);
                        }

                        return shipsAtAI.get(i);
                    } else { // shout touches a ship, but doesn't kill it. We need to return 2 state of Cell
                        return shipsAtAI.get(i).getCells().get(j);
                    }
                }
            }
        }
        // if shout does not touch any ship, we return 1 state
        cellUnderAttack.setState(1); // ВОЗМОЖНО НУЖНО УЧЕСТЬ ПРОМАХИ И ОТРАЗИТЬ ИХ
        return cellUnderAttack;
    }

    public List<Cell> initializePlayerCells(){
        List<Cell> cells = new ArrayList<>();

        for (int i = 0; i < 10; i++){ // x axis
            for (int j = 0; j < 10; j++){ // y axis
                cells.add(new Cell(0, i, j));
            }
        }
        return cells;
    }

    public List<Ship> initializePlayerShips(){
        List<Ship> ships = new ArrayList<>();

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,-1,-1));
        ships.add(new Ship(cells, 0 ));

        return ships;
    }

    /**
     * Сначала выясняем ситуацию - есть ли большие корабли (живы ли)
     * и ежив ли наибльший из них - Линкор.
     * Если Линкор жив, то используем максимально эффективную стратегию для поиска наибольшего корабля
     * Если живы только большие корабли, но не линкор, то идёт по второй стратегии, что дополняет первую.
     * В конце, если остались только катерки, работаем на удачу и молимся корейскому рандому
     * @return
     */
    public Cell makeAShoutAtPlayerShips (){
        Cell cellForAttack;
        boolean biggestShipIsAlive = false;
        boolean bigShipsAreAlive = false;
        List<Cell> currentStrategy = new ArrayList<>();

        for( int i = 0; i < shipsAtPlayer.size(); i++){
            if(shipsAtPlayer.get(i).getCells().size() == 4 && shipsAtPlayer.get(i).getState() == 0){ // Большой корабль жив
                biggestShipIsAlive = true;
                bigShipsAreAlive = true;
            }
            else if(shipsAtPlayer.get(i).getCells().size() == 3 && shipsAtPlayer.get(i).getState() == 0 // Живы 3ые и 2ые
                    || shipsAtPlayer.get(i).getCells().size() == 2 && shipsAtPlayer.get(i).getState() == 0){
                biggestShipIsAlive = false;
                bigShipsAreAlive = true;
            }
            else{ // Большие корабли уничтожены
                biggestShipIsAlive = false;
                bigShipsAreAlive = false;
            }
        }

        if(biggestShipIsAlive){
            currentStrategy.add(new Cell(0, 0,3));
            currentStrategy.add(new Cell(0, 1,2));
            currentStrategy.add(new Cell(0, 2,1));
            currentStrategy.add(new Cell(0, 3,0));

            currentStrategy.add(new Cell(0, 0,7));
            currentStrategy.add(new Cell(0, 1,6));
            currentStrategy.add(new Cell(0, 2,5));
            currentStrategy.add(new Cell(0, 3,4));
            currentStrategy.add(new Cell(0, 4,3));
            currentStrategy.add(new Cell(0, 5,2));
            currentStrategy.add(new Cell(0, 6,1));
            currentStrategy.add(new Cell(0, 7,0));

            currentStrategy.add(new Cell(0, 2,9));
            currentStrategy.add(new Cell(0, 3,8));
            currentStrategy.add(new Cell(0, 4,7));
            currentStrategy.add(new Cell(0, 5,6));
            currentStrategy.add(new Cell(0, 6,5));
            currentStrategy.add(new Cell(0, 7,4));
            currentStrategy.add(new Cell(0, 8,3));
            currentStrategy.add(new Cell(0, 9,2));

            currentStrategy.add(new Cell(0, 6,9));
            currentStrategy.add(new Cell(0, 5,8));
            currentStrategy.add(new Cell(0, 4,7));
            currentStrategy.add(new Cell(0, 3,6));

            // Проверяем есть ли в нашей стратегии уже проверянные ячейки
            // По необходимости удаляем их
            for( int i = 0; i < cellsAtPlayer.size(); i++){
                if(cellsAtPlayer.get(i).getState() != 0 && currentStrategy.contains(cellsAtPlayer.get(i))){
                    currentStrategy.remove(cellsAtPlayer.get(i));
                }
            }

            cellForAttack = currentStrategy.get(random.nextInt(currentStrategy.size()));
        }
        else if(bigShipsAreAlive){ // шахматный порядок, таким образом найдём и тройки и двойки
            for(int i = 0; i < cellsAtPlayer.size(); i++){
                if((cellsAtPlayer.get(i).getX() + cellsAtPlayer.get(i).getY()) % 2 != 1){
                    currentStrategy.add(cellsAtPlayer.get(i));
                }
            }
            // Очищаем от проверянных
            for( int i = 0; i < cellsAtPlayer.size(); i++){
                if(cellsAtPlayer.get(i).getState() != 0 && currentStrategy.contains(cellsAtPlayer.get(i))){
                    currentStrategy.remove(cellsAtPlayer.get(i));
                }
            }

            cellForAttack = currentStrategy.get(random.nextInt(currentStrategy.size()));
        }
        else{
            for( int i = 0; i < cellsAtPlayer.size(); i++){
                if(cellsAtPlayer.get(i).getState() == 0){
                    currentStrategy.add(cellsAtPlayer.get(i));
                }
            }

            cellForAttack = currentStrategy.get(random.nextInt(currentStrategy.size()));
        }

        return cellForAttack;
    }

    /**
     * Главный метод формирования (расстановки) кораблей искусственным интеллектом.
     * Использует ГПЧ для случайного выбора одной из трёх стратегий, которые в свою очередь случайно
     * расставляет на свободных позициях катера.
     */
    public void formAIShips(){
        // generate 1, 2 or 3
        int chosenStrategy = random.nextInt(3) + 1;

        if(chosenStrategy == 1){
            shipsAtAI = firstStrategy();
        }
        else if(chosenStrategy == 2){
            shipsAtAI = secondStrategy();
        }
        else{
            shipsAtAI = thirdStrategy();
        }
    }

    private List<Ship> firstStrategy(){
        ArrayList<Ship> ships = new ArrayList<>();

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(new Cell(4,0,0));
        cells.add(new Cell(4,0,1));
        cells.add(new Cell(4,0,2));
        cells.add(new Cell(4,0,3));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,2,0));
        cells.add(new Cell(4,2,1));
        cells.add(new Cell(4,2,2));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,2,4));
        cells.add(new Cell(4,2,5));
        cells.add(new Cell(4,2,6));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,0,5));
        cells.add(new Cell(4,0,6));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,0,8));
        cells.add(new Cell(4,0,9));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,2,8));
        cells.add(new Cell(4,2,9));
        ships.add(new Ship(cells, 0 ));

        ships = (ArrayList<Ship>)pasteSmallShips(ships);
        return ships;
    }

    private List<Ship> secondStrategy(){
        ArrayList<Ship> ships = new ArrayList<>();

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(new Cell(4,0,0));
        cells.add(new Cell(4,0,1));
        cells.add(new Cell(4,0,2));
        cells.add(new Cell(4,0,3));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,0,5));
        cells.add(new Cell(4,0,6));
        cells.add(new Cell(4,0,7));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,2,0));
        cells.add(new Cell(4,3,0));
        cells.add(new Cell(4,4,0));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,0,9));
        cells.add(new Cell(4,1,9));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,6,0));
        cells.add(new Cell(4,7,0));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,9,0));
        cells.add(new Cell(4,9,1));
        ships.add(new Ship(cells, 0 ));

        ships = (ArrayList<Ship>)pasteSmallShips(ships);
        return ships;
    }

    private List<Ship> thirdStrategy(){
        ArrayList<Ship> ships = new ArrayList<>();

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(new Cell(4,0,0));
        cells.add(new Cell(4,0,1));
        cells.add(new Cell(4,0,2));
        cells.add(new Cell(4,0,3));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,9,0));
        cells.add(new Cell(4,9,1));
        cells.add(new Cell(4,9,2));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,9,7));
        cells.add(new Cell(4,9,8));
        cells.add(new Cell(4,9,9));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,0,5));
        cells.add(new Cell(4,0,6));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,0,8));
        cells.add(new Cell(4,0,9));
        ships.add(new Ship(cells, 0 ));

        cells = new ArrayList<>();
        cells.add(new Cell(4,9,4));
        cells.add(new Cell(4,9,5));
        ships.add(new Ship(cells, 0 ));

        ships = (ArrayList<Ship>)pasteSmallShips(ships);
        return ships;
    }

    /**
     * Сначала создается поле игры, которые надо расчертить,
     * т.е. забить существующими кораблями и заблокировать зону вокруг них.
     * Дальше нужно выбрать случайную свободнуюю ячейку,
     * постаивть туда кораблик и ограничить зону вокруг него,
     * повторить четыре раза. На выходе все корабли расставлены по наилучшим стратегиям.
     * @param список кораблей без катеров
     * @return список корабей с катерами
     */
    private List<Ship> pasteSmallShips(List<Ship> ships){
        ArrayList<Cell> allCells = new ArrayList<>();

        for (int i = 0; i < 10; i++){ // x axis
            for (int j = 0; j < 10; j++){ // y axis
                allCells.add(new Cell(0, i, j));
            }
        }

        int currentX = 0;
        int currentY = 0;

        for (int i = 0; i < ships.size(); i++){ // ships loop
            for (int j = 0; j < ships.get(i).getCells().size(); j++) {// loop for cells that constitute ship
                currentX = ships.get(i).getCells().get(j).getX();
                currentY = ships.get(i).getCells().get(j).getY();
                allCells = (ArrayList<Cell>) changeTheStateOfNeighboursOfCertainCell(currentX, currentY, 1, allCells);
            }
        }

        ArrayList<Cell> emptyCells = new ArrayList<>();

        for(int i = 0; i < allCells.size(); i++){
            if( allCells.get(i).getState() == 0){
                emptyCells.add(allCells.get(i));
            }
        }

        // Четыре так как 4 маленьких кораблика - катера. Каждый после создания кидаем в корабли.
        // До создания следующего нужно удалить занятые предыдущим ячейки
        for (int i = 0; i < 4; i++) {
            int indexOfSmallShip = random.nextInt(emptyCells.size());
            Cell cellWithCutter = emptyCells.get(indexOfSmallShip);

            List<Cell> cellForCutter = new ArrayList<>();
            cellForCutter.add(cellWithCutter);
            ships.add(new Ship(cellForCutter, 0));

            emptyCells = (ArrayList<Cell>) deleteNotEmptyCells(cellWithCutter.getX(), cellWithCutter.getY(), emptyCells);
        }

        // DELETE THIS!!!
        return ships;
    }

    // Теперь проходимся по всем соседям, при условии того, что они не за границами, и меняем их состояние на
    // занятые, т.е. эти ячейки не могут быть заняты единичными корабликами
    private List<Cell> changeTheStateOfNeighboursOfCertainCell(int currentX, int currentY, int state, List<Cell> allCells){

        // ячейка может быть перезаписана случайно, нужно этого избежать дав ей состояние корабля
        for (Cell cell : allCells) {
            if (cell.getX() == currentX && cell.getY() == currentY) {
                cell.setState(4);
                break;
            }
        }

        if ( currentX - 1 >= 0 && currentX - 1 <= 9 && currentY - 1 >= 0 && currentY - 1 <= 9){
            for (Cell allCell : allCells) {
                if (allCell.getX() == currentX - 1 && allCell.getY() == currentY - 1) {
                    if (allCell.getState() != 4) // чтобы ячейка одного корабля не стёрла состояние соседней ячейки, того же корабля
                        allCell.setState(state);
                    break;
                }
            }
        }

        if (currentY - 1 >= 0 && currentY - 1 <= 9){
            for (Cell allCell : allCells) {
                if (allCell.getX() == currentX && allCell.getY() == currentY - 1) {
                    if (allCell.getState() != 4)
                        allCell.setState(state);
                    break;
                }
            }
        }

        if (currentX - 1 >= 0 && currentX - 1 <= 9){
            for (Cell allCell : allCells) {
                if (allCell.getX() == currentX - 1 && allCell.getY() == currentY) {
                    if (allCell.getState() != 4)
                        allCell.setState(state);
                    break;
                }
            }
        }

        if ( currentX + 1 >= 0 && currentX + 1 <= 9 && currentY - 1 >= 0 && currentY - 1 <= 9){
            for (Cell allCell : allCells) {
                if (allCell.getX() == currentX + 1 && allCell.getY() == currentY - 1) {
                    if (allCell.getState() != 4)
                        allCell.setState(state);
                    break;
                }
            }
        }

        if ( currentX - 1 >= 0 && currentX - 1 <= 9 && currentY + 1 >= 0 && currentY + 1 <= 9){
            for (Cell allCell : allCells) {
                if (allCell.getX() == currentX - 1 && allCell.getY() == currentY + 1) {
                    if (allCell.getState() != 4)
                        allCell.setState(state);
                    break;
                }
            }
        }

        if (currentY + 1 >= 0 && currentY + 1 <= 9){
            for (Cell allCell : allCells) {
                if (allCell.getX() == currentX && allCell.getY() == currentY + 1) {
                    if (allCell.getState() != 4)
                        allCell.setState(state);
                    break;
                }
            }
        }

        if ( currentX + 1 >= 0 && currentX + 1 <= 9){
            for (Cell allCell : allCells) {
                if (allCell.getX() == currentX + 1 && allCell.getY() == currentY) {
                    if (allCell.getState() != 4)
                        allCell.setState(state);
                    break;
                }
            }
        }

        if ( currentX + 1 >= 0 && currentX + 1 <= 9 && currentY + 1 >= 0 && currentY + 1 <= 9){
            for (Cell allCell : allCells) {
                if (allCell.getX() == currentX + 1 && allCell.getY() == currentY + 1) {
                    if (allCell.getState() != 4)
                        allCell.setState(state);
                    break;
                }
            }
        }

        return allCells;
    }

    /**
     * Нужно удалить из пришедшего списка те ячейки, которые заняты катером или
     * окружают его, опять же нужно проверить по всем восьми сторонам
     */
    private List<Cell> deleteNotEmptyCells(int currentX, int currentY, List<Cell> allCells){

        // удалим собственно ячейку с кораблём
        for(int k = 0; k < allCells.size(); k++){
            if(allCells.get(k).getX() == currentX && allCells.get(k).getY() == currentY){
                allCells.remove(k);
                break;
            }
        }

        if ( currentX - 1 >= 0 && currentX - 1 <= 9 && currentY - 1 >= 0 && currentY - 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX - 1 && allCells.get(k).getY() == currentY - 1){
                    allCells.remove(k);
                    break;
                }
            }
        }

        if (currentY - 1 >= 0 && currentY - 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX && allCells.get(k).getY() == currentY - 1){
                    allCells.remove(k);
                    break;
                }
            }
        }

        if (currentX - 1 >= 0 && currentX - 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX - 1 && allCells.get(k).getY() == currentY){
                    allCells.remove(k);
                    break;
                }
            }
        }

        if ( currentX + 1 >= 0 && currentX + 1 <= 9 && currentY - 1 >= 0 && currentY - 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX + 1 && allCells.get(k).getY() == currentY - 1){
                    allCells.remove(k);
                    break;
                }
            }
        }

        if ( currentX - 1 >= 0 && currentX - 1 <= 9 && currentY + 1 >= 0 && currentY + 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX - 1 && allCells.get(k).getY() == currentY + 1){
                    allCells.remove(k);
                    break;
                }
            }
        }

        if (currentY + 1 >= 0 && currentY + 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX && allCells.get(k).getY() == currentY + 1){
                    allCells.remove(k);
                    break;
                }
            }
        }

        if ( currentX + 1 >= 0 && currentX + 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX + 1 && allCells.get(k).getY() == currentY){
                    allCells.remove(k);
                    break;
                }
            }
        }

        if ( currentX + 1 >= 0 && currentX + 1 <= 9 && currentY + 1 >= 0 && currentY + 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX + 1 && allCells.get(k).getY() == currentY + 1){
                    allCells.remove(k);
                    break;
                }
            }
        }

        return allCells;
    }
}
