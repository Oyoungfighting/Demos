package com.oyoung.jnidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.oyoung.jnidemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'jnidemo' library on application startup.
    static {
        System.loadLibrary("jnidemo");
    }

    private ActivityMainBinding binding;

    private String name = "oyoung";
    private int age = 18;

    private static String love = "hly";

    private int add(int a, int b) {
        return a + b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());

        System.out.println("更改之前" + name);
        changeName();
        System.out.println("更改之后" + name);

        System.out.println("更改之前" + age);
        changeAge();
        System.out.println("更改之后" + age);

        System.out.println("更改之前" + love);
        changeLove();
        System.out.println("更改之后" + love);

        callAddMethod();
    }

    /**
     * A native method that is implemented by the 'jnidemo' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native void changeName();

    public native void changeAge();
    public native void changeLove();
    public native void callAddMethod();
}