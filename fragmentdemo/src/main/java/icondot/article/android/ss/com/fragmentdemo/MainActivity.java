package icondot.article.android.ss.com.fragmentdemo;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MainActivity extends FragmentActivity {
    String TAG = "testLinearlayoutA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "[onCreate]");
//        findViewById(R.id.testLinearlayout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(v.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
//            }
//        });

        TempClass tempClass = new TempClass(new View(MainActivity.this));
        Constructor  constructor = null;
        try {
            constructor =TempClass.class.getDeclaredConstructor(View.class);
            Log.d(TAG, "[constructor]    = " + constructor.newInstance(new View(MainActivity.this)));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "[onStart]");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "[onResume]");
        super.onResume();
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(TAG, "[onDetachedFromWindow]");
        super.onDetachedFromWindow();
    }

    @Override
    public void onAttachedToWindow() {
        Log.d(TAG, "[onAttachedToWindow]");
        super.onAttachedToWindow();
    }
}
