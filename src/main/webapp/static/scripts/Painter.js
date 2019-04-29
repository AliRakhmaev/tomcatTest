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
                div.classList.add(`${className}_cell`);
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

function setOnClick(className) {
    console.log('setOnClick');
    //пройтись по всем ячейкам и добавить 'cell-hover';
    let cells = document.getElementsByClassName(className);
    for (let i =0; i<cells.length; i++) {
        cells[i].classList.add('cell-hover');
    }
    console.log(cells);
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
    const cell_id =`${className}_cell_${i}${j}`;
    let cell = document.getElementById(cell_id);
    if (cell.classList.contains('hurt_cell')){
        cell.classList.remove('hurt_cell')
    }
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

//если корабль умер закрасить все ячейки умершего корабля (перед этим необходимо закрасить ячейки 3 статусом (убитая))
function drawkillShip(ship, className) {
    document.getElementById('h1').innerText = 'Убит!';
    for (let cell in ship.cells) {
        if(ship.cells[cell].state===3){
            let i = ship.cells[cell].y;
            let j = ship.cells[cell].x;
            killed(className, i, j)
            //убрать my-ship class если требуется
        }
    }
    markAround(ship,className);
}

function markAround(ship,className) {
    for (let cell of ship.cells){
        let i = cell.y;
        let j = cell.x;
        if (checkMarkCells(className,i+1,j)) hitBy(className,i+1,j);
        if (checkMarkCells(className,i+1,j-1)) hitBy(className,i+1,j-1);
        if (checkMarkCells(className,i+1,j+1)) hitBy(className,i+1,j+1);
        if (checkMarkCells(className,i,j-1)) hitBy(className,i,j-1);
        if (checkMarkCells(className,i,j+1)) hitBy(className,i,j+1);
        if (checkMarkCells(className,i-1,j+1)) hitBy(className,i-1,j+1);
        if (checkMarkCells(className,i-1,j)) hitBy(className,i-1,j);
        if (checkMarkCells(className,i-1,j-1)) hitBy(className,i-1,j-1);
    }
    document.getElementById('h1').innerText = 'Убит!';
}
function checkMarkCells(className,im,jm) {
    if(im<0 || jm<0 || im>9 || jm>9)
        return false;
    const cell_id =`${className}_cell_${im}${jm}`;
    let cell = document.getElementById(cell_id);
    if(cell.classList.contains('killed_cell')){
        return false;
    }
    else{
        return true;
    }
}

function changeH1(string) {
    document.getElementById('h1').innerText = string;
}

function endOfGame(){
    let div = document.createElement('div');
    div.classList.add('endOfgame');
    div.classList.add('w3-center');

    let h1 = document.createElement('h1');
    h1.id = 'h1_end';
    h1.className = "w3-center h1-end";
    h1.innerText = 'Конец игры!';
    let h12 = document.createElement('h1');
    h12.id = 'h12_end';
    h12.className = "w3-center h1-end";
    h12.innerText = 'Вы хотите сыграть ещё?';
    div.appendChild(h1);
    div.appendChild(h12);
    let container = document.createElement('div');
    container.classList.add('buttons_div');
    //добавляем невидимую кнопки
    let btn1 = document.createElement('button');
    btn1.className = "w3-center w3-btn btn__game-repeat";
    btn1.innerText = "Да! =)";
    btn1.id ='btn_yes';
    let btn2 = document.createElement('button');
    btn2.className = "w3-center w3-btn btn__game-repeat";
    btn2.innerText = "Нет =(";
    btn2.id ='btn_no';
    container.appendChild(btn1);
    container.appendChild(btn2);
    div.appendChild(container);
    let field_container = document.getElementsByClassName('wrapper');
    field_container[0].appendChild(div);
}

function Goodbuy(){
    document.getElementById('h1_end').remove();
    document.getElementById('h12_end').innerText = 'Спасибо, что играли в нашу игру!';
    document.getElementById('btn_yes').remove();
    document.getElementById('btn_no').remove();
}






