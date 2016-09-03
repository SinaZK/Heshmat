package Cars.Tank;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by sinazk on 8/31/16.
 * 00:38
 */
public class TextureMath
{

	public static ArrayList<Vector2> getNormalizedPoints(ArrayList<Vector2> v, int k, double offset) { // v[0] should be a pinned-on-tank point
		int n = v.size();
		v.add(v.get(0));

		double totalLength = 0;

		for(int i = 0; i < n; i++)
			totalLength += Math.hypot(v.get(i).x - v.get(i + 1).x, v.get(i).y - v.get(i + 1).y); // in java: Math.hypot

		double step = totalLength / k;

//		offset = fmod(fmod(offset, step) + step, step); // in java: (offset % step + step) % step

		offset = (offset % step + step) % step;

		double prevLength = 0;
		int pos = 0;

//		vector<point> p(k);
		ArrayList<Vector2> p = new ArrayList<Vector2>();
		for(int i = 0; i < k; i++) {
			double targetLength = offset + i * step;

			double currentLength = Math.hypot(v.get(pos).x - v.get(pos + 1).x, v.get(pos).y - v.get(pos + 1).y);
			while(prevLength + currentLength < targetLength) {
				prevLength += currentLength;
				pos++;
				currentLength = Math.hypot(v.get(pos).x - v.get(pos + 1).x, v.get(pos).y - v.get(pos + 1).y);
			}

			double mul = (targetLength - prevLength) / currentLength;

			p.add(new Vector2((float)(v.get(pos).x + mul * (v.get(pos + 1).x - v.get(pos).x)),
					(float) (v.get(pos).y + mul * (v.get(pos + 1).y - v.get(pos).y))));
		}

		return p;
	}

	public static ArrayList<Vector2> getNormalizedPointsBySize(ArrayList<Vector2> v, float width, double offset) { // v[0] should be a pinned-on-tank point
		int n = v.size();
		v.add(v.get(0));

		double totalLength = 0;

		for(int i = 0; i < n; i++)
			totalLength += Math.hypot(v.get(i).x - v.get(i + 1).x, v.get(i).y - v.get(i + 1).y); // in java: Math.hypot

		int k = (int)(totalLength / width);
		double step = totalLength / k;

//		offset = fmod(fmod(offset, step) + step, step); // in java: (offset % step + step) % step

		offset = (offset % step + step) % step;

		double prevLength = 0;
		int pos = 0;

//		vector<point> p(k);
		ArrayList<Vector2> p = new ArrayList<Vector2>();
		for(int i = 0; i < k; i++) {
			double targetLength = offset + i * step;

			double currentLength = Math.hypot(v.get(pos).x - v.get(pos + 1).x, v.get(pos).y - v.get(pos + 1).y);
			while(prevLength + currentLength < targetLength) {
				prevLength += currentLength;
				pos++;
				currentLength = Math.hypot(v.get(pos).x - v.get(pos + 1).x, v.get(pos).y - v.get(pos + 1).y);
			}

			double mul = (targetLength - prevLength) / currentLength;

			p.add(new Vector2((float)(v.get(pos).x + mul * (v.get(pos + 1).x - v.get(pos).x)),
					(float) (v.get(pos).y + mul * (v.get(pos + 1).y - v.get(pos).y))));
		}

		return p;
	}

}
