/**
 * Login table - validation of textbox: - null text box with login or password -
 * unaccepted Cookie communicate
 */

function checkForm(form) {

	var containerCookieCheck = document.getElementById("containerCookiePanel");
	// var lockLogin = document.getElementById("button");
	if (containerCookieCheck != null) {
		alert("You must accept Cookie information.");
		return false;
	}

	if (form.userName.value == "") {
		alert("Login cannot be blank!");
		form.username.focus();
		return false;
	}
	if (form.password.value == "") {
		alert("Password cannot be blank!");
		form.password.focus();
		return false;
	}
	
	//lock double click
	document.getElementById('button').disabled=true;
	
	return true;
}

/**
 * Main logon page - display window (create div) 'Contact to Administrator'.
 */
function adminContact() {

	/*
	 * //otwieranie function clickHandler(e) { if (e.preventDefault) {
	 * e.preventDefault(); } else { e.returnValue = false; }
	 * 
	 * containerContactAdmin.setAttribute('style', 'opacity: 0.02');
	 * 
	 * var interval = window.setInterval(function() {
	 * containerContactAdmin.style.opacity += 0.01;
	 * 
	 * if (containerContactAdmin.style.opacity >= 1.0) {
	 * document.body.appendChild(containerContactAdmin);
	 * window.clearInterval(interval); } }, 4); }
	 * 
	 */
	// alert("Contact to administrator:\ne-mail:
	// SSD.administrator@company.com\nPhone no.: xxx xxx xxx");
	var containerContactAdmin = document.createElement('div');

	containerContactAdmin.setAttribute("class", "containerContactAdmin");

	// inside main -> left panel
	var containerContactAdminText = document.createElement('div');
	containerContactAdminText.setAttribute("id", "containerText");
	containerContactAdminText.setAttribute("class", "containerText");
	containerContactAdminText.innerHTML = "<h5> Contact to Administrator:</h5><h4>If any problem with login or application please inform SSD Administrator<br>e-mail: Raport.sd.administrator@company.com <br><a href=\"mailto: admin@company.com?subject=SSD - contact to administrator&body=%0D%0A Hello, %0D%0A%0D%0A I have problem with Service Desk Support: %0D%0A%0D%0A <em>add your footer</em>  %0D%0A%0D%0A  \"></h4>Open Outlook</a> </p>";
	containerContactAdmin.appendChild(containerContactAdminText);

	// inside main -> right panel
	var buttonDivContactAdmin = document.createElement('div');
	buttonDivContactAdmin.setAttribute("class", "buttonDivContactAdmin");

	// inside right panel -> accept button
	var acceptButton = document.createElement('submit');
	acceptButton.setAttribute("class", "acceptButton")

	buttonDivContactAdmin.appendChild(acceptButton);
	containerContactAdmin.appendChild(buttonDivContactAdmin);

	acceptButton.setAttribute("href", "#");
	acceptButton.setAttribute("title", "Close");

	acceptButton.innerHTML = "Close";

	function clickHandler(e) {
		if (e.preventDefault) {
			e.preventDefault();
		} else {
			e.returnValue = false;
		}

		/*containerContactAdmin.setAttribute('style', 'opacity: 1');

		var interval = window.setInterval(function() {
			containerContactAdmin.style.opacity -= 0.01;

			if (containerContactAdmin.style.opacity <= 0.02) { */
				document.body.removeChild(containerContactAdmin);
				/*window.clearInterval(interval);
			}
		}, 4);*/
	}

	if (acceptButton.addEventListener) {
		acceptButton.addEventListener('click', clickHandler);
	} else {
		acceptButton.attachEvent('onclick', clickHandler);
	}

	document.body.appendChild(containerContactAdmin);

	// erase class
	var containerCookieEraseFloat = document.createElement('div');
	containerCookieEraseFloat.setAttribute("class", "eraserFloat");
	document.body.appendChild(containerCookieEraseFloat);

	return true;

}

/**
 * Method to display and checking cookie communicate (create div with
 * communicate).
 * 
 * @returns
 */
