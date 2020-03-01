/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {

	// [0] Load grid while opening the page.
	loadGrid("", "","");
	preparePage();

	//Select the clicked row index
	var rowIndex;
	$('body').on('click', 'a.eClass', function(event) {
		rowIndex = $(this).closest('tr').index();	     
	    //console.debug('rowIndex', rowIndex);
	});
	$('body').on('click', 'a.dClass', function(event) {
		rowIndex = $(this).closest('tr').index();	   
	    //console.debug('rowIndex', rowIndex);
	});
	
	
	// [1] Open an add modal.
	$(document).on("click",".addClass",function() {				
		prepareFieldViewForAddMode();		
		$('#saveFormButtonId').attr("disabled", false);			
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
		var actionNameUniqueness="Yes";
		saveAndUpdateRecord(actionNameUniqueness);		
		refreshGridPage();// Refresh with recently added record
	});
	
	// [3] Open an edit modal.
	$(document).on("click",".eClass",function() {		
		prepareFieldViewForEditMode();		
		$('#updateFormButtonId').attr("disabled", false);		
		getIdBasedRecord($(this).attr("href").split('=')[1]); 
		var isRestricted=putRestrictionOnUpdateForPredefinedActions(t.rows($(this).val()).data(),rowIndex);
		
		if(isRestricted){
			$('#modal-restricted').modal('toggle');
			return true; 
		}
		$("#commonModalTitleId").html('Update a record');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id','updateFormButtonId');
		$("#updateFormButtonId").html('Update');
		$('#modal-common').modal('toggle');
	});

	// [4] Update a record.
	$(document).on("click", "#updateFormButtonId", function() {
		cleanAllMsg();
		var actionNameUniqueness="No";
		saveAndUpdateRecord(actionNameUniqueness);			
		refreshGridPage();// Reload with recently edited record
	});

	// [5] Open a delete confirm modal.
	$(document).on("click",	".dClass",function() {		
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		event.preventDefault(); // Will restrict the clicked url to be posted
		var isRestricted=putRestrictionOnUpdateForPredefinedActions(t.rows($(this).val()).data(),rowIndex);
		if(isRestricted){
			$('#modal-restricted').modal('toggle');
			return true; 
		}
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
		prepareFieldViewForViewMode();		
		getIdBasedRecord($(this).attr("href").split('=')[1]);
		$("#commonModalTitleId").html('View a record');
		$('#modal-common').modal('toggle');
	});

	// [8] Open a selected delete confirm modal.
	$("#deleteSelected").click(function() {			
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		$('#deleteSelFormButtonId').attr("disabled", true);	
		//alert("F = "+GetSelectedRowID()[0] == "");
		//alert("S = "+GetSelectedRowID()[1] == "");
		if (GetSelectedRowID()[0] == "" && GetSelectedRowID()[1] == "") {			
			$('#showAllertMsgR').html("Please select at least one record.");
			$('#showSelectedRowR').html("");
		} else {
			//Unrestricted record for delete			
			if(!GetSelectedRowID()[0][1] ==""){
				$('#deleteSelFormButtonId').attr("disabled", false);
			    $('#showAllertMsgU').html("<label>Do you really want to delete "	+ GetSelectedRowID()[0][2]
			                                + " records? This process cannot be undone.<br></label>");	
			$('#showSelectedRowU').html(GetSelectedRowID()[0][1]);
			}			
			//Restricted record for delete			
			if(!GetSelectedRowID()[1][1] ==""){				
			    $('#showAllertMsgR').html("<label><span style='color:red'>Following [ "+GetSelectedRowID()[1][2]+" ] records can't be deleted. They are pre-defined actions on each domain class.</span><br></label>");	
			$('#showSelectedRowR').html(GetSelectedRowID()[1][1]);
			}				
		}		
		$('#modal-sDelete').modal('toggle');
	});

	// [9] Delete selected records.
	$('#deleteSelFormButtonId').on('click', function(e) {
		if (GetSelectedRowID() != "") {
			deleteSelectedRecord(GetSelectedRowID()[0][0]);
			$('#deleteSelFormButtonId').attr("disabled", true);
		}
		refreshGridPage();
	});

	// [10] Open a search modal.
	$(document).on("click",".searchClass",function() {		
		prepareFieldSearchForSearchMode();
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
		var departmentId = $('#departmentNameId').val();
		var moduleNameId = $('#moduleNameId').val();		
		var domainNameId = $('#domainNameId').val();		
		searchAndReloadGrid(departmentId,moduleNameId,domainNameId);
	});

	// [12] New one.
	$("#reloadGrid").click(function() {
		reloadGrid();
	});
	
	// [13] New one.
	$("#refreshGrid").click(function() {
		refreshGridPage();
	});
	
	

});// End of document ready


