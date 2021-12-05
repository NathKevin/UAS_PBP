package com.example.uts.UnitTesting;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

public class PengantarPresenterTest {
    @Mock
    private PengantarView view;
    @Mock
    private PengantarService service;
    private PengantarPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new PengantarPresenter(view, service);
    }

    @Test
    public void shoulShowErrorMessageWhenNameIsEmpty() throws Exception {
        when(view.getName()).thenReturn("");
        System.out.println("Testing Pertama: Inputan Nama Kosong");
        System.out.println("Nama Depan : " + view.getName());

        presenter.onPengantarClicked();
        verify(view).showNameError("Nama tidak boleh kosong");
    }

    @Test
    public void shoulShowErrorMessageWhenNoTelpIsEmpty() throws Exception {
        System.out.println("\n\n" + "Testing Kedua: Inputan No Telp Kosong");

        when(view.getName()).thenReturn("Edwin");
        System.out.println("Nama Depan : " + view.getName());

        when(view.getNoTelp()).thenReturn("");
        System.out.println("No Telp : " + view.getNoTelp());

        presenter.onPengantarClicked();
        verify(view).showNoTelpError("No telp tidak boleh kosong");
    }

    @Test
    public void shoulShowErrorMessageWhenNoTelpFalseStart() throws Exception {
        System.out.println("\n\n" + "Testing Ketiga: Inputan No Telp tidak 08");

        when(view.getName()).thenReturn("Edwin");
        System.out.println("Nama Depan : " + view.getName());

        when(view.getNoTelp()).thenReturn("22222222222");
        System.out.println("No Telp : " + view.getNoTelp());

        presenter.onPengantarClicked();
        verify(view).showNoTelpError("No telp tidak sesuai");
    }

    @Test
    public void shoulShowErrorMessageWhenNoTelpNotInRange() throws Exception {
        System.out.println("\n\n" + "Testing Keempat: Inputan No Telp tidak sesuai jumlah");

        when(view.getName()).thenReturn("Edwin");
        System.out.println("Nama Depan : " + view.getName());

        when(view.getNoTelp()).thenReturn("0822");
        System.out.println("No Telp : " + view.getNoTelp());

        presenter.onPengantarClicked();
        verify(view).showNoTelpError("No telp tidak sesuai");
    }

    @Test
    public void shoulShowErrorMessageWhenNoTelpNotInRangeLot() throws Exception {
        System.out.println("\n\n" + "Testing Kelima: Inputan No Telp tidak sesuai jumlah kebanyakan");

        when(view.getName()).thenReturn("Edwin");
        System.out.println("Nama Depan : " + view.getName());

        when(view.getNoTelp()).thenReturn("08222222222222222222");
        System.out.println("No Telp : " + view.getNoTelp());

        presenter.onPengantarClicked();
        verify(view).showNoTelpError("No telp tidak sesuai");
    }
}