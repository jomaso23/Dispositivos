 function validateForm(){
        var checkBoxes = document.getElementsByName("dispositivosSelecciondos");
        var isChecked = false;
        for( var i =0;i<checkBoxes.length;i++){
            if(checkBoxes[i].checked){
            isChecked = true;
            break;
            }
        }
        if(!isChecked){
        alert("debes seleccionar al menos un dispositivo");
        return false;
        }
        return true;
//    console.log("prueba");
//    return true;
    }