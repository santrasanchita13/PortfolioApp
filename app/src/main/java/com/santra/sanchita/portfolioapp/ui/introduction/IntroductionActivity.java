package com.santra.sanchita.portfolioapp.ui.introduction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.ui.base.BaseActivity;
import com.santra.sanchita.portfolioapp.utils.Constants;
import com.santra.sanchita.portfolioapp.utils.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sanchita on 25/12/17.
 */

public class IntroductionActivity extends BaseActivity implements IntroductionMvpView {

    @Inject
    IntroductionMvpPresenter<IntroductionMvpView> presenter;

    @BindView(R.id.coordinator_layout_introduction)
    ViewGroup container;

    @BindView(R.id.recycler_view_introduction)
    RecyclerView recyclerViewIntroduction;

    @BindView(R.id.rel_layout_introduction)
    RelativeLayout relLayoutIntroduction;

    @BindView(R.id.appbar_introduction)
    AppBarLayout appbarIntroduction;

    @BindView(R.id.text_phone_introduction)
    TextView textPhoneIntroduction;

    @BindView(R.id.text_email_introduction)
    TextView textEmailIntroduction;

    @BindView(R.id.text_info_introduction)
    TextView textInfoIntroduction;

    List<IntroductionItem> listIntroduction;

    IntroductionAdapter introductionAdapter;

    private GoogleSignInClient mGoogleSignInClient;

    private long callContactId = 0;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, IntroductionActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        supportPostponeEnterTransition();

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        if (savedInstanceState != null) {
            animateEnterTransitions();
        }

        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();

        hideActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(callContactId != 0) {
            List<String> permissionList = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.CALL_PHONE);
                }
                if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.READ_CONTACTS);
                }
                if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.WRITE_CONTACTS);
                }
                if (checkSelfPermission(Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.WRITE_CALL_LOG);
                }
                if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.READ_CALL_LOG);
                }
                if (permissionList.size() > 0) {
                    requestPermissions(permissionList.toArray(new String[permissionList.size()]), Constants.DELETE_CONTACT_PERMISSION);
                    permissionList.clear();
                } else {
                    deleteContact(Constants.MY_PHONE_NUMBER, Constants.MY_NAME);
                }
            } else {
                deleteContact(Constants.MY_PHONE_NUMBER, Constants.MY_NAME);
            }
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();

        super.onDestroy();
    }

    @Override
    protected void setUp() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope("https://mail.google.com/"))
                .requestServerAuthCode(getString(R.string.default_web_client_id), true)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        textInfoIntroduction.setOnClickListener(v -> showPopUpExplanation(getString(R.string.introduction_activity_info), Gravity.BOTTOM));

        appbarIntroduction.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float percentage = ((float) Math.abs(verticalOffset) / (float) appBarLayout.getTotalScrollRange());
            relLayoutIntroduction.setAlpha(1f - percentage);

            if (percentage == 1f) {
                textPhoneIntroduction.setVisibility(View.VISIBLE);
                textPhoneIntroduction.animate().alpha(1f).setDuration(Constants.FADE_DEFAULT_TIME).start();
                textEmailIntroduction.setVisibility(View.VISIBLE);
                textEmailIntroduction.animate().alpha(1f).setDuration(Constants.FADE_DEFAULT_TIME).start();
            } else {
                textPhoneIntroduction.setVisibility(View.GONE);
                textEmailIntroduction.setVisibility(View.GONE);
                textPhoneIntroduction.setAlpha(0f);
                textEmailIntroduction.setAlpha(0f);
            }
        });

        textPhoneIntroduction.setOnClickListener(v -> {

            List<String> permissionList = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.CALL_PHONE);
                }
                if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.READ_CONTACTS);
                }
                if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.WRITE_CONTACTS);
                }
                if (checkSelfPermission(Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.WRITE_CALL_LOG);
                }
                if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(android.Manifest.permission.READ_CALL_LOG);
                }
                if (permissionList.size() > 0) {
                    requestPermissions(permissionList.toArray(new String[permissionList.size()]), Constants.CALL_PERMISSION);
                    permissionList.clear();
                }
                else {
                    callMe();
                }
            }
            else {
                callMe();
            }
        });

        textEmailIntroduction.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_STORAGE_PERMISSION);
                }
            }
            else {
                checkSignIn();
            }
        });

        listIntroduction = new ArrayList<>();
        listIntroduction.add(new IntroductionItem(VIEW_TYPE_PROFILE, "Profile"));
        listIntroduction.add(new IntroductionItem(VIEW_TYPE_ABOUT, "Honesty", 0.99f, "Being true to myself is the most" +
                " important thing in my life because 70 years is too short a time to survive with a guilty conscience. " +
                "Those who show themselves to be perfect are often the ones who deliver less. That's the reason I do " +
                "extensive planning before promising a result."));
        listIntroduction.add(new IntroductionItem(VIEW_TYPE_ABOUT, "Creativity", 0.95f, "Every mind has something unique" +
                " to offer, but it's human nature to discard anything too different from their mindset. " +
                "Creativists mix that uniqueness with what majority considers normal and give an acceptable new product. " +
                "I consider myself such an artist."));
        listIntroduction.add(new IntroductionItem(VIEW_TYPE_ABOUT, "Knowledge", 0.90f, "Obviously, I have even less " +
                "than 1% knowledge of what is learnable in this world. So, the percentage is based on how much I have gathered vs how much" +
                " I could have gathered until now. I have what I like to call 'Da Vinci' syndrome. There are way too many areas I become" +
                " interested in and start learning about. I have maximum expertise in software development though and can be trusted" +
                " to make efficient use of every development tool available to give results."));
        listIntroduction.add(new IntroductionItem(VIEW_TYPE_ABOUT, "Intellect", 0.85f, "My IQ is above average as" +
                " I've understood from various tests, albeit unofficial. The percentage is based on the average of my scores during my education."));
        listIntroduction.add(new IntroductionItem(VIEW_TYPE_ABOUT, "Courage", 0.80f, "Courage is not the lack of fear." +
                " I fear a lot of things. Putting my real self out here for you to judge and criticize is one of my biggest fears." +
                " But acting despite fear is what I consider to be courage. So, I'm doing it anyway. Knowing whom you will trust to" +
                " turn your dream into reality is very important."));
        listIntroduction.add(new IntroductionItem(VIEW_TYPE_ABOUT, "Perseverance", 0.75f, "Patience is a quality I used" +
                " to lack for a very long time. The low perseverance meter has lead to a few of my major failures in life." +
                " I finally learned it during my corporate work years. Completing 45 hours of work a week for 2 years taught me how" +
                " to shut down the voice telling me to quit. Now, I'm able to complete most of the work I undertake."));
        listIntroduction.add(new IntroductionItem(VIEW_TYPE_ABOUT, "Sociability", 0.70f, "Mostly, I'm a lone-wolf." +
                " I've always preferred observation as opposed to conversation. In recent years, though, I've realized the greatest " +
                "creations of man are made in collaboration. It's that realization which has given me the patience to accept collaborations" +
                " if necessary."));

        if(introductionAdapter == null) {
            introductionAdapter = new IntroductionAdapter(IntroductionActivity.this, listIntroduction, container);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(IntroductionActivity.this);
            recyclerViewIntroduction.setLayoutManager(mLayoutManager);
            recyclerViewIntroduction.setAdapter(introductionAdapter);
            introductionAdapter.notifyDataSetChanged();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition enterTransition = getWindow().getSharedElementEnterTransition();
            enterTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    transition.removeListener(this);
                    animateEnterTransitions();
                    setupExitTransitions();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
        else {
            animateEnterTransitions();
        }

        recyclerViewIntroduction.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

                supportStartPostponedEnterTransition();

                recyclerViewIntroduction.clearOnChildAttachStateChangeListeners();
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {

            }
        });
    }

    public void animateEnterTransitions() {
        if(introductionAdapter != null && introductionAdapter.profileViewHolder != null) {
            introductionAdapter.profileViewHolder.imageBgItemProfileIntroduction.animate().rotation(-7).setDuration(Constants.MOVE_DEFAULT_TIME).start();
        }
    }

    public void animateExitTransitions() {
        if(introductionAdapter != null && introductionAdapter.profileViewHolder != null) {
            introductionAdapter.profileViewHolder.imageBgItemProfileIntroduction.animate().rotation(0).setDuration(Constants.MOVE_DEFAULT_TIME).start();
        }
    }

    public void setupExitTransitions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //TODO: Not working. Google bug
            //getWindow().getSharedElementReturnTransition().setStartDelay(Constants.MOVE_DEFAULT_TIME + Constants.MOVE_DELAY_TIME);

            getWindow().getReturnTransition().setDuration(Constants.MOVE_DEFAULT_TIME).addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    transition.removeListener(this);
                    animateExitTransitions();
                }

                @Override
                public void onTransitionEnd(Transition transition) {

                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == Constants.CALL_PERMISSION) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                                callMe();
                            }
                        }
                    }
                }
            }
        }
        else if(requestCode == Constants.DELETE_CONTACT_PERMISSION) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                                deleteContact(Constants.MY_PHONE_NUMBER, Constants.MY_NAME);
                            }
                        }
                    }
                }
            }
        }
        else if(requestCode == Constants.WRITE_EXTERNAL_STORAGE_PERMISSION) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                checkSignIn();
            }
        }
    }

    @Override
    public void updateDetails(DesignItem designItem) {
        if(introductionAdapter != null) {
            introductionAdapter.profileViewHolder.imageItemProfileIntroduction.setBackground(ContextCompat.getDrawable(this, Integer.parseInt(designItem.getImagePath())));
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void callMe() {
        ArrayList<ContentProviderOperation> ops =
                new ArrayList<>();

        int rawContactID = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, Constants.MY_PHONE_NUMBER)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, "1").build());

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, Constants.MY_NAME)
                .build());

        try {
            ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);

            callContactId = ContentUris.parseId(res[0].uri);

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + Constants.MY_PHONE_NUMBER));
            startActivity(intent);
        } catch (OperationApplicationException|RemoteException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public boolean deleteContact(String phone, String name) {

        String queryString = "NUMBER='" + Constants.MY_PHONE_NUMBER + "'";
        getContentResolver().delete(CallLog.Calls.CONTENT_URI, queryString, null);

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        ops.add(ContentProviderOperation
                .newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = ?",
                        new String[] {callContactId + ""})
                .build());
        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
            ops.clear();
            return true;
        } catch (OperationApplicationException|RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void checkSignIn() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null) {
            if(mGoogleSignInClient != null) {
                mGoogleSignInClient.signOut().addOnCompleteListener(task -> signIn());
            }
        }
        else {
            showPopUpEmail(account);
        }
    }

    @Override
    public void showPopUpEmail(GoogleSignInAccount account) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        if(inflater != null) {
            View popupView = inflater.inflate(R.layout.pop_up_email_introduction, null);

            final EditText editText = popupView.findViewById(R.id.edit_text_pop_up_introduction);
            TextView sendButton = popupView.findViewById(R.id.text_send_pop_up_introduction);

            // create the popup window
            int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            popupWindow.setAnimationStyle(R.style.SlideAnimation);

            // show the popup window
            popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);

            sendButton.setOnClickListener(v -> {

                if (!editText.getText().toString().isEmpty() && editText.getText().toString().length() > 50) {
                    presenter.sendEmail(account, editText.getText().toString());
                    popupWindow.dismiss();
                } else {
                    if (popupWindow.isShowing() && !isFinishing()) {
                        showMessage("Why such a short message?");
                    }
                }
            });
        }
    }

    @Override
    public void emailSent(boolean sent) {
        if(sent) {
            showMessage("I'll reply soon!!");
        }
        else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_STORAGE_PERMISSION);
                }
            }
            else {
                showMessage("Email failed");
            }
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.REQUEST_CODE_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account.getAccount() != null) {

                SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF_AUTH_CODE, Context.MODE_PRIVATE);
                sp.edit().putString(Constants.AUTH_CODE, account.getServerAuthCode()).commit();

                showPopUpEmail(account);
            }
        } catch(ApiException apiException) {
            if(mGoogleSignInClient != null) {
                mGoogleSignInClient.signOut();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.REQUEST_CODE_SIGN_IN) {
            if(resultCode == RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}