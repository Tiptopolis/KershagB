package prime.TEST.zTest1.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

		float aX = Maths.roundToNearest(pos.x - mod.x, Unit.x);
		float aY = Maths.roundToNearest(pos.y - mod.y, Unit.y);
		float aZ = Maths.roundToNearest(pos.z - mod.z, Unit.z);
		if (Math.signum(pos.x) < 0)
			aX -= Unit.x;
		if (Math.signum(pos.y) < 0)
			aY -= Unit.y;
		if (Math.signum(pos.z) < 0)
			aZ -= Unit.z;
		Vector3 AtInd = new Vector3(aX / Unit.x, aY / Unit.y, aZ / Unit.z);
		Font.setColor(Color.WHITE);
		for (int x = (int) (Org.x); x < (Org.x + R.width) - 1; x += Unit.x) {
			for (int y = (int) (Org.y); y < (Org.y + R.height) - 1; y += Unit.y) {
				if (!antiColor)
					Sketcher.setColor(0, 0, 1, 0.5f);
				else
					Sketcher.setColor(1, 1, 0, 0.5f);
				Sketcher.Drawer.rectangle(x, y, Unit.x, Unit.y);
				if (!antiColor)
					Sketcher.setColor(1, 0, 0, 0.5f);
				else
					Sketcher.setColor(0, 1, 1, 0.5f);
				Sketcher.Drawer.rectangle(x - mod.x - 1, y - mod.y - 1, Unit.x, Unit.y);

				// Vector2 txtAt = new Vector2(x - mod.x - 1, y - mod.y - 1);
				float tX = Maths.roundToNearest(x - mod.x - 1, Unit.x);
				float tY = Maths.roundToNearest(y - mod.y - 1, Unit.y);
				Vector2 txtAt = new Vector2(tX, tY);
				Vector2 indAt = new Vector2(tX / Unit.x, tY / Unit.y);
				Font.draw(Sketcher.getBatch(), " " + ((int) (indAt.x) + ",\n, " + (int) (indAt.y)), txtAt.x,
						txtAt.y + (Unit.y) - 2);
			}
		}
		Font.setColor(Color.BLACK);
		Font.draw(Sketcher.getBatch(), ("[(" + (int) AtInd.x + "," + (int) AtInd.y + "," + (int) AtInd.z + ")]"), pos.x,
				pos.y);
	}

}
