package com.bailv.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

public class CircleProgressBar extends View {

	private int maxProgress = 100;
	private int progress = 15;
	private int progressStrokeWidth = 16;
	private int marxArcStorkeWidth = 24;
	
	private TextView target;
	
	private RotateAnimation animation;
	
	// 画圆所在的距形区域
	RectF oval;
	Paint paint;

	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		oval = new RectF();
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		super.onDraw(canvas);
		int width = this.getWidth();
		int height = this.getHeight();

		width = (width > height) ? height : width;
		height = (width > height) ? height : width;

		paint.setAntiAlias(true); // 设置画笔为抗锯齿   
		paint.setColor(Color.rgb(220, 220, 220)); // 设置画笔颜色
		canvas.drawColor(Color.TRANSPARENT); // 白色背景
		paint.setStrokeWidth(progressStrokeWidth); // 线宽
		paint.setStyle(Style.STROKE);

		oval.left = marxArcStorkeWidth / 2; // 左上角x
		oval.top = marxArcStorkeWidth / 2; // 左上角y
		oval.right = width - marxArcStorkeWidth / 2; // 左下角x
		oval.bottom = height - marxArcStorkeWidth / 2; // 右下角y

		canvas.drawArc(oval, -90, 360, false, paint); // 绘制白色圆圈，即进度条背景
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(marxArcStorkeWidth);
		canvas.drawArc(oval, -90, ((float) progress / maxProgress) * 360,
				false, paint); // 绘制进度圆弧，这里是蓝色

		//画出数据
		paint.setStrokeWidth(1);
		String value = progress + "";
		String fuhao = "%";
		int textHeight = height / 4;
		paint.setFakeBoldText(true);
		paint.setTextSize(textHeight);
		int valueWidth = (int) paint.measureText(value, 0, value.length());
		paint.setStyle(Style.FILL);
		canvas.drawText(value, width / 2 - valueWidth / 2, height / 2, paint);
		
		//画出单位
		int fuhaoHeight = height/6;
		int fuhaoWidth = (int) paint.measureText(fuhao, 0, fuhao.length());
		paint.setFakeBoldText(false);
		paint.setTextSize(fuhaoHeight);
		canvas.drawText(fuhao, width / 2 - fuhaoWidth / 2, height / 2
				+ textHeight , paint);

	}

	public int getMaxProgress() {
		return maxProgress;
	}

	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}
	
	public void setTextView(TextView tempView){
		target = tempView;
	}

	/**
	 * 设置进度
	 * 
	 * @param progress
	 *            进度百分比
	 * @param view
	 *            标识进度的节点视图
	 */
//	public void setProgress(int progress, View view) {
//		this.progress = progress;
//		view.setAnimation(pointRotationAnima(0,
//				(int) (((float) 360 / maxProgress) * progress)));
//		this.invalidate();
//	}
	
	
	
	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
		target.setText(progress+"");
		this.invalidate();
	}

	/**
	 * 设置进度,不带标识
	 * 
	 * @param progress
	 *            进度百分比
	 */
	public void setProgressWithNoPoint(int progress){
		this.progress = progress;
		this.invalidate();
	}

	/**
	 * 非ＵＩ线程调用
	 */
	public void setProgressNotInUiThread(int progress, View view) {
		this.progress = progress;
		view.setAnimation(pointRotationAnima(0,
				(int) (((float) 360 / maxProgress) * progress)));
		this.postInvalidate();
	}

	/**
	 * 进度标注点的动画
	 * 
	 * @param fromDegrees
	 * @param toDegrees
	 * @return
	 */
	private Animation pointRotationAnima(float fromDegrees, float toDegrees) {
		int initDegress = 306;// 进度点起始位置(图片偏移约54度)
		animation = new RotateAnimation(fromDegrees,
				initDegress + toDegrees, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1);// 设置动画执行时间
		animation.setRepeatCount(1);// 设置重复执行次数
		animation.setFillAfter(true);// 设置动画结束后是否停留在结束位置
		return animation;
	}

}