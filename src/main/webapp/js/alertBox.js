//----------------------------------------------------------------------------------------------------------------------

const alertBox = (function () {

    //------------------------------------------------------------------------------------------------------------------

    function hide()
    {
        document.getElementById('alert_box').classList.add('d-none');
    }

    //------------------------------------------------------------------------------------------------------------------

    function show(str, err) {

        function updateClassList(toRemove, toAdd) {
            document.getElementById('alert_box').classList.remove(toRemove);
            document.getElementById('alert_box').classList.add(toAdd);
        }

        if(err)
            updateClassList('alert-sucess', 'alert-danger'); //alert box red
        else
            updateClassList('alert-danger', 'alert-success'); //alert box green

        document.getElementById('alert_box').innerHTML = str;
        document.getElementById('alert_box').classList.remove('d-none');

    }

    //------------------------------------------------------------------------------------------------------------------

    return {
        hide: hide,
        show: show
    }

})();

//----------------------------------------------------------------------------------------------------------------------