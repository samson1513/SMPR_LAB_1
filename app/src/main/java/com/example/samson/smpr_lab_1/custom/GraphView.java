package com.example.samson.smpr_lab_1.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.samson.smpr_lab_1.R;

import java.util.ArrayList;

/**
 * Created by samson on 10.09.15.
 */
public class GraphView extends ImageView {

    private static final int RADIUS_NODE = 35;

    private float radius;
    private int padding;
    private int paddingText;

    private boolean graphEnable = false;
    private int[][] matrix;
    private ArrayList<Coord> coordNodes;
    private int numNode;

    private Paint paintNode, paintLapse, paintArc, paintEdge, paintText;

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

        Resources resources = getResources();

        radius = resources.getDimension(R.dimen.node_radius);
        padding = (int) resources.getDimension(R.dimen.graph_padding);
        paddingText = (int) resources.getDimension(R.dimen.node_text_padding);

        paintNode = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintNode.setColor(Color.BLUE);
        paintNode.setStyle(Paint.Style.STROKE);
        paintNode.setStrokeWidth(5f);

        paintLapse = new Paint(paintNode);
        paintLapse.setStyle(Paint.Style.FILL);

        paintEdge = new Paint(paintNode);
        paintEdge.setColor(Color.CYAN);

        paintArc = new Paint(paintEdge);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setStrokeWidth(2f);
        paintText.setTextSize(resources.getDimension(R.dimen.node_text_size));
        paintText.setColor(Color.RED);
    }

    public void setGraph(int[][] matrix, int n){
        this.matrix = matrix;
        numNode = n;
        coordNodes = new ArrayList<>();
        calcCoordApex();
        graphEnable = true;
        invalidate();
    }

    private void calcCoordApex(){
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int bigRadius = Math.min(centerX, centerY) - padding;
        double angle = 2 * Math.PI / numNode;
        int x, y;
        for(int i = 0; i < numNode; ++i){
            x = centerX + (int) (bigRadius * Math.cos(i * angle));
            y = centerY + (int) (bigRadius * Math.sin(i * angle));
            coordNodes.add(new Coord(x, y));
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        canvas.drawColor(Color.WHITE);

        if(graphEnable){
            drawApexes(canvas);
            drawArcsEdges(canvas);
            drawText(canvas);
        }
    }

    private void drawApexes(final Canvas canvas){
        for(int i = 0; i < numNode; ++i){
            if(matrix[i][i] == 1)
                canvas.drawCircle(coordNodes.get(i).x, coordNodes.get(i).y, radius, paintLapse);
            else
                canvas.drawCircle(coordNodes.get(i).x, coordNodes.get(i).y, radius, paintNode);
        }
    }

    private Coord start = new Coord();
    private Coord end = new Coord();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private void drawArcsEdges(final Canvas canvas){
        for(int row = 0; row < numNode - 1; ++row){
            for(int col = row + 1; col < numNode; ++col){
                int typeLine = matrix[row][col] - matrix[col][row];
                if(matrix[row][col] + matrix[col][row] != 0) {
                    switch (typeLine) {
                        case -1:
                            start = coordNodes.get(col);
                            end = coordNodes.get(row);
                            paintArc.setShader(new LinearGradient(start.x, start.y, end.x, end.y, Color.BLUE, Color.CYAN, Shader.TileMode.MIRROR));
                            paint.set(paintArc);
                            break;
                        case 0:
                            start = coordNodes.get(row);
                            end = coordNodes.get(col);
                            paint.set(paintEdge);
                            break;
                        case 1:
                            start = coordNodes.get(row);
                            end = coordNodes.get(col);
                            paintArc.setShader(new LinearGradient(start.x, start.y, end.x, end.y, Color.BLUE, Color.CYAN, Shader.TileMode.REPEAT));
                            paint.set(paintArc);
                            break;
                    }
                    drawLine(canvas, paint);
                }
            }
        }
    }

    private void drawLine(final Canvas canvas, Paint paint){
        canvas.drawLine(start.x, start.y, end.x, end.y, paint);
    }

    private void drawText(final Canvas canvas){
        for(int i = 0; i < numNode; ++i){
            canvas.drawText(String.valueOf(i + 1),
                    coordNodes.get(i).x - paddingText,
                    coordNodes.get(i).y + paddingText,
                    paintText
            );
        }
    }

    class Coord{
        int x;
        int y;

        public Coord() {
            this.x = 0;
            this.y = 0;
        }

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
