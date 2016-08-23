
function Team(name) {
	this.position = 0;
	this._name = name;
	this.points = 0;
	this.played = 0;
	this.wins = 0;
	this.draws = 0;
	this.losses = 0;
	this.goalsScored = 0;
	this.goalsConceded = 0;
	this.differenceGoals = 0;

	this.incrementPlayed = function() {
    	this.played++;
	};

	this.incrementWins = function() {
	  	this.wins++;
  	};

	this.incrementDraws = function() {
        this.draws++;
  	};

  	this.incrementLosses = function() {
    	this.losses++;
  	};

  	this.incrementGoalsScored = function(goalsScored) {
        this.goalsScored += goalsScored;
  	};

  	this.incrementGoalsConceded = function(goalsConceded) {
        this.goalsConceded += goalsConceded;
	};

  	this.incrementOnePoint = function() {
        this.points++;
  	};

  	this.incrementThreePoints = function() {
	    this.points += 3;
	};
}

function Game(home, goalsHome, away, goalsAway)
{
	this.home = home;
	this.away = away;
	this.nameHome = this.home._name;
	this.nameAway = this.away._name;
	this.goalsHome = goalsHome;
	this.goalsAway = goalsAway;

	this.win = function() {
		this.home.incrementWins();
		this.away.incrementLosses();

		this.home.incrementThreePoints();
	};

	this.loss = function() {
		this.away.incrementWins();
		this.home.incrementLosses();

		this.away.incrementThreePoints();
	};

	this.draw = function() {
		this.home.incrementDraws();
		this.away.incrementDraws();
		this.home.incrementOnePoint();

		this.away.incrementOnePoint();
	};

	this.updateTeams = function() {
		this.home.incrementPlayed();
		this.away.incrementPlayed();

		this.home.incrementGoalsScored(this.goalsHome);
		this.away.incrementGoalsScored(this.goalsAway);
		this.home.incrementGoalsConceded(this.goalsAway);
		this.away.incrementGoalsConceded(this.goalsHome);

		this.home.differenceGoals = this.home.goalsScored - this.home.goalsConceded;
		this.away.differenceGoals = this.away.goalsScored - this.away.goalsConceded;

		if(this.goalsHome > this.goalsAway)
			this.win();
		else if(this.goalsHome < this.goalsAway)
			this.loss();
		else if(this.goalsHome == this.goalsAway)
			this.draw();
	}
}

var teams = []; // array of Team
var patt = /[a-z]/i;

$('#classification tr').each(function() {
	$(this).find('td').each(function() {
		if(patt.test(this.innerHTML))
			teams.push(new Team(this.innerHTML));
	});
});

for(i = 0; i < teams.length; i++)
	teams[i].position = i + 1;

var cells = []; // array of strings

$('#matchday1 tr').each(function() {
	$(this).find('td').each(function() {
		cells.push(this.innerHTML);
	});
});

var games = []; // array of Game
var team; // Team
var teamName; // string
var home, away; // Team

for(i = 0; i < cells.length; i += 4)
{
	for(var j = 0; j < teams.length; j++)
	{
		team = teams[j];
		teamName = teams[j]._name;

		if(cells[i] == teamName)
			home = team;
		else if(cells[i + 2] == teamName)
			away = team;
	}

	games.push(new Game(home, parseInt(cells[i + 1]), away, parseInt(cells[i + 3])));
}

cells = [];

$('#matchday2 tr').each(function() {
	$(this).find('td').each(function() {
		cells.push(this.innerHTML);
	});
});

for(i = 0; i < cells.length; i += 4)
{
	for(j = 0; j < teams.length; j++)
	{
		team = teams[j];
		teamName = teams[j]._name;

		if(cells[i] == teamName)
			home = team;
		else if(cells[i + 2] == teamName)
			away = team;
	}

	games.push(new Game(home, parseInt(cells[i + 1]), away, parseInt(cells[i + 3])));
}

cells = [];

$('#matchday3 tr').each(function() {
	$(this).find('td').each(function() {
		cells.push(this.innerHTML);
	});
});

for(i = 0; i < cells.length; i += 4)
{
	for(j = 0; j < teams.length; j++)
	{
		team = teams[j];
		teamName = teams[j]._name;

		if(cells[i] == teamName)
			home = team;
		else if(cells[i + 2] == teamName)
			away = team;
	}

	games.push(new Game(home, parseInt(cells[i + 1]), away, parseInt(cells[i + 3])));
}

for(i = 0; i < games.length; i++)
	games[i].updateTeams();

var maxArr = teams.length;
var newV;
var newP;
var arrSortedLength = 1;

for(j = arrSortedLength; arrSortedLength < maxArr; j--)
{
	if(teams[j].points > teams[j - 1].points)
	{
		newV = teams[j].position;
		newP = teams[j];
		teams[j].position = teams[j - 1].position;
		teams[j] = teams[j - 1]
		teams[j - 1].position = newV;
		teams[j - 1] = newP;

		if(j == 1)
		{
			arrSortedLength++;

			j = arrSortedLength + 1;
		}
	}
	else
	{
		arrSortedLength++;

		j = arrSortedLength + 1;
	}
}

var teamAttributes = [];

i = 0;
var zombie = new Team("zombie");

for(var property in zombie)
{
	if(i == 10)
		break;

	i++;

	teamAttributes.push(property);
}

var teamAttribValues = [];

for(i = 0; i < teams.length; i++)
	for(j = 0; j < teamAttributes.length; j++)
		teamAttribValues.push(teams[i][teamAttributes[j]]);

var arr = $("#classification tr").map(function () {
    return $(this).children().map(function () {
        return $(this);
    });
});

var k = 0;

for(i = 1; i <= teams.length; i++)
	for(j = 0; j < teamAttributes.length; j++)
	{
		arr[i][j].text(teamAttribValues[k]);

		k++;
	}
