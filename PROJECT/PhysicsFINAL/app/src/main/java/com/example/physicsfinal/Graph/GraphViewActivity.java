package com.example.physicsfinal.Graph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.physicsfinal.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        GraphView graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> seriesa = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> seriesb = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> seriesc = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>();

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-20);
        graph.getViewport().setMaxY(20);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-20);
        graph.getViewport().setMaxX(20);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);


        series1 = makeGraph(
                getIntent().getDoubleExtra("10", 0.0),
                getIntent().getDoubleExtra("11", 0.0),
                getIntent().getDoubleExtra("12", 0.0),
                getIntent().getDoubleExtra("13", 1.0),
                getIntent().getDoubleExtra("14", 1.0),
                getIntent().getDoubleExtra("15", 0.0),
                seriesa);

        if (getIntent().getDoubleExtra("20", 0.0) != 0.0) {
            series2 = makeGraph(
                    getIntent().getDoubleExtra("20", 0.0),
                    getIntent().getDoubleExtra("21", 0.0),
                    getIntent().getDoubleExtra("22", 0.0),
                    getIntent().getDoubleExtra("23", 1.0),
                    getIntent().getDoubleExtra("24", 1.0),
                    getIntent().getDoubleExtra("25", 0.0),
                    seriesb);
        }

        if (getIntent().getDoubleExtra("30", 0.0) != 0.0) {
            series3 = makeGraph(
                    getIntent().getDoubleExtra("30", 0.0),
                    getIntent().getDoubleExtra("31", 0.0),
                    getIntent().getDoubleExtra("32", 0.0),
                    getIntent().getDoubleExtra("33", 1.0),
                    getIntent().getDoubleExtra("34", 1.0),
                    getIntent().getDoubleExtra("35", 0.0),
                    seriesc);
        }

        series1.setColor(Color.BLUE);
        series2.setColor(Color.GREEN);
        series3.setColor(Color.RED);

        graph.addSeries(series1);
        graph.addSeries(series2);
        graph.addSeries(series3);


    }

    public static LineGraphSeries<DataPoint> makeGraph(double algNum, double constValD, double constValI, double powValD, double powValI, double sqrt, LineGraphSeries<DataPoint> series) {

        if (algNum == 0.0) return series;

        double powerValue = 1.0;
        double constValue = 0.0;

        double x0 = -125.0;
        double x1 = 125.0;

        if (constValI != 0) {
            constValue = constValI;
        }
        if (constValD != 0) {
            constValue =  constValD;
        }
        if (powValI != 1) {
            powerValue = powValI;
        }
        if (powValD != 1) {
            powerValue =  powValD;
        }


        if (sqrt == 1) {
            if (powerValue % 2 != 0 ) {
                x0 = 0.0;
                x1 = 250.0;
            }
        }

        double x, y;

        if (sqrt == 1) {
            if (algNum == 1) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.sqrt(Math.pow(i, powerValue)) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 2) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.cos(Math.sin(Math.sqrt(Math.pow(i, powerValue)) + constValue));
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 3) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.sin(Math.sqrt(Math.pow(i, powerValue)) + constValue);
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 4) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.sin(Math.cos(Math.sqrt(Math.pow(i, powerValue)) + constValue));
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 5) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.cos(Math.sqrt(Math.pow(i, powerValue)) + constValue);
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 6) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.cos(Math.sin(Math.sqrt(Math.pow(i, powerValue)))) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 7) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.sin(Math.sqrt(Math.pow(i, powerValue))) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 8) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.sin(Math.cos(Math.sqrt(Math.pow(i, powerValue)))) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 9) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.cos(Math.sqrt(Math.pow(i, powerValue))) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
        }
        else {
            if (algNum == 1) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.pow(i, powerValue) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 2) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.cos(Math.sin(Math.pow(i, powerValue) + constValue));
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 3) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.sin(Math.pow(i, powerValue) + constValue);
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 4) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.sin(Math.cos(Math.pow(i, powerValue) + constValue));
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 5) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.cos(Math.pow(i, powerValue) + constValue);
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 6) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.cos(Math.sin(Math.pow(i, powerValue))) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 7) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.sin(Math.pow(i, powerValue)) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 8) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.sin(Math.cos(Math.pow(i, powerValue))) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }
            if (algNum == 9) {
                for (double i = x0; i < x1; i += 0.1) {
                    x = i;
                    y = Math.cos(Math.pow(i, powerValue)) + constValue;
                    series.appendData(new DataPoint(x, y), true, 5000);
                }
            }

        }
        return  series;
    }
}
