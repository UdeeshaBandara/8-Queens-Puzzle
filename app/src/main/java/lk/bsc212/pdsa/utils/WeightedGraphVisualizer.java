package lk.bsc212.pdsa.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import lk.bsc212.pdsa.R;
import lk.bsc212.pdsa.model.WeightedGraph;

public class WeightedGraphVisualizer extends View {
    private Paint circlePaint;
    private Paint textPaint;
    private Paint weightPaint;
    private Paint linePaint;
    private Paint circleHighlightPaint;
    private Paint lineHighlightPaint;
    private Rect bounds;

    private Map<Integer, Point> pointMap = new HashMap<>();

    private WeightedGraph graph;
    int systemSelectedCity;

    public WeightedGraphVisualizer(Context context) {
        super(context);
        initialise();
    }

    public WeightedGraphVisualizer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    private void initialise() {
        circlePaint = new Paint();
        textPaint = new Paint();

        //numbers-nodes
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(getDimensionInPixelFromSP(15));
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        bounds = new Rect();
        textPaint.getTextBounds("0", 0, 1, bounds);

        //lines/edges
        weightPaint = new Paint(textPaint);
        weightPaint.setColor(Color.BLACK);
        weightPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        weightPaint.setTextSize(getDimensionInPixelFromSP(18));

        //circle around nodes
        circlePaint.setColor(getResources().getColor(R.color.primary));
        circlePaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setStrokeWidth(7);
        linePaint.setColor(Color.BLACK);

        circleHighlightPaint = new Paint(circlePaint);
        circleHighlightPaint.setColor(Color.BLUE);


        lineHighlightPaint = new Paint(linePaint);
        lineHighlightPaint.setColor(Color.BLUE);
        lineHighlightPaint.setStrokeWidth(10);
    }

    private @NonNull //bkground around edge weight values to view clearly*
    Rect getTextBackgroundSize(float x, float y, @NonNull String text, @NonNull Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float halfTextLength = paint.measureText(text) / 2 + 5;
        return new Rect((int) (x - halfTextLength), (int) (y + fontMetrics.top), (int) (x + halfTextLength), (int) (y + fontMetrics.bottom));
    }

    @Override//graph rectangle size
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getDimensionInPixel(400));
    }

    //graph data to be drawn
    public void setData(WeightedGraph graph, int systemSelectedCity) {
        this.graph = graph;
        this.systemSelectedCity = systemSelectedCity;
        invalidate();
    }

    @Override//drawing graph
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (graph != null)
            drawGraph(canvas);
    }

    //locations to draw nodes
    private void drawGraph(Canvas canvas) {

        int[][] vertices = {
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},

                //x-axis location
                {getWidth() - getDimensionInPixel(50),
                        getWidth() - getDimensionInPixel(50),
                        getWidth() / 2 + getDimensionInPixel(80),
                        getWidth() / 2 - getDimensionInPixel(80),
                        getDimensionInPixel(40),
                        getDimensionInPixel(40),
                        getWidth() / 2 + getDimensionInPixel(80),
                        getWidth() / 2 - getDimensionInPixel(80),
                        getWidth() / 2 + getDimensionInPixel(10),
                        getWidth() / 2 - getDimensionInPixel(40)


                },

                //y-axis location
                {getHeight() / 2 + getDimensionInPixel(80),
                        getHeight() / 2 - getDimensionInPixel(45),
                        getDimensionInPixel(40),
                        getDimensionInPixel(40),
                        getHeight() / 2 + getDimensionInPixel(60),
                        getHeight() / 2 - getDimensionInPixel(90),
                        getHeight() - getDimensionInPixel(30),
                        getHeight() - getDimensionInPixel(30),
                        getHeight() / 2 - getDimensionInPixel(55),
                        getHeight() / 2 + getDimensionInPixel(80)

                }
        };

        //mode locations added to 2d array(x and y) and passed to addNode()
        for (int i = 0; i < graph.size(); i++) {
            int node = vertices[0][i];
            Point p = new Point(vertices[1][i], vertices[2][i]);
            addNode(p, node);
        }

        drawNodes(canvas);
    }

    //nodes added to a map
    private void addNode(Point point, int i) {
        pointMap.put(i, point);
    }

    //drawing nodes in correct position
    private void drawNodes(Canvas canvas) {
        for (Map.Entry<Integer, Point> entry : pointMap.entrySet()) {
            Integer key = entry.getKey();

            for (int i : graph.neighbors(key)) {
                drawNodeLine(canvas, key, i, (int) graph.getWeight(key, i));
            }

        }
        for (Map.Entry<Integer, Point> entry : pointMap.entrySet()) {
            Integer key = entry.getKey();
            Point value = entry.getValue();
            drawCircleTextNode(canvas, value, key);
        }
    }

    //drawing circle around nodes
    private void drawCircleTextNode(Canvas canvas, Point p, int number) {
        String text = String.valueOf(number);

        //if circle is system selected city, circle will be red
        if (systemSelectedCity == number)
            circlePaint.setColor(Color.RED);

        //if not, default node color
        else
            circlePaint.setColor(getResources().getColor(R.color.primary));

        //draw library called and used-drawing the circle in colors selected previously
        canvas.drawCircle(p.x, p.y, getDimensionInPixel(15), circlePaint);


        int yOffset = bounds.height() / 2;

        canvas.drawText(text, p.x, p.y + yOffset, textPaint);

    }

    //draw edge/node line
    private void drawNodeLine(Canvas canvas, int s, int e, int weight) {

        //take start and end nodes to draw edge
        Point start = pointMap.get(s);
        Point end = pointMap.get(e);

        // by taking mid position of two nodes and drawn edge
        int midx = (start.x + end.x) / 2;
        int midy = (start.y + end.y) / 2;

        //draw edge
        canvas.drawLine(start.x, start.y, end.x, end.y, linePaint);

        //add number/edge weight by adding background*
        Rect background = getTextBackgroundSize(midx, midy, String.valueOf(weight), weightPaint);
        canvas.drawRect(background, textPaint);
        canvas.drawText(String.valueOf(weight), midx, midy, weightPaint);

    }

    //to get same dp even if different devices are used
    public int getDimensionInPixel(int dp) {
        int density = getResources().getDisplayMetrics().densityDpi;

        int modifieddp = dp;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                modifieddp = dp - dp / 2;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                modifieddp = dp - dp / 3;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                modifieddp = dp - dp / 4;
                break;
            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_XXXHIGH:
                modifieddp = dp;
                break;
            default:
                break;
        }

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, modifieddp, getResources().getDisplayMetrics());
    }

    public int getDimensionInPixelFromSP(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

}
