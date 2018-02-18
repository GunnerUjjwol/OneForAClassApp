package com.example.ujjwol.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ujjwol.myapplication.LoginActivity;
import com.example.ujjwol.myapplication.R;
import com.example.ujjwol.myapplication.SharedPrefManager;


public class ProfileFragment extends Fragment {
    private TextView currentUserName;
    private TextView currentUserEmail;
    private TextView currentUserRoll;
    private TextView currentUserPhone;
    private Button LogoutButton;


    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {

    }


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        currentUserName=view.findViewById(R.id.currentUserName);
        currentUserEmail=view.findViewById(R.id.currentUserEmail);
        currentUserRoll=view.findViewById(R.id.currentUserRoll);
        currentUserPhone=view.findViewById(R.id.currentUserPhone);
        LogoutButton=view.findViewById(R.id.btn_logout);

        currentUserName.setText(SharedPrefManager.getInstance(getActivity()).getUsername());
        currentUserEmail.setText(SharedPrefManager.getInstance(getActivity()).getUserEmail());
        currentUserRoll.setText(SharedPrefManager.getInstance(getActivity()).getUserRollno());

        LogoutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getActivity()).logout();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;

    }





    @Override
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
    }

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
