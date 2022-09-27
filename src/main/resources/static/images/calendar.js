
function toggleAvailabilities() {
    this.classList.toggle('visible');
    this.classList.toggle('invisible');
}
document.querySelector('.demo').addEventListener('click', toggleAvailabilities);

function almostFull(){

}
function completelyFull(){

}
function notFull(){

}

function toggleBold() {
    this.classList.toggle('makeBold');
    this.classList.toggle('makeNotBold');
}
document.querySelector('.demo').addEventListener('click', toggleBold);
