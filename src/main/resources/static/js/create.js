/**
 * 
 */
function checkPassword() {
	var password = document.getElementById("password");
	var password_confirm = document.getElementById("password_confirm");
	var errorMsg = "";
	var error = document.getElementById("error");
	
	// 正規表現チェック 小文字、大文字1桁以上、数字1桁以上、記号1桁以上, 8桁以上、30桁以下
	var regex = new RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,30}$/);
	if (!regex.test(password.value)) {
		errorMsg = "パスワード条件：小文字、大文字1桁以上、数字1桁以上、記号1桁以上、8桁以上<br>";
		document.getElementById("pw_check").setAttribute("src", "/img/delete-button.png")
		error.innerHTML = errorMsg;
	} else {
		error.innerHTML = "";
		document.getElementById("pw_check").setAttribute("src", "/img/accept.png")
	}
	
	// passwordと確認用passwordの一致確認
	if (password.value != password_confirm.value) {
		errorMsg = "パスワードが不一致<br>";
		document.getElementById("submit").setAttribute("disabled", true); 
		document.getElementById("pw_confirm").setAttribute("src", "/img/delete-button.png")
		error.innerHTML += errorMsg;
	} else {
		document.getElementById("submit").removeAttribute("disabled");
		document.getElementById("pw_confirm").setAttribute("src", "/img/accept.png")
	}
	return;
}

function submitConfirm(){
	if (confirm('実行する？')){
		return true;
	}
	return false;
}

function checkUsername() {
	var username = document.getElementById("username").value;
	var userCheck = document.getElementById("username_check");
	var error = document.getElementById("error");
	
	if (username.length < 8 || username.length > 20) {
		userCheck.setAttribute("src", "/img/delete-button.png")
		document.getElementById("submit").setAttribute("disabled", true); 
		error.innerHTML = "ユーザ名の長さは8から20桁内";
	} else {
		userCheck.setAttribute("src", "/img/accept.png")
		document.getElementById("submit").removeAttribute("disabled");
		error.innerHTML = "";
	}
}

function checkDisplayname() {
	var display_name = document.getElementById("display_name").value;
	var displayCheck = document.getElementById("display_name_check");
	var error = document.getElementById("error");
	
	if (display_name.length > 15) {
		displayCheck.setAttribute("src", "/img/delete-button.png")
		document.getElementById("submit").setAttribute("disabled", true); 
		error.innerHTML = "表示名の長さは15桁内";
	} else {
		displayCheck.setAttribute("src", "/img/accept.png")
		document.getElementById("submit").removeAttribute("disabled");
		error.innerHTML = "";
	}
}

function checkEmail() {
	var email = document.getElementById("email");
	var email_check = document.getElementById("email_check");
	// 正規表現チェック xxx123@xxx.xxx
	var regex = new RegExp(/^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9]{1,}\.)+[a-zA-Z]{2,}$/);
	if (!regex.test(email.value)) {
		email_check.setAttribute("src", "/img/delete-button.png")
		error.innerHTML = "Emailの入力値が正しくない";
	} else {
		error.innerHTML = "";
		email_check.setAttribute("src", "/img/accept.png")
	}
	
}