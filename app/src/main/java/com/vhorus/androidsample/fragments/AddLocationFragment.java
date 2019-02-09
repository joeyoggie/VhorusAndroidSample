package com.vhorus.androidsample.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.vhorus.androidsample.MyLocation;
import com.vhorus.androidsample.MySettings;
import com.vhorus.androidsample.R;
import com.vhorus.androidsample.Utils;
import com.vhorus.androidsample.activities.MainActivity;
import com.vhorus.androidsample.entities.Location;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddLocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddLocationFragment extends Fragment {
    private static final String TAG = AddLocationFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    private static final int RC_PERMISSION_LOCATION = 1004;
    private static final int RC_ACTIVITY_LOCATION_TURN_ON = 1008;

    EditText currentLatitudeEditText, currentLongitudeEditText, currentAltitudeEditText, currentTimestampEditText;
    Button saveButton, cancelButton;

    double currentLongitude, currentLatitude, currentAltitude;
    long currentTimestamp;

    public AddLocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddLocationFragment.
     */
    public static AddLocationFragment newInstance(String param1, String param2) {
        AddLocationFragment fragment = new AddLocationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_location, container, false);
        MainActivity.setActionBarTitle(Utils.getString(getActivity(), R.string.add_location), getResources().getColor(R.color.whiteColor));

        currentLongitudeEditText = view.findViewById(R.id.current_longitude_edittext);
        currentLatitudeEditText = view.findViewById(R.id.current_latitude_edittext);
        currentAltitudeEditText = view.findViewById(R.id.current_altitude_edittext);
        currentTimestampEditText = view.findViewById(R.id.current_timestamp_edittext);

        saveButton = view.findViewById(R.id.save_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        checkLocationPermissions();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction = Utils.setAnimations(fragmentTransaction, Utils.ANIMATION_TYPE_FADE);
                    LocationsFragment locationsFragment = new LocationsFragment();
                    fragmentTransaction.replace(R.id.fragment_view, locationsFragment, "locationsFragment");
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.validateInputs(currentLongitudeEditText, currentLatitudeEditText, currentAltitudeEditText, currentTimestampEditText)){
                    final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(getActivity()).create();
                    LinearLayout layout = new LinearLayout(getActivity());
                    layout.setOrientation(LinearLayout.VERTICAL);

                    dialog.setTitle(Utils.getString(getActivity(), R.string.location_name));

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight = 1.0f;
                    Resources r = getActivity().getResources();
                    float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());
                    float pxRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());
                    float pxTopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());
                    float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());
                    layoutParams.setMargins(Math.round(pxLeftMargin), Math.round(pxTopMargin), Math.round(pxRightMargin), Math.round(pxBottomMargin));
                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

                    TextView titleTextView = new TextView(getActivity());
                    titleTextView.setText(Utils.getString(getActivity(), R.string.location_name_message));
                    titleTextView.setTextSize(20);
                    titleTextView.setGravity(Gravity.CENTER);
                    titleTextView.setLayoutParams(layoutParams);

                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight = 1.0f;
                    Resources r2 = getActivity().getResources();
                    float pxLeftMargin2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r2.getDisplayMetrics());
                    float pxRightMargin2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r2.getDisplayMetrics());
                    float pxTopMargin2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r2.getDisplayMetrics());
                    float pxBottomMargin2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, r2.getDisplayMetrics());
                    layoutParams2.setMargins(Math.round(pxLeftMargin2), Math.round(pxTopMargin2), Math.round(pxRightMargin2), Math.round(pxBottomMargin2));
                    layoutParams2.gravity = Gravity.CENTER_HORIZONTAL;

                    final EditText titleEditText = new EditText(getActivity());
                    titleEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    titleEditText.setHint(Utils.getString(getActivity(), R.string.app_name));
                    titleEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    titleEditText.setLayoutParams(layoutParams2);

                    Button okButton = new Button(getActivity());
                    okButton.setText(Utils.getString(getActivity(), R.string.ok));
                    okButton.setTextColor(getActivity().getResources().getColor(R.color.whiteColor));
                    okButton.setBackgroundColor(getActivity().getResources().getColor(R.color.blueColor));
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(titleEditText.getText().toString() != null && titleEditText.getText().toString().length() >= 4) {
                                Location location = new Location();
                                location.setLongitude(currentLongitude);
                                location.setLatitude(currentLatitude);
                                location.setAltitude(currentAltitude);
                                location.setTimestamp(currentTimestamp);
                                location.setTitle(titleEditText.getText().toString());

                                MySettings.addLocation(location);

                                dialog.dismiss();

                                FragmentManager fragmentManager = getFragmentManager();
                                if(fragmentManager != null) {
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction = Utils.setAnimations(fragmentTransaction, Utils.ANIMATION_TYPE_FADE);
                                    LocationsFragment locationsFragment = new LocationsFragment();
                                    fragmentTransaction.replace(R.id.fragment_view, locationsFragment, "locationsFragment");
                                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    fragmentTransaction.commitAllowingStateLoss();
                                }
                            }else{
                                YoYo.with(Techniques.Shake)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(titleEditText);
                            }
                        }
                    });

                    layout.addView(titleTextView);
                    layout.addView(titleEditText);
                    layout.addView(okButton);

                    dialog.setView(layout);

                    dialog.show();
                }
            }
        });

        return view;
    }

    private void getCurrentLocation(){
        if(checkLocationServices()){
            Utils.showLoading(getActivity());
            new MyLocation().getLocation(getActivity(), new MyLocation.LocationResult() {
                @Override
                public void gotLocation(android.location.Location location) {
                    currentLongitude = location.getLongitude();
                    currentLatitude = location.getLatitude();
                    currentAltitude = location.getAltitude();
                    currentLongitudeEditText.setText(""+currentLongitude);
                    currentLatitudeEditText.setText(""+currentLatitude);
                    currentAltitudeEditText.setText(""+currentAltitude);

                    currentTimestamp = new Date().getTime();
                    currentTimestampEditText.setText(Utils.getTimeStringDateHoursMinutes(currentTimestamp));

                    Utils.dismissLoading();
                }
            });
        }
    }

    private void checkLocationPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                getCurrentLocation();
            }else{
                requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, RC_PERMISSION_LOCATION);
            }
        }else{
            //no need to show runtime permission stuff
            getCurrentLocation();
        }
    }

    private boolean checkLocationServices(){
        boolean enabled = true;
        if(getActivity() != null && getActivity().getSystemService(Context.LOCATION_SERVICE) != null){
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsProviderEnabled, isNetworkProviderEnabled;
            isGpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGpsProviderEnabled && !isNetworkProviderEnabled) {
                enabled = false;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(Utils.getString(getActivity(), R.string.location_required_title));
                builder.setMessage(Utils.getString(getActivity(), R.string.location_required_message));
                builder.setPositiveButton(Utils.getString(getActivity(), R.string.go_to_location_settings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, RC_ACTIVITY_LOCATION_TURN_ON);
                    }
                });
                builder.setNegativeButton(Utils.getString(getActivity(), R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FragmentManager fragmentManager = getFragmentManager();
                        if(fragmentManager != null) {
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction = Utils.setAnimations(fragmentTransaction, Utils.ANIMATION_TYPE_FADE);
                            LocationsFragment locationsFragment = new LocationsFragment();
                            fragmentTransaction.replace(R.id.fragment_view, locationsFragment, "locationsFragment");
                            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentTransaction.commitAllowingStateLoss();
                        }
                    }
                });
                builder.show();
            }
        }
        return enabled;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_ACTIVITY_LOCATION_TURN_ON){
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode){
            case RC_PERMISSION_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if(grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //allowed
                    getCurrentLocation();
                }
                else{
                    //denied
                    Utils.showToast(getActivity(), "You need to enable location permission", true);
                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale("android.permission.ACCESS_FINE_LOCATION")) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Location permission")
                                .setMessage("You need to enable location permissions for the app to detect nearby devices")
                                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, RC_PERMISSION_LOCATION);
                                    }
                                })
                                .show();
                    }
                }
            }
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
