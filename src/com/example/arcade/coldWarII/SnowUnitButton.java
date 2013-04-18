package com.example.arcade.coldWarII;


import android.content.res.Resources;
import android.graphics.*;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import com.example.arcade.R;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class SnowUnitButton extends Sprite {
    private static Canvas canvas;
    private String label;
    private static int imageHeight, imageWidth;
    private Paint paint;
    private static Resources res = Game.getInstance().getResources();
    private boolean active;
    private SnowUnitType type;

    public SnowUnitButton(float x, float y, String text, Paint p, boolean flag) {
        super(new Image(initDrawable(R.drawable.snowbutton, text, p)));
        label = text;
        paint = p;
        setPosition(x, y);
        active = flag;
    }

    public boolean isActive() {
        return active;
    }

    protected void setActive(boolean flag) {
        active = flag;
    }

    public String getLabel() {
        return label;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public Paint getPaint() {
        return paint;
    }

    public SnowUnitType getType() {
        return type;
    }

    private static BitmapDrawable initDrawable(int drawableId, String text, Paint p) {
        Bitmap bm = BitmapFactory.decodeResource(res, drawableId)
                .copy(Bitmap.Config.ARGB_8888, true);

        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        p.setTypeface(tf);
        p.setStyle(Style.FILL);
        p.setTextAlign(Align.CENTER);
        p.setTextSize(convertToPixels(11));

        canvas = new Canvas(bm);

        Rect textRect = new Rect();
        p.getTextBounds(text, 0, text.length(), textRect);

        //If the text is bigger than the canvas , reduce the font size
        if (textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            p.setTextSize(convertToPixels(7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((p.descent() + p.ascent()) / 2));

        imageHeight = bm.getHeight();
        imageWidth = bm.getWidth();

        canvas.drawText(text, xPos, yPos, p);
        return new BitmapDrawable(res, bm);
    }

    private static int convertToPixels(int nDP) {
        final float conversionScale = res.getDisplayMetrics().density;
        return (int) ((nDP * conversionScale) + 0.5f);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this == o)
            return false;
        if (o instanceof SnowUnitButton) {
            if (this.type == ((SnowUnitButton) o).getType())
                return true;

        }

        return false;
    }

}
