package com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.di.component.ActivityComponent;
import com.santra.sanchita.portfolioapp.ui.base.BaseFragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.CheckoutFormMvpView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sanchita on 19/12/17.
 */

public class CheckoutForm1Fragment extends BaseFragment implements CheckoutForm1MvpView {

    @Inject
    CheckoutForm1MvpPresenter<CheckoutForm1MvpView> presenter;

    @BindView(R.id.edit_name_checkout_form1)
    EditText editNameCheckoutForm1;

    @BindView(R.id.text_name_checkout_form1)
    TextView textNameCheckoutForm1;

    @BindView(R.id.edit_street_checkout_form1)
    EditText editStreetCheckoutForm1;

    @BindView(R.id.text_street_checkout_form1)
    TextView textStreetCheckoutForm1;

    @BindView(R.id.edit_address_checkout_form1)
    EditText editAddressCheckoutForm1;

    @BindView(R.id.text_address_checkout_form1)
    TextView textAddressCheckoutForm1;

    @BindView(R.id.edit_pin_checkout_form1)
    EditText editPinCheckoutForm1;

    @BindView(R.id.text_pin_checkout_form1)
    TextView textPinCheckoutForm1;

    @BindView(R.id.edit_country_checkout_form1)
    EditText editCountryCheckoutForm1;

    @BindView(R.id.text_country_checkout_form1)
    TextView textCountryCheckoutForm1;

    @BindView(R.id.edit_state_checkout_form1)
    EditText editStateCheckoutForm1;

    @BindView(R.id.text_state_checkout_form1)
    TextView textStateCheckoutForm1;

    @BindView(R.id.edit_city_checkout_form1)
    EditText editCityCheckoutForm1;

    @BindView(R.id.text_city_checkout_form1)
    TextView textCityCheckoutForm1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_form1, container, false);

        ActivityComponent component = getActivityComponent();

        if(component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        setOnEditTextFocusChanged(editNameCheckoutForm1, textNameCheckoutForm1);
        setOnEditTextFocusChanged(editStreetCheckoutForm1, textStreetCheckoutForm1);
        setOnEditTextFocusChanged(editAddressCheckoutForm1, textAddressCheckoutForm1);
        setOnEditTextFocusChanged(editPinCheckoutForm1, textPinCheckoutForm1);
        setOnEditTextFocusChanged(editCountryCheckoutForm1, textCountryCheckoutForm1);
        setOnEditTextFocusChanged(editStateCheckoutForm1, textStateCheckoutForm1);
        setOnEditTextFocusChanged(editCityCheckoutForm1, textCityCheckoutForm1);

        editCityCheckoutForm1.setOnEditorActionListener((v, actionId, event) -> {
            // If triggered by an enter key, this is the event; otherwise, this is null.
            if (event != null) {
                // if shift key is down, then we want to insert the '\n' char in the TextView;
                // otherwise, the default action is to send the message.
                if (!event.isShiftPressed()) {
                    if(getActivity() instanceof CheckoutFormMvpView) {
                        ((CheckoutFormMvpView) getActivity()).gotoForm2();
                    }
                    return true;
                }
                return false;
            }

            if(getActivity() instanceof CheckoutFormMvpView) {
                ((CheckoutFormMvpView) getActivity()).gotoForm2();
            }
            return true;
        });
    }

    @OnClick(R.id.button_next_step_checkout_form1)
    void onProceedButtonClick() {
        if(getActivity() instanceof CheckoutFormMvpView) {
            ((CheckoutFormMvpView) getActivity()).gotoForm2();
        }
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

        if(editNameCheckoutForm1.getText() == null || editNameCheckoutForm1.getText().toString().isEmpty()) {
            editNameCheckoutForm1.setError("How will we deliver without your name?");
            editNameCheckoutForm1.requestFocus();
            i++;
        }
        else if(editStreetCheckoutForm1.getText() == null || editStreetCheckoutForm1.getText().toString().isEmpty()) {
            editStreetCheckoutForm1.setError("We need your street address to deliver");
            editStreetCheckoutForm1.requestFocus();
            i++;
        }
        else if(editPinCheckoutForm1.getText() == null || editPinCheckoutForm1.getText().toString().isEmpty()) {
            editPinCheckoutForm1.setError("We can't proceed without the pincode");
            editPinCheckoutForm1.requestFocus();
            i++;
        }
        else if(editPinCheckoutForm1.getText().toString().length() < 6) {
            editPinCheckoutForm1.setError("Please enter a valid 6 digits pincode");
            editPinCheckoutForm1.requestFocus();
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
