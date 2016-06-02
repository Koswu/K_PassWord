package com.koswu.kpassword;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.koswu.kpassword.MainActivity;
import com.koswu.kpassword.tool.PassWord;

public class MainActivity extends AppCompatActivity
{
    private Button button_create=null;
    private Button button_copy=null;
    private EditText edittext_memory_password=null;
    private EditText edittext_diff_code=null;
    TextView textview_created_password=null;
    String created_password="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button_create=(Button)findViewById(R.id.button_create);
        button_copy=(Button)findViewById(R.id.button_copy);
        edittext_memory_password=(EditText)findViewById(R.id.edittext_input_password);
        edittext_diff_code=(EditText)findViewById(R.id.edittext_input_code);
        textview_created_password=(TextView)findViewById(R.id.textview_password);
        button_create.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View p1)
                {
                    // TODO: Implement this method
                    String diff_code=edittext_diff_code.getText().toString();
                    String memory_password=edittext_memory_password.getText().toString();
                    if(!(
                       TextUtils.isEmpty(diff_code)||
                       TextUtils.isEmpty(memory_password)
                       ))
                    {
                        try
                        {
                            created_password=PassWord.CreatePassword(memory_password,diff_code);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        textview_created_password.setText(created_password);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,R.string.cant_create,Toast.LENGTH_SHORT).show();
                    }
                }
                
            
        });
        button_copy.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View p1)
                {
                    // TODO: Implement this method
                    if (TextUtils.isEmpty(created_password))
                    {
                        Toast.makeText(MainActivity.this,R.string.cant_copy,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(created_password.trim());
                    Toast.makeText(MainActivity.this,R.string.copy_ok,Toast.LENGTH_SHORT).show();
                }
                
            
        });
    }
}
