
function team(name) {
	this.name = name;
	this.points = 0;
	this.matches = 0;
	this.wins = 0;
	this.draws = 0;
	this.losses = 0;
	this.goalsScored = 0;
	this.goalsConceded = 0;
	this.goalsDifference = 0;

	this.win = function() {
		this.matches++;		
		this.points += 3;
		this.wins++;
	};

	this.draw = function() {
		this.matches++;				
		this.points++;
		this.draws++;
	};

	this.loss = function() {
		this.matches++;		
		this.losses++;
	};		
}

var teamsNameConcat = $("main tr td:nth-child(2)").text();
var teamsNameConcatLength = teamsNameConcat.length;

var ch_begin, ch_end;

var teams = [];

for(var i = 0; i < teamsNameConcatLength; i++)
{
	var ch = teamsNameConcat.charCodeAt(i);

	if(i == 0)
	{
		ch_end = 0;
		ch_begin = 0;
	}
	else if(ch >= 65 && ch <= 90 && teamsNameConcat.charCodeAt(i - 1) != 32)
	{
		ch_end = ch_begin;
		ch_begin = i;

		teams.push(teamsNameConcat.slice(ch_end, ch_begin));
	}
}

teams.push(teamsNameConcat.slice(ch_begin, teamsNameConcatLength));

for(var i = 0; i < teams.length; i++)
{
	var teamName = teams[i];

	teams[i] = new team(teams[i]);
	teams[i].place = i + 1; 
	
	console.log(teams[i]);
}

teams[teams.length - 1].win();
teams[teams.length - 1].draw();
teams[teams.length - 1].loss(); 

for(var i = 0; i < teams.length; i++)
{
	console.log(teams[i]);
}
