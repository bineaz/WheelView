package com.zby.wheeldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zby.wheelview.WheelMaskLayer;
import com.zby.wheelview.WheelSuffixLayer;
import com.zby.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WheelView<Integer> wheelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wheelView = findViewById(R.id.wheel);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        wheelView.setData(list);
        wheelView.addWheelLayer(new WheelMaskLayer(new int[]{0xFFFFFFFF, 0x00FFFFFF, 0xFFFFFFFF}, new float[]{0, .5f, 1}));
        wheelView.addWheelLayer(new WheelSuffixLayer("%%", 16, Color.BLACK, 10));

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });
    }

    private void play() {
        EditText editText = findViewById(R.id.editText);
        if (editText.getText().toString().trim().isEmpty())
            return;

        wheelView.setFroze(true);
        final int height = Integer.parseInt(editText.getText().toString().trim());

        final ObjectAnimator animator = ObjectAnimator.ofInt(editText, "backgroundColor", wheelView.getHeight(), height);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = wheelView.getLayoutParams();
                layoutParams.height = value;
                wheelView.setLayoutParams(layoutParams);

                if (value == height) {
                    wheelView.setFroze(false);
                }
            }
        });

        animator.start();
    }
}