$('#departmentNameId').on('change', function(e) {
	cleanAllMsg();
	//alert("Going to load State combo");
	//This will clean the state combo of state and show only select in the combo box
	$('#moduleNameId').empty().append('<option  value="">-Select-</option>');	
	$('#domainNameId').empty().append('<option  value="">-Select-</option>');	
	loadModuleCombo($('#departmentNameId').val());
	
	
});
$('#moduleNameId').on('change', function(e) {
	cleanAllMsg();	
	$('#domainNameId').empty().append('<option  value="">-Select-</option>');	
	loadDomainCombo($('#moduleNameId').val());
});
$('#domainNameId').on('change', function(e) {
	cleanAllMsg();	
	var domainNameId=$('#domainNameId').val();	
	loadDomainBasePackage(domainNameId);	
});

function putRestrictionOnUpdateForPredefinedActions(selectedRows,rowIndex){	
	var isRestricted=false;	
	if(selectedRows[rowIndex].actionNumber<6){		
		isRestricted=true;
	}
	return isRestricted;
}

//Reload Grid.
function reloadGrid() {
	event.preventDefault();
	clearCheckBoxSelection();
	window.location.replace("/admin/pvt/sec/acl/dmnActionMaster/");
	}

function refreshGridPage() {
	event.preventDefault();
	clearCheckBoxSelection();	
	t.draw();
}

function searchAndReloadGrid(departmentNameId,moduleNameId,domainNameId) {
	clearCheckBoxSelection();
	var table = $('#domainActionId').DataTable();
	table.ajax.url(
			"paginated?departmentNameId=" + departmentNameId + "&moduleNameId=" +moduleNameId
			+ "&domainNameId=" +domainNameId).load();
	$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
}

//Get Selected Row IDs
function GetSelectedRowID() {
	var table = $('#domainActionId').DataTable();
	var checkedRowIdAndName = [];
	var checkedRowIdAndNameRestricted = [];
	var checkedRowIdAndNameUnRestricted = [];	
	var checkedRowIdsU = [];
	var checkedRowNamesU = [];
	var checkedRowIdsR = [];
	var checkedRowNamesR = [];
	var recordCountR = 0;
	var recordCountU = 0;
	$(".chkIndvRow").each(function() {		
		if ($(this).is(':checked')) {
			// $(this).val() will return row index
			var selectedRows = table.rows($(this).val()).data();
			if(selectedRows[0].actionNumber < 6){
				checkedRowNamesR.push(selectedRows[0].departmentName +" => "
					             +selectedRows[0].moduleName +" => "
					             +selectedRows[0].domainName +" => "
					             +selectedRows[0].actionName +" => "
					             +selectedRows[0].actionNumber 
					             +"</br>");			
			checkedRowIdsR.push(selectedRows[0].id);
			recordCountR++;
			}else{
				checkedRowNamesU.push(selectedRows[0].departmentName +" => "
			             +selectedRows[0].moduleName +" => "
			             +selectedRows[0].domainName +" => "
			             +selectedRows[0].actionName +" => "
			             +selectedRows[0].actionNumber 
			             +"</br>");			
				checkedRowIdsU.push(selectedRows[0].id);
				recordCountU++;					
			}		 
		}
	});
	// Here You will get all selected persons ID	
	if (recordCountU > 0) {		
		checkedRowIdAndNameUnRestricted.push(checkedRowIdsU);
		checkedRowIdAndNameUnRestricted.push(checkedRowNamesU);
		checkedRowIdAndNameUnRestricted.push(recordCountU);	
	}
	if (recordCountR > 0) {		
		checkedRowIdAndNameRestricted.push(checkedRowIdsR);
		checkedRowIdAndNameRestricted.push(checkedRowNamesR);
		checkedRowIdAndNameRestricted.push(recordCountR);		
	}
	//Must be placed outside of the above if statement otherwise condition check where it will be called will not work.
	checkedRowIdAndName.push(checkedRowIdAndNameUnRestricted);
	checkedRowIdAndName.push(checkedRowIdAndNameRestricted);
	return checkedRowIdAndName;
}

