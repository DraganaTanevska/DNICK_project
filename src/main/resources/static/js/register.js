function proverka() {
    pass = document.getElementById("password").value
    cpass = document.getElementById("cPassword").value
    if (cpass != pass) {
        kopce=document.getElementById("kopce")
        kopce.hidden=true
        window.alert("passwords do not match")
    }
    if(cpass == pass){
        kopce=document.getElementById("kopce")
        kopce.hidden=false
    }
}