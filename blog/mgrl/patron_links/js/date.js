var today = new Date();
var dd = String(today.getDate()).padStart(2, '0');
var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
var yyyy = today.getFullYear();
var n = today.toLocaleTimeString();
today = mm + '/' + dd + '/' + yyyy;
           
document.getElementById('dateBox').innerHTML = today + "&nbsp;&nbsp;&nbsp;&nbsp;" + n;