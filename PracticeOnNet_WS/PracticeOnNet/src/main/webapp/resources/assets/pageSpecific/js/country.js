/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {

	// [0] Load grid while opening the page.
	loadGrid("", "", "");

	// [1] Open an add modal.
	$(document).on("click",".addClass",function() {
		clearFormData();
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		removeReadOnlyProp();
		$('#saveFormButtonId').attr("disabled", false);
		$('#phoneCodeRowId').show();
		preparePage();
		$("#commonModalTitleId").html('Add a record');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id',
		'saveFormButtonId');
		$("#saveFormButtonId").html('Save');
		$('#modal-common').modal('toggle');
	});

	// [2] Save a record.
	$(document).on("click", "#saveFormButtonId", function(e) {
		cleanAllMsg();
		saveAndUpdateRecord();		
		refreshGridPage();// Refresh with recently added record
	});

	// [3] Open an edit modal.
	$(document).on("click",".eClass",function() {
		clearFormData();
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		removeReadOnlyProp();
		$('#updateFormButtonId').attr("disabled", false);
		preparePage();
		$('#phoneCodeRowId').show();
		getIdBasedRecord($(this).attr("href").split('=')[1]);

		$("#commonModalTitleId")
		.html('Update a record');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id',
		'updateFormButtonId');
		$("#updateFormButtonId").html('Update');
		$('#modal-common').modal('toggle');
	});

	// [4] Update a record.
	$(document).on("click", "#updateFormButtonId", function() {
		cleanAllMsg();
		saveAndUpdateRecord();		
		refreshGridPage();// Reload with recently edited record
	});

	// [5] Open a delete confirm modal.
	$(document).on("click",	".dClass",function() {
		clearFormData();
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		event.preventDefault(); // Will restrict the clicked url to be posted
		$('#deleteFormButtonId').attr("disabled", false);
		$('#recordIdForDelete').val($(this).attr("href").split('=')[1]);
		$('#modal-delete').modal('toggle');
	});

	// [6] Delete a record.
	$('#deleteFormButtonId').on('click', function(e) {
		deleteRecord($('#recordIdForDelete').val());
		$('#deleteFormButtonId').attr("disabled", true);
		refreshGridPage();
	});

	// [7] Open a view modal.
	$(document).on("click", ".vClass", function() {
		clearFormData();
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();

		applyReadOnlyProp();
		$('#phoneCodeRowId').show();
		getIdBasedRecord($(this).attr("href").split('=')[1]);
		$("#commonModalTitleId").html('View a record');
		$('#modal-common').modal('toggle');
	});

	// [8] Open a selected delete confirm modal.
	$("#deleteSelected").click(function() {
		clearFormData();
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		if (GetSelectedRowID() == "") {
			$("#deleteSelFormButtonId").attr("disabled", true);
			$('deleteSelFormButtonId').prop('disabled', true);
			$('#showAllertMsg')
			.html("Please select at least one record.");
			$('#showSelectedRow').html("");
		} else {
			$('#deleteSelFormButtonId').attr("disabled", false);
			$('#showAllertMsg').html(
					"<label>Do you really want to delete these "
					+ GetSelectedRowID()[2]
					+ " records? This process cannot be undone.</label>");
			$('#showSelectedRow').html(GetSelectedRowID()[1]);
		}
		$('#deleteSelFormButtonId').attr("disabled", false);
		$('#modal-sDelete').modal('toggle');
	});

	// [9] Delete selected records.
	$('#deleteSelFormButtonId').on('click', function(e) {
		if (GetSelectedRowID() != "") {
			deleteSelectedRecord(GetSelectedRowID()[0]);
			$('#deleteSelFormButtonId').attr("disabled", true);
		}

		refreshGridPage();
	});

	// [10] Open a search modal.
	$(document).on("click",".searchClass",function() {
		clearFormData();
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		removeReadOnlyProp();
		preparePage();

		$("#searchMsgId").html(
		'<span style="color:green"><h4>Search with any combination.</h4><\span>');
		$("#searchMsgId").show();
		$("#commonModalTitleId").html('Search records');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id', 'searchFormButtonId');
		$("#searchFormButtonId").html('Search');
		$('#modal-common').modal('toggle');
	});

	// [11] Search record
	$(document).on("click", "#searchFormButtonId", function(e) {
		var name = $('#countryNameId').val();
		var sortname = $('#countrySortNameId').val();
		var phonecode = $('#countryPhoneCodeId').val();
		searchAndReloadGrid(name, sortname, phonecode);
	});

	// [12] New one.
	$("#reloadGrid").click(function() {
		reloadGrid();
	});
	
	// [12] New one.
	$("#refreshGrid").click(function() {
		refreshGridPage();
	});
	
	

});// End of document ready


