/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.KrakenRisingOrSomething;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *
 * @author Pi_Joules
 */
public class HelpScreenActivity extends Activity {
    
    Button back;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.help);
        // ToDo add your GUI initialization code here  
        
        setTitle("Help and Examples");
        
        back = (Button) findViewById(R.id.back);
        
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
        
    }
    
}
