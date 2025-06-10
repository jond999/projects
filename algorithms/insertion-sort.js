// Insertion Sort

var arr = [3, 1, 4, 4, 4, 2, 0, 3, 3, 0, 4, 1, 1, 6, 1, 4, 6, 1];

for (i = 0; i < arr.length; i++)
	console.log(arr[i]);

var maxArr = arr.length;
var newV;
var arrSortedLength = 1;
var i, j;

for (j = arrSortedLength; arrSortedLength < maxArr; j--) {
	if (arr[j] > arr[j - 1]) {
		newV = arr[j];
		arr[j] = arr[j - 1];
		arr[j - 1] = newV;

		if (j == 1) {
			arrSortedLength++;

			j = arrSortedLength + 1;
		}
	}
	else {
		arrSortedLength++;

		j = arrSortedLength + 1;
	}
}

console.log("-----------------------------------");

for (i = 0; i < arr.length; i++)
	console.log(arr[i]);
