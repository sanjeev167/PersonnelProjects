/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/

// ###########################################################//
// ########## Start of grid and CRUD related code ###########//
// #########################################################//

$(document).ready(function() {
	 // Select and loop the container element of the elements you want to equalise
    $('.container3Div').each(function(){      
      // Cache the highest
      var highestBox = 0;      
      // Select and loop the elements you want to equalise
      $('.item', this).each(function(){        
        // If this box is higher than the cached highest then store it
        if($(this).height() > highestBox) {
          highestBox = $(this).height(); 
        }      
      });              
      // Set the height of all those children to whichever was highest 
      $('.item',this).height(highestBox);                 
    }); 
	
	
	 
	$('#hideUpdateButtonId').hide();
	//$('#selectRolePageId').hide();
	//$('#selectOpPageId').hide();
	//$('#NoOpDefinedOnPageId').hide();
	//$('#NoRoleDefinedEithinDepartmentId').hide();

// [0] Load grid while opening the page.
       loadGrid("", "","","","B");
	   preparePage();
	   
	   
	 //This will set the width of the datatable search box. It must be place after grid loading
	 	   $('.dataTables_filter input[type="search"]').
	 	   attr('placeholder','Search text').
	 	   css({'width':'100px','display':'inline-block'});	
       
// [1] Load role and operation based on selected pageId.
	  $(document).on("click", ".rbacRightsClass", function(e) {
		 event.preventDefault();
		 var pageId = $(this).attr("href").split('=')[1];
		 $('#pageRecordIdForRole').val(pageId);//Preserved it for future usage
		 getIdBasedRecord(pageId);// This will load both role and operation page						
		$('#hideUpdateButtonId').hide();
		$('#selectRolePageId').show();
		$('#selectOpPageId').show();		
		$('#roleSelectedId').html("");
		$('#updateSuccessMsgId').html("");
		});
// [2] Load selected role and its pre-assigned rights on the
// selected page for rights update.
	 $(document).on("click",".roleSelClass",	function(e) {
		event.preventDefault();
		$('#hideUpdateButtonId').show();
		$('#updateSuccessMsgId').html("");		
		var roleId = $(this).attr("href").split('=')[1];		
		var pageIdSelectedForRights = $('#pageRecordIdForRole').val();//Was preserved while loading role and operation on page
		loadOperationOfSelectedPage(roleId,pageIdSelectedForRights);
		enableOPerationDisabledCheckBox();
	});

// [3] Save a record.
	$(document).on("click", "#saveFormButtonId", function() {	
	  saveAndUpdateRecord();
	  var pageId = $('#pageRecordIdForRole').val();//Was preserved while loading role and operation on page	
	  getIdBasedRecord(pageId);// This will load both role and operation page
	  $('#hideUpdateButtonId').hide();
	  $('#roleSelectedId').html("");
	  
	 });

// [4] Open a search modal.
	$(document).on("click",	".searchClass",	function() {
	 
	  $("#searchMsgId").html('<span style="color:green"><h4>Search with any combination.</h4><\span>');
	  $("#searchMsgId").show();
	  $("#commonModalTitleId").html('Search records');
	  $('.commonButtonActionClass').show();
	  $('.commonButtonActionClass').attr('id', 'searchFormButtonId');
	  $("#searchFormButtonId").html('Search');
	  $('#modal-common').modal('toggle');
	});

// [5] Search record
	$(document).on("click", "#searchFormButtonId", function(e) {
		var departmentId = $('#departmentNameId').val();
		var moduleNameId = $('#moduleNameId').val();		
		var pageNameId = $('#pageNameId').val();		
		var roleNameId = $('#roleNameId').val();		
		var pageViewCondition = $("input[name='pageViewCondition']:checked").val();		
		searchAndReloadGrid(departmentId,moduleNameId,pageNameId,roleNameId,pageViewCondition);
	});

// [6] New one.
	$("#reloadGrid").click(function() {
		reloadGrid();
	});

// [7] Refresh grid page
	$("#refreshGridPage").click(function() {
	 refreshGridPage();
	});
	
// [8] When department combo change, load module combo
	$('#departmentNameId').on('change', function(e) {		
		$('#moduleNameId').empty().append('<option  value="">-Select-</option>');
		loadModuleCombo($('#departmentNameId').val());
		loadRoleCombo($('#departmentNameId').val());
	});
// [9] When module combo change, load page combo		
	$('#moduleNameId').on('change', function(e) {
		$('#pageNameId').empty().append('<option  value="">-Select-</option>');
		loadPageCombo($('#moduleNameId').val());
	});


});// End of document ready

