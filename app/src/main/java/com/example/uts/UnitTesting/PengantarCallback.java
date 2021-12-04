package com.example.uts.UnitTesting;

import com.example.uts.model.Pengantar;

public interface PengantarCallback {
    void onSuccess(boolean value, Pengantar pengantar);
    void onError();
}