window
		.addEventListener(
				'load',
				function() {

					(function cookiesCommunicate() {

						// prevent to display cookie when refresh or logout
						// application
						if (readCookie('cookieAccept') != null) {
							// alert("usuwanie cookie");
							return false;
						} else {
							// main panel - create div for communicate
							var containerCookiePanel = document
									.createElement('div');
							containerCookiePanel.setAttribute("class",
									"containerCookiePanel");
							containerCookiePanel.setAttribute("id",
									"containerCookiePanel");

							// inside main -> left panel
							var containerCookieText = document
									.createElement('div');
							containerCookieText.setAttribute("id",
									"containerText");
							containerCookieText.setAttribute("class",
									"containerText");
							containerCookieText.innerHTML = "<h6> This site uses cookies! </h6> <p>We use information saved using cookies to ensure maximum convenience in using our application. If you agree to save the information contained in cookies, click \"Accept\" in the upper right corner of this information. If you do not agree, you can change cookie settings in your browser. </p>";
							containerCookiePanel
									.appendChild(containerCookieText);

							// inside main -> right panel
							var buttonDivCookie = document.createElement('div');
							buttonDivCookie.setAttribute("class",
									"buttonDivCookie");

							// inside right panel -> accept button
							var acceptButton = document.createElement('submit');
							acceptButton.setAttribute("class", "acceptButton")

							buttonDivCookie.appendChild(acceptButton);
							containerCookiePanel.appendChild(buttonDivCookie);

							/*
							 * containerCookie.setAttribute("id", "cookieInfo");
							 * containerCookie.setAttribute("class",
							 * "cookie-alert"); containerCookie.innerHTML = "<h6>
							 * This site uses cookies </h6> <p>We use
							 * information saved using cookies to ensure maximum
							 * convenience in using our application. If you
							 * agree to save the information contained in
							 * cookies, click & quot; x & quot; in the upper
							 * right corner of this information. If you do not
							 * agree, you can change cookie settings in your
							 * browser. </p>";
							 * 
							 * link.setAttribute("href", "#");
							 * link.setAttribute("title", "Zamknij"); //dodane
							 * nowe link.setAttribute("class",
							 * "cookieAcceptButton"); link.innerHTML = "x";
							 */

							acceptButton.setAttribute("href", "#");
							acceptButton.setAttribute("title", "Zamknij");
							// link.setAttribute("class", "cookieAcceptButton");
							acceptButton.innerHTML = "Accept";

							function clickHandler(e) {
								if (e.preventDefault) {
									e.preventDefault();
								} else {
									e.returnValue = false;
								}

								containerCookiePanel.setAttribute('style',
										'opacity: 1');

								var interval = window
										.setInterval(
												function() {
													containerCookiePanel.style.opacity -= 0.01;

													if (containerCookiePanel.style.opacity <= 0.02) {
														document.body
																.removeChild(containerCookiePanel);

														window
																.clearInterval(interval);
													}
												}, 4);

								// document.body.removeChild(containerCookiePanel);

							}

							if (acceptButton.addEventListener) {
								acceptButton.addEventListener('click',
										clickHandler);
							} else {
								acceptButton.attachEvent('onclick',
										clickHandler);
							}

							document.body.appendChild(containerCookiePanel);

							// erase class
							var containerCookieEraseFloat = document
									.createElement('div');
							containerCookieEraseFloat.setAttribute("class",
									"eraserFloat");
							document.body
									.appendChild(containerCookieEraseFloat);

							// create cookie
							createCookie('cookieAccept',
									'cookieCommunicateAccepted', 7);

							return true;
						}

					})();
				});

/**
 * Method to create cookie after accept cookie-communicate.
 * 
 * This method help prevent "display-again" cookie-communicate after refresh or
 * logout application.
 * 
 * @param name
 * @param value
 * @param days
 * @returns
 */
function createCookie(name, value, days) {
	var expires = "";
	if (days) {
		var date = new Date();
		date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
		expires = "; expires=" + date.toUTCString();
	}
	document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

/**
 * Method to read cookie by name.
 * 
 * This method help prevent "display-again" cookie-communicate after refresh or
 * logout application.
 * 
 * @param name
 * @returns
 */
function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ')
			c = c.substring(1, c.length);
		if (c.indexOf(nameEQ) == 0) {
			return c.substring(nameEQ.length, c.length);
		}
	}
	return null;
}