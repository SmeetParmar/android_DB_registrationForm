package com.au.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String[] skill = {"Select A Product","Web Designing","PHP","MySQL","Video Editing","Canva"};
    Spinner sp;
    EditText ed;

    CheckBox far,vomit,master,tp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=findViewById(R.id.spinner);
        ArrayAdapter<String> a = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,skill) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        };
        sp.setAdapter(a);
        ed=findViewById(R.id.name);

        far=findViewById(R.id.far);
        vomit=findViewById(R.id.vomit);
        master=findViewById(R.id.master);
        tp=findViewById(R.id.tpass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu,m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       RadioGroup rg=findViewById(R.id.rbg);
        int i=rg.getCheckedRadioButtonId();
        RadioButton rb=findViewById(i);
        String skill="";
        if(far.isChecked())
        {
            skill+=far.getText().toString()+",";
        }
        if(vomit.isChecked())
        {
            skill+=vomit.getText().toString()+",";
        }
        if(master.isChecked())
        {
            skill+=master.getText().toString()+",";
        }
        if(tp.isChecked())
        {
            skill+=tp.getText().toString()+",";
        }

        switch(item.getItemId())
        {
            case R.id.insert:
                db d=new db(this);
                boolean b=d.insert(ed.getText().toString(),rb.getText().toString(),skill,sp.getSelectedItem().toString());
                if(b==true)
                {
                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Sorry......", Toast.LENGTH_SHORT).show();
                }
                rb.setChecked(false);
                ed.setText("");
                vomit.setChecked(false);
                master.setChecked(false);
                tp.setChecked(false);
                far.setChecked(false);
                sp.setSelection(0);
                break;

            case R.id.select:
                db d2=new db(this);
                Cursor c=d2.select();
                AlertDialog.Builder b2=new AlertDialog.Builder(this);
                if(c.getCount()==0)
                {
                    b2.setMessage("Table Khali Se.....");
                    b2.setTitle("Table");
                    b2.setNegativeButton("Nikar",null);
                    b2.setPositiveButton("Bhai",null);
                    b2.show();
                }
                else
                {
                    String msg="";
                    while(c.moveToNext())
                    {
                        msg+=c.getString(0)+"   "+c.getString(1)+"   "+c.getString(2)+"   "+c.getString(3)+"   "+c.getString(4)+"\n";
                    }
                    b2.setMessage(msg);
                    b2.setTitle("Table");
                    b2.setNegativeButton("Nikar",null);
                    b2.setPositiveButton("Bhai",null);
                    b2.show();
                }
                break;
        }
        return  true;
    }
}