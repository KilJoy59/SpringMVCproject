$(function() {
    var now = new Date();
    var format_time= now.toLocaleTimeString("ru");
    var format_date = now.toLocaleDateString("ru");

    const appendTask = function(data) {

        var taskCode = 'Задача: ' + data.name + '<br>' + 'Описание: '
                + data.description + '<br>' +
            'Дата: ' + format_date + ' ' +  format_time;
        $('#task-list')
            .append('<div>' + taskCode + '</div>');
    };

    $.get('/tasks/', function (response) {
        for (i in response) {
            appendTask(response[i]);
        }
    });

    $('.open-button').click(function () {
        $('.form-popup').css('display','flex');
    });

    //Adding task
    $('#save-task').click(function() {
        var data = $('#todo-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/tasks/',
            data: data,
            success: function(response){
                $('.form-popup').css('display', 'none');
                var task = {};
                task.id = response.id;
                var dataArray = $('#todo-form form').serializeArray();
                for(i in dataArray) {
                    task[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(task);
            }
        });
        return false;
    });
});