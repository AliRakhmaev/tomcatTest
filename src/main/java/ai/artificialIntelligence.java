package ai;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class artificialIntelligence {
    List<Ship> shipsAtAI;
    List<Cell> cellsAtAI;
    final Random random = new Random();

    public Entity checkTheResultOfShout (Cell cellUnderAttack){
        for( int i = 0; i < shipsAtAI.size(); i++){ // loop for ships
            for (int j = 0; j < shipsAtAI.get(i).getCells().size(); j++) { // loop for cells, which constitute certain ship
                if (cellUnderAttack.getX() == shipsAtAI.get(i).getCells().get(j).getX() && cellUnderAttack.getY() == shipsAtAI.get(i).getCells().get(j).getY()) {
                    // shout reached the goal
                    shipsAtAI.get(i).getCells().get(j).setState(2);

                    // shout is done. Is this destroyed? Lets's make a loop for all cells at ship and check their state
                    boolean shipIsDestroyed = true;

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
                        return shipsAtAI.get(i);
                    } else { // shout touches a ship, but doesn't kill it. We nedd to return 2 state
                        return shipsAtAI.get(i).getCells().get(j);
                    }
                }
            }
        }
        // if shout does not touch any ship, we return 1 state
        cellUnderAttack.setState(1); // ВОЗМОЖНО НУЖНО УЧЕСТЬ ПРОМАХИ И ОТРАЗИТЬ ИХ
        return cellUnderAttack;
    }

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
                changeTheStateOfNeighboursOfCertainCell(currentX, currentY, 1, allCells);
            }
        }

        ArrayList<Cell> emptyCells = new ArrayList<>();

        for(int i = 0; i < allCells.size(); i++){
            if( allCells.get(i).getState() == 0){
                emptyCells.add(allCells.get(i));
            }
        }

        int indexForPuttingSmallShip = random.nextInt(emptyCells.size());


        // DELETE THIS!!!
        return ships;
    }

    // Теперь проходимся по всем соседям, при условии того, что они не за границами, и меняем их состояние на
    // занятые, т.е. эти ячейки не могут быть заняты единичными корабликами
    private void changeTheStateOfNeighboursOfCertainCell(int currentX, int currentY, int state, List<Cell> allCells){

        // ячейка может быть перезаписана случайно, нужно этого избежать дав ей состояние корабля
        for(int k = 0; k < allCells.size(); k++){
            if(allCells.get(k).getX() == currentX && allCells.get(k).getY() == currentY){
                allCells.get(k).setState(4);
            }
        }

        if ( currentX - 1 >= 0 && currentX - 1 <= 9 && currentY - 1 >= 0 && currentY - 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX - 1 && allCells.get(k).getY() == currentY - 1){
                    if(allCells.get(k).getState() != 4) // чтобы ячейка одного корабля не стёрла состояние соседней ячейки, того же корабля
                    allCells.get(k).setState(1);
                }
            }
        }

        if (currentY - 1 >= 0 && currentY - 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX && allCells.get(k).getY() == currentY - 1){
                    allCells.get(k).setState(1);
                }
            }
        }

        if (currentX - 1 >= 0 && currentX - 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX - 1 && allCells.get(k).getY() == currentY){
                    allCells.get(k).setState(1);
                }
            }
        }

        if ( currentX + 1 >= 0 && currentX + 1 <= 9 && currentY - 1 >= 0 && currentY - 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX + 1 && allCells.get(k).getY() == currentY - 1){
                    allCells.get(k).setState(1);
                }
            }
        }

        if ( currentX - 1 >= 0 && currentX + 1 <= 9 && currentY - 1 >= 0 && currentY - 1 <= 9){
            for(int k = 0; k < allCells.size(); k++){
                if(allCells.get(k).getX() == currentX + 1 && allCells.get(k).getY() == currentY - 1){
                    allCells.get(k).setState(1);
                }
            }
        }
    }
}
