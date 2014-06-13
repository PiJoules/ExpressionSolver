package com.KrakenRisingOrSomething;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity
{
    
    EditText edit;
    TextView solution;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setTitle("Expression Solver");
        
        edit = (EditText) findViewById(R.id.expr);
        solution = (TextView) findViewById(R.id.sol);
        
        edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
                solution.setText(solve(edit.getText().toString()));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                
            }
        });
        
        System.out.println("done loading");
    }
    
    private boolean contains(String pattern, String s){
        return (s.length() - s.replaceAll(pattern, "").length() > 0);
    }
    
    private String solve(String s){
        
        s = s.replaceAll("\\s+", "");
        if (s.equals("")) return s;
        
        if (contains("[^x]", s.replaceAll("[^a-zA-Z]", ""))) return s;
        //if (contains("[^\\d\\.\\=\\-\\/\\*\\=\\x]", s)) return s;
        
        /*System.out.println((s.contains("=") && !s.contains("x"))
                + ", " + (s.contains("x") && !s.contains("="))
                + ", " + (!contains("[\\+\\-\\*\\/][\\d\\.]+", s))
                + ", " + (contains("[^x\\d\\.\\+\\-\\*\\/]", s)));*/
        
        if (s.contains("=") && !s.contains("x")
                || s.contains("x") && !s.contains("=")) return s;
        
        
        if (!s.contains("x")) s+= "=x";
        s = s.replaceAll("-", "+-")
                .replaceAll("-x", "-1x")
                .replaceAll("\\+x", "+1x")
                .replaceAll("=x", "=1x");
        
        String eval = evalMultiplication(s);
        if (eval.equals("")) return s;
        s = eval;
        eval = evalDivision(s);
        if (eval.equals("")) return s;
        s = eval;
        
        String[] sides = s.split("=");
        if (sides.length != 2) return s;
        String[] side1 = sides[0].split("\\+");System.out.println(Arrays.toString(side1));
        for (int i = 0; i < side1.length; i++){
            if (side1[i].equals("")) side1[i] = "0";
            if (!contains("\\d",side1[i])) return s;
        }System.out.println(Arrays.toString(side1));
        String[] side2 = sides[1].split("\\+");System.out.println(Arrays.toString(side2));
        for (int i = 0; i < side2.length; i++){
            if (side2[i].equals("")) side2[i] = "0";
            if (!contains("\\d",side2[i])) return s;
        }System.out.println(Arrays.toString(side2));
        
        double[] side1vars = {0,0};
        double[] side2vars = {0,0};
        
        side1vars = combineTerms(side1, side1vars);System.out.println(side1vars[0] + ", " + side1vars[1]);
        side2vars = combineTerms(side2, side2vars);System.out.println(side2vars[0] + ", " + side2vars[1]);
        
        //return "poop";
        return display(side1vars, side2vars);
    }
    
    private String display(double[] side1vars, double[] side2vars){
        if (side1vars[1] > side2vars[1]){
            side1vars[1] -= side2vars[1];
            side2vars[0] -= side1vars[0];
            if (side1vars[1] != 0) return "x=" + side2vars[0]/side1vars[1];
            else return "divide by zero err";
        }
        else {
            side2vars[1] -= side1vars[1];
            side1vars[0] -= side2vars[0];
            if (side2vars[1] != 0) return "x=" + side1vars[0]/side2vars[1];
            else return "divide by zero err";
        }
    }
    
    private double[] combineTerms(String[] side, double[] sidevars){
        for (String s : side){
            if (s.contains("x")) sidevars[1] += Double.parseDouble(s.replaceAll("x", ""));
            else sidevars[0] += Double.parseDouble(s);
        }
        return sidevars;
    }
    
    private String multiply(String s){
        String[] vals = s.split("\\*");
        return (Double.parseDouble(vals[0]) * Double.parseDouble(vals[1])) + "";
    }
    
    private String evalMultiplication(String s){
        
        while (s.contains("*")){
            Pattern pattern = Pattern.compile("[\\d\\.]+\\*[\\d\\.]+");
            if (search(pattern, s, "start") == -1) return "";
            int start = search(pattern, s, "start");
            int end = search(pattern, s, "end");
            s = s.substring(0,start) + multiply(s.substring(start, end)) + s.substring(end);
        }
        
        return s;
    }
    
    private String divide(String s){
        String[] vals = s.split("\\/");
        return (Double.parseDouble(vals[0]) / Double.parseDouble(vals[1])) + "";
    }
    
    private String evalDivision(String s){
        
        while (s.contains("/")){
            Pattern pattern = Pattern.compile("[\\d\\.]+\\/[\\d\\.]+");
            if (search(pattern, s, "start") == -1) return "";
            int start = search(pattern, s, "start");
            int end = search(pattern, s, "end");
            s = s.substring(0,start) + divide(s.substring(start, end)) + s.substring(end);
        }
        
        return s;
    }
    
    private int search(Pattern pattern, String s, String pos){
        Matcher match = pattern.matcher(s);
        return match.find() ? (pos.equals("end") ? match.end() : match.start()) : -1;
    }
}
