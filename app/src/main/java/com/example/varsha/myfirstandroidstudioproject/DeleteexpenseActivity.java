package com.example.varsha.myfirstandroidstudioproject;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by varsha on 10/9/2016.
 */
public class DeleteexpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Button getExpenseButton,deleteExpenseButton,cancelButton;
    private Spinner viewExpensesSpinner;
    private DatePicker datePicker;
    private int day,month,year;
    private EditText name, amount;
    private List<String>spinnerArray=null;
    private String expenseName="";

    View.OnClickListener deleteExpense=new View.OnClickListener(){

        public void onClick (View view){
            day=datePicker.getDayOfMonth();
            month=datePicker.getMonth();
            year=datePicker.getYear();
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("DELETE FROM EXPENSE WHERE NAME='"+expenseName+"' AND DAY='"+ day + "' AND MONTH='" + month + "' AND YEAR='" + year + "'");
            NavUtils.navigateUpFromSameTask(DeleteexpenseActivity.this);
            if(expenseName!="")
                Toast.makeText(getApplicationContext(),"Expense "+expenseName+" successfully deleted!!", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener cancelHandler=new View.OnClickListener(){

        public void onClick (View view){
            NavUtils.navigateUpFromSameTask(DeleteexpenseActivity.this);
        }
    };
    View.OnClickListener getExpense = new View.OnClickListener(){

        public void onClick (View view){
            day=datePicker.getDayOfMonth();
            month=datePicker.getMonth();
            year=datePicker.getYear();
            spinnerArray = new ArrayList<String>();
            String column1 = null;
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT NAME FROM EXPENSE WHERE DAY='" + day + "' AND MONTH='" + month + "' AND YEAR='" + year + "'", null);
            if (c.moveToFirst()) {
                do {
                    //assiging values
                    column1 = c.getString(0);
                    System.out.println("Expense name:" + column1);
                    spinnerArray.add(column1);

                }
                while (c.moveToNext());
            }
            loadSpinnerData(spinnerArray);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteexpense);
        datePicker=(DatePicker)findViewById(R.id.deletedatePicker);
        getExpenseButton=(Button)findViewById(R.id.viewExpenseButton);
        getExpenseButton.setOnClickListener(getExpense);
        deleteExpenseButton=(Button)findViewById(R.id.deleteExpenseButton);
        deleteExpenseButton.setOnClickListener(deleteExpense);
        cancelButton=(Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(cancelHandler);
        viewExpensesSpinner=(Spinner)findViewById(R.id.spinner);
        viewExpensesSpinner.setOnItemSelectedListener(this);


    }
    public void loadSpinnerData(List<String> s)
    {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        viewExpensesSpinner.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        expenseName = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),expenseName, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        expenseName="";

    }
}