//## Code for loading grid ##
var t;
function loadGrid(departmentNameId,moduleNameId,domainNameId) {
	t = $('#domainActionId').DataTable(
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
				"aaSorting" : [ [ 3, "asc" ], [ 4, "asc" ], [ 4, "asc" ] ],
				"ajax" : {
					async:false,
					"url" : "paginated?departmentNameId=" + departmentNameId + "&moduleNameId=" + moduleNameId 
					+ "&domainNameId=" + domainNameId +"",

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
						"data" : "departmentName",
						"name" : "departmentName",
						"title" : "App. Context"
					},
					{
						"data" : "moduleName",
						"name" : "moduleName",
						"title" : "Module"
					},
					{
						"data" : "domainName",
						"name" : "domainName",
						"title" : "Domain"
					},
					{
						"searchable" : false,
						"bVisible" : false, // used for hiding a column
						"data" : "packageName",
						"name" : "packageName",
						"title" : "Package"
					},
					
					{
						"data" : "actionName",
						"name" : "actionName",
						"title" : "Action"
					},
					
					{
						"data" : "sortName",
						"name" : "sortName",
						"title" : "Sort"
					},
					{
						"data" : "actionNumber",
						"name" : "actionNumber",
						"title" : "Number",
						"searchable" : false
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

var baseUrl = '/admin/pvt/sec/acl/dmnActionMaster';

function clearFormData() {
	//alert("Clearing form data");
	$("#addEditFormId").trigger("reset");
	$('#moduleNameId').val("");
	$('#departmentNameId').val("");
	$('#domainNameId').val("");
	$('#packageNameId').val("");
	
	$('#actionNameId').val("");
	$('#sortNameId').val("");
	$('#actionNumberId').val("");
	//Will make module and page combo clean
	$('#moduleNameId').empty()
    .append('<option selected="selected" value="">-Select-</option>');
	$('#domainNameId').empty()
    .append('<option selected="selected" value="">-Select-</option>');
}
//This will be called for cleaning the error message already shown on the page.
function cleanAllMsg() {
	$("#showSelectedRowR").html("");
	$("#showSelectedRowU").html("");
	$("#showAllertMsgR").html("");
	$("#showAllertMsgU").html("");
	
	
	$("#successMsgId").html("");
	
	$('#deleteSuccessMsgId').html("");
	
	$('#deleteSelectedSuccessMsgId').html("");
	
	$('#moduleNameId_err').html("");
	$('#departmentNameId_err').html("");
	$('#domainNameId_err').html("");
	$('#packageName_err').html("");
	$('#actionName_err').html("");
	$('#sortName_err').html("");
	$('#actionNumber_err').html("");
	
}

function hideAllRequired() {	
	$("#searchMsgId").hide();
	$('.commonButtonActionClass').hide();
	$('#packageNameLabledId').hide();
	$('#actionNameLabledId').hide();
	$('#sortNameLabledId').hide();
	$('#actionNumberLabledId').hide();
}

function cleanAllHiddenInput() {
	$('#actionRecordId').val("");
	$('#recordIdForDelete').val("");
}

function prepareFieldSearchForSearchMode(){
	clearFormData();
	cleanAllMsg();
	hideAllRequired();	
	cleanAllHiddenInput();
	$('#buttonLabledId').show();
	
	//Hide following fields for search
	$('#packageNameLabledId').hide();		
	$('#actionNameLabledId').hide();
	$('#sortNameLabledId').hide();		
	$('#actionNumberLabledId').hide();
	//Remove read only	
	$('#departmentNameId').attr('disabled', false);
	$('#moduleNameId').attr('disabled', false);
	$('#domainNameId').attr("disabled", false);
	
}
function prepareFieldViewForAddMode(){
	clearFormData();
	cleanAllMsg();		
	cleanAllHiddenInput();
	$('#buttonLabledId').show();
	$('#packageNameLabledId').show();		
	$('#actionNameLabledId').show();
	$('#sortNameLabledId').show();		
	$('#actionNumberLabledId').show();
	//Remove read only	
	$('#departmentNameId').attr('disabled', false);
	$('#moduleNameId').attr('disabled', false);
	$('#domainNameId').attr("disabled", false);
	$('#packageNameId').prop("readonly", true);//While adding and action on a domain, only this field should be in read only mode so that it can't be changed.
	$('#actionNameId').prop("readonly", false);
	$('#sortNameId').prop("readonly", false);
	$('#actionNumberId').prop("readonly", false);
}
function prepareFieldViewForEditMode(){

	cleanAllMsg();
	hideAllRequired();
	cleanAllHiddenInput();
	$('#buttonLabledId').show();
	$('#packageNameLabledId').show();		
	$('#actionNameLabledId').show();
	$('#sortNameLabledId').show();		
	$('#actionNumberLabledId').show();
	//Remove read only	
	$('#departmentNameId').attr('disabled', false);
	$('#moduleNameId').attr('disabled', false);
	$('#domainNameId').attr("disabled", false);
	$('#packageNameId').prop("readonly", true);//While adding and action on a domain, only this field should be in read only mode so that it can't be changed.
	$('#actionNameId').prop("readonly", false);
	$('#sortNameId').prop("readonly", false);
	$('#actionNumberId').prop("readonly", false);
}
function prepareFieldViewForViewMode(){
	cleanAllMsg();
	hideAllRequired();
	cleanAllHiddenInput();
	$('#buttonLabledId').show();
	$('#packageNameLabledId').show();		
	$('#actionNameLabledId').show();
	$('#sortNameLabledId').show();		
	$('#actionNumberLabledId').show();
	//Apply read only	
	$('#departmentNameId').attr('disabled', true);
	$('#moduleNameId').attr('disabled', true);
	$('#domainNameId').attr("disabled", true);
	$('#packageNameId').prop("readonly", true);
	$('#actionNameId').prop("readonly", true);
	$('#sortNameId').prop("readonly", true);
	$('#actionNumberId').prop("readonly", true);
}
//If the form requires anything pre-loaded. it can be done here.
function preparePage() {
   loadDepartmentCombo();  
}

//This will be called with an ajax response object and it will be used for
//loading the page with the response.
function loadRecordInForm(response) {
	$('#actionRecordId').val(response.id);	
	$('#departmentNameId').val(response.departmentNameId);
	
	//This will clean the state combo of state and show without select in the combo box
	$('#moduleNameId').empty();
	loadModuleCombo(response.departmentNameId);
	$('#moduleNameId').val(response.moduleNameId);
	
	$('#domainNameId').empty();
	loadDomainCombo(response.moduleNameId);
	$('#domainNameId').val(response.domainName);	
	$('#packageNameId').val(response.packageName);
	
	$('#actionNameId').val(response.actionName);
	$('#sortNameId').val(response.sortName);
	$('#actionNumberId').val(response.actionNumber);
	
	
}

//Fetch a e=record based on id
function getIdBasedRecord(id) {
	/* stop form from submitting normally */
	event.preventDefault();	
	method = 'GET';
	url = baseUrl + "/getRecord" + "?id=" + id;
	// alert("posting url = "+url);
	$.ajax({
		async:false,
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
				//alert(response.formObject.countryNameId);
				
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
	$('#departmentNameId_err').html(fieldErrMsgMap.departmentNameId);	
	$('#moduleNameId_err').html(fieldErrMsgMap.moduleNameId);
	$('#domainNameId_err').html(fieldErrMsgMap.domainNameId);	
	$('#packageName_err').html(fieldErrMsgMap.packageName);
	
	$('#actionName_err').html(fieldErrMsgMap.actionName);
	$('#sortName_err').html(fieldErrMsgMap.sortName);
	$('#actionNumber_err').html(fieldErrMsgMap.actionNumber);
	
}

function saveAndUpdateRecord(actionNameUniqueness) { 	
	/* stop form from submitting normally */
	event.preventDefault();
	/* get the form data of the from */	
	var id = $('#actionRecordId').val();	
	var departmentNameId = $('#departmentNameId').val();	
	var moduleNameId = $('#moduleNameId').val();	
	var domainNameId = $('#domainNameId').val();	
	//alert("domainNameId = "+domainNameId);		
	var actionName = $('#actionNameId').val();	
	var sortName = $('#sortNameId').val();	
	var actionNumber = $('#actionNumberId').val();	
	var json = {
			"id" : id,
			"departmentNameId" : departmentNameId,
			"moduleNameId" : moduleNameId,
			"domainNameId" : domainNameId,			
			"actionName":actionName,
			"sortName":sortName,
			"actionNumber":actionNumber,
			"actionNameUniqueness":actionNameUniqueness
	};
	
	$.ajax({
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
				//$('#saveFormButtonId').attr("disabled", true);
			}else{
				//alert("Form has a validation error");
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
	/* stop form from submitting normally */
	event.preventDefault();
	
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

function loadDepartmentCombo(){	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/admin/pvt/sec/env/department" + "/list";	
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
			if(response.status){
				var ele = document.getElementById('departmentNameId');	       
		        for (var i = 0; i < response.comboList.length; i++) {		        	
		            // POPULATE SELECT ELEMENT WITH JSON.
		            ele.innerHTML = ele.innerHTML +
		                '<option value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
		        }
				
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

function loadModuleCombo(id){	
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/admin/pvt/sec/env/module" + "/list?id=" + id;		
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
			if(response.status){				
						
				var ele = document.getElementById('moduleNameId');	       
		        for (var i = 0; i < response.comboList.length; i++) {		        	
		            // POPULATE SELECT ELEMENT WITH JSON.
		            ele.innerHTML = ele.innerHTML +
		                '<option value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
		        }
				
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

function loadDomainCombo(id){	
	
	/* stop form from submitting normally */
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/admin/pvt/sec/acl/dmnMaster" + "/list?id=" + id;	

	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {			
			if(response.status){				
						
				var ele = document.getElementById('domainNameId');	       
		        for (var i = 0; i < response.comboList.length; i++) {		        	
		            // POPULATE SELECT ELEMENT WITH JSON.
		            ele.innerHTML = ele.innerHTML +
		                '<option value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
		        }
				
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

function loadDomainBasePackage(domainNameId){
	
	/* stop form from submitting normally */
	event.preventDefault();	
	method = 'GET';
	
	url = "/admin/pvt/sec/acl/dmnMaster" + "/getRecord" + "?id=" + domainNameId;
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
			//$('#successMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
				//alert(response.formObject.countryNameId);				
				$("#packageNameId").val(response.formObject.packageName);
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
	
}

//#######################################################################//
//########## End: Methods for supporting above operations ############//
//#####################################################################//