//Reload Grid.
function reloadGrid() {
	//event.preventDefault();
	clearCheckBoxSelection();

	//var table = $('#countryId').DataTable();

	// table.clear().draw();
	// table.ajax.reload();//Loading existing opened page only
	// table.ajax.url( "paginated?name="+""+"&sortname="+""+"&phonecode="+""+"").load();	
	// Reset column order    
	// table.colReorder.reset();

	// Redraw table (and reset main search filter and column specific filter if any)	
	// t.search( '' ).columns().search( '' ).draw();//Will work without global search4

	window.location.replace("/admin/pvt/masters/country/");


}
function refreshGridPage() {
	//event.preventDefault();
	clearCheckBoxSelection();	
	t.draw();//Loading existing opened page only
	//t.ajax.url( "paginated?name="+""+"&sortname="+""+"&phonecode="+""+"").load();
	// t.search( '' ).columns().search( '' ).draw();//Will work without global search4
	// Example call to load a new file
	//  t.fnReloadAjax( 'media/examples_support/json_source2.txt' );

	// Example call to reload from original file
	//t.fnReloadAjax();
}


function searchAndReloadGrid(name, sortname, phonecode) {
	clearCheckBoxSelection();
	var table = $('#countryId').DataTable();
	table.ajax.url(
			"paginated?name=" + name + "&sortname=" + sortname + "&phonecode="
			+ phonecode + "").load();
	$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
}

//Get Selected Row IDs
function GetSelectedRowID() {
	var table = $('#countryId').DataTable();
	var checkedRowIdAndName = [];
	var checkedRowIds = [];
	var checkedRowNames = [];
	var recordCount = 0;
	$(".chkIndvRow").each(function() {
		if ($(this).is(':checked')) {
			// $(this).val() will return row index
			var selectedRows = table.rows($(this).val()).data();
			checkedRowNames.push(selectedRows[0].name + "</br>");
			checkedRowIds.push(selectedRows[0].id);
			recordCount++;
		}
	});
	// Here You will get all selected persons ID
	// alert("Selected Rows:- " + checkedRowIds);
	if (recordCount > 0) {
		checkedRowIdAndName.push(checkedRowIds);
		checkedRowIdAndName.push(checkedRowNames);
		checkedRowIdAndName.push(recordCount);
	}
	return checkedRowIdAndName;
}

//## Code for loading grid ##
var t;
function loadGrid(name, sortname, phonecode) {
	
	t = $('#countryId').DataTable(
			{

				"retrieve" : true,// used for refreshing
				"bAutoWidth" : true,
				//"scrollY" : '110vh',
				//"scrollCollapse" : true,
				"lengthMenu" : [ 5, 10, 15, 20 ],
				"processing" : true,
				"serverSide" : true,
				"ordering" : true,
				"searching" : true,
				"aaSorting" : [ [ 3, "asc" ], [ 4, "asc" ], [ 5, "asc" ] ],
				"ajax" : {
					"url" : "paginated?name=" + name + "&sortname=" + sortname
					+ "&phonecode=" + phonecode + "",

					"type" : "POST",
				},

				"columns" : [
					{
						"searchable" : false,
						"orderable" : false,
						"targets" : 0,
						"render" : function(data, type, full, meta) {
							return meta.row + 1;// Will send row index
						}
					},
					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, full, meta) {
							return '<input class="chkIndvRow" value='
							+ meta.row + ' type="checkbox" >';// Will
							// index
						}
					},

					{
						"data" : "id",
						"name" : "ID",
						"title" : "ID",
						"searchable" : false,
						"bVisible" : false, // used for hiding a column
					},
					{
						"data" : "name",
						"name" : "name",
						"title" : "name"
					},
					{
						"data" : "sortname",
						"name" : "sortname",
						"title" : "sortname"
					},
					{
						"searchable" : false,
						// "orderable" : false,
						"data" : "phonecode",
						"name" : "phonecode",
						"title" : "phonecode"
					},

					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, row) {
							return '<a class="vClass" href=?record_id='
							+ row.id + '>'
							+ '<i class="fa fa-eye"></i>' + '</a>';
						}
					},
					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, row) {
							return '<a class="eClass" href=?record_id='
							+ row.id + '>'
							+ '<i  class="fa fa-edit"></i>'
							+ '</a>';
						}
					},
					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, row) {
							return '<a class="dClass" href=?record_id='
							+ row.id + '>'
							+ '<i class="fa fa-trash-o"></i>'
							+ '</a>';
						}
					} ]
			});

	
	

}// End of loading grid

//###########################################################//
//########## End of grid and CRUD related code #############//
//#########################################################//


//#######################################################################//
//########## Start: Methods for supporting above operations ############//
//#####################################################################//

var baseUrl = '/admin/pvt/masters/country';

