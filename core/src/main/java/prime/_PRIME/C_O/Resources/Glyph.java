package prime._PRIME.C_O.Resources;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

//implements iSymbol
public abstract class Glyph{

	public Character Char;
	public CharTexture Tex;
	public CharSprite Spr;

	public Quaternion size = new Quaternion(0, 0, 0, 0);

	public Glyph(GlyphSheet from, char using) {
		this.Char = using;
		this.Tex = new CharTexture(using, from.chars.get(using));
		this.Spr = new CharSprite(using, from.getGlyph(using));
		this.size = new Quaternion(Tex.T.getRegionWidth(), Tex.T.getRegionHeight(), 1, 1);
	}

	public Object getAsNew() {
		return this;
	}
	
	@Override
	public String toString()
	{
		return "["+this.Char+"]";
	}
	
	public String toLog()
	{
		String log = "";
		log +="["+this.Char+"]" +"\n";
		log += ""+this.size;
		return log;
	}

	public class CharTexture implements Map.Entry<Character, TextureRegion> // raw texture
	{
		Character C;
		TextureRegion T;
		String Char;

		public CharTexture(Character c, TextureRegion t) {
			this.C = c;
			this.Char = "" + c;
			this.T = t;
		}

		@Override
		public Character getKey() {
			return this.C;
		}

		@Override
		public TextureRegion getValue() {
			return this.T;
		}

		@Override
		public TextureRegion setValue(TextureRegion value) {
			this.T = value;
			return this.T;
		}

	}

	public class CharSprite implements Map.Entry<Character, Sprite> // rotated,scaled,positioned
	{
		Character C;
		Sprite S;
		String Char;

		public CharSprite(Character c, Sprite s) {
			this.C = c;
			this.S = s;
			this.Char = "" + c;
		}

		@Override
		public Character getKey() {
			return this.C;
		}

		@Override
		public Sprite getValue() {
			return this.S;
		}

		@Override
		public Sprite setValue(Sprite value) {
			this.S = value;
			return this.S;
		}

	}

}
