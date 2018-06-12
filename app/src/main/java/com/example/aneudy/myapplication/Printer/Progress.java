package com.example.aneudy.myapplication.Printer;

public interface Progress {
    void showProgressPrint(Boolean b);
    void error(String msj);
    void finishPrint();
}
