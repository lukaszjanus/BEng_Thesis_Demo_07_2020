/**
 * Method sticking menu to Upper and bottom part of display.
 * @returns
 */
function initMenu() {
	$(document).ready(function() {
		var NavY = $('.MainMenu').offset().top;

		var stickyNav = function() {
			var ScrollY = $(window).scrollTop();

			if (ScrollY > NavY) {
				$('.MainMenu').addClass('sticky');
			} else {
				$('.MainMenu').removeClass('sticky');
			}
		};

		stickyNav();

		$(window).scroll(function() {
			stickyNav();
		});
	
	
		 /* bottom panel */
		var downY = $('.bottomPanel').offset().top;

		var downNav = function() {
			var ScrollY = $(window).scrollTop();

			if (ScrollY < downY) {
				$('.bottomPanel').addClass('catchDown');
			} else {
				$('.bottomPanel').removeClass('catchDown');
			}
		};

		downNav();

		$(window).scroll(function() {
			downNav();	
		});
	
	});
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
	containerContactAdminText.innerHTML = "<h5> Contact to Administrator:</h5><h4>If any problem with login or application please inform SSD Administrator<br>e-mail: SSD.administrator@company.com <br><a href=\"mailto: admin@company.com?subject=SSD - contact to administrator&body=%0D%0A Hello, %0D%0A%0D%0A I have problem with Service Desk Support: %0D%0A%0D%0A <em>add your footer</em>  %0D%0A%0D%0A  \"></h4>Open Outlook</a> </p>";
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
	// link.setAttribute("class", "cookieAcceptButton");
	acceptButton.innerHTML = "Close";

	function clickHandler(e) {
		if (e.preventDefault) {
			e.preventDefault();
		} else {
			e.returnValue = false;
		}

		containerContactAdmin.setAttribute('style', 'opacity: 1');

		var interval = window.setInterval(function() {
			containerContactAdmin.style.opacity -= 0.01;

			if (containerContactAdmin.style.opacity <= 0.02) {
				document.body.removeChild(containerContactAdmin);
				window.clearInterval(interval);
			}
		}, 4);
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
