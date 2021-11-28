package prime.TEST.zTest1.z2;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Maths;
import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.C_O.Resources.FontAtlas;
import prime._PRIME.RAUM._Environment;

public class VisKernel {

	public Camera c;
	_Environment e;
	public rNode root;
	BitmapFont Font = FontAtlas.SystemDefault;

	public Array<Vector3> mapIndices;
	// offset matrix?

	public VisKernel(Transform t, _Environment e) {

		this.e = e;
		this.root = new rNode(e, t);
		this.root.transform = t;
		this.mapIndices = new Array<Vector3>(true, (int) (e.Dimension.len() + 0.5f), Vector3.class);
	}

	public void draw(boolean antiColor) {
		Vector3 Unit = e.getUnit();
		float unit = Unit.len() / 3;
		Vector3 pos = this.root.transform.GetLocalPosition();
		Vector3 scl = this.root.transform.GetLocalScale();

		this.root.body = new Rect(0, 0, Unit.x * 10, Unit.y * 10);

		this.root.update();

		Rect R = this.root.body.cpy().extendBy(true, new Vector2(Unit.x * 2, Unit.y * 2));
		R.centerAt(pos);
		Vector2 Org = R.getOrigin().cpy();
		Vector2 Cnt = R.getCenter();
		Vector2 org = this.root.body.getOrigin().cpy();
		Vector2 cnt = this.root.body.getCenter();

		this.root.render();

		// Log("[" + this.root.body.width/Unit.x + "," + this.root.body.height/Unit.y +
		// "]");

		Sketcher.setColor(Color.ORANGE);
		Sketcher.Drawer.rectangle(R.getRectangle());
		Vector3 mod = new Vector3((int) (pos.x % Unit.x), (int) (pos.y % Unit.y), (int) (pos.z % Unit.z));
		// Log(">>>" + mod);
		Vector3 fukUfactor = new Vector3(Math.signum(pos.x), Math.signum(pos.y), Math.signum(pos.z));

		float aX = Maths.roundToNearest(pos.x - mod.x, Unit.x);
		float aY = Maths.roundToNearest(pos.y - mod.y, Unit.y);
		float aZ = Maths.roundToNearest(pos.z - mod.z, Unit.z);
		// if (Math.signum(pos.x) < 0|| pos.x>MathUtils.PI2) {
		if (Math.signum(pos.x) < 0) {
			aX -= Unit.x;
			fukUfactor.x = -1;
		}
		// if (Math.signum(pos.y) < 0 || pos.y>MathUtils.PI2) {
		if (Math.signum(pos.y) < 0) {
			aY -= Unit.y;
			fukUfactor.y = -1;
		}
		// if (Math.signum(pos.z) < 0|| pos.z>MathUtils.PI2) {
		if (Math.signum(pos.z) < 0) {
			aZ -= Unit.z;
			fukUfactor.z = -1;
		}
		Vector3 AtInd = new Vector3((int) (aX / Unit.x), (int) ((aY) / Unit.y), (int) (aZ / Unit.z));
		Font.setColor(Color.WHITE);
		for (int x = (int) (Org.x); x < (int) (Org.x + R.width) - 1; x += (int) Unit.x) {
			for (int y = (int) (Org.y); y < (int) (Org.y + R.height) - 1; y += (int) Unit.y) {
				if (!antiColor)
					Sketcher.setColor(0, 0, 1, 0.5f);
				else
					Sketcher.setColor(1, 1, 0, 0.5f);
				Sketcher.Drawer.rectangle(x, y, Unit.x, Unit.y);
				if (!antiColor)
					Sketcher.setColor(1, 0, 0, 0.5f);
				else
					Sketcher.setColor(0, 1, 1, 0.5f);
				Sketcher.Drawer.rectangle((int) (x - mod.x), (int) (y - mod.y), Unit.x, Unit.y);

				// Vector2 txtAt = new Vector2(x - mod.x - 1, y - mod.y - 1);
				float tX = Maths.roundToNearest(x - mod.x, Unit.x);
				float tY = Maths.roundToNearest(y - mod.y, Unit.y);
				Vector2 txtAt = new Vector2((int) tX, tY);
				Vector2 indAt = new Vector2((int) tX / Unit.x, tY / Unit.y);
				Font.draw(Sketcher.getBatch(), " " + ((int) (indAt.x) + ",\n, " + (int) (indAt.y)), txtAt.x,
						txtAt.y + (Unit.y) - 2);
			}
		}
		Font.setColor(Color.BLACK);
		Font.draw(Sketcher.getBatch(), ("[(" + (int) AtInd.x + "," + (int) AtInd.y + "," + (int) AtInd.z + ")]"), pos.x,
				pos.y);
	}

}