$('#assignedPageViewId').on('click',function(e){
	//Set initial value	
	$("#roleNameId").val('');
	$("#moduleNameId").val('');
	$("#pageNameId").val('');
	
	
	$("#roleSelectionId").show();
	$("#moduleSelectionId").show();
	$("#pageSelectionId").hide();
	$("#roleLabelId").html("Assigned Role");	
});


$('#bothPageViewId').on('click',function(e){
	//Set initial value	
	$("#roleNameId").val('');
	$("#moduleNameId").val('');
	$("#pageNameId").val('');
	
	
	$("#roleSelectionId").hide();
	$("#moduleSelectionId").show();
	$("#pageSelectionId").show();	
});

$('#unassignedPageViewId').on('click',function(e){	
	//Set initial value	
	$("#roleNameId").val('');
	$("#moduleNameId").val('');
	$("#pageNameId").val('');
	
	$("#roleSelectionId").show();
	$("#moduleSelectionId").show();
	$("#pageSelectionId").hide();
	$("#roleLabelId").html("Unassigned Role");	
});

function enableOPerationDisabledCheckBox(){	
	$('.opSelClass').each(function() {
	    if($(this).prop('checked')||!$(this).prop('checked')) {
	        $(this).prop('disabled', false);
	    }
	});		
}

// Reload Grid.
function reloadGrid() {
	event.preventDefault();
	clearCheckBoxSelection();
	window.location.replace("/admin/pvt/sec/rbac/monitor/");

}
function refreshGridPage() {
	event.preventDefault();
	clearCheckBoxSelection();
	t.draw();// Loading existing opened page only
	
}


