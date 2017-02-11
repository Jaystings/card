package com.jaystings.card;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EditPrompt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prompt);
    }
    public void SaveChanges(View v){
        TextView t1 = (TextView)findViewById(R.id.txtPg1Txt);
        TextView t2 = (TextView)findViewById(R.id.txtPg2Txt);
        TextView t3 = (TextView)findViewById(R.id.txtPg3Txt);

        Intent i = new Intent(this, BookActivity.class);
        i.putExtra("s1", t1.getText().toString());
        i.putExtra("s2", t2.getText().toString());
        i.putExtra("s3", t3.getText().toString());
        startActivity(i);
    }

    public void Cancel(View v){
        Intent i = new Intent(this, BookActivity.class);
        startActivity(i);
    }
}
