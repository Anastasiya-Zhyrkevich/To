$(function() {
	$('.update-local').on('click', function() {
		var PassPhrase = "The Moon is a Harsh Mistress."; 

		// The length of the RSA key, in bits.
		var Bits = 1024; 

		var MattsRSAkey = cryptico.generateRSAKey(PassPhrase, Bits);
		
		var MattsPublicKeyString = cryptico.publicKeyString(MattsRSAkey);   
		console.log(MattsPublicKeyString);
		var MattsPrivateKeyString = cryptico.privateKeyString(MattsRSAkey);    
		
		// Put the object into storage
		localStorage.setItem($('.username').val() + 'privateKey', MattsPrivateKeyString);
		
		$('.public-key').val(MattsPublicKeyString);
	});
});

var save_keys = function(){
	
}

