package com.example.uts.UnitTesting;

import com.example.uts.model.Pengantar;

public class PengantarPresenter {
    private PengantarView view;
    private PengantarService service;
    private PengantarCallback callback;
    private Pengantar pengantar;

    public PengantarPresenter(PengantarView view, PengantarService service) {
        this.view = view;
        this.service = service;
    }

    public void onPengantarClicked() {
        String regex = "[08][0-9]{10,13}";

        if (view.getName().isEmpty()) {
            view.showNameError("Nama tidak boleh kosong");
            return;
        } else if (view.getNoTelp().isEmpty()) {
            view.showNoTelpError("No telp tidak boleh kosong");
            return;
        } else if (!(view.getNoTelp().matches(regex))) {
            view.showNoTelpError("No telp tidak sesuai");
        } else {
            service.pengantar(view, pengantar, new PengantarCallback() {
                @Override
                public void onSuccess(boolean value, Pengantar pengantar) {
                    view.startMainPengantar();
                }

                @Override
                public void onError() {

                }
            });
            return;
        }
    }


}
