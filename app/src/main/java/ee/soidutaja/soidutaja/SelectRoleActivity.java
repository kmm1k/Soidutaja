package ee.soidutaja.soidutaja;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ee.soidutaja.soidutaja.facebook.*;

public class SelectRoleActivity extends AppCompatActivity {

    private Button driverBtn;
    private Button passengerBtn;
    private Button profileBtn;
    private List<String> locations;
    private ProgressBar progressBar;
    private List<Drive> drives;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ee.soidutaja.soidutaja.facebook.User user = new ee.soidutaja.soidutaja.facebook.User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_first);

        driverBtn = (Button) findViewById(R.id.driverButton);
        passengerBtn = (Button) findViewById(R.id.passengerButton);
        profileBtn = (Button) findViewById(R.id.profileButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent logOut = new Intent(SelectRoleActivity.this, LoginActivity.class);
                startActivity(logOut);
                finish();
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    RequestPackage rp = new RequestPackage();
                    rp.setMethod("POST");
                    rp.setUri("http://193.40.243.200/soidutaja_php/");
                    rp.setParam("allDrives", "yes");

                    Task task = new Task();
                    task.execute(rp);
                }
            }
        });

        if (isOnline()) {
            Log.d("lammas", "nett on olemas");
            requestData();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void requestData() {
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri("http://193.40.243.200/soidutaja_php/");
        p.setParam("locations", "yes");
        DownloadData downloadData = new DownloadData();
        downloadData.execute(p);

    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        callbackManager=CallbackManager.Factory.create();
        loginButton= (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile", "email","user_friends");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.registerCallback(callbackManager, mCallBack);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(isLoggedIn()) {
            profileBtn.setVisibility(View.VISIBLE);
        }
        else {
            profileBtn.setVisibility(View.INVISIBLE);
        }
    }


    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            final GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            Log.e("response: ", response + "");
                            try {
                                user.setFacebookID(object.getString("id").toString());
                                user.setEmail(object.getString("email").toString());
                                user.setName(object.getString("name").toString());
                                user.setGender(object.getString("gender").toString());

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            Toast.makeText(SelectRoleActivity.this,"welcome " + user.getName(), Toast.LENGTH_LONG).show();
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, name, email, gender");
            Log.d("logi", parameters.toString());
            request.setParameters(parameters);
            Log.d("logi", request.toString());
            request.executeAsync();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    public void addButton() {
        passengerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(SelectRoleActivity.this, SelectLocationsActivity.class);
                    intent.putExtra("loc", (ArrayList<String>) locations);
                    startActivity(intent);
                } else {
                    Toast.makeText(SelectRoleActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        driverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRoleActivity.this, MakeDriveActivity.class);
                intent.putExtra("loc", (ArrayList<String>) locations);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DownloadData extends AsyncTask<RequestPackage, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Log.d("lammas", "somthing went wrong");
            } else {
                Log.d("lammas", result);
                locations = JSONParser.parseLocations(result);
                addButton();
            }
        }
    }

    private class Task extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
            if (result == null) {
                Log.d("lammas", "somthing went wrong");
            } else {
                Log.d("lammas", result);
                drives = JSONParser.parseDriveObjects(result);
                Intent intent = new Intent(SelectRoleActivity.this, ProfileViewActivity.class);
                intent.putParcelableArrayListExtra("driverList", (ArrayList<? extends Parcelable>) drives);
                intent.putParcelableArrayListExtra("passengerList", (ArrayList<? extends Parcelable>) drives);
                startActivity(intent);
            }
        }
    }
}
