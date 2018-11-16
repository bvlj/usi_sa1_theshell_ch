function check(){

	var question1 = document.quiz.question1.value;
	var question2 = document.quiz.question2.value;
	var question3 = document.quiz.question3.value;
	var question4 = document.quiz.question4.value;
	var question5 = document.quiz.question5.value;


	var correct = 0;


	if (question1 == "Is a location to a folder or file in a file system of a Operating System") {
		correct++;
}
	if (question2 == "Is defined as specifying the location of a file or directory from the root directory(/)") {
		correct++;
}
	if (question3 == "YES") {
		correct++;
	}
	if (question4 == "") {
		correct++;
	}
	if (question5 == "YES") {
		correct++;
	}


	var pictures = ["img/win.gif", "img/meh.jpeg", "img/lose.gif"];
	var messages = ["Great job!", "That's just okay", "You really need to do better"];
	var score;

	if (correct == 0) {
		score = 4;
	}

	if (correct > 0 && correct < 5) {
		score = 3;
	}

	if (correct == 5) {
		score = 0;
	}

	document.getElementById("after_submit").style.visibility = "visible";

	document.getElementById("message").innerHTML = messages[score];
	document.getElementById("number_correct").innerHTML = "You got " + correct + " correct.";
	document.getElementById("picture").src = pictures[score];
	}
