package com.example.nomad.curpgen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Spinner spn_states;
    EditText    txt_paternal,
                txt_maternal,
                txt_name,
                txt_bdate;
    RadioButton rb_male,
                rb_female;
    Button      btn_generate;

    TextView    tv_lbl_curp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spn_states = findViewById(R.id.spn_states);
        //  we get the array from the xml
        ArrayAdapter adapterarray = ArrayAdapter.createFromResource(this,R.array.states,android.R.layout.simple_spinner_item);
        //  prepear the array so it is assigned to the spinner with java
        adapterarray.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //  we assigned the array to the spinner
        spn_states.setAdapter(adapterarray);

        txt_paternal = findViewById(R.id.txt_paternal);
        txt_maternal = findViewById(R.id.txt_maternal);
        txt_name = findViewById(R.id.txt_name);
        txt_bdate = findViewById(R.id.txt_bdate);

        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

        btn_generate = findViewById(R.id.btn_generate);

        tv_lbl_curp = findViewById(R.id.tv_lbl_curp);

        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String CURP = "";
                int int_namestart = 0;

                //  with objects use tostring, primitive data use valueof

                String  paternal = txt_paternal.getText().toString().toUpperCase(),
                        maternal = txt_maternal.getText().toString().toUpperCase(),
                        name = txt_name.getText().toString().toUpperCase(),
                        bdate = txt_bdate.getText().toString();

                // 1st letter and vowel from paternal surname
                //  if there is no vowel put x

                //  convert to a string: int value or other kind, concatenate with a string, even a empty one like " "
                CURP = CURP + paternal.charAt(0);

                for(int i = 1; i < paternal.length(); i++){
                    if (paternal.charAt(i) == 65 || paternal.charAt(i) == 69 || paternal.charAt(i) == 73 || paternal.charAt(i) == 79 || paternal.charAt(i) == 85) {
                        CURP = CURP + paternal.charAt(i);
                        //  retrun insted of break is more exrtreme, probably will take you out of the whole event, so.. no 90s shenanigans
                        break;
                    }
                }

                CURP = CURP + maternal.charAt(0);


                if(name.charAt(0) == 74)
                    for (int i = 0; i < name.length(); i++) {
                        if (name.charAt(i) == 32) {
                            int_namestart = i + 1;
                            CURP = CURP + name.charAt(int_namestart);
                        }
                }
                else{
                    CURP = CURP + name.charAt(0);
                }

                CURP = CURP + bdate;

                if(rb_male.isChecked())
                    CURP = CURP + "H";
                if(rb_female.isChecked())
                    CURP = CURP + "M";

                String states [] = {"AS", "BC", "JC", "NT", "SL", "ZS"};

                CURP = CURP + states[spn_states.getSelectedItemPosition()];

                //  first consonant no initial from paternal surname
                for(int i = 1; i < paternal.length(); i++) {
                    if (paternal.charAt(i) != 65 && paternal.charAt(i) != 69 && paternal.charAt(i) != 73 && paternal.charAt(i) != 79 && paternal.charAt(i) != 85) {
                        CURP = CURP + paternal.charAt(i);
                        break;
                    }
                }

                for(int i = 1; i < maternal.length(); i++) {
                    if (maternal.charAt(i) != 65 && maternal.charAt(i) != 69 && maternal.charAt(i) != 73 && maternal.charAt(i) != 79 && maternal.charAt(i) != 85) {
                        CURP = CURP + maternal.charAt(i);
                        break;
                    }
                }

                for(int i = int_namestart + 2; i < name.length(); i++) {
                    if (name.charAt(i) != 65 && name.charAt(i) != 69 && name.charAt(i) != 73 && name.charAt(i) != 79 && name.charAt(i) != 85) {
                        CURP = CURP + name.charAt(i);
                        break;
                    }
                }



                //  COCD870314HJCRSV07
                tv_lbl_curp.setText(CURP);

            }
        });
    }
}

