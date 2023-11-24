function checkStock() {
    var dateSortie = document.querySelector('[name="dateSortie"]').value;
    var idarticle = document.getElementById("idarticle").value;
    var idmagasin = document.getElementById("idmagasin").value;
    var idunite = document.getElementById("idunite").value;
    var quantite = document.getElementById("quantite").value;
    var boiteerr = document.getElementById("error");
    while(boiteerr.firstChild)
    {
        boiteerr.removeChild(boiteerr.firstChild);
    }
    var xhr1 = new XMLHttpRequest();
    xhr1.open('GET', 'http://localhost:8080/GestionStock/StockController?dateSortie=' + dateSortie + '&idarticle='+idarticle+'&idmagasin='+idmagasin+'&idunite='+idunite+'&quantite='+quantite, true);
    xhr1.onreadystatechange = function () {
        if (xhr1.readyState === 4 && xhr1.status === 200) {
            var responseData = xhr1.responseText.trim();
            if (responseData.trim() !== /"/g) {
                var paragraph = document.createElement("p");
                paragraph.textContent = responseData.replace(/"/g, ' ');
                boiteerr.appendChild(paragraph);
            }
        }
    };
    xhr1.send();
}