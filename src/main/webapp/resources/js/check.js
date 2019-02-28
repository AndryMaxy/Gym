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
        repEl.required = true;
        setEl.disabled = false;
        setEl.required = true;
        weightEl.disabled = false;
        weightEl.required = true;
        trEl.bgColor = "#ffffff";
    } else {
        repEl.disabled = true;
        repEl.required = false;
        setEl.disabled = true;
        setEl.required = false;
        weightEl.disabled = true;
        weightEl.required = false;
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
        gramEl.required = true;
        trEl.bgColor = "#ffffff";
    } else {
        gramEl.disabled = true;
        gramEl.required = false;
        trEl.bgColor = "#EBEBE4";
    }
}