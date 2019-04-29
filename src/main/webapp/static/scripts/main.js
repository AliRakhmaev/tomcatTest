class Cell {

    constructor(i,j,state='0'){
        this.y = i;
        this.x= j;
        this.state = state;
    }
    //0-пустая ячейка; 1-проверенная пустая ячейка(мимо); 2-проверенная клетка с кораблем противника(ранил/убил); 3-гарантированно пустая клетка; 4 -клетка с вашим кораблём?
    fresh(){
        status
        //метод из филлера, который рисует что ячейка занята
    }
    hit_by(){
        this.status="1";
        //метод из филлера, которы рисует точку на этой ячейке
    }
    hurt(){
        //метод из филлера, который рисует что ячейка занята
    }
    killed(){

    }
    getIntState(){
        //преобразовать строку в число
    }
}
class Ship {
    constructor(cells=[], state=null){
        this.cells=cells;
        this.state=state;
    }
    addCell(cell){
        this.cells.push(cell);
    }
}
class Armada {
    constructor(ships=[]){
        this.ships=ships;
    }
}

console.log('ArrangeTheShips');
const n=10;
const nn=n*n;
let myShips;
let createShipsMatr = [];
let gameState=0;
const userId = newGuid();
logic();


function logic(){
    if (gameState === 0) {
        //рисуем страницу заполнения кораблей
        drawCreateShipsPage();
        //пересоздаем вспомогательную матрицу для алгоритма проверки валидности заполнения
        fillCreateShipsMatr();
        //добавляем нужную реакцию на клики по матрице
        addListener();
    }
}

function addListener(){
    let list = document.getElementsByClassName('cell-hover');
    console.log(list);
    for(let i=0; i<nn; i++){
        list[i].addEventListener( "click" , clickCell);
    }
}

function clickCell(){
    console.log('id of cell(ClickCell): '+this.id);
    let i=parseInt(this.id[this.id.length-2]);
    let j=parseInt(this.id[this.id.length-1]);

    //если ячейка пуста и ее хотят закрасить
    if (createShipsMatr[i][j]===false){
        //проверяем на то, что ее можно закрасить
        if (isAvailable(i,j)) {
            let cell = document.getElementById(this.id);
            console.log(cell);
            cell.classList.add('my-ship-cell');
            console.log('успех');
        }
        else{
            console.log ('нельзя');
        }
    }
    else{
        //если ячейка уже закрашена удалить
        let cell = document.getElementById(this.id);
        console.log(cell);
        cell.classList.remove('my-ship-cell');
        unset(i,j);
        let btn = document.getElementsByClassName('btn__begin-game');
        btn[0].style.visibility = "hidden";
    }
}

function isAvailable(i,j){
    if (checkDiagonalCollision(createShipsMatr,i,j)===false){//если мы не прошли проверку на диагональные элементы
        createShipsMatr[i][j]=true;
        //запускаем проверку как будто элемент добавлен
        let ships = findShips(createShipsMatr);
        let ship4=0;
        let ship3=0;
        let ship2=0;
        let ship1=0;
        let k=0;
        console.log(ships);
        while(k<ships.length){

            if (ships[k].cells.length===1) ship1++;
            else if (ships[k].cells.length===2) ship2++;
            else if (ships[k].cells.length===3) ship3++;
            else if (ships[k].cells.length===4) ship4++;
            else if (ships[k].cells.length>4) {
                createShipsMatr[i][j]=false;
                return false;
            }
            if((ship4+ship3+ship2+ship1)   >10){
                //лишний однопалубник
                //выкинуть ошибку что таких кораблей >
                createShipsMatr[i][j]=false;
                return false;
            }
            if((ship4+ship3+ship2)   >6){
                //лишний двухпалубник
                //выкинуть ошибку что таких кораблей >
                createShipsMatr[i][j]=false;
                return false;
            }
            if((ship4+ship3)>3){
                //лишний трёхпалубник
                //выкинуть ошибку что таких кораблей >
                createShipsMatr[i][j]=false;
                return false;
            }
            if(ship4>1){
                //лишний четырехпалубник
                //выкинуть ошибку что таких кораблей >
                createShipsMatr[i][j]=false;
                return false;
            }
            k++;
        }
        if((ship1===4)&&(ship2===3)&&(ship3===2)&&(ship4===1)){
            let btn = document.getElementsByClassName('btn__begin-game');
            btn[0].style.visibility = "visible";
            btn[0].addEventListener( "click" , clickBeginGame);
            myShips =ships;
            setMyShipCellsStatus();
        }
        return true;
    }
    else{
        //выкини ошибку пользователю про диагональ
        createShipsMatr[i][j]=false;
        return false;
    }
}

