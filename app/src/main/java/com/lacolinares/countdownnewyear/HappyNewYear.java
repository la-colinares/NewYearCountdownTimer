package com.lacolinares.countdownnewyear;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.yasic.library.particletextview.MovingStrategy.RandomMovingStrategy;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;
import com.yasic.library.particletextview.View.ParticleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HappyNewYear extends AppCompatActivity {


    @BindView(R.id.txt_particle)
    ParticleTextView txtParticle;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy_new_year);
        ButterKnife.bind(this);
        mContext = this;

        RandomMovingStrategy randomMovingStrategy = new RandomMovingStrategy();
        ParticleTextViewConfig config = new ParticleTextViewConfig.Builder()
                .setRowStep(8)
                .setColumnStep(8)
                .setTargetText("Happy New Year!!!")
                .setReleasing(0.2)
                .setParticleRadius(4)
                .setMiniDistance(0.1)
                .setTextSize(120)
                .setMovingStrategy(randomMovingStrategy)
                .instance();

        txtParticle.setConfig(config);
        txtParticle.startAnimation();
    }

    @Override
    public void onBackPressed() {
        txtParticle.stopAnimation();
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
}
