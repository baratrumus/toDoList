$(loadItems());

function loadItems() {
    var onlyDoneTasks;
    if ($('#chbDone').is(":checked")) {
        onlyDoneTasks = 'y';
    } else {
        onlyDoneTasks = 'n';
    }
    console.log("Check State is " + onlyDoneTasks);
    $.ajax('./items', {
        method : 'get',
        data: {
            onlyDone: onlyDoneTasks,
        },
        complete: function(data) {
            if (typeof data === "undefined") {
                return;
            }
            var items = JSON.parse(data.responseText);
            console.log(items);
            $("#currentItems tbody").empty();
            $.each(items, function (key, value) {
                var akk = "";
                var rowId = value.id;
                var currentState = value.done;
                console.log(rowId, currentState);
                $.each(value, function (key, value) {
                    if ((key === 'created')) {
                        akk += '<td>' + new Date(value).toLocaleString() + '</td>';
                    } else {
                        akk += '<td>' + value + '</td>';
                    }
                });
                akk += '<td><input type="checkbox" onclick="changeState(' + rowId;
                akk += ',' + currentState + ')"/></td>';
                console.log(akk);
                $('#currentItems tbody').append('<tr>' + akk + '</tr>');
            });

        }
    });
}


function addTask() {
    var description = $('#description');

    $.ajax('./items', {
        method : 'post',
        data: description,
        cache: false,
        complete: loadItems()
    });
}


function changeState(rowId, state) {
    var stateData = { itemId : rowId,
        chState : state }

    $.ajax('./state', {
        method : 'post',
        data: stateData,
        complete: loadItems()
    });
}
