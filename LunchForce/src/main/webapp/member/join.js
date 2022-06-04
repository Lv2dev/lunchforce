// Example starter JavaScript for disabling form submissions if there are invalid fields
(function() {
	'use strict'

	// Fetch all the forms we want to apply custom Bootstrap validation styles to
	var forms = document.querySelectorAll('.needs-validation')

	// Loop over them and prevent submission
	Array.prototype.slice.call(forms)
		.forEach(function(form) {
			form.addEventListener('submit', function(event) {
				if (!form.checkValidity()) {
					event.preventDefault()
					event.stopPropagation()
				}

				form.classList.add('was-validated')
			}, false)
		})
})()

function check_pw() {
	var pw = document.getElementById('pw').value;
	if (document.getElementById('pw').value != '' && document.getElementById('pw2').value != '') {
		if (document.getElementById('pw').value == document.getElementById('pw2').value) {
			document.getElementById('pwChk').innerHTML = '비밀번호가 일치합니다.'
			document.getElementById('pwChk').style.color = 'blue';
		}
		else {
			document.getElementById('pwChk').innerHTML = '비밀번호가 일치하지 않습니다.';
			document.getElementById('pwChk').style.color = 'red';
		}
	}
}