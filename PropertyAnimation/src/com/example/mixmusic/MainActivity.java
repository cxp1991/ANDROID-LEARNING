package com.example.mixmusic;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {

	CheckBox mCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animations);

        final Button alphaButton = (Button) findViewById(R.id.alphaButton);
        final Button translateButton = (Button) findViewById(R.id.translateButton);
        final Button rotateButton = (Button) findViewById(R.id.rotateButton);
        final Button scaleButton = (Button) findViewById(R.id.scaleButton);
        final Button setButton = (Button) findViewById(R.id.setButton);
       
        setupAnimation(alphaButton,  R.animator.fade);
        setupAnimation(translateButton,  R.animator.move);
        setupAnimation(rotateButton,  R.animator.spin);
        setupAnimation(scaleButton,  R.animator.scale);
        setupAnimation(setButton, R.animator.combo);

    }

    private void setupAnimation(View view, final int animationID) {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Animator anim = AnimatorInflater.loadAnimator(getBaseContext(), animationID);
                    anim.setTarget(v);
                    anim.start();
                    return;
            }
        });
    }
}
