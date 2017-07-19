package com.nutricampus.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nutricampus.app.R;

//import com.nutricampus.app.R;

/**
 * Created by Felipe on 19/07/2017.
 */

public class ConteudoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    String parametro1;
    //String parametro2;

    public static ConteudoFragment newInstance(String param1) {
        ConteudoFragment fragment = new ConteudoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parametro1= getArguments().getString(ARG_PARAM1);
            //parametro2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle params = getArguments();
        String texto1 = params.getString(ARG_PARAM1);
        //String texto2 = params.getString(ARG_PARAM2);

        View layout = inflater.inflate(R.layout.fragment_conteudo, container, false);

        TextView txt1 = (TextView) layout.findViewById(R.id.textView1);
        txt1.setText(texto1);

        //TextView txt2 = (TextView) layout.findViewById(R.id.textView2);
        //txt2.setText(texto2);

        return layout;

    }

}
