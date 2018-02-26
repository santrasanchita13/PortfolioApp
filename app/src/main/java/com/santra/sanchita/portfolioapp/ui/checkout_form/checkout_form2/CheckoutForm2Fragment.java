package com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form2;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.di.component.ActivityComponent;
import com.santra.sanchita.portfolioapp.ui.base.BaseFragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.CheckoutFormMvpView;
import com.santra.sanchita.portfolioapp.ui.custom_views.AutoAddTextWatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sanchita on 19/12/17.
 */

public class CheckoutForm2Fragment extends BaseFragment implements CheckoutForm2MvpView {

    @Inject
    CheckoutForm2MvpPresenter<CheckoutForm2MvpView> presenter;

    @BindView(R.id.spinner_month_checkout_form2)
    Spinner spinnerMonthCheckoutForm2;

    @BindView(R.id.spinner_year_checkout_form2)
    Spinner spinnerYearCheckoutForm2;

    @BindView(R.id.edit_name_checkout_form2)
    EditText editNameCheckoutForm2;

    @BindView(R.id.text_name_checkout_form2)
    TextView textNameCheckoutForm2;

    @BindView(R.id.edit_card_checkout_form2)
    EditText editCardCheckoutForm2;

    @BindView(R.id.text_card_checkout_form2)
    TextView textCardCheckoutForm2;

    @BindView(R.id.text_card_visa_checkout_form2)
    TextView textCardVisaCheckoutForm2;

    @BindView(R.id.edit_cvv_checkout_form2)
    EditText editCvvCheckoutForm2;

    @BindView(R.id.text_cvv_checkout_form2)
    TextView textCvvCheckoutForm2;

    IBinder windowToken;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_form2, container, false);

        ActivityComponent component = getActivityComponent();

        if(component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        return view;
    }

    @OnClick(R.id.button_next_step_checkout_form2)
    void onProceedClick() {
        if(getActivity() instanceof CheckoutFormMvpView) {
            ((CheckoutFormMvpView) getActivity()).gotoForm3();
        }
    }

    @Override
    protected void setUp(View view) {

        windowToken = view.getWindowToken();

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(getBaseActivity(),
                R.array.month_array, R.layout.spinner_item_checkout_form2);
        monthAdapter.setDropDownViewResource(R.layout.spinner_item_drop_down_checkout_form2);
        spinnerMonthCheckoutForm2.setAdapter(monthAdapter);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(getBaseActivity(),
                R.array.year_array, R.layout.spinner_item_checkout_form2);
        yearAdapter.setDropDownViewResource(R.layout.spinner_item_drop_down_checkout_form2);
        spinnerYearCheckoutForm2.setAdapter(yearAdapter);

        setOnEditTextFocusChanged(editNameCheckoutForm2, textNameCheckoutForm2);
        setOnEditTextFocusChanged(editCardCheckoutForm2, textCardCheckoutForm2);
        setOnEditTextFocusChanged(editCvvCheckoutForm2, textCvvCheckoutForm2);

        editCvvCheckoutForm2.setOnEditorActionListener((v, actionId, event) -> {
            // If triggered by an enter key, this is the event; otherwise, this is null.
            if (event != null) {
                // if shift key is down, then we want to insert the '\n' char in the TextView;
                // otherwise, the default action is to send the message.
                if (!event.isShiftPressed()) {
                    if(getActivity() instanceof CheckoutFormMvpView) {
                        ((CheckoutFormMvpView) getActivity()).gotoForm3();
                    }
                    return true;
                }
                return false;
            }

            if(getActivity() instanceof CheckoutFormMvpView) {
                ((CheckoutFormMvpView) getActivity()).gotoForm3();
            }
            return true;
        });

        editCardCheckoutForm2.addTextChangedListener(new AutoAddTextWatcher(editCardCheckoutForm2, "-", new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null && s.length() > 0) {
                    if (s.charAt(0) == '4') {
                        textCardVisaCheckoutForm2.setVisibility(View.VISIBLE);
                        textCardVisaCheckoutForm2.setText(getString(R.string.fa_visa));
                        textCardVisaCheckoutForm2.setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.colorPrimary));
                    } else {
                        textCardVisaCheckoutForm2.setVisibility(View.VISIBLE);
                        textCardVisaCheckoutForm2.setText(getString(R.string.fa_master_card));
                        textCardVisaCheckoutForm2.setTextColor(ContextCompat.getColor(getBaseActivity(), android.R.color.holo_orange_dark));
                    }
                }
                else {
                    textCardVisaCheckoutForm2.setVisibility(View.GONE);
                }
            }
        }, 4, 8, 12));
    }

    public void setOnEditTextFocusChanged(EditText editText, TextView textView) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                textView.setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.colorPrimary));
            }
            else {
                if (editText.getText() != null && !editText.getText().toString().isEmpty()) {
                    textView.setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.colorPrimary));
                } else {
                    textView.setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.grey));
                }
            }
        });
    }

    @Override
    public boolean showErrors() {
        int i = 0;

        if(editNameCheckoutForm2.getText() == null || editNameCheckoutForm2.getText().toString().isEmpty()) {
            editNameCheckoutForm2.setError("Every card detail is mandatory");
            editNameCheckoutForm2.requestFocus();
            i++;
        }
        else if(editCardCheckoutForm2.getText() == null || editCardCheckoutForm2.getText().toString().isEmpty()) {
            editCardCheckoutForm2.setError("Every card detail is mandatory");
            editCardCheckoutForm2.requestFocus();
            i++;
        }
        else if(editCardCheckoutForm2.getText().toString().length() < 16) {
            editCardCheckoutForm2.setError("We don't recognize your card");
            editCardCheckoutForm2.requestFocus();
            i++;
        }
        else if(spinnerMonthCheckoutForm2.getSelectedItem().toString().equals("Expiry month")) {
            InputMethodManager imm = (InputMethodManager)getBaseActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(imm != null) {
                imm.hideSoftInputFromWindow(windowToken, 0);
                onError("Please select the expiry month for your card");
            }
            i++;
        }
        else if(spinnerYearCheckoutForm2.getSelectedItem().toString().equals("Expiry year")) {
            InputMethodManager imm = (InputMethodManager)getBaseActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(imm != null) {
                imm.hideSoftInputFromWindow(windowToken, 0);
                onError("Please select the expiry year for your card");
            }
            i++;
        }
        else if(editCvvCheckoutForm2.getText() == null || editCvvCheckoutForm2.getText().toString().isEmpty()) {
            editCvvCheckoutForm2.setError("See the image below to find your CVV");
            editCvvCheckoutForm2.requestFocus();
            i++;
        }
        else if(editCvvCheckoutForm2.getText().toString().length() < 3) {
            editCvvCheckoutForm2.setError("There must be 3 digits to your CVV. Please check again!");
            editCvvCheckoutForm2.requestFocus();
            i++;
        }

        return (i != 0);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
