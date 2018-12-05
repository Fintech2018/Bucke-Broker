const dialogTemplate = '\
    <div class ="modal" data-backdrop="static" id="digimango_messageBox" role="dialog" aria-hidden="true">\
        <div class ="modal-dialog">\
            <div class ="modal-content">\
                <div class ="modal-body">\
                    <p class ="text-success" id="digimango_messageBoxMessage" style="color:#007bff !important">Some text in the modal.</p>\
                    <p><textarea id="digimango_messageBoxTextArea" cols="70" rows="5"></textarea></p>\
                </div>\
                <div class ="modal-footer">\
                    <button type="button" class ="btn btn-primary" id="digimango_messageBoxOkButton">OK</button>\
                    <button type="button" class ="btn btn-default" data-dismiss="modal" id="digimango_messageBoxCancelButton">Cancel</button>\
                </div>\
            </div>\
        </div>\
    </div>';


// See the comment inside function digimango_onOkClick(event) {
var digimango_numOfDialogsOpened = 0;


function messageBox(msg, significance, options, actionConfirmedCallback) {
    if ($('#digimango_MessageBoxContainer').length == 0) {
        var iDiv = document.createElement('div');
        iDiv.id = 'digimango_MessageBoxContainer';
        document.getElementsByTagName('body')[0].appendChild(iDiv);
        $("#digimango_MessageBoxContainer").html(dialogTemplate);
    }

    var okButtonName, cancelButtonName, showTextBox, textBoxDefaultText;

    if (options == null) {
        okButtonName = 'OK';
        cancelButtonName = null;
        showTextBox = null;
        textBoxDefaultText = null;
    } else {
        okButtonName = options.okButtonName;
        cancelButtonName = options.cancelButtonName;
        showTextBox = options.showTextBox;
        textBoxDefaultText = options.textBoxDefaultText;
    }

    if (showTextBox == true) {
        if (textBoxDefaultText == null)
            $('#digimango_messageBoxTextArea').val('');
        else
            $('#digimango_messageBoxTextArea').val(textBoxDefaultText);

        $('#digimango_messageBoxTextArea').show();
    }
    else
        $('#digimango_messageBoxTextArea').hide();

    if (okButtonName != null)
        $('#digimango_messageBoxOkButton').html(okButtonName);
    else
        $('#digimango_messageBoxOkButton').html('OK');

    if (cancelButtonName == null)
        $('#digimango_messageBoxCancelButton').hide();
    else {
        $('#digimango_messageBoxCancelButton').show();
        $('#digimango_messageBoxCancelButton').html(cancelButtonName);
    }

    $('#digimango_messageBoxOkButton').unbind('click');
    $('#digimango_messageBoxOkButton').on('click', { callback: actionConfirmedCallback }, digimango_onOkClick);

    $('#digimango_messageBoxCancelButton').unbind('click');
    $('#digimango_messageBoxCancelButton').on('click', digimango_onCancelClick);

    var content = $("#digimango_messageBoxMessage");

    if (significance == 'error')
        content.attr('class', 'text-danger');
    else if (significance == 'warning')
        content.attr('class', 'text-warning');
    else
        content.attr('class', 'text-success');

    content.html(msg);

    if (digimango_numOfDialogsOpened == 0)
        $("#digimango_messageBox").modal();

    digimango_numOfDialogsOpened++;
}

function digimango_onOkClick(event) {
    // JavaScript's nature is unblocking. So the function call in the following line will not block,
    // thus the last line of this function, which is to hide the dialog, is executed before user
    // clicks the "OK" button on the second dialog shown in the callback. Therefore we need to count
    // how many dialogs is currently showing. If we know there is still a dialog being shown, we do
    // not execute the last line in this function.
    if (typeof (event.data.callback) != 'undefined')
        event.data.callback($('#digimango_messageBoxTextArea').val());

    digimango_numOfDialogsOpened=0;

    if (digimango_numOfDialogsOpened == 0)
        $('#digimango_messageBox').modal('hide');
}

function digimango_onCancelClick() {
    digimango_numOfDialogsOpened--;

    if (digimango_numOfDialogsOpened == 0)
        $('#digimango_messageBox').modal('hide');
}