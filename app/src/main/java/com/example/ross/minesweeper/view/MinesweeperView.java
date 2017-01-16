package com.example.ross.minesweeper.view;

    import android.content.Context;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.Color;
    import android.graphics.Paint;
    import android.util.AttributeSet;
    import android.view.MotionEvent;
    import android.view.View;

    import com.example.ross.minesweeper.MainActivity;
    import com.example.ross.minesweeper.R;
    import com.example.ross.minesweeper.model.MinesweeperModel;

    import static android.graphics.Color.rgb;

public class MinesweeperView extends View {
    private Paint paintBg;
    private Paint paintLine;
    private Paint paintText;
    public static boolean trying = true;
    public static boolean playing = true;
    private Bitmap flag;
    private Bitmap bomb;
    private Bitmap tile;

    private int GAMESIZE;

    public MinesweeperView(Context context, AttributeSet attrs) {
            super(context, attrs);

            paintBg = new Paint();
            paintBg.setColor(Color.LTGRAY);
            paintBg.setStyle(Paint.Style.FILL);

            paintLine = new Paint();
            paintLine.setColor(Color.DKGRAY);
            paintLine.setStyle(Paint.Style.STROKE);
            paintLine.setStrokeWidth(3);

            paintText = new Paint();
            paintText.setColor(Color.BLACK);
            paintText.setTextSize(10);

            flag = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.flag);
            bomb = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bomb);
            tile = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.tile);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            int scaledBy = 10;
            if (GAMESIZE == 5) scaledBy = 10;
            if (GAMESIZE == 8) scaledBy = 13;
            if (GAMESIZE == 10) scaledBy = 15;

            flag = Bitmap.createScaledBitmap(flag, getWidth()/scaledBy, getHeight()/scaledBy, false);
            bomb = Bitmap.createScaledBitmap(bomb, getWidth()/scaledBy, getHeight()/scaledBy, false);
            tile = Bitmap.createScaledBitmap(tile, getWidth()/GAMESIZE, getHeight()/GAMESIZE, false);

            if (GAMESIZE == 5) paintText.setTextSize(getWidth()/9);
            if (GAMESIZE == 8) paintText.setTextSize(getWidth()/12);
            if (GAMESIZE == 10) paintText.setTextSize(getWidth()/20);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            paintBg.setColor(Color.LTGRAY);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

            drawCounts(canvas);
            drawField(canvas);
        }

        private void drawField(Canvas canvas) {
            int length = getWidth() ;

            for (int i=0; i<=GAMESIZE; i++) {
                canvas.drawLine(0, i*length/GAMESIZE, length, i*length/GAMESIZE, paintLine);
                canvas.drawLine(i*length/GAMESIZE, 0, i*length/GAMESIZE, length, paintLine);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = MeasureSpec.getSize(heightMeasureSpec);
            int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
            setMeasuredDimension(d, d);
        }


        public void gameSetUp(int size) {
            GAMESIZE = size;
            MinesweeperModel.getInstance().newGame();

            playing = true;
            // set mines
            for (int i=0; i<MinesweeperModel.getInstance().getNumMines(); i++) {
                int randomX = (int) (Math.random() * GAMESIZE);
                int randomY = (int) (Math.random() * GAMESIZE);

                if (MinesweeperModel.getInstance().getFieldContent(randomX, randomY) != MinesweeperModel.MINE)
                    MinesweeperModel.getInstance().setFieldContent(randomX, randomY, MinesweeperModel.MINE);
                else {
                    while (MinesweeperModel.getInstance().getFieldContent(randomX, randomY) == MinesweeperModel.MINE) {
                        randomX = (int) (Math.random() * GAMESIZE);
                        randomY = (int) (Math.random() * GAMESIZE);
                    }
                    MinesweeperModel.getInstance().setFieldContent(randomX, randomY, MinesweeperModel.MINE);
                }

                // update counts
                int current;
                if (randomX > 0 && randomY > 0) {
                    // top left
                    current = MinesweeperModel.getInstance().getFieldContent(randomX - 1, randomY - 1);
                    if (current != MinesweeperModel.MINE) MinesweeperModel.getInstance().setFieldContent(randomX - 1, randomY - 1, current+1);
                }
                if (randomX > 0 ) {
                    // left
                    current = MinesweeperModel.getInstance().getFieldContent(randomX - 1, randomY);
                    if (current != MinesweeperModel.MINE) MinesweeperModel.getInstance().setFieldContent(randomX - 1, randomY, current+1);
                }
                if (randomX > 0 && randomY < GAMESIZE-1) {
                    // bottom left
                    current = MinesweeperModel.getInstance().getFieldContent(randomX-1, randomY+1);
                    if (current != MinesweeperModel.MINE) MinesweeperModel.getInstance().setFieldContent(randomX-1, randomY+1, current+1);
                }
                if (randomX < GAMESIZE-1 && randomY > 0) {
                    // top right
                    current = MinesweeperModel.getInstance().getFieldContent(randomX + 1, randomY - 1);
                    if (current != MinesweeperModel.MINE) MinesweeperModel.getInstance().setFieldContent(randomX + 1, randomY - 1, current+1);
                }
                if (randomY > 0) {
                    // top
                    current = MinesweeperModel.getInstance().getFieldContent(randomX, randomY-1);
                    if (current != MinesweeperModel.MINE) MinesweeperModel.getInstance().setFieldContent(randomX, randomY-1, current+1);
                }
                if (randomY < GAMESIZE-1) {
                    // bottom
                    current = MinesweeperModel.getInstance().getFieldContent(randomX, randomY + 1);
                    if (current != MinesweeperModel.MINE) MinesweeperModel.getInstance().setFieldContent(randomX, randomY + 1, current+1);
                }
                if (randomY < GAMESIZE-1 && randomX < GAMESIZE-1) {
                    // bottom right
                    current = MinesweeperModel.getInstance().getFieldContent(randomX + 1, randomY + 1);
                    if (current != MinesweeperModel.MINE) MinesweeperModel.getInstance().setFieldContent(randomX + 1, randomY + 1, current+1);
                }
                if (randomX < GAMESIZE-1) {
                    // right
                    current = MinesweeperModel.getInstance().getFieldContent(randomX+1, randomY);
                    if (current != MinesweeperModel.MINE) MinesweeperModel.getInstance().setFieldContent(randomX+1, randomY, current+1);
                }
//                Log.d("MSG_TEST", "place a mine at x:" +randomX + "y:"+ randomY);
            }
        }

        private void drawCounts(Canvas canvas) {

            MinesweeperModel instance= MinesweeperModel.getInstance();

            for (int i = 0; i < GAMESIZE; i++) {
                for (int j = 0; j < GAMESIZE; j++) {

                    if (instance.isVisible(i,j)) {
                        float centerX = i * getWidth() / GAMESIZE + getWidth() / 15;
                        float centerY = j * getHeight() / GAMESIZE + getHeight() / 8;
                        if (GAMESIZE == 5) {
                            centerX = i * getWidth() / GAMESIZE + getWidth() / 15;
                            centerY = j * getHeight() / GAMESIZE + getHeight() / 8;
                        }
                        else if (GAMESIZE == 8) {
                            centerX = i * getWidth() / GAMESIZE + getWidth() / 20;
                            centerY = j * getHeight() / GAMESIZE + getHeight() / 12;
                        }
                        else if (GAMESIZE == 10) {
                            centerX = i * getWidth() / GAMESIZE + getWidth() / 30 ;
                            centerY = j * getHeight() / GAMESIZE + getHeight() / 16;
                        }

                        if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.ONE) {
                            paintText.setColor(Color.BLUE);
                            canvas.drawText("1", centerX, centerY, paintText);
                        } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.TWO) {
                            paintText.setColor(rgb(0, 153, 0));
                            canvas.drawText("2", centerX, centerY, paintText);
                        } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.THREE) {
                            paintText.setColor(Color.RED);
                            canvas.drawText("3", centerX, centerY, paintText);
                        } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.FOUR) {
                            paintText.setColor(Color.YELLOW);
                            canvas.drawText("4", centerX, centerY, paintText);
                        } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.MINE) {
                            paintText.setColor(Color.BLACK);

                            float leftX = i * getWidth() / GAMESIZE;
                            float leftY = j * getHeight() / GAMESIZE;

                            float adjustX = 0;
                            float adjustY = 0;

                            if (GAMESIZE == 5) {
                                adjustX = getWidth() / 50;
                                adjustY = getHeight() / 13;
                            }
                            if (GAMESIZE == 8) {
                                adjustX = getWidth() / 50;
                                adjustY = getHeight() / 15;
                            }
                            if (GAMESIZE == 10) {
                                adjustX = getWidth() / 60;
                                adjustY = getHeight() / 20;
                            }

                            if (playing || !trying) {
                                canvas.drawBitmap(tile, leftX, leftY, null);
                                canvas.drawBitmap(flag, centerX-adjustX, centerY-adjustY, null);
                            }
                            else {
                                canvas.drawBitmap(bomb, centerX-adjustX, centerY-adjustY, null);
                            }
                        }
                    }
                    else {
                        float leftX = i * getWidth() / GAMESIZE;
                        float leftY = j * getHeight() / GAMESIZE;

                        canvas.drawBitmap(tile, leftX, leftY, null);
                    }
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (playing) {
                    int tX = ((int) event.getX()) / (getWidth() / GAMESIZE);
                    int tY = ((int) event.getY()) / (getHeight() / GAMESIZE);

                    if (tX < GAMESIZE && tY < GAMESIZE) {
                        if (trying) {
                            MinesweeperModel.getInstance().markVisible(tX, tY);
                            if (MinesweeperModel.getInstance().isMine(tX, tY)) {
                                endGame();
                            }
                        } else {
                            if (MinesweeperModel.getInstance().isMine(tX, tY)) {
                                MinesweeperModel.getInstance().markVisible(tX, tY);
                                MinesweeperModel.getInstance().decrementMines();
                            } else endGame();
                        }

                        invalidate();
                    }
                }
            }

            checkWon();

            return true;
        }

        public void checkWon() {
            if (MinesweeperModel.getInstance().getMines() == 0) {
                playing = false;
                ((MainActivity) getContext()).showOverMessage(
                        getContext().getString(R.string.won_txt)
                );
            }
        }

        public void endGame() {
            playing = false;
            ((MainActivity)getContext()).showOverMessage(
                    getContext().getString(R.string.over_txt)
            );
        }

        public void changeMode() {
            trying = !trying;
            if (trying) {
                MainActivity.btnMode.setText(R.string.try_txt);
                ((MainActivity)getContext()).showModeMessage(
                        getContext().getString(R.string.mode_txt, getContext().getString(R.string.try_txt))
                );
            }
            else {
                MainActivity.btnMode.setText(R.string.flag_txt);
                ((MainActivity)getContext()).showModeMessage(
                        getContext().getString(R.string.mode_txt, getContext().getString(R.string.flag_txt))
                );
            }

        }

        public void restartGame(int size) {
            MinesweeperModel.getInstance().resetModel();
            trying = true;
            MainActivity.btnMode.setText(R.string.try_txt);
            ((MainActivity)getContext()).showModeMessage(
                    getContext().getString(R.string.mode_txt, getContext().getString(R.string.try_txt))
            );
            invalidate();
            gameSetUp(size);
        }


    }
