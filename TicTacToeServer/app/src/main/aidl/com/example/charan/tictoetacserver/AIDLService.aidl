// AIDLService.aidl
package com.example.charan.tictoetacserver;

// Declare any non-default types here with import statements

interface AIDLService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void setMark(int h,int v,int mark);
    boolean isWin();
    boolean isDraw();
    int getResult();
    void setList();
    }
