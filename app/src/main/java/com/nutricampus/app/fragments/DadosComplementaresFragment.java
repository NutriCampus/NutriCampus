package com.nutricampus.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nutricampus.app.R;


/**
 * Created by Felipe on 19/07/2017.
 */

public class DadosComplementaresFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private String mParam1;

    public static DadosComplementaresFragment newInstance(String param1) {
        DadosComplementaresFragment fragment = new DadosComplementaresFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Bundle params = getArguments();
        //String texto1 = params.getString(ARG_PARAM1);

        View layout = inflater.inflate(R.layout.fragment_dados_complementares, container, false);

        //TextView txt1 = (TextView) layout.findViewById(R.id.textView1);
        //txt1.setText(texto1);

        return layout;

        //return inflater.inflate(R.layout.fragment_dados_complementares, container, false);
    }

}
