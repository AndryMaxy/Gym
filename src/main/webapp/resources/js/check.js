function checkExercise(id) {
    var exercise = 'exercise-' + id;
    var nodeList = document.getElementsByName(exercise);
    var checkBox = 'checkerEx-' + id;
    var checkerEl = document.getElementsByName(checkBox).item(0);
    var tr = 'trEx-' + id;
    var trEl = document.getElementById(tr);
    checker(checkerEl, nodeList, trEl);
}

function checkProduct(id) {
    var product = 'product-' + id;
    var nodeList = document.getElementsByName(product);
    var checkBox = 'checkerPr-' + id;
    var checkerEl = document.getElementsByName(checkBox).item(0);
    var tr = 'trPr-' + id;
    var trEl = document.getElementById(tr);
    checker(checkerEl, nodeList, trEl);
}

function checker(checkerEl, nodeList, trEl) {
    if (checkerEl.checked) {
        for (var i = 0; i < nodeList.length; i++) {
            var element1 = nodeList.item(i);
            element1.disabled = false;
            element1.required = true;
        }
        trEl.bgColor = "#ffffff";
    } else {
        for (var j = 0; j < nodeList.length; j++) {
            var element2 = nodeList.item(j);
            element2.disabled = true;
            element2.required = false;
            element2.value = null;
        }
        trEl.bgColor = "#EBEBE4";
    }
}