function checkDiagonalCollision(matr,i,j) {
    console.log(matr);
    console.log('i'+i+','+'j'+j);
    return  collisionWith(matr,i+1,j+1) ||
        collisionWith(matr,i+1,j-1) ||
        collisionWith(matr,i-1,j+1) ||
        collisionWith(matr,i-1,j-1);
}

function collisionWith(matr,i,j){
    if(i<0 || j<0 || i>9 || j>9)
        return false;
    return matr[i][j];
}
/*           else if ((i===(n-1))&&(j===(n-1))&&(matr[i-1][j-1]===true)){
               console.log('Нельзя размещать корабль так близко!');
               return false;
           }
               else if ((i===0)&&(j===(n-1))&&(matr[i+1][j-1]===true)){
                   console.log('Нельзя размещать корабль так близко!');
                   return false;
               }
                   else if ()

   return true;
}*/

function fillCreateShipsMatr(){
    for (let i=0; i<n; i++) {
        //значения в матрице = false, что эквивалентно незакрашенным ячейкам
        createShipsMatr.push([false,false,false,false,false,false,false,false,false,false]);
    }
}
//функция установки значения false ячейке (незакрашено)
const unset=(x,y)=>createShipsMatr[x][y]=false;

//функция установки значения false ячейке (закрашено)
const set=(x,y)=>createShipsMatr[x][y]=true;

findShips=(mtr)=>{
    const ships=[];
    const unChecked = mtr.map((val)=>val.map((v)=>true));//ставим в соответствие нашей матрице матрицу с true (в ней rue - непроверенное)
    for(let i in mtr)
        for (let j in mtr[i]){
            if(unChecked[i][j]){
                unChecked[i][j] = false;
                if(mtr[i][j]){
                    const shipBelow = shipUnder(i,j,mtr,unChecked);
                    const shipAtTheRight = shipAfter(i,j,mtr,unChecked);
                    if(shipBelow.cells.length > shipAtTheRight.cells.length)
                        ships.push(shipBelow);
                    else
                        ships.push(shipAtTheRight);
                }
            }
        }
    return ships;
}

shipUnder=(i,j,mtr,unChecked)=>{
    let ship=new Ship();
    for (let k = parseInt(j); (k < 10 && mtr[i][k]); k++){
        unChecked[i][k] = false;
        ship.cells.push(new Cell(parseInt(i),k));
    }
    return ship;
}

shipAfter=(i,j,mtr,unChecked)=>{
    let ship=new Ship();
    for (let k = parseInt(i); (k < 10 && mtr[k][j]); k++){
        unChecked[k][j] = false;
        ship.cells.push(new Cell(k,parseInt(j)));
    }
    return ship;
}

function clickBeginGame(){
    gameState=1;
    //убрали кнопку play
    let btn = document.getElementsByClassName('btn__begin-game');
    btn[0].style.visibility = "hidden";
    //поменять h1
    let h1 = document.getElementById('h1');
    h1.innerText='Ударьте по вражеской армаде!';
    //отключили отклик на поле игрока
    removeOnClick();
    //нарисовали вражеское поле
    drawOpponentField();
    //запустили listener на игру
    addGameListener();

}

