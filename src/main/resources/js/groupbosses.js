AJS.$(function() {

    // кнопка открытия диалога для добавления строки
    AJS.$("#dialog-show-button").click(function(e) {
        e.preventDefault();
        showDialog(true);
    });

    // кнопка субмит
    AJS.$("#dialog-submit-button").click(function (e) {
        e.preventDefault();
        submitDialog();
        AJS.dialog2("#demo-dialog").hide();
    });

    // кнопка отмены
    AJS.$("#dialog-cancel-button").click(function (e) {
        e.preventDefault();
        AJS.dialog2("#demo-dialog").hide();
    });



    // инициализация селектов
    // группы
    AJS.$("#podr-field").auiSelect2();

    //////////////////////////////////////
    // при изменении подразделения
    //////////////////////////////////////
    AJS.$("#podr-field").on("change", function(e){
        // сбрасываем значение сотрудников
        AJS.$("#boss-field").auiSelect2('data', null);
        // устанавливаем новый список выбора сотрудников
        setOptionsForUsersSelect(AJS.$(this).auiSelect2("data").text);
    });

    // первоначальная инициализация - необходима, без нее не заполняется селект при первом выборе
    var data = [ {"id": "q", "text": "Option 1"}, {"id": "qq", "text": "Option 2"}, {"id": "qqq", "text": "Option 2"} ];
    AJS.$("#boss-field").auiSelect2({
        data: data
    })

    //////////////////////////////////////
    // инициализация событий кнопок в таблице
    //////////////////////////////////////

    // редактирование строки
    AJS.$("#bosses-table tbody tr .doedit").each(function(index, value) {
        AJS.$(this).click(function(e) {

            var parentRow = AJS.$(this).parent().parent();

            // получаем переменные
            var numberid        = parentRow.find("input[type='hidden'][name='numberid']").val();
            var groupname       = parentRow.find("span.groupname").text();
            var userdisplayname = parentRow.find("span.boss").text();
            var username        = parentRow.find("input[type='hidden'][name='username']").val();

            // console.log(numberid);
            // console.log(groupname);
            // console.log(userdisplayname);
            // console.log(username);

            // присваиваем значения на форму редактирования
            AJS.$("#demo-dialog input[type='hidden'][name='numberid']").val(numberid);
            AJS.$("#demo-dialog input[type='hidden'][name='groupname']").val(groupname);
            AJS.$("#demo-dialog input[type='hidden'][name='username']").val(username);
            AJS.$("#demo-dialog input[type='hidden'][name='userdisplayname']").val(userdisplayname);

            showDialog(false);
        });
    });

    // удаление строки
    AJS.$("#bosses-table tbody tr .dodelete").each(function(index, value) {
        AJS.$(this).click(function(e) {
            AJS.$(this).parent().parent().remove();
        });
    });

});

// открытие окна диалога
// forAddRow = true - добавление новой записи
// forAddRow = false - редактирование существующей записи
// function showDialog(forAddRow) {
function showDialog(isAddRow) {
    if (isAddRow) {
        AJS.$("#demo-dialog .aui-dialog2-header-main").text("Добавить запись");
        AJS.$("#demo-dialog input[type='hidden'][name='typezapros']").val("addrow");
    } else {
        AJS.$("#demo-dialog .aui-dialog2-header-main").text("Редактировать запись");
        AJS.$("#demo-dialog input[type='hidden'][name='typezapros']").val("editrow");

        // нужно присвоить значения текущей группы и пользователя из строки
        //$("#yourSelector").select2("data", { id: 1, text: "Some Text" });

        // группа
        AJS.$("#podr-field").auiSelect2("data", {
            id: AJS.$("#demo-dialog input[type='hidden'][name='groupname']").val(),
            text: AJS.$("#demo-dialog input[type='hidden'][name='groupname']").val()
        });

        // сотрудник
        // устанавливаем новый список выбора сотрудников

        var username = AJS.$("#demo-dialog input[type='hidden'][name='username']").val();
        var userdisplayname = AJS.$("#demo-dialog input[type='hidden'][name='userdisplayname']").val();

        console.log(username);
        console.log(userdisplayname);

        setOptionsForUsersSelect(AJS.$("#podr-field").auiSelect2("data").text);
        AJS.$("#boss-field").auiSelect2("data", {
            id: username,
            text: userdisplayname
        });


    }

    AJS.dialog2("#demo-dialog").show();
}

// передача данных на сервер
function submitDialog() {
    // определяем - добавляем строку или редактируем текущую

    if (AJS.$("#demo-dialog input[type='hidden'][name='typezapros']").val() == "addrow") {
        // добавляем строку
    } else {
        // редактируем текущую

    }



}

// настройка вариантов выбора пользователя в зависимости от названия группы
function setOptionsForUsersSelect(groupName) {
    AJS.$.ajax({
        url: AJS.params.baseURL + "/rest/api/2/group/member?groupname=" + groupName,
        type: "get",
        // async: false,
        dataType: "json",
        success: function(data){

            // console.log("================== data from server");
            // console.log(data);

            var arrJSON = [];
            for (var i = 0; i < data.total; i++) {
                var objJSON = {};
                objJSON.id = data.values[i].name;
                objJSON.text = data.values[i].displayName;
                arrJSON.push(objJSON);
            };


            // var jsonText = JSON.stringify(arrJSON);
            //
            // console.log("================== data transformed");
            // console.log(jsonText);

            AJS.$("#boss-field").auiSelect2({
                data: arrJSON
            });
        }
    });

}