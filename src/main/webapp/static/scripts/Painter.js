function fillField(amountOfCells, className, listener) {
    let e =document.getElementsByClassName(className);
    console.log(e);
    for (let i=0; i<amountOfCells;i++){
        for(let j=0; j<amountOfCells; j++) {
            let div = document.createElement('div');
            div.className = "cell";
            const cell_id =`${className}_cell_${i}${j}`;
            div.id = cell_id;
            if (listener===true){
                div.className = "cell cell-hover";
            }
            e[0].appendChild(div);
        }
    }
}

//рисуем страницу заполнения поля
function drawCreateShipsPage() {
    //создали поле для заполнения кораблями
    let div = document.createElement('div');
    div.className = "my-playing-field field";
    let field_container = document.getElementsByClassName('field-container');
    field_container[0].appendChild(div);

    //дописали заголовок
    let wrapper = document.getElementsByClassName('wrapper');
    let h1 = document.createElement('h1');
    h1.id = 'h1';
    h1.className = "w3-center";
    h1.innerText = 'Подготовьтесь к атаке!';
    wrapper[0].insertBefore(h1, field_container[0]);

    //заполнили поле ячейками, с cell-hover (будут реагировать на отклик)
    fillField(n, 'my-playing-field', true);

    //добавляем невидимую кнопку "играть"
    let btn = document.createElement('button');
    btn.className = "w3-center w3-btn btn__begin-game";
    btn.innerText = "Играть!";
    wrapper[0].appendChild(btn);
    let btn1 = document.getElementsByClassName('btn__begin-game');
    btn1[0].style.visibility = "hidden";
}

function drawOpponentField() {
    //создали поле для заполнения кораблями
    let div = document.createElement('div');
    div.className = "opponent-playing-field field";
    let field_container = document.getElementsByClassName('field-container');
    field_container[0].appendChild(div);

    //заполнили поле ячейками, с cell-hover (будут реагировать на отклик)
    fillField(n, 'opponent-playing-field', true);
}

function removeOnClick() {
    //пройтись по всем ячейкам и убрать класс cell-hover
    let cells = document.getElementsByClassName('cell-hover');
    console.log(cells.length);
    console.log(cells);
    for (let i =0; i<cells.length; i++) {
        console.log(i);
        console.log(cells[i]);
        cells[i].classList.remove('cell-hover');
        i--;
    }
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

//ранил
function hurt(className,i,j) {
    document.getElementById('h1').innerText = 'Ранен!';
    const cell_id =`${className}_cell_${i}${j}`;
    let cell = document.getElementById(cell_id);
    cell.classList.add('hurt_cell');
}

//убил
function killed(className,i,j) {
    document.getElementById('h1').innerText = 'Убит!';
    const cell_id =`${className}_cell_${i}${j}`;
    let cell = document.getElementById(cell_id);
    cell.classList.add('killed_cell');
}

//мимо
function hitBy(className,i,j) {
    document.getElementById('h1').innerText = 'Мимо!';
   // let t=getRandomInt(1,9);
    //console.log(t);
    const cell_id =`${className}_cell_${i}${j}`;
    let cell = document.getElementById(cell_id);
    cell.classList.add('hit_by');
   // cell.style.backgroundImage = `url('/static/images/fields_images/hit-by/${t}.png')`;
}

//метод удаления контейнеров со страницы
function clearPageFields() {
    let my = document.getElementsByClassName('my-playing-field');
    my[0].remove();
    let opponent = document.getElementsByClassName('opponent-playing-field');
    opponent[0].remove();
}

//если корабль умер закрасить все ячейки умершего корабля
function killShip(ship) {
    for (let cell in ship) {
    }
}
