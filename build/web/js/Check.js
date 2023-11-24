 function check() {
    var dateSortie = document.querySelector('[name="dateSortie"]').value;
    var quantite = document.getElementById("quantite").value;
    var boiteerr = document.getElementById("error");
    while(boiteerr.firstChild)
    {
        boiteerr.removeChild(boiteerr.firstChild);
    }
    var paragraph = document.createElement("p");
    if(dateSortie==="")
    {
        paragraph.textContent ="La date ne peut pas être null";
    }
    else if(quantite<=0)
    {
        paragraph.textContent ="La quantite ne peut etre negative ou nulle";
    }
    boiteerr.appendChild(paragraph);
}

 function checkEntree() {
    var dateEntree = document.querySelector('[name="dateEntree"]').value;
    var quantite = document.getElementById("quantite").value;
    var boiteerr = document.getElementById("error");
    while(boiteerr.firstChild)
    {
        boiteerr.removeChild(boiteerr.firstChild);
    }
    var paragraph = document.createElement("p");
    if(dateEntree==="")
    {
        paragraph.textContent ="La date ne peut pas être null";
    }
    else if(quantite<=0)
    {
        paragraph.textContent ="La quantite ne peut etre negative ou nulle";
    }
    boiteerr.appendChild(paragraph);
}