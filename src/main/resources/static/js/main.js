'use strict';

var usersSingleUploadForm = document.querySelector('#usersSingleUploadForm');
var usersSingleFileUploadInput = document.querySelector('#usersSingleFileUploadInput');
var usersSingleFileUploadError = document.querySelector('#usersSingleFileUploadError');
var usersSingleFileUploadSuccess = document.querySelector('#usersSingleFileUploadSuccess');

function uploadUsersSheet(file) {
	    var formData = new FormData();
	    formData.append("file", file);
	
	    var xhr = new XMLHttpRequest();
	    xhr.open("POST", "/uploadUsersSheet");
	    
	    usersSingleFileUploadSuccess.innerHTML = "<p> Loading Users.... </p>";
	
	    xhr.onload = function() {
	        console.log(xhr.responseText);
	        var response = JSON.parse(xhr.responseText);
	        if(xhr.status == 200) {
	            // once upload done successfully load uploaded list into DB. 
	        	var loadUsersXhr = new XMLHttpRequest();
	            loadUsersXhr.open("GET", "/loadUsers");
	            loadUsersXhr.send(); 
	            
	            loadUsersXhr.onload = function() {
	    	        console.log(loadUsersXhr.responseText);
	    	        if(xhr.status == 200) {
	    	            usersSingleFileUploadError.style.display = "none";
	    	            usersSingleFileUploadSuccess.innerHTML = "<p>Users List Loaded Successfully</p>";
	    	            usersSingleFileUploadSuccess.style.display = "block";
	    	        } else {
	    	        	usersSingleFileUploadSuccess.style.display = "none";
	    	        	usersSingleFileUploadError.innerHTML = "Some Error Occurred";
	    	        }
	    	    }
//	            usersSingleFileUploadError.style.display = "none";
//	            usersSingleFileUploadSuccess.innerHTML = "<p>Users List Loaded Successfully</p>";
//	            usersSingleFileUploadSuccess.style.display = "block";
	        } else {
	        	usersSingleFileUploadSuccess.style.display = "none";
	        	usersSingleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
	        }
	    }
	
	    xhr.send(formData);
}

usersSingleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;
    if(files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
    }
    uploadUsersSheet(files[0]);
    event.preventDefault();
}, true);


var invoicesSingleUploadForm = document.querySelector('#invoicesSingleUploadForm');
var invoicesSingleFileUploadInput = document.querySelector('#invoicesSingleFileUploadInput');
var invoicesSingleFileUploadError = document.querySelector('#invoicesSingleFileUploadError');
var invoicesSingleFileUploadSuccess = document.querySelector('#invoicesSingleFileUploadSuccess');

const sleep = (milliseconds) => {
	  return new Promise(resolve => setTimeout(resolve, milliseconds))
	}


function uploadInvoices(file) {
	    var formData = new FormData();
	    formData.append("file", file);
	
	    var xhr = new XMLHttpRequest();
	    xhr.open("POST", "/uploadInvoicesSheet");
	
	    invoicesSingleFileUploadSuccess.innerHTML = "<p> Loading invoices.... </p>";
			
	    xhr.onload = function() {
	        console.log(xhr.responseText);
	        var response = JSON.parse(xhr.responseText);
	        if(xhr.status == 200) {
	            // once upload done successfully load uploaded list into DB. 
	        	var loadInvoicesXhr = new XMLHttpRequest();
	            loadInvoicesXhr.open("GET", "/loadPhilipsInvoice");
	            loadInvoicesXhr.send(); 
	            
	            loadInvoicesXhr.onload = function() {
	    	        console.log(loadInvoicesXhr.responseText);
	    	        if(xhr.status == 200) {
	    	        	invoicesSingleFileUploadError.style.display = "none";
	    	        	invoicesSingleFileUploadSuccess.innerHTML = "<p>Invoices List Loaded Successfully</p>";
	    	        	invoicesSingleFileUploadSuccess.style.display = "block";
	    	        } else {
	    	        	invoicesSingleFileUploadSuccess.style.display = "none";
	    	        	invoicesSingleFileUploadError.innerHTML = "Some Error Occurred";
	    	        }
	    	    }
//	        	invoicesSingleFileUploadError.style.display = "none";
//	        	invoicesSingleFileUploadSuccess.innerHTML = "<p>Invoices List Loaded Successfully.</p>";
//	        	invoicesSingleFileUploadSuccess.style.display = "block";
	        } else {
	        	invoicesSingleFileUploadSuccess.style.display = "none";
	        	invoicesSingleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
	        }
	    }
	
	    xhr.send(formData);
}

invoicesSingleUploadForm.addEventListener('submit', function(event){
    var files = invoicesSingleFileUploadInput.files;
    if(files.length === 0) {
    	invoicesSingleFileUploadError.innerHTML = "Please select a file";
    	invoicesSingleFileUploadError.style.display = "block";
    }
//    if( ! (files[0].toString().includes("xlsx")) ) {
//    	invoicesSingleFileUploadError.innerHTML = "Please select Excel file";
//    	invoicesSingleFileUploadError.style.display = "block";
//    	return true; 
//    }
    uploadInvoices(files[0]);
    event.preventDefault();
}, true);