function addGameListener(){
    let list = document.getElementsByClassName('cell-hover');
    console.log(list);
    for(let i=0; i<nn; i++){
        list[i].addEventListener( "click" , clickOpponentCell);
    }
}
function clickOpponentCell(){
    console.log('id of cell(ClickCell): '+this.id);
    let i=parseInt(this.id[this.id.length-2]);
    let j=parseInt(this.id[this.id.length-1]);

    let cell = new Cell(i,j,0); //0- нулевой нейтральный статус, когда мы бьем по пустой ячейке предполагается, что она 0
    cell.userId = userId;
    $.ajax({
        url: '/userHitServlet',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(cell),
        success: function (data) {
            console.log(data);
            if(analiseData(data)==='cell'){
                if (data.state ===2 ){//мы ранили ячейку
                    // проверяем у ячеек статус onclick и если его нет добавляем его, чтобы пользователь мог стрелять еще
                    if (checkOnClickCellsStatus('opponent-playing-field_cell')===false){
                        setOnClick('opponent-playing-field_cell');
                    }
                    //подсветить ячейку как раненую
                    hurt('opponent-playing-field',data.y,data.x);
                    //выводим сообщение "вы попали" стреляйте еще
                    changeH1('Вы попали, стреляйте еще!');
                }
                else if (data.state===1) {
                    //мы не ранили ячейку
                    changeH1('Вы не попали, теперь стреляет компьютер!');
                    //подветить как "мимо"
                    hitBy('opponent-playing-field', data.y,data.x);
                    //блокируем у ячеек статус onclick
                    removeOnClick();
                    //запускаем функцию которая обрабатывает стрельбу компьютера
                    getServerHit();
                }
            }
            else if (analiseData(data)==='ship'){
                //мы убили корабль либо выйграли игру
                if(data.state===1){
                    //убили корабль(у него должны все ячейки быть =3)
                    //закрасить все ячейки корабля убитыми +внутри метод который их обводит
                    drawkillShip(data,'opponent-playing-field');
                }
                else if(data.state===2){
                    //конец игры
                    //закрасить все ячейки корабля убитыми +внутри метод который их обводит
                    drawkillShip(data,'opponent-playing-field');
                    endOfGame();
                    document.getElementById('btn_yes').addEventListener("click", clickRepeat);
                    document.getElementById('btn_no').addEventListener("click", clickNoRepeat);
                }
            }
            /* else if (analiseData(data)==='string'){
                запускаем только если будем присылать строку
             }*/
        },
        error:function () {
        }
    });
}
function clickRepeat() {
    location.href=location.href;
}
function clickNoRepeat() {
    Goodbuy();
}

function setMyShipCellsStatus() {
    console.log('function setNullStatus to All Ships');
    for (let ship in myShips){
        for (let cell in myShips[ship].cells){
            myShips[ship].cells[cell].state = 4;//мой корабль, в него еще не стреляли
        }
    }
    console.log(myShips);
}

function testFunction(){
    analiseData(myShips[2]);
    CheckOurSellForHurting(0,0);
}

//функция проверки корабль это или ячейка
function analiseData(data) {
    console.log('aniliseData');
    if(data.cells === undefined){
        //значит это не корабль
        if(data.state ===undefined){
            //значит это строка
            console.log('string');
            return 'string';
        }
        else {
            console.log('cell');
            return 'cell';
        }
    }
    else{
        console.log('ship');
        return 'ship';
    }
}

//проверяет есть ли у ячеек (противника или свои) в зависимости от classname класс "cell-hover" отвечающий за событие onclick
function checkOnClickCellsStatus(className) {
    console.log('checkOnClickCellsStatus');
    let cells = document.getElementsByClassName(className);
    if (hasClass(cells[0],`cell-hover`)){
        return true;
    }
    else return false;
}

//определяет есть ли класс у элемента
function hasClass(element, className) {
    console.log('hasClass');
    let rx = new RegExp('(?:^| )' + className + '(?: |$)');
    return rx.test(element.className);
}

//функция обрабатывающая удары сервера
function getServerHit(){
    //посылаем get запрос чтобы сервер вернул нам ячейку в которую он будет атаковать
    $.ajax({
        url: "/aiStrikesBackServlet",
        type: "get",
        data: {userId:userId},
        //data: userId,
        success: function(data){
            //нам прислали ячейку
            //запускаем функцию проверки попал он или нет
            //(data.y, data.x)
            CheckOurSellForHurting(data.x,data.y);
            /*if (check==='hitby'){
                console.log('Промах');
            }
            else if(CheckOurSellForHurting(data.x,data.y)==='kill'){
                console.log('Пользователя ранили');
            }
            else if (CheckOurSellForHurting(data.x,data.y)==='hurt'){
                console.log('Корабль пользователя убит');
            }*/
        },
        error: function(xhr) {
        }
    });
}