function clearFormData() {
	// alert("Clearing form data");
	$("#addEditFormId").trigger("reset");
	$('#countryNameId').val("");
	$('#countrySortNameId').val("");
	$('#countryPhoneCodeId').val("");
}
//This will be called for cleaning the error message already shown on the page.
function cleanAllMsg() {
	$("#successMsgId").html("");
	$('#deleteSuccessMsgId').html("");
	$('#deleteSelectedSuccessMsgId').html("");


	$('#countryNameId_err').html("");
	$('#countrySortNameId_err').html("");
	$('#countryPhoneCodeId_err').html("");


}

function hideAllRequired() {
	$('#phoneCodeRowId').hide();
	$("#searchMsgId").hide();
	$('.commonButtonActionClass').hide();
}

function cleanAllHiddenInput() {
	$('#countryRecordId').val("");
	$('#recordIdForDelete').val("");
}

//Apply read only on all the fields of the form so that thevalue of the field
//can not be changed.
function applyReadOnlyProp() {
	$('#countryNameId').prop("readonly", true);
	$('#countrySortNameId').prop("readonly", true);
	$('#countryPhoneCodeId').prop("readonly", true);
}

//Remove read only from all the fields of the form so that they could be
//changed.
function removeReadOnlyProp() {
	$('#countryNameId').prop("readonly", false);
	$('#countrySortNameId').prop("readonly", false);
	$('#countryPhoneCodeId').prop("readonly", false);
}

//If the form requires anything pre-loaded. it can be done here.
function preparePage() {

}

//This will be called with an ajax response object and it will be used for
//loading the page with the response.
function loadRecordInForm(response) {
	$('#countryRecordId').val(response.id);
	$('#countryNameId').val(response.name);
	$('#countrySortNameId').val(response.sortname);
	$('#countryPhoneCodeId').val(response.phonecode);

}

//Fetch a e=record based on id
function getIdBasedRecord(id) {
	/* stop form from submitting normally */
	event.preventDefault();
	clearFormData();
	method = 'GET';
	url = baseUrl + "/getRecord" + "?id=" + id;
	// alert("posting url = "+url);
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming");
			$('#successMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){				
				loadRecordInForm(response.formObject);
			}else{
				showBusinessEerror(response.fieldErrMsgMap);
			}
			// Now load the page with response
			if (response.status == "ErrorFree")
				loadRecord(response);
			else
				showBusinessEerror(response);
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});
	
}// End of getIdBasedRecord

function showBusinessEerror(fieldErrMsgMap) {
	$('#countryNameId_err').html(fieldErrMsgMap.name);
	$('#countrySortNameId_err').html(fieldErrMsgMap.sortname);
	$('#countryPhoneCodeId_err').html(fieldErrMsgMap.phonecode);
}

function saveAndUpdateRecord() { 
	/* stop form from submitting normally */
	event.preventDefault();
	/* get the form data of the from */
	// var formSerializeData = $('#commonFormId').serialize();

	var id = $('#countryRecordId').val();
	var name = $('#countryNameId').val();
	var sortname = $('#countrySortNameId').val();
	var phonecode = $('#countryPhoneCodeId').val();
	var json = {
			"id" : id,
			"name" : name,
			"sortname" : sortname,
			"phonecode" : phonecode
	};

	$
	.ajax({
		type : 'POST',
		url : baseUrl + "/saveAndUpdateRecord",
		data : JSON.stringify(json),

		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming");
			$('#successMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
				//Do something if required	
				$('#saveFormButtonId').attr("disabled", true);
			}else{
				//alert("Form has an error");
				showBusinessEerror(response.fieldErrMsgMap);
			}

		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});
	
}

function deleteRecord(id) {	
	/* stop form from submitting normally */
	event.preventDefault();
	clearFormData();
	method = 'GET';
	url = baseUrl + "/deleteRecord" + "?id=" + id;
	// alert("posting url = "+url);
	$
	.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			$('#deleteSuccessMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
				//Do something if required			
			}else{
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});
	
}

function deleteSelectedRecord(recordIdArray) {

	//alert("Going to delete a record "+recordIdArray);


	//alert("recordIdArray[0] = "+GetSelectedRowID()[0][0]);
	//alert("recordIdArray[1] = "+GetSelectedRowID()[0][1]);
	/* stop form from submitting normally */
	event.preventDefault();
	clearFormData();
	method = 'POST';
	url = baseUrl + "/deleteSelected";
	// alert("posting url = "+url);
	var idsArray=[];
	for(i=0;i<recordIdArray.length;i++)
		idsArray[i]=recordIdArray[i];	


	$.ajax({
		type : method,
		url : url,
		data : JSON.stringify(idsArray),//JSON.stringify(sendRecordIdArray),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			$('#deleteSelectedSuccessMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
				//Do something if required
			}else{				
				showBusinessEerror(response.fieldErrMsgMap);
			}

		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});
	
}

//#######################################################################//
//########## End: Methods for supporting above operations ############//
//#####################################################################//


