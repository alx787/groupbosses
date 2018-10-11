AJS.$(function() {

    // кнопка открытия диалога для добавления строки
    AJS.$("#dialog-show-button").click(function(e) {
        e.preventDefault();
        showDialog(true);
    });

    // кнопка субмит
    AJS.$("#dialog-submit-button").click(function (e) {
        e.preventDefault();

        var errStatus = submitDialog();

        if (errStatus.error) {
            AJS.messages.error("#aui-message-bar", {
                title: 'Ошибка !!!',
                body: '<p>' + errStatus.errorText + '</p>'
            });
        } else {
            AJS.dialog2("#demo-dialog").hide();
        }

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
    // var data = [ {"id": "q", "text": "Option 1"}, {"id": "qq", "text": "Option 2"}, {"id": "qqq", "text": "Option 2"} ];
    // AJS.$("#boss-field").auiSelect2({
    //     data: data
    // })

    var data = [ ];
    AJS.$("#boss-field").auiSelect2({
        data: data
    })


    //AJS.$("#boss-field").auiSelect2();


    //////////////////////////////////////
    // инициализация событий кнопок в таблице
    //////////////////////////////////////

    // редактирование строки
    AJS.$("#bosses-table tbody tr .doedit").each(function(index, value) {
        AJS.$(this).click(function(e) {

            // var parentRow = AJS.$(this).parent().parent();
            //
            // // получаем переменные
            // var numberid        = parentRow.find("input[type='hidden'][name='numberid']").val();
            // var groupname       = parentRow.find("span.groupname").text();
            // var userdisplayname = parentRow.find("span.boss").text();
            // var username        = parentRow.find("input[type='hidden'][name='username']").val();
            //
            // // console.log(numberid);
            // // console.log(groupname);
            // // console.log(userdisplayname);
            // // console.log(username);
            //
            // // присваиваем значения на форму редактирования
            // AJS.$("#demo-dialog input[type='hidden'][name='numberid']").val(numberid);
            // AJS.$("#demo-dialog input[type='hidden'][name='groupname']").val(groupname);
            // AJS.$("#demo-dialog input[type='hidden'][name='username']").val(username);
            // AJS.$("#demo-dialog input[type='hidden'][name='userdisplayname']").val(userdisplayname);
            //
            // showDialog(false);

            clickEventonEditRow(AJS.$(this));
        });
    });

    // удаление строки
    AJS.$("#bosses-table tbody tr .dodelete").each(function(index, value) {
        AJS.$(this).click(function(e) {
            AJS.$(this).parent().parent().remove();
        });
    });
});


function clickEventonEditRow(editButtonObj) {
    var parentRow = editButtonObj.parent().parent();

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

}

// открытие окна диалога
// forAddRow = true - добавление новой записи
// forAddRow = false - редактирование существующей записи
// function showDialog(forAddRow) {
function showDialog(isAddRow) {
    if (isAddRow) {
        // меняем надписи на форме
        AJS.$("#demo-dialog .aui-dialog2-header-main").text("Добавить запись");
        AJS.$("#demo-dialog input[type='hidden'][name='typezapros']").val("addrow");

        // сделаем группу доступной для редактирования
        AJS.$("#podr-field").removeAttr("readonly");

        // инициализация списка выбора пользователя
        setOptionsForUsersSelect(AJS.$("#podr-field").auiSelect2("data").text);

    } else {
        // меняем надписи на форме
        AJS.$("#demo-dialog .aui-dialog2-header-main").text("Редактировать запись");
        AJS.$("#demo-dialog input[type='hidden'][name='typezapros']").val("editrow");

        // нужно присвоить значения текущей группы и пользователя из строки
        //$("#yourSelector").select2("data", { id: 1, text: "Some Text" });

        // группа
        AJS.$("#podr-field").auiSelect2("data", {
            id: AJS.$("#demo-dialog input[type='hidden'][name='groupname']").val(),
            text: AJS.$("#demo-dialog input[type='hidden'][name='groupname']").val()
        });
        // сделаем группу только для чтения
        AJS.$("#podr-field").attr("readonly", "readonly");

        // сотрудник
        // устанавливаем новый список выбора сотрудников

        var username = AJS.$("#demo-dialog input[type='hidden'][name='username']").val();
        var userdisplayname = AJS.$("#demo-dialog input[type='hidden'][name='userdisplayname']").val();

        // console.log(username);
        // console.log(userdisplayname);

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
        //////////////////////////////////////////////////
        // добавляем строку
        //////////////////////////////////////////////////

        // проконтролируем что такой группы в таблице нет
        if (AJS.$("#podr-field").auiSelect2("data") != null) {
            var objGrNames = AJS.$("#bosses-table tbody span.groupname");

            for (var i = 0; i < objGrNames.length; i++) {
                if (AJS.$(objGrNames[i]).text() == AJS.$("#podr-field").auiSelect2("data").text) {
                    return {error:true, errorText: "Подразделение " + AJS.$(objGrNames[i]).text() + " настроено ранее"};
                }
            }

            // AJS.$("#bosses-table tbody span.groupname").each(function(index, value){
            //
            //     console.log("=============");
            //     console.log(value);
            //     console.log(AJS.$(value));
            //     console.log(AJS.$(value).text());
            //     console.log(AJS.$("#podr-field").auiSelect2("data").text);
            //
            //     console.log(AJS.$(value).text() == AJS.$("#podr-field").auiSelect2("data").text);
            //
            //     if (AJS.$(value).text() == AJS.$("#podr-field").auiSelect2("data").text) {
            //         return {error:true, errorText: "Подразделение " + AJS.$(value).text() + " настроено ранее"};
            //     }
            // })
        };


        // проверка заполненности подразделения
        if (AJS.$("#podr-field").auiSelect2("data") == null) {
            return {error:true, errorText: "Не выбрано подразделение"};
        };
        // проверка заполненности руководителя
        if (AJS.$("#boss-field").auiSelect2("data") == null) {
            return {error:true, errorText: "Не выбран руководитель"};
        };

        // добавляемая строка
        var htmlRowObj = AJS.$("<tr>"
                       + "<td><span class=\"groupname\"></span><input type=\"hidden\" name=\"numberid\" value=\"\"></td>"
                       + "<td><span class=\"boss\"></span><input type=\"hidden\" name=\"username\" value=\"\"></td>"
                       + "<td><button class=\"aui-button doedit\">Редактировать</button></td>"
                       + "<td><button class=\"aui-button dodelete\">Удалить</button></td>"
                       + "</tr>");

        // добавляем
        AJS.$("#bosses-table tbody").append(htmlRowObj);

        // кнопка редактирование
        htmlRowObj.find("button.doedit").click(function (e) {
            clickEventonEditRow(AJS.$(this));
        });

        // кнопка удаление строки
        htmlRowObj.find("button.dodelete").each(function(index, value) {
            AJS.$(this).click(function(e) {
                AJS.$(this).parent().parent().remove();
            });
        });

        // устанавливаем значения
        //var numberid            = AJS.$("#demo-dialog input[type='hidden'][name='numberid']").val();
        var newgroupname        = AJS.$("#podr-field").auiSelect2("data").id;
        var newusername         = AJS.$("#boss-field").auiSelect2("data").id;
        var newuserdisplayname  = AJS.$("#boss-field").auiSelect2("data").text;

        htmlRowObj.find("span.groupname").text(newgroupname);
        htmlRowObj.find("span.boss").text(newuserdisplayname);
        htmlRowObj.find("input[name='username'][type='hidden']").val(newusername);


    } else {
        //////////////////////////////////////////////////
        // редактируем текущую

        if (AJS.$("#boss-field").auiSelect2("data") == null) {
            return {error:true, errorText: "Не выбран руководитель"};
        }

        var numberid            = AJS.$("#demo-dialog input[type='hidden'][name='numberid']").val();
        var newgroupname        = AJS.$("#podr-field").auiSelect2("data").id;
        var newusername         = AJS.$("#boss-field").auiSelect2("data").id;
        var newuserdisplayname  = AJS.$("#boss-field").auiSelect2("data").text;

        if (AJS.$("#bosses-table input[value='" + numberid + "']").length != 1) {
            return {error:true, errorText: "Не найден идентификатор"};
        }

        var parentRow = AJS.$("#bosses-table input[value='" + numberid + "']").parent().parent();
        parentRow.find("span.groupname").text(newgroupname);
        parentRow.find("span.boss").text(newuserdisplayname);
        parentRow.find("input[name='username'][type='hidden']").val(newusername);
    }


    return {error:false, errorText: ""};

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