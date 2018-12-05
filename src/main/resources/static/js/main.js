$(document).ready(function () {

    $("#btnSubmit").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_fileUpload_submit();
        $('#positivefeedbackLoadResult').text("");
        $('#negativefeedbackLoadResult').text("");
//        $('#neutralfeedbackLoadResult').text("");
        $("#feedbackLoadResult").text("");
    });
    
    $("#btnFeedbackSubmit").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_feedback_submit();
        $('#positivefeedbackLoadResult').text("");
        $('#negativefeedbackLoadResult').text("");
        $('#neutralfeedbackLoadResult').text("");
        $("#result").text("");
    });
    
    $("#btnFeedbackSubmitPositive").click(function (event) {
        event.preventDefault();
        $("#btnFeedbackSubmitPositive").prop("disabled", true);
        // var url="/broker/storage/uploadFile/positive";
        var url= "jdjoydeepde-eval-test.apigee.net/reviewfeedbackproxy/positive?apikey=QclhCTGbH2XgFiotkytQr33vKJdAOvQJ";
        generateFeedbackBySystem(url,"positive");
        $("#btnFeedbackSubmitPositive").prop("disabled", false);
        $('#negativefeedbackLoadResult').text("");
        $('#neutralfeedbackLoadResult').text("");
        $("#feedbackLoadResult").text("");
        $("#result").text("");
    });
    $("#btnFeedbackSubmitNegative").click(function (event) {
        event.preventDefault();
        $("#btnFeedbackSubmitNegative").prop("disabled", true);
       //= var url="/broker/storage/uploadFile/negative";
        var url = "jdjoydeepde-eval-test.apigee.net/reviewfeedbackproxy/negative?apikey=QclhCTGbH2XgFiotkytQr33vKJdAOvQJ";
        		
        generateFeedbackBySystem(url,"negative");
        $("#btnFeedbackSubmitNegative").prop("disabled", false);
        $('#positivefeedbackLoadResult').text("");
        $('#neutralfeedbackLoadResult').text("");
        $("#feedbackLoadResult").text("");
        $("#result").text("");
    });
    $("#btnFeedbackSubmitNeutral").click(function (event) {
        event.preventDefault();
        $("#btnFeedbackSubmitNeutral").prop("disabled", true);
        //var url="/broker/storage/uploadFile/neutral";
        var url = "jdjoydeepde-eval-test.apigee.net/reviewfeedbackproxy/neutral?apikey=QclhCTGbH2XgFiotkytQr33vKJdAOvQJ";
        
        
        generateFeedbackBySystem(url,"neutral");
        $("#btnFeedbackSubmitNeutral").prop("disabled", false);
        $('#negativefeedbackLoadResult').text("");
        $('#positivefeedbackLoadResult').text("");
        $("#feedbackLoadResult").text("");
        $("#result").text("");
    });

});

