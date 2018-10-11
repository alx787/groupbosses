AJS.$(function() {

    // кнопка открытия диалога для добавления строки
    AJS.$("#test-ajax").click(function(e) {

        console.log("привет");

        AJS.$.ajax({
            type:   "post",
            // dataType: "json",
            // contentType: "application/json; charset=utf-8",
            url:    AJS.params.baseURL + "/secure/GroupBossesSettingsAction!add.jspa",
            // data:   strJSON,
            data:  {bossname: "Gorbunkov", groupname: "inrotech"},
            // success: function(e) {
            //     AJS.messages.info({
            //         title: '',
            //         fadeout: true,
            //         body: '<p>Сохранено</p>',
            //     });
            // },
            // error: function(xhr, ajaxOptions, thrownError) {
            //     AJS.messages.info({
            //         title: '',
            //         fadeout: true,
            //         body: '<p>Не сохранено</p>',
            //     });
            // },
        });
    });

});

