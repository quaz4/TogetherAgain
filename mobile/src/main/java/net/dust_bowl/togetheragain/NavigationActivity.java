package net.dust_bowl.togetheragain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

	public static final String LOGIN_PREF = "TogetherAgainLogin";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setTitle("Together Again");
		toolbar.setSubtitle("Gallery");

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		//DEBUG
		SharedPreferences googleLogin = getSharedPreferences(LOGIN_PREF, 0);
		TextView debug = (TextView) findViewById(R.id.debug);
		debug.setText(googleLogin.getString("personId", "Nothing found after logout"));
	}

	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if(drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		} else
		{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if(id == R.id.action_logout)
		{
			logout();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if(id == R.id.nav_pair)
		{
			// Handle the camera action
		}
		else if(id == R.id.nav_gallery)
		{
			//Handle gallery action
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	public void logout()
	{
		//DEBUG
		SharedPreferences googleLogin = getSharedPreferences(LOGIN_PREF, 0);
		TextView debug = (TextView) findViewById(R.id.debug);
		debug.setText(googleLogin.getString("personId", "Nothing found after logout"));

		SharedPreferences.Editor loginInfoEditor = googleLogin.edit();
		loginInfoEditor.clear();
		loginInfoEditor.commit();

		loginInfoEditor.putBoolean("logout", true);
		loginInfoEditor.commit();

		//getBaseContext() could be "getBaseContext()"?
		Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
		//intent.putExtra("LOGOUT_INTENT", true);
		startActivity(intent);
	}
}