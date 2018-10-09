AJS.$(function() {

    // AJS.$("#addrow").on("click", function() {
    //     alert("всем привет !!!");
    // })


// Shows the dialog when the "Show dialog" button is clicked
    AJS.$("#dialog-show-button").click(function(e) {
        e.preventDefault();
        AJS.dialog2("#demo-dialog").show();
    });

// Hides the dialog
    AJS.$("#dialog-submit-button").click(function (e) {
        e.preventDefault();
        AJS.dialog2("#demo-dialog").hide();
    });


    // инициализация селектов
    // группы
    AJS.$("#podr-field").auiSelect2();
    AJS.$("#podr-field").on("change", function(e){
        setOptionsForUsersSelect(AJS.$(this).select2("data").text);
    });
    // сотрудники
    AJS.$("#boss-field").auiSelect2();

    // var data = [ {"id": 1, "text": "Option 1"}, {"id": 2, "text": "Option 2"}, {"id": 2, "text": "Option 2"} ];
    // AJS.$("#boss-field").select2({
    //     data: data
    // })

})

function setOptionsForUsersSelect(groupName) {
    //alert(groupName);

    AJS.$.ajax({
        url: AJS.params.baseURL + "/rest/api/2/group/member?groupname=" + groupName,
        type: "get",
//        data: ({projectsOnly : "true"}),
        dataType: "json",
        success: function(data){
            var arrJSON = [];
            for (var i = 0; i < data.total; i++) {
                var objJSON = {};
                objJSON.id = data.values[i].name;
                objJSON.text = data.values[i].displayName;
                arrJSON.push(objJSON);
            };

            console.log(JSON.stringify(arrJSON));

            var jsonText = JSON.stringify(arrJSON);
            AJS.$("#boss-field").auiSelect2({
                data: jsonText
                // results: function (data, page) {
                //             return {
                //                 results: data
                //             };
            });
        }
    });
}