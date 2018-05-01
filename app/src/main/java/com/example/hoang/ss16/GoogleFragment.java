package com.example.hoang.ss16;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleFragment extends Fragment {

    private static final String TAG = "GoogleFragment";
    @BindView(R.id.bt_log_out)
    Button btLogOut;
    GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    Context context;

    @BindView(R.id.tv_gg_name)
    TextView tvGgName;
    @BindView(R.id.tv_gg_mail)
    TextView tvGgMail;
    @BindView(R.id.iv_google)
    ImageView ivGoogle;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    Unbinder unbinder;

    public GoogleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google, container, false);
        context = getContext();
        unbinder = ButterKnife.bind(this, view);

        // Inflate the layout for this fragment

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }

            }
        });


        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        updateUI(account);


    }

    private void updateUI(GoogleSignInAccount account) {

    }


    //function SignIn
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            btLogOut.setVisibility(View.VISIBLE);
            tvGgName.setVisibility(View.VISIBLE);
            tvGgMail.setVisibility(View.VISIBLE);
            ivGoogle.setVisibility(View.VISIBLE);
            tvGgName.setText(personName);
            tvGgMail.setText("Private Email");
            Picasso.get().load(acct.getPhotoUrl()).into(ivGoogle);
            Log.d(TAG, "onStart: image " + acct.getPhotoUrl());
        }
    }

    public void loaddata() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_log_out)
    public void onViewClicked() {
        signOut();
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        btLogOut.setVisibility(View.GONE);
                        tvGgName.setVisibility(View.GONE);
                        tvGgMail.setVisibility(View.GONE);
                        ivGoogle.setVisibility(View.GONE);
                    }
                });
    }

}
