package com.example.charan.tictoetacserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;

public class ICallService extends Service {

    ArrayList ticTocTable=new ArrayList();
    int key=0;

    public ICallService() {

        try {
            mBinder.setList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    private AIDLService.Stub mBinder=new AIDLService.Stub() {

        public void setList()
        {
            for(int i=0;i<9;i++)
            {
                ticTocTable.add(i,"0");
            }
        }
        @Override
        public void setMark(int h, int v, int mark) throws RemoteException {
            // String key=Integer.toString(h)+Integer.toString(v);

            String newMark="";
            Log.d("testing1111", "check h v"+key+" h : "+h+" v : "+v);
            if(mark==0)
            {
                newMark="O";
            }else {
                newMark="X";
            }
            if(h==0)
            {
                key=v;
            }else if(h==1)
            {
                key=v+3;
            }else if (h==2)
            {
                key=v+6;
            }
            ticTocTable.set(key,newMark);
            Log.d("testing1111", "setMark: "+key +" is "+ticTocTable.get(key));


        }
        public int getResult()
        {

            if(ticTocTable.get(0)!="0" && ticTocTable.get(1)!="0" && ticTocTable.get(2)!="0")
            {
                if(ticTocTable.get(0) == ticTocTable.get(1) && ticTocTable.get(2)==ticTocTable.get(1))
                {

                    return 1;

                }

            }
            if(ticTocTable.get(3)!="0" && ticTocTable.get(4)!="0" && ticTocTable.get(5)!="0")
            {
                if(ticTocTable.get(3) == ticTocTable.get(4) && ticTocTable.get(5)==ticTocTable.get(4))
                {

                    return 1;

                }

            }
            if(ticTocTable.get(6)!="0" && ticTocTable.get(7)!="0" && ticTocTable.get(8)!="0")
            {
                if(ticTocTable.get(6) == ticTocTable.get(7) && ticTocTable.get(8)==ticTocTable.get(7))
                {

                    return 1;

                }

            }
            if(ticTocTable.get(0)!="0" && ticTocTable.get(3)!="0" && ticTocTable.get(6)!="0")
            {
                if(ticTocTable.get(0) == ticTocTable.get(3) && ticTocTable.get(6)==ticTocTable.get(3))
                {

                    return 1;

                }

            }
            if(ticTocTable.get(1)!="0" && ticTocTable.get(4)!="0" && ticTocTable.get(7)!="0")
            {
                if(ticTocTable.get(1) == ticTocTable.get(4) && ticTocTable.get(7)==ticTocTable.get(4))
                {

                    return 1;

                }

            }
            if(ticTocTable.get(2)!="0" && ticTocTable.get(5)!="0" && ticTocTable.get(8)!="0")
            {
                if(ticTocTable.get(2) == ticTocTable.get(5) && ticTocTable.get(8)==ticTocTable.get(5))
                {

                    return 1;

                }

            }
            if(ticTocTable.get(0)!="0" && ticTocTable.get(4)!="0" && ticTocTable.get(8)!="0")
            {
                if(ticTocTable.get(0) == ticTocTable.get(4) && ticTocTable.get(8)==ticTocTable.get(4))
                {

                    return 1;

                }

            }
            if(ticTocTable.get(2)!="0" && ticTocTable.get(4)!="0" && ticTocTable.get(6)!="0")
            {
                if(ticTocTable.get(2) == ticTocTable.get(4) && ticTocTable.get(6)==ticTocTable.get(4))
                {

                    return 1;

                }

            }
            return 0;
        }

        @Override
        public boolean isWin() throws RemoteException {
            if(getResult()==0)
            {
                return false;
            }else{
                ticTocTable.clear();
                setList();
                return true;
            }
        }

        @Override
        public boolean isDraw() throws RemoteException {
            int x=0;
            if(getResult()==0 && ticTocTable.size()==9)
            {
                for(int i=0;i<9;i++)
                {
                    if(ticTocTable.get(i)!="0")
                    {
                        x++;
                    }
                }
                if(x==9) {
                    ticTocTable.clear();
                    setList();
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
