package com.example.interviewfortest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 1  2  3  4
 * 5  6  7  8
 * 9  10 11 12
 * 13 14 15 16
 * 顺时针打印
 *
 * 1、测试用例
 * 2、验证是否是抄的
 */
public class Test1Activity extends AppCompatActivity {

    String TAG = Test1Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        int[][] num = {{1, 2, 3, 4},
                       {5, 6, 7, 8},
                       {9, 10, 11, 12},
                       {13, 14, 15, 16}};
        PrintMatrixColockwisely(num, 4, 4);
    }

    private void PrintMatrixColockwisely(int[][] numbers, int columns, int rows) {
        if (numbers == null || columns <= 0 || rows <= 0) {
            return;
        }
        int start = 0;
        while (columns > start * 2 && rows > start * 2) {
            PrintMatrixIncircle(numbers, columns, rows, start);
            ++start;
        }
    }

    private void PrintMatrixIncircle(int[][] numbers, int columns, int rows, int start) {
        int endX = columns - 1 - start;
        int endY = rows - 1 - start;

        // 从左到右
        for (int i = start; i <= endX; ++i) {
            int number = numbers[start][i];
            Log.d(TAG, "" + number);
        }

        // 从上到下
        if (start < endY) {
            for (int i = start + 1; i <= endY; ++i) {
                int number = numbers[i][endX];
                Log.d(TAG, "" + number);
            }
        }

        // 从右到左
        if (start < endX && start < endY) {
            for (int i = endX - 1; i >= start; --i) {
                int number = numbers[endY][i];
                Log.d(TAG, "" + number);
            }
        }

        // 从下到上
        if (start < endX && start < endY - 1) {
            for (int i = endY - 1; i >= start + 1; i--) {
                int number = numbers[i][start];
                Log.d(TAG, "" + number);
            }
        }
    }

}
