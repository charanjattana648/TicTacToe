package com.example.charan.tictoetacclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charan.tictoetacserver.AIDLService;

import java.util.ArrayList;

public class TicTacToeView extends View {
    TextView tv_Pstatus,tv_label;
    AIDLService aidlService=null;
    Paint linePaint=new Paint();
    Paint tPaint=new Paint();
    float v1X,v2X,h1Y,h2Y;
    float x1,x2,x3,y1,y2,y3;
    float mX,mY,cpX,cpY;
    float[] cX=new float[9];
    float[] cY=new float[9];
    boolean isWon=false;


    ArrayList markerP=new ArrayList();
    int h,v;
    String mark="O";
    int click=0;
    public TicTacToeView(Context context) {
        super(context);
        init();
    }

    public TicTacToeView(Context context,AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TicTacToeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        if (aidlService == null) {
            Intent intent = new Intent("TTTGame");
            intent.setPackage("com.example.charan.tictoetacserver");
            if (getContext().getApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE) == true) {
                Toast.makeText(getContext(), "Service Connected", Toast.LENGTH_SHORT).show();
            } else {

            }
        }
        initialList();
        linePaint.setColor(Color.WHITE);
        tPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        tPaint.setColor(Color.WHITE);
        tPaint.setTextSize(45f);









        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {

                    mX=event.getX();
                    mY=event.getY();
                    click=(click+1)%2;
                    if(click%2==0)
                    {
                        mark="O";
                    }
                    else {
                        mark="X";
                    }
                    sethv();
                    try {
                        markerP.add(mark);
                        aidlService.setMark(h,v,click);
                        if(aidlService.isDraw()==true)
                        {
                            Toast.makeText(getContext(), "Match Draw", Toast.LENGTH_SHORT).show();

                            tv_Pstatus.setText("Match Draw");
                        }
                        if(aidlService.isWin()==true)
                        {

                            isWon=true;
                            Toast.makeText(getContext(), "Match won", Toast.LENGTH_SHORT).show();
                            int player=click+1;
                            tv_Pstatus.setText("Player "+player+" Won");

                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return true;

                }
                return false;
            }
        });
    }
    public void initialList()
    {

    for(int i=0;i<9;i++) {
        cX[i] = 0;
        cY[i] = 0;
        markerP.add(i, "0");
    }
    }

    public void setMarker(int pos,float cx,float cy,String mark1)
    {
       cX[pos]=cx;
        cY[pos]=cy;
        if(markerP.get(pos).toString().equalsIgnoreCase("0") && isWon==false) {
            markerP.set(pos, mark1);
            tv_Pstatus=getRootView().findViewById(R.id.player_status_txt);
            int player=click+1;
            tv_Pstatus.setText("Player : "+player+" turn");
        }else if(!markerP.get(pos).toString().equalsIgnoreCase("0")) {
           mark=markerP.get(pos).toString();
        }
    }

    public void sethv()
    {

        if (mX>0 && mX<x1)
        {

            cpX=(x1+0)/2;
            if (mY>0 && mY<y1)
            {
                h=0;
                v=0;
                cpY=(y1+0)/2;
                setMarker(0,cpX,cpY,mark);


            }
            if (mY>y1 && mY<y2)
            {
                cpY=(y1+y2)/2;
                setMarker(1,cpX,cpY,mark);
                h=1;
                v=0;
            }
            if (mY>y2 && mY<y3)
            {
                cpY=(y3+y2)/2;
                h=2;
                v=0;
                setMarker(2,cpX,cpY,mark);

            }
        }
        if (mX>x1 && mX<x2)
        {

            cpX=(x1+x2)/2;
            if (mY>0 && mY<y1)
            {

                cpY=y1/2;
                h=0;
                v=1;
                setMarker(3,cpX,cpY,mark);
            }
            if (mY>y1 && mY<y2)
            {
                cpY=(y1+y2)/2;
                h=1;
                v=1;
                setMarker(4,cpX,cpY,mark);
            }
            if (mY>y2 && mY<y3)
            {
                cpY=(y3+y2)/2;
                h=2;
                v=1;
                setMarker(5,cpX,cpY,mark);

            }
        }
        if (mX>x2 && mX<x3)
        {
            cpX=(x3+x2)/2;

            if (mY>0 && mY<y1)
            {
                cpY=(y1+0)/2;
                h=0;
                v=2;
                setMarker(6,cpX,cpY,mark);
            }
            if (mY>y1 && mY<y2)
            {
                cpY=(y1+y2)/2;
                h=1;
                v=2;
                setMarker(7,cpX,cpY,mark);
            }
            if (mY>y2 && mY<y3)
            {
                cpY=(y3+y2)/2;
                h=2;
                v=2;
                setMarker(8,cpX,cpY,mark);

            }
        }

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        v1X=getWidth()/3;
        v2X=getWidth()*2/3;
        h1Y=getHeight()/3;
        h2Y=getHeight()*2/3;
        x1=getWidth()/3;
        x2=getWidth()*2/3;
        x3=getWidth();
        y1=getHeight()/3;
        y2=getHeight()*2/3;
        y3=getHeight();




    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(h1Y,0,h1Y,getWidth(),linePaint);
        canvas.drawLine(h2Y,0,h2Y,getWidth(),linePaint);
        canvas.drawLine(0,v1X,getHeight(),v1X,linePaint);
        canvas.drawLine(0,v2X,getHeight(),v2X,linePaint);
        int x=0;
        for(int i=0;i<cX.length;i++) {
            if(!markerP.get(i).toString().equalsIgnoreCase("0")) {
            canvas.drawText(markerP.get(i).toString(), 0, 1, cX[i], cY[i], tPaint);
        }
        }


    }
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlService=AIDLService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            aidlService=null;
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            aidlService.setList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