function searchAndReloadGrid(departmentNameId, moduleNameId,pageNameId,roleNameId,pageViewCondition) {		
	
	if((roleNameId==="") && ((pageViewCondition==="A") ||(pageViewCondition==="U"))){
		//alert("No role selection");
		if(pageViewCondition==="A")
		$("#roleNameId_err").html("Assigned to which Role ?");
		if(pageViewCondition==="U")
			$("#roleNameId_err").html("Unassigned to which Role ?");
		$("#roleResourceLabelId").hide();
		$("#roleAssUnassLabelId").hide();		
		return false;
	}
	var appContextName=($("#departmentNameId option:selected").text());
	var moduleName=($("#moduleNameId option:selected").text());
	var roleName=$("#roleNameId option:selected").text();
	if(departmentNameId==="") appContextName="All";
	if(moduleNameId==="") moduleName="All";
	if(roleNameId==="") roleName="All Roles";
	
	if(((pageViewCondition==="A") ||(pageViewCondition==="U"))){
		//alert("Role selected");
		//Clean error message		
		$("#roleNameId_err").html("");
		//Show role above grid for knowing which resources have been either assigned or unassigned
		if(pageViewCondition==="A")
			$("#roleAssUnassLabelId").html("<span style='color:red;'>Assigned Rrsources of</span>  <span style='color:blue;'>["
					+appContextName+"]<\span><span style='color:red;'> context </span>and <span style='color:blue;'>["
					+moduleName+"]<\span><span style='color:red;'> module</span> <br> <span style='color:red;'>for role : </span>");
			if(pageViewCondition==="U")
				$("#roleAssUnassLabelId").html("<span style='color:red;'>Unassigned Rrsources of</span>  <span style='color:blue;'>["
						+appContextName+"]<\span><span style='color:red;'> context </span>and <span style='color:blue;'>["
						+moduleName+"]<\span><span style='color:red;'> module</span> <br> <span style='color:red;'>for role : </span>");
			
			
		$("#roleLabelForGridId").html("[ "+roleName+" ]");
		$("#roleResourceLabelId").show();
		//Load grid
		var table = $('#accessRightsRbacId').DataTable();
		table.ajax.url(
				"paginated?departmentNameId=" + departmentNameId + "&moduleNameId="
						+ moduleNameId+"&pageNameId="+pageNameId+"&roleNameId="+roleNameId+"&pageViewCondition="+pageViewCondition).load();
		$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
		
		
	}
	if((pageViewCondition==="B")){		
		$("#roleAssUnassLabelId").html("");
		$("#roleAssUnassLabelId").html("<span style='color:red;'>Mixed Rrsources of</span>  <span style='color:blue;'>["
				+appContextName+"]<\span><span style='color:red;'> context </span>and <span style='color:blue;'>["
				+moduleName+"]<\span><span style='color:red;'> module</span> <br> <span style='color:red;'>for role : </span>");
	
		
		$("#roleLabelForGridId").html("[ "+roleName+" ]");
		$("#roleResourceLabelId").show();
		//Load grid
		var table = $('#accessRightsRbacId').DataTable();
		table.ajax.url(
				"paginated?departmentNameId=" + departmentNameId + "&moduleNameId="
						+ moduleNameId+"&pageNameId="+pageNameId+"&roleNameId="+roleNameId+"&pageViewCondition="+pageViewCondition).load();
		$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
		
	   }
	}

// Get Selected Row IDs
function GetSelectedRowID() {
	var table = $('#operationTableId');
	var checkedRowIds = [];
	$(".chkIndvRow").each(function() {
		if ($(this).is(':checked')) {
			// $(this).val() will return row index
			var roleId = ($(this).attr("name"));
			checkedRowIds.push(roleId);
		}
	});
	return checkedRowIds;
}

// ## Code for loading grid ##
var t;
function loadGrid(departmentNameId, moduleNameId,pageNameId,roleNameId,pageViewCondition) {
	t = $('#accessRightsRbacId')
			.DataTable(
					{
						"retrieve" : true,// used for refreshing
						"bAutoWidth" : true,
						// "scrollY" : '110vh',
						// "scrollCollapse" : true,
						"lengthMenu" : [ 5, 10, 15, 20 ],
						"processing" : true,
						"serverSide" : true,
						"ordering" : true,
						"searching" : true,
						"aaSorting" : [ [ 2, "asc" ], [ 3, "asc" ],
								[ 4, "asc" ] ],
						"ajax" : {

							"url" : "paginated?departmentNameId="
									+ departmentNameId + "&moduleNameId="
									+ moduleNameId + "&pageNameId="+pageNameId
									+ "&roleNameId="+roleNameId
									+ "&pageViewCondition="+pageViewCondition
									+"",
							"type" : "POST",							
						},

						"columns" : [
								{
									"searchable" : false,
									"orderable" : false,
									"targets" : 0,
									"render" : function(data, type, full, meta) {
										return meta.row + 1;// Will send row
										// index
									}
								},

								{
									"data" : "id",
									"name" : "ID",
									"title" : "ID",
									"searchable" : false,
									"bVisible" : false, // used for hiding a
								// column
								},
								{
									"data" : "departmentName",
									"name" : "departmentName",
									"title" : "App. Context",
									"bVisible" : false,
								},
								{
									"data" : "moduleName",
									"name" : "moduleName",
									"title" : "Module"
								},
								{
									"data" : "pageName",
									"name" : "pageName",
									"title" : "Page"
								},
								{
									"data" : null,
									"sortable" : false,
									"render" : function(data, type, row) {
										return '<a class="rbacRightsClass" href=?record_id='
										+ row.id + '>'
										+ '<i  class="fa fa-edit"></i>'
										+ '</a>';
									}
								}
							 ]
					});

}// End of loading grid

