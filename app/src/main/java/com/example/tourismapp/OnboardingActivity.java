package com.example.tourismapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

public class OnboardingActivity extends AppCompatActivity {

    LinearLayout lay;
    ImageView img;
    TextView tvQuotation, tvSentence;
    Button bGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        lay = (LinearLayout) findViewById(R.id.onBoardingAct);
        img = (ImageView) findViewById(R.id.image);
        tvQuotation = (TextView) findViewById(R.id.quotation);
        tvSentence = (TextView) findViewById(R.id.sentence);
        bGetStarted = (Button) findViewById(R.id.moveButton);
        int [] i = {2};

        // To change the color of the last word on the onboarding screen
        SpannableString span = new SpannableString(tvQuotation.getText());
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7029")), 31, 35, span.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvQuotation.setText(span);

        // To apply animation
        bGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i[0] == 2) {
                    lay.animate() .translationX(-300f) .alpha(0f) .setDuration(400)
                            .setInterpolator(new AccelerateInterpolator())
                            .withEndAction(() -> {

                        img.setImageResource(R.drawable.onboarding_image2);

                        tvQuotation.setText(R.string.onboarding_quotation2);
                        SpannableString span2 = new SpannableString(tvQuotation.getText());
                        span2.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7029")), 30, 37, span2.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvQuotation.setText(span2);

                        tvSentence.setText(R.string.onboarding_sentence2);
                        bGetStarted.setText(R.string.onboarding_button2);

                        lay.setTranslationX(300f);
                        lay.animate()
                                .translationX(0f)
                                .alpha(1f)
                                .setInterpolator(new AccelerateInterpolator())
                                .setDuration(150);
                    });
                }
                else if (i[0] == 3) {
                    lay.animate() .translationX(-300f) .alpha(0f) .setDuration(400)
                            .setInterpolator(new AccelerateInterpolator())
                            .withEndAction(() -> {
                        img.setImageResource(R.drawable.onboarding_image3);

                        tvQuotation.setText(R.string.onboarding_quotation3);
                        SpannableString span3 = new SpannableString(tvQuotation.getText());
                        span3.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7029")), 30, 36, span3.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvQuotation.setText(span3);

                        tvSentence.setText(R.string.onboarding_sentence3);
                        bGetStarted.setText(R.string.onboarding_button3);

                        lay.setTranslationX(300f);
                        lay.animate()
                                .translationX(0f)
                                .alpha(1f)
                                .setInterpolator(new AccelerateInterpolator())
                                .setDuration(150);
                    });
                }
                else {
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), RegistrationActivity.class);
                    startActivity(i);
                }
                i[0] += 1;
            }
        });

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    }
}