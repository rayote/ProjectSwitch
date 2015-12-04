package kkook.team.projectswitch.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by Askai on 2015-12-04.
 */
public class Utils {
	public static Bitmap blur(Context context, Bitmap sentBitmap, int radius) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
			Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

			final RenderScript rs = RenderScript.create(context);
			final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
					Allocation.USAGE_SCRIPT);
			final Allocation output = Allocation.createTyped(rs, input.getType());
			final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
			script.setRadius(radius); //0.0f ~ 25.0f
			script.setInput(input);
			script.forEach(output);
			output.copyTo(bitmap);

			return bitmap;
		}

		return null;
	}
}
