package com.example.uts.UnitTesting;

import android.content.Context;
import android.content.Intent;

import com.example.uts.PengantarForm;

public class ActivityUtil {
    private Context context;
    
    public ActivityUtil(Context context) {
        this.context = context;
    }
    public void startMainPengantar() {
        context.startActivity(new Intent(context, PengantarForm.class));
    }

}
