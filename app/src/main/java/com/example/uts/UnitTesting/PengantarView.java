package com.example.uts.UnitTesting;

public interface PengantarView {
    String getName();
    void showNameError(String message);
    
    String getNoTelp();
    void showNoTelpError(String message);
    
    void startMainPengantar();
    
    void showPengantarError(String message);
    void showErrorResponse(String message);
}