function fire_ajax_fileUpload_submit() {

    // Get form
   var form = $('#fileUploadForm')[0];
 /*  if($('.file').val()==undefined){
	   $('#result').css('color','Red');
       $("#result").text("No File Selected!");
	   return;
   }*/
    var data = new FormData(form);
    $("#btnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
       // url: "/broker/storage/uploadFile/feedbackFile",
        url : "jdjoydeepde-eval-test.apigee.net/reviewfeedbackproxy/feedbackFile?apikey=QclhCTGbH2XgFiotkytQr33vKJdAOvQJ",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	if(data=="Success"){
        		$('#result').css('color','Green');
                $("#result").text(data);
        	}else{
        		$('#result').css('color','Red');
                $("#result").text(data);
        	}
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);
            $('#fileUploadForm')[0].reset();
        },
        error: function (e) {
        	 $('#result').css('color','Red');
            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);
            $('#fileUploadForm')[0].reset();
        }
    });
}
    function fire_ajax_feedback_submit() {
        if($('#feedback').val()==""){
        	$('#feedbackLoadResult').css('color','Red');
        	$("#feedbackLoadResult").text("Feedback is Empty!");
        	return;
        }
        $("#btnFeedbackSubmit").prop("disabled", true);
        $.ajax({
            type: "POST",
            // url: "/broker/storage/uploadFile/feedback",
            url : "jdjoydeepde-eval-test.apigee.net/reviewfeedbackproxy/feedback?apikey=QclhCTGbH2XgFiotkytQr33vKJdAOvQJ",
            data:$('form[name=feedbackUploadForm]').serialize(), 
            timeout: 600000,
            success: function (data) {
            	if(data=="Success"){
            		$('#feedbackLoadResult').css('color','Green');
                    $("#feedbackLoadResult").text(data);
                    console.log("SUCCESS : ", data);
            	}else{
            		$('#feedbackLoadResult').css('color','Red');
                    $("#feedbackLoadResult").text(data);
                    console.log("SUCCESS : ", data);
            	}
                $("#btnFeedbackSubmit").prop("disabled", false);
                $('#feedbackUploadForm')[0].reset();
            },
            error: function (e) {
            	 $('#feedbackLoadResult').css('color','Red');
                $("#feedbackLoadResult").text(e.responseText);
                console.log("ERROR : ", e);
                $("#btnFeedbackSubmit").prop("disabled", false);
                $('#feedbackUploadForm')[0].reset();
            }
        });
}
    
    
    function generateFeedbackBySystem(url,resultId){
           $.ajax({
               type: "GET",
               url: url,
               success: function (data) {
            	   if(data=="Success"){
            		   if(resultId=="positive"){
            			   $('#positivefeedbackLoadResult').css('color','Green');
                           $('#positivefeedbackLoadResult').text(data);
            		   }else if(resultId=="negative"){
            			   $('#negativefeedbackLoadResult').css('color','Green');
                           $('#negativefeedbackLoadResult').text(data);
            		   }else if(resultId=="neutral"){
            			   $('#neutralfeedbackLoadResult').css('color','Green');
                           $('#neutralfeedbackLoadResult').text(data);
            		   }
                       console.log("SUCCESS : ", data);
            	   }else{
            		   if(resultId=="positive"){
            			   $('#positivefeedbackLoadResult').css('color','Red');
                           $('#positivefeedbackLoadResult').text(data);
            		   }else if(resultId=="negative"){
            			   $('#negativefeedbackLoadResult').css('color','Red');
                           $('#negativefeedbackLoadResult').text(data);
            		   }else if(resultId=="neutral"){
            			   $('#neutralfeedbackLoadResult').css('color','Red');
                           $('#neutralfeedbackLoadResult').text(data);
            		   }
                       console.log("FAILURE : ", data);
            	   }
               },
               error: function (e) {
                   if(resultId=="positive"){
        			   $('#positivefeedbackLoadResult').css('color','Red');
                       $('#positivefeedbackLoadResult').text(e.responseText);
        		   }else if(resultId=="negative"){
        			   $('#negativefeedbackLoadResult').css('color','Red');
                       $('#negativefeedbackLoadResult').text(e.responseText);
        		   }else if(resultId=="neutral"){
        			   $('#neutralfeedbackLoadResult').css('color','Red');
                       $('#neutralfeedbackLoadResult').text(e.responseText);
        		   }
                   console.log("ERROR : ", e);
               }
           });
    }
    
    
    
    
    
    $("#syncTwitter").click(function (event) {
    	debugger;
        event.preventDefault();
        messageBox('Twitter Syncing will take some time, Stay tuned!', 'info', { okButtonName: 'Ok'}, function () {
        	
        });
        $.ajax({
            type: "GET",
            url: "/broker/storage/sync_twitter",
            success: function (data) {
         	   if(data=="Success"){
                    console.log("SUCCESS : ", data);
                    messageBox('Twitter Syncing completed!', 'info', { okButtonName: 'Close'}, function () {
                    	
                    });
         	   }else{
         		  messageBox(data, 'info', { okButtonName: 'Close'}, function () {
                    	
                  });
         	   }
            },
            error: function (e) {
                console.log("ERROR : ", e);
                messageBox('Failed to Sync Twitter!', 'info', { okButtonName: 'Close'}, function () {
                });
            }
        });
    });
    
    $("#syncEmail").click(function (event) {
        event.preventDefault();
        messageBox('Email Syncing will take some time, Stay tuned!', 'info', { okButtonName: 'Ok'}, function () {
        	
        });
        $.ajax({
            type: "GET",
            url: "/broker/storage/sync_email",
            success: function (data) {
         	   if(data=="Success"){
                    console.log("SUCCESS : ", data);
                    messageBox('Email Syncing completed!', 'info', { okButtonName: 'Close'}, function () {
                    	
                    });
         	   }else{
         		  messageBox(data, 'info', { okButtonName: 'Close'}, function () {
                  	
                  });
         	   }
            },
            error: function (e) {
                console.log("ERROR : ", e);
                messageBox('Failed to Sync Email!', 'info', { okButtonName: 'Close'}, function () {
                });
            }
        });
    });