// ###########################################################//
// ########## End of grid and CRUD related code #############//
// #########################################################//








// #######################################################################//
// ########## Start: Methods for supporting above operations ############//
// #####################################################################//

var baseUrl = '/admin/pvt/sec/rbac/monitor';

// If the form requires anything pre-loaded. it can be done here.
function preparePage() {
	loadDepartmentCombo();
}
function loadRecordInRoleSelectionForm(response) {
	$('#departmentNameIdForRole').html(response.departmentName);
	var roleListInDepartment = response.roleListInDepartment;	
	var ctrl = "";
	$("#roleHolder").html(ctrl);
	for (i = 0; i < roleListInDepartment.length; i++) {
		var index = i + 1;
		if (roleListInDepartment[i]['accessCount'] != 0)
			ctrl = "<tr><td>[" + index + "]&nbsp;<label style='color: blue;'>"
					+ roleListInDepartment[i]['name'] + "  <span style='color: green;'>[P:-"
					+ roleListInDepartment[i]['accessCount']
					+ "<span>]</label></td><td><a class='roleSelClass' href='?id="
					+ roleListInDepartment[i]['id']
					+ "'><i class='fa fa-edit'></a></td></tr>";
		else
			ctrl = "<tr><td>[" + index + "]&nbsp;<label style='color: blue;'>"
					+ roleListInDepartment[i]['name'] + "  <span style='color: red;'>[P:-"
					+ roleListInDepartment[i]['accessCount']
					+ "<span>]</label></td><td><a class='roleSelClass' href='?id="
					+ roleListInDepartment[i]['id']
					+ "'><i class='fa fa-edit'></a></td></tr>";
		$("#roleHolder").append(ctrl);
	}
	
	if(roleListInDepartment.length<1){
		$('#NoRoleDefinedEithinDepartmentId').show();
		$('#roleDefinedEithinDepartmentId').hide();
	}else{$('#roleDefinedEithinDepartmentId').show();$('#NoRoleDefinedEithinDepartmentId').hide();}
	
	
}

function loadRecordInOperationSelectionForm(response) {
	
	clearCheckBoxSelection();
	$('#departmentNameIdOnOpSel').html(response.departmentName);
	$('#moduleNameIdOnOpSel').html(response.moduleName);
	$('#pageNameIdOnOpSel').html(response.pageName);
	var opListOnThisPage = response.allOperationsDefinedOnThisPage;	
	var ctrl = "";
	var isChecked = "";
	$("#opHolder").html(ctrl);
	for (i = 0; i < opListOnThisPage.length; i++) {		
		ctrl = "<tr><td><input class='opSelClass chkIndvRow' " + opListOnThisPage[i]['allReadyAssigned']
				+ " type='checkbox' name='" + opListOnThisPage[i]['id']
				+ "' />&nbsp;<label style='color: blue;'>"
				+ opListOnThisPage[i]['name'] + "</label></td></tr>";
		$("#opHolder").append(ctrl);
	}
	if(opListOnThisPage.length<1){
		$('#NoOpDefinedOnPageId').show();
		$('#opDefinedOnPageId').hide();
	}else{$('#opDefinedOnPageId').show();$('#NoOpDefinedOnPageId').hide();}
	
	//Disable all the operation check boxes
	$('.opSelClass').each(function() {
	    if($(this).prop('checked')||!$(this).prop('checked')) {
	        $(this).prop('disabled', true);
	    }
	});
	
	
	
	
}

