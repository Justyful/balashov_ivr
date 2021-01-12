package com.example.physicsfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class GraphViewActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        String n, constant, sign;
        int x0 = 0, lastDot = 500, addition, power;
        boolean isDouble = false, isPower = false, negative = false;
        ArrayList<Character> unEvenList = new ArrayList<Character>();
        unEvenList.add('1');
        unEvenList.add('3');
        unEvenList.add('5');
        unEvenList.add('7');
        unEvenList.add('9');
        ArrayList<String> arrayList = getIntent().getStringArrayListExtra("array");
        n = getIntent().getStringExtra("n");
        n = n.replace(",", ".");
        constant = getIntent().getStringExtra("const");
        constant = constant.replace(",", ".");
        sign = getIntent().getStringExtra("sign");

        GraphView graph = findViewById(R.id.graph);
        series = new LineGraphSeries<>();

        //int numDataPoints = 500;
        if (arrayList.contains("sqrt")) {
            if (n.contains(".")) {
                x0 = 0;
            }
            else {
                if (unEvenList.contains(n.substring(n.length() - 1))) {
                    x0 = 0;
                }
            }
        }
        else {
            x0 = -249;
        }
        if (x0 < 0) {
            lastDot = 250;
        }
        if (arrayList.contains("const")) {
            addition = Integer.parseInt(constant);
            if (sign != "+") {
                negative = true;
            }
        }

        if (arrayList.contains("power")) {
            power = Integer.parseInt(n);
        }
        int asdf = 1234;



    }
}