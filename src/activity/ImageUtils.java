package activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;

public class ImageUtils {
	private static int width;
	private static int height;
	private static Bitmap bitmap2;
	private static int before;
	private static int after;

	public static Bitmap getJava(Bitmap bitmap) {
		width = bitmap.getWidth();
		height = bitmap.getHeight();

		bitmap2 = Bitmap.createBitmap(width, height, Config.RGB_565);
		before = bitmap.getPixel(0, 0);
		after = bitmap.getPixel(0, 0);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int current = bitmap.getPixel(i, j);
				//0--255 127 中间灰
				int r= Color.red(current)-Color.red(after)+127;
				int g= Color.green(current)-Color.green(after)+127;
				int b= Color.blue(current)-Color.blue(after)+127;
				int a= Color.alpha(current);
				//合成了调颜色
				int d_color = Color.argb(a, r, g, b);
				
			}
		}
		return bitmap;
	}
}