//функция проверки попали по нашим кораблям или нет
function CheckOurSellForHurting(x,y) {//должно быть hit
    console.log('function CheckOurSellForHurting');
    for (let ship in myShips){
        for (let cell in myShips[ship].cells){
            //если среди наших кораблей есть такая ячейка(попали)
            if((myShips[ship].cells[cell].x ===x)&&(myShips[ship].cells[cell].y===y)){ //должно быть hit.x
                console.log('попали');
                //проверка убит или ранен
                if (HurtOrKill(myShips[ship], myShips[ship].cells[cell])){
                    killShip(myShips[ship]);
                    console.log('return kill');
                    return 'kill';
                }
                else {
                    hurtCell( myShips[ship].cells[cell]);
                    console.log('return hurt');
                    return 'hurt';
                }
            }
        }
    }
    hitByCell(x,y);
    console.log('return hitby');
    return 'hitby';

}
//проверка ранена ячейка в корабле пользователя или убита
function HurtOrKill(ship,cell) {
    cell.state=2;
    console.log('function HurtOrKill');
    let length = ship.cells.length;
    for (let cell in ship.cells){
        if (ship.cells[cell].state===2){//если в нашем корабле ячейка ранена, то
            length--;
        }
    }
    if (length<1){
        console.log('корабль убит');
        return true;
    }
    else {
        console.log('ячейка ранена');
        return false;
    }
}
//метод который реагирует на убийство корабля (убивает все ячейки у корабля (меняет на статус 3) и отправляет post-get запросы)
function killShip(ship) {
    console.log('function killShip');
    ship.state=1;
    ship.userId = userId;
    for (let cell in ship.cells){
        ship.cells[cell].state=3;
    }
    //метод который рисует убитый корабль
    drawkillShip(ship,'my-playing-field');

    if(checkEndOfGame()){
        console.log('все корабли убиты, конец игры');
        ship.state=2;
        $.ajax({
            url: '/playerShipDestroyedServlet',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(ship),
            success: function (data) {
            }
        });
        //все корабли убиты => конец игры
    }
    else{
        console.log('отправляем корабль ship и запускаем заново ожидание');
        $.ajax({
            url: '/playerShipDestroyedServlet',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(ship),
            success: function (data) {
                getServerHit();
            },
            error: function (){
                getServerHit();
            }
        });

    }

}
//метод который проверяет все ли корабли у нас убиты
function checkEndOfGame() {
    console.log('function checkEndOfGame');
    let length = myShips.length;
    for (let ship in myShips){
        if (myShips[ship].state===1){
            length--;
        }
    }
    if (length===0){//осталась только одна живая ячейка
        console.log('последний корабль убит, конец игры!');
        return true;
    }
    else {
        console.log('остались еще корабли');
        return false;
    }
}
//метод который реагирует на ранение ячейки
function hurtCell(cell) {
    console.log('function hurtCell');
    cell.state = 2;
    cell.userId = userId;
    //рисуем раненую ячейку
    hurt('my-playing-field',cell.y,cell.x);
    //post запрос с этой ячейкой
    $.ajax({
        url: '/aiStrikesBackServlet',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(cell),
        success: function (data) {
            //запускаем заново get
            getServerHit();
        }
    });


}
function hitByCell(x,y) {
    console.log('function hitByCell');
    //рисуем, что мы попали мимо
    hitBy('my-playing-field',y,x);
    //отсылаем post запрос компьютеру что компьютер промахнулся
    let cell = new Cell(y,x,1);
    cell.userId = userId;
    $.ajax({
        url: '/aiStrikesBackServlet',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(cell),
        success: function (data) {
        }
    });
    //присваиваем onclick полю компьютера
}

function newGuid(){
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}
