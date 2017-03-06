package com.example.interviewfortest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * 输入一个正数s,打印出所有和为s的连续正整数序列（至少含有两个数）。
 * 例如输入15，由于 1+2+3+4+5 = 4+5+6 = 7+8 = 15 所以结果打印出
 * 3个序列1~5、4~6 和 7~8.
 */
public class Test2Activity extends AppCompatActivity {

    String TAG = Test2Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        FindContinuousSequence(15);
    }

    private void FindContinuousSequence(int sum) {
        if (sum < 3) return;

        int small = 1;
        int big = 2;
        int middle = (1 + sum) / 2;
        int curSum = small + big;

        while (small < middle) {
            if (curSum == sum) {
                print(small, big);
            }

            while (curSum > sum && small < middle) {
                curSum -= small;
                small++;
                if (curSum == sum) {
                    print(small, big);
                }
            }
            big++;
            curSum += big;
        }
    }

    private void print(int small, int big) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = small; i <= big; i++) {
            stringBuilder.append(i).append(" ");
        }
        Log.d(TAG, stringBuilder.toString());
        Toast.makeText(Test2Activity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
    }
}
