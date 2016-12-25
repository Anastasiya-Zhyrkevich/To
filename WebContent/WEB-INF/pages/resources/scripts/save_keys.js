

var save_keys = function(){
	alert("success save_key");
	console.log("Hello");
	var testObject = { 'one': 1, 'two': 2, 'three': 3 };

	var PassPhrase = "The Moon is a Harsh Mistress."; 

	// The length of the RSA key, in bits.
	var Bits = 1024; 

	var MattsRSAkey = cryptico.generateRSAKey(PassPhrase, Bits);
	
	console.log(PassPhrase);

	// Put the object into storage
	localStorage.setItem('testObject', JSON.stringify(testObject));
	console.log(localStorage);
}

