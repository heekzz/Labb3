package freha_tddd13.labb3;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class List extends View {
    Context context;

    public List(Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect();
        rect.set(0,0,getLeft(), getRight() );



        Paint green = new Paint();
        green.setColor(Color.GREEN);
        green.setStyle(Paint.Style.FILL);

        canvas.drawRect(rect, green);
    }


}
