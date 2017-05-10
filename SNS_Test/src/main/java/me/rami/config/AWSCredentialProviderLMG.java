package me.rami.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;




public class AWSCredentialProviderLMG implements AWSCredentialsProvider {

	

    
	// Credentials long terme fourni par les admins 
    AWSCredentials Lcredentials = null;
	// Credential temporaire
    AWSCredentials Tcredentials = null;
	public AWSCredentialProviderLMG()
	{
		
	}


	public AWSCredentialProviderLMG(AWSCredentials awsCre)
	{
		Lcredentials = awsCre;

	}


	public AWSCredentials getCredentials() {
		// TODO Auto-generated method stub
		return Tcredentials;
	}


	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	

}
