package com.santra.sanchita.portfolioapp.ui.contact;

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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.ActivityCompat;
import android.widget.EditText;
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
import butterknife.OnClick;

/**
 * Created by sanchita on 25/1/18.
 */

public class ContactActivity extends BaseActivity implements ContactMvpView {

    @Inject
    ContactMvpPresenter<ContactMvpView> presenter;

    @BindView(R.id.edit_text_contact)
    EditText editText;

    @BindView(R.id.text_contact_topic)
    TextView textContactTopic;

    private long callContactId = 0;

    private GoogleSignInClient mGoogleSignInClient;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ContactActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

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

        Bundle extras = getIntent().getExtras();

        presenter.onViewPrepared(extras);
    }

    @OnClick(R.id.button_call_contact)
    public void onCallClick() {
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
    }

    @OnClick(R.id.button_send_contact)
    public void onSendClick() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_STORAGE_PERMISSION);
            }
        }
        else {
            checkSignIn();
        }
    }

    @Override
    public void updateDetails(DesignItem designItem) {
        //textContactTopic.setText(designItem.getDesignItemName());
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
        } catch (OperationApplicationException |RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteContact(String phone, String name) {

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == Constants.CALL_PERMISSION) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        callMe();
                    }
                }
            }
        }
        else if(requestCode == Constants.DELETE_CONTACT_PERMISSION) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        deleteContact(Constants.MY_PHONE_NUMBER, Constants.MY_NAME);
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
    public void checkSignIn() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null) {
            if(mGoogleSignInClient != null) {
                mGoogleSignInClient.signOut().addOnCompleteListener(task -> signIn());
            }
        }
        else {
            if(!editText.getText().toString().isEmpty() && editText.getText().toString().length() > 50) {
                presenter.sendEmail(account, editText.getText().toString());
            }
            else {
                editText.setError("Why such a short message?");
                editText.requestFocus();
            }
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

                if(!editText.getText().toString().isEmpty() && editText.getText().toString().length() > 50) {
                    presenter.sendEmail(account, editText.getText().toString());
                }
                else {
                    editText.setError("Why such a short message?");
                    editText.requestFocus();
                }
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