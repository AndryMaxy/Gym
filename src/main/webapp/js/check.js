function checkExercise(id) {
    var rep = 'repCount-' + id;
    var repEl = document.getElementsByName(rep).item(0);
    var set = 'setCount-' + id;
    var setEl = document.getElementsByName(set).item(0);
    var weight = 'weight-' + id;
    var weightEl = document.getElementsByName(weight).item(0);
    var checker = 'checkerEx-' + id;
    var checkerEl = document.getElementsByName(checker).item(0);
    var tr = 'trEx-' + id;
    var trEl = document.getElementById(tr);
    if (checkerEl.checked) {
        repEl.disabled = false;
        setEl.disabled = false;
        weightEl.disabled = false;
        trEl.bgColor = "#ffffff";
    } else {
        repEl.disabled = true;
        setEl.disabled = true;
        weightEl.disabled = true;
        trEl.bgColor = "#EBEBE4";
    }
}
function checkProduct(id) {
    var gram = 'gram-' + id;
    var gramEl = document.getElementsByName(gram).item(0);
    var checker = 'checkerPr-' + id;
    var checkerEl = document.getElementsByName(checker).item(0);
    var tr = 'trPr-' + id;
    var trEl = document.getElementById(tr);
    if (checkerEl.checked) {
        gramEl.disabled = false;
        trEl.bgColor = "#ffffff";
    } else {
        gramEl.disabled = true;
        trEl.bgColor = "#EBEBE4";
    }
}