// Fetch a e=record based on id
function getIdBasedRecord(id) {
	/* stop form from submitting normally */
	event.preventDefault();
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
			$('#successMsgId').html(
					"<span style='color:green;font:bold;'>"
							+ response.statusMsg + "</span>");
			if (response.status) {
				// alert(response.formObject.countryNameId);
				loadRecordInRoleSelectionForm(response.formObject);
				loadRecordInOperationSelectionForm(response.formObject);
			} else {
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
	return false; // prevent the browser following the href
}// End of getIdBasedRecord

function saveAndUpdateRecord() {
	var recordIdArray = GetSelectedRowID();
	/* stop form from submitting normally */
	event.preventDefault();
	var pageId = $('#pageRecordIdForRole').val();
	var roleId = $('#opRecordIdForRole').val();
	var accessRightsRbacId = $('#accessRightsRbacId').val();	
	$.ajax({
		async:false,//Client call will wait for the completion of ajax call
		type : 'POST',
		url : baseUrl + "/saveAndUpdateRecord?accessRightsRbacId="
				+ accessRightsRbacId + "&pageId=" + pageId + "&roleId="
				+ roleId + "&recordIdArray=" + recordIdArray + "",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming");
			$('#updateSuccessMsgId').html(
					"<span style='color:green;font:bold;'>"
							+ response.statusMsg + "</span>");
			if (response.status) {
				// Do something if required			
				
			} else {
				// alert("Form has an error");
				showBusinessEerror(response.fieldErrMsgMap);
			}

		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});

}

function loadDepartmentCombo() {
	/* stop form from submitting normally */
	method = 'GET';
	var url = "/admin/pvt/sec/env/department" + "/list";
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			if (response.status) {
				var ele = document.getElementById('departmentNameId');
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ele.innerHTML = ele.innerHTML + '<option value="'
							+ response.comboList[i]['id'] + '">'
							+ response.comboList[i]['name'] + '</option>';
				}

				// Do something if required
			} else {
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});
}

function loadModuleCombo(id) {
	/* stop form from submitting normally */
	method = 'GET';
	var url = "/admin/pvt/sec/env/module" + "/list?id=" + id;
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			if (response.status) {
				var ele = document.getElementById('moduleNameId');
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ele.innerHTML = ele.innerHTML + '<option value="'
							+ response.comboList[i]['id'] + '">'
							+ response.comboList[i]['name'] + '</option>';
				}

				// Do something if required
			} else {

				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});

}

function loadPageCombo(id){	
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/admin/pvt/sec/env/page" + "/list?id=" + id;		
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
						
				var ele = document.getElementById('pageNameId');	       
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

function loadOperationOfSelectedPage(roleId, pageIdSelectedForRights) {
	/* stop form from submitting normally */
	event.preventDefault();
	
	
	
	//
	var method = 'GET';
	var url = baseUrl + "/loadOperationOnPage" + "?roleId=" + roleId + "&pageId="
			+ pageIdSelectedForRights;	
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

			if (response.status) {
				//alert("Response is coming");
				$('#roleSelectedId').html(response.formObject.roleName);
				$('#pageNameIdOnOpSel').html(response.formObject.pageName);
				// {reserve these for using them while updating
				$('#pageRecordIdForRole').val(response.formObject.pageNameId);
				$('#opRecordIdForRole').val(response.formObject.roleNameId);
				$('#accessRightsRbacId').val(response.formObject.accessRightsRbacId);				
				loadRecordInOperationSelectionForm(response.formObject);
			} else {
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

	//
	
	
	
	
	
	
}

function loadRoleCombo(id){		
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/admin/pvt/sec/rbac/role" + "/list?id=" + id;
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
				var ele = document.getElementById('roleNameId');	
			
		        for (var i = 0; i < response.comboList.length; i++) {		        	
		            // POPULATE SELECT ELEMENT WITH JSON.		        	
		            ele.innerHTML = ele.innerHTML +
		                '<option  value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
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

// #######################################################################//
// ########## End: Methods for supporting above operations ############//
// #####################################################################//

