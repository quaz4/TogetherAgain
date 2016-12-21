package net.dust_bowl.togetheragain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener
{

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SignInActivity";
    public static final String LOGIN_PREF = "TogetherAgainLogin";
	private SharedPreferences googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		//Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Disable login button
        findViewById(R.id.sign_in_button).setEnabled(false);

        //Assign button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        googleLogin = getSharedPreferences(LOGIN_PREF, 0);

        if(googleLogin.getString("personId", null) != null)
        {
            //TODO Next activity
        }

        //User not logged in, enable login button
        findViewById(R.id.sign_in_button).setEnabled(true);

        //Configure sign-in to request the user's ID, email address, and basic
        //profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Build a GoogleApiClient with access to the Google Sign-In API and the
        //options specified by gso.
		mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        //An unresolvable error has occurred and Google APIs (including Sign-In) will not
        //be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onClick(View view)
    {
        //Used switch to make extensions easy later
        switch(view.getId())
        {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }


    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(requestCode == RC_SIGN_IN)
		{
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result)
	{
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if(result.isSuccess())
        {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personId = acct.getId();
            String personPhoto = acct.getPhotoUrl().toString();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();

            //Get shared preferences editor
            SharedPreferences.Editor loginInfoEditor = googleLogin.edit();

            //Save user information
            loginInfoEditor.putString("personId", personId);
            loginInfoEditor.putString("personPhoto", personPhoto);
            loginInfoEditor.putString("personGivenName", personGivenName);
            loginInfoEditor.putString("personFamilyName", personFamilyName);

            //Commit the edits
            loginInfoEditor.commit();
        }
    }
}