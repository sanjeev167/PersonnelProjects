/***************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 
 **************************/

// ###########################################################//
// ########## Start of grid and CRUD related code ###########//
// #########################################################//

$(document).ready(function() {
	
	// Select and loop the container element of the elements you want to equalise
    $('.container3Div').each(function(){      
      // Cache the highest
      var highestBox = 0;     
      // Select and loop the elements you want to equalize
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
	//$('#selectRoleDomainId').hide();
	//$('#selectActionDomainId').hide();
	//$('#NoActionDefinedOnDomainId').hide();
	//$('#NoRoleDefinedEithinDepartmentId').hide();

// [0] Load grid while opening the page.
       loadGrid("", "","");
	   preparePage();
	   
//This will set the width of the datatable search box.
	   $('.dataTables_filter input[type="search"]').
	   attr('placeholder','Search text').
	   css({'width':'100px','display':'inline-block'});	
	   
// [1] Load role and operation based on selected domainId.
	  $(document).on("click", ".aclRightsClass", function(e) {
		 event.preventDefault();
		 var domainId = $(this).attr("href").split('=')[1];
		 $('#domainRecordIdForRole').val(domainId);//Preserved it for future usage
		 getIdBasedRecord(domainId);// This will load both role and operation page	
		// Check #x
		$( "#permissionBaseIdForRole" ).prop( "checked", true );	   
		// Uncheck #x
		$('#userAndRoleDefinedWithinDepartmentId').hide();
		$( "#permissionBaseIdForUser" ).prop( "checked", false );
		  
		$('#hideUpdateButtonId').hide();
		$('#selectRoleDomainId').show();
		$('#selectActionDomainId').show();		
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
		var domainIdSelectedForRights = $('#domainRecordIdForRole').val();//Was preserved while loading role and operation on page
		loadActionsOnRoleSelection(roleId,domainIdSelectedForRights);
		enableActionDisabledCheckBox();
	});
	 
	 
//This will be called when a user is selected.
	 $(document).on("click",".userSelectedForRightsAssignment",	function(e) {
			event.preventDefault();
			$('#hideUpdateButtonId').show();
			$('#updateSuccessMsgId').html("");				
			var roleId = $('#roleIdSelectedForUsersListing').val();
			var userId = $(this).attr("href").split('=')[1];			
			$('#userIdSelectedForRightsAssignment').val(userId);//Preserve it. Will be used actions assignes are updated.			
			var domainId = $('#domainRecordIdForRole').val();//Was preserved while loading role and operation on page
			loadActionsOnUserSelection(roleId,userId,domainId);
			
			enableActionDisabledCheckBox();
		});

	 
	 
	 
	 
// [3] Save a record.
	$(document).on("click", "#saveFormButtonId", function() {	
	  saveAndUpdateRecord();
	  var domainId = $('#domainRecordIdForRole').val();//Was preserved while loading role and operation on page		  
	  getIdBasedRecord(domainId);// This will load both role and operation page	 
	  
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
		var domainNameId = $('#domainNameId').val();		
		searchAndReloadGrid(departmentId,moduleNameId,domainNameId);
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
	});
// [9] When module combo change, load page combo		
	$('#moduleNameId').on('change', function(e) {
		$('#domainNameId').empty().append('<option  value="">-Select-</option>');
		loadDomainCombo($('#moduleNameId').val());
	});

//[10] When permission base is selected	access rights assignment
	$('.permissionBaseClass').on('click', function(e) {	
		//Clean the earlier selection
		// UnCheck #x
		$( ".roleSelectionForUsersListing" ).prop( "checked", false );
		//
		$("#roleBaserUserHolderId").html("");
		$("#userLinkForSelHolderId").html("");
		
		//Bring in the previous state of both role and action listing forms		 
		  $('#hideUpdateButtonId').hide();
		  $('#roleSelectedId').html("");//Will restore the previoust state of unselected roles	
		  clearCheckBoxSelection();//Will clear all checkbox selected previously
		  disableAllActionCheckBoxes();
		  $('#updateSuccessMsgId').html("");
		//Hide role based details and open user based details
		if($(this).attr("value")=="U"){
		$("#roleDefinedEithinDepartmentId").hide();
		$("#userAndRoleDefinedWithinDepartmentId").show();
		$("#permissionBaseId").html("User");		
		}else{
			$("#roleDefinedEithinDepartmentId").show();
			$("#userAndRoleDefinedWithinDepartmentId").hide();
			$("#permissionBaseId").html("Role");
		}		
	});

//[11] Load users list when a role is selected. This is the case when the permission base is user
	$(document).on("click", ".roleSelectionForUsersListing", function(e) {		
	   var selectedRoleId=$(this).attr("value");	
	 //Preserve this role id so that it could be used while updating user's permission	
		$("#roleIdSelectedForUsersListing").val(selectedRoleId);
	   loadUsersListBasedOnSelectedRole(selectedRoleId);
	});	
});// End of document ready


function enableActionDisabledCheckBox(){	
	$('.actionSelClass').each(function() {
	    if($(this).prop('checked')||!$(this).prop('checked')) {
	        $(this).prop('disabled', false);
	    }
	});		
}

// Reload Grid.
function reloadGrid() {
	event.preventDefault();
	clearCheckBoxSelection();
	window.location.replace("/admin/pvt/sec/acl/aclPermissions/");

}
function refreshGridPage() {
	event.preventDefault();
	clearCheckBoxSelection();
	t.draw();// Loading existing opened page only
	
}

function searchAndReloadGrid(departmentNameId, moduleNameId,domainNameId) {	
	var table = $('#accessRightsAclId').DataTable();
	table.ajax.url(
			"paginated?departmentNameId=" + departmentNameId + "&moduleNameId="
					+ moduleNameId+"&domainNameId="+domainNameId).load();
	
	$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
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
function loadGrid(departmentNameId, moduleNameId,domainNameId) {
	t = $('#accessRightsAclId')
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
						"aaSorting" : [ [ 2, "asc" ], [ 3, "asc" ]],
						"ajax" : {

							"url" : "paginated?departmentNameId="
									+ departmentNameId + "&moduleNameId="
									+ moduleNameId + "&domainNameId="+domainNameId+"",
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
									"title" : "App. Context"
								},
								{
									"data" : "moduleName",
									"name" : "moduleName",
									"title" : "Module"
								},
								{
									"data" : "domainName",
									"sortable" : false,
									
									"render" : function(data, type, row) {
										return '<a class="aclRightsClass" href=?record_id='
												+ row.id
												+ '><span style="color:blue;">'
												+ data + '</span></a>';
									},
									"title" : "Domain"
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

var baseUrl = '/admin/pvt/sec/acl/aclPermissions';

// If the form requires anything pre-loaded. it can be done here.
function preparePage() {
	loadDepartmentCombo();
}

function loadInRoleSelectionForm(response) {
	
	$('#departmentNameIdForRole').html(response.departmentName);
	var roleListInDepartment = response.roleListInDepartment;	
	var ctrl = "";
	var ctrlForUserSelection = "";
	$("#roleHolder").html("");
	$("#roleUserHolder").html("");
	
	for (i = 0; i < roleListInDepartment.length; i++) {
		var index = i + 1;
		if (roleListInDepartment[i]['accessCount'] != 0){
			ctrl = "<tr><td>[" + index + "]&nbsp;<span style='color: blue;'>"
					+ roleListInDepartment[i]['name'] + "  <label style='color: green;'>[P:-"
					+ roleListInDepartment[i]['accessCount']
					+ "<span>]</label></td><td><a class='roleSelClass' href='?id="
					+ roleListInDepartment[i]['id']
					+ "'><i class='fa fa-edit'></a></td></tr>";		
		}
		else{
			ctrl = "<tr><td>[" + index + "]&nbsp;<span style='color: blue;'>"
					+ roleListInDepartment[i]['name'] + "  <label style='color: red;'>[P:-"
					+ roleListInDepartment[i]['accessCount']
					+ "<span>]</label></td><td><a class='roleSelClass' href='?id="
					+ roleListInDepartment[i]['id']
					+ "'><i class='fa fa-edit'></a></td></tr>";
		}
		
		//This is used when the permission base is user
		ctrlForUserSelection = "<tr><td>[" + index + "]&nbsp;"           
               + "<input type='radio' class='roleSelectionForUsersListing' name='roleSelectedForUser' value='"+roleListInDepartment[i]['id']+"'/> "            
            + roleListInDepartment[i]['name'] 
            +"</td></tr>";		
		//End
		
		
		$("#roleUserHolder").append(ctrlForUserSelection);
		$("#roleHolder").append(ctrl);
	}
	
	if(roleListInDepartment.length<1){
		$('#NoRoleDefinedEithinDepartmentId').show();
		$('#roleDefinedEithinDepartmentId').hide();
	}else{
		$('#roleDefinedEithinDepartmentId').show();
		$('#NoRoleDefinedEithinDepartmentId').hide();
		$('#userAndRoleDefinedWithinDepartmentId').hide();
		$( "#permissionBaseIdForUser").prop( "checked", false );$( "#permissionBaseIdForRole").prop( "checked", true );}	
}

function loadUsersListBasedOnSelectedRole(selectedRoleId){
	
	
	//alert("Going to load users when roleId  = "+selectedRoleId);
	
	/* stop form from submitting normally */
	method = 'GET';
	var url = "/admin/pvt/sec/rbac/role" + "/listRoleBasedUsers?id=" + selectedRoleId;
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
				//alert("Response is coming = ");				
				// Do something if required
				prepareUserListing(response.comboList);
			} else {

				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function() {
			;
		}
	});

}

function prepareUserListing(roleBasedUserList){
	
	var ctrlUser;
	var ctrlUserLink;
	$("#roleBaserUserHolderId").html("");
	$("#userLinkForSelHolderId").html("");
	
	for (i = 0; i < roleBasedUserList.length; i++) {
		if(roleBasedUserList[i]['accessCount']>0)
		   ctrlUser = "<tr><td>" + roleBasedUserList[i]['name'] +"<label style='color:green;'>[P-"+roleBasedUserList[i]['accessCount']+"]</label></td></tr>";			
		else
			ctrlUser = "<tr><td>" + roleBasedUserList[i]['name'] +"<label style='color:red;'>[P-"+roleBasedUserList[i]['accessCount']+"]</label></td></tr>";			
		
		ctrlUserLink = "<tr><td><a class='userSelectedForRightsAssignment' href='?id="+ roleBasedUserList[i]['id']+ "'><i class='fa fa-edit'></a></td></tr>" +
				"<tr><td>&nbsp;</td></tr>";		
			
		$("#roleBaserUserHolderId").append(ctrlUser);
		$("#userLinkForSelHolderId").append(ctrlUserLink);
	    }	
	}

//This function will actions list as well as pre-assigned access rights in action selection form.
//This is used for both the cases i.e. when permission base is user and when permission base is role.
function loadInActionSelectionForm(response) {	
	clearCheckBoxSelection();
	$('#departmentNameIdOnActionSel').html(response.departmentName);
	$('#moduleNameIdOnActionSel').html(response.moduleName);
	$('#domainNameIdOnActionSel').html(response.domainName);
	var actionListOnThisDomain = response.allActionsDefinedOnThisDomain;	
	var ctrl = "";
	var isChecked = "";
	$("#opHolder").html(ctrl);
	for (i = 0; i < actionListOnThisDomain.length; i++) {		
		ctrl = "<tr><td><input class='actionSelClass chkIndvRow' " + actionListOnThisDomain[i]['allReadyAssigned']
				+ " type='checkbox' name='" + actionListOnThisDomain[i]['id']
				+ "' />&nbsp;<span style='color: blue;'>"
				+ actionListOnThisDomain[i]['name'] + "</span></td></tr>";
		$("#opHolder").append(ctrl);
	}
	if(actionListOnThisDomain.length<1){
		$('#NoActionDefinedOnDomainId').show();
		$('#actionDefinedOnDomainId').hide();
	}else{$('#actionDefinedOnDomainId').show();$('#NoActionDefinedOnDomainId').hide();}
	
	//Disable all the action check boxes
	disableAllActionCheckBoxes();
}

function disableAllActionCheckBoxes(){
	$('.actionSelClass').each(function() {
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
				
				loadInRoleSelectionForm(response.formObject);
				loadInActionSelectionForm(response.formObject);
			} else {
				showBusinessEerror(response.fieldErrMsgMap);
			}
			// Now load the page with response
			if (response.status == "ErrorFree")
				loadRecord(response);
			else
				showBusinessEerror(response);
		},
		error : function() {
			;
		}
	});
	return false; // prevent the browser following the href
}// End of getIdBasedRecord

function saveAndUpdateRecord() {	
	var recordIdArray = GetSelectedRowID();
	/* stop form from submitting normally */
	event.preventDefault();
	var domainId = $('#domainRecordIdForRole').val();
	var roleId = $('#roleIdSelectedForActionListing').val();
	var userId = $('#userIdSelectedForRightsAssignment').val();
	
	var permissionBase=$("input:radio.permissionBaseClass:checked").val();	
	var accessRightsAclId = $('#accessRightsAclId').val();	
	$.ajax({
		async:false,//Client call will wait for the completion of ajax call
		type : 'POST',
		url : baseUrl + "/saveAndUpdateRecord?accessRightsAclId="
				+ accessRightsAclId + "&domainId=" + domainId + "&roleId="
				+ roleId + "&userId=" + userId 
				+ "&recordIdArray=" + recordIdArray + "&permissionBase=" + permissionBase + "",
				
				
				
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
		error : function() {
			;
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
		error : function() {
			;
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
		error : function() {
			;
		}
	});

}

function loadDomainCombo(id){	
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/admin/pvt/sec/acl/dmnMaster" + "/list?id=" + id;		
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
		error : function() {
			;
		}
	});
	
}

function loadActionsOnRoleSelection(roleId, domainIdSelectedForRights) {
	/* stop form from submitting normally */
	event.preventDefault();	
	var method = 'GET';
	var url = baseUrl + "/loadActionsOnRoleSelection" + "?roleId=" + roleId + "&domainId="
			+ domainIdSelectedForRights;	
	
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
				///alert("Sanjeev Response is coming");
				$('#roleSelectedId').html(response.formObject.roleName);
				$('#domainNameIdOnActionSel').html(response.formObject.domainName);
				// {reserve these for using them while updating
				$('#domainRecordIdForRole').val(response.formObject.domainNameId);
				$('#roleIdSelectedForActionListing').val(response.formObject.roleNameId);
				$('#accessRightsAclId').val(response.formObject.accessRightsAclId);				
				loadInActionSelectionForm(response.formObject);
			} else {
				showBusinessEerror(response.fieldErrMsgMap);
			}
			// Now load the page with response
			if (response.status == "ErrorFree")
				loadRecord(response);
			else
				showBusinessEerror(response);
		},
		error : function() {
			;
		}
	});

	//	
}



function loadActionsOnUserSelection(roleId,userId, domainIdSelectedForRights) {
	/* stop form from submitting normally */
	event.preventDefault();	
	var method = 'GET';
	var url = baseUrl + "/loadActionsOnUserSelection" + "?roleId="+roleId+"&userId=" + userId + "&domainId="
			+ domainIdSelectedForRights;	
	
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
				///alert("Sanjeev Response is coming");
				$('#roleSelectedId').html(response.formObject.userName);
				$('#domainNameIdOnActionSel').html(response.formObject.domainName);
				// {reserve these for using them while updating
				$('#domainRecordIdForRole').val(response.formObject.domainNameId);
				$('#roleIdSelectedForActionListing').val(response.formObject.roleNameId);
				$('#accessRightsAclId').val(response.formObject.accessRightsAclId);				
				loadInActionSelectionForm(response.formObject);
			} else {
				showBusinessEerror(response.fieldErrMsgMap);
			}
			// Now load the page with response
			if (response.status == "ErrorFree")
				loadRecord(response);
			else
				showBusinessEerror(response);
		},
		error : function() {
			;
		}
	});

	//
	
	
	
	
	
	
}



// #######################################################################//
// ########## End: Methods for supporting above operations ############//
// #####################################################################//

