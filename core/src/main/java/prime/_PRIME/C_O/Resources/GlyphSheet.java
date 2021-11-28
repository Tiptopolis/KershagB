package prime._PRIME.C_O.Resources;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._PRIME.DefaultResources.*;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class GlyphSheet {

	// an inside-out font lol
	

	protected BitmapFont font;
	protected BitmapFontData data;
	public HashMap<Character, TextureRegion> chars = new HashMap<Character, TextureRegion>();
	public HashMap<Character, Sprite> glyphs = new HashMap<Character, Sprite>();

	public GlyphSheet(BitmapFont fromFont) {
		this.font = fromFont;
		this.data = this.font.getData();

		for (char c : DEFAULT_FITTING.toCharArray()) {
			BitmapFont.Glyph glyph = data.getGlyph(c);
			TextureRegion page = font.getRegion(glyph.page);
			TextureRegion newGlyph = new TextureRegion(page.getTexture(), glyph.u, glyph.v, glyph.u2, glyph.v2);
			newGlyph.flip(false, true);
			this.chars.put(c, newGlyph);
			Sprite newSprite = new Sprite(newGlyph);
			this.glyphs.put(c, newSprite);
		}
	}

	public GlyphSheet(BitmapFont fromFont, String characterList) {
		this.font = fromFont;
		this.data = this.font.getData();

		for (char c : characterList.toCharArray()) {
			if (data.getGlyph(c) != null) {
				BitmapFont.Glyph glyph = data.getGlyph(c);
				TextureRegion page = font.getRegion(glyph.page);
				TextureRegion newGlyph = new TextureRegion(page.getTexture(), glyph.u, glyph.v, glyph.u2, glyph.v2);
				newGlyph.flip(false, true);
				this.chars.put(c, newGlyph);
				Sprite newSprite = new Sprite(newGlyph);
				this.glyphs.put(c, newSprite);
			}
		}
	}

	public GlyphSheet(String characterList) {
		this.font = FontAtlas.SystemDefault;
		this.data = this.font.getData();

		for (char c : characterList.toCharArray()) {
			if (data.getGlyph(c) != null) {
				BitmapFont.Glyph glyph = data.getGlyph(c);
				TextureRegion page = font.getRegion(glyph.page);
				TextureRegion newGlyph = new TextureRegion(page.getTexture(), glyph.u, glyph.v, glyph.u2, glyph.v2);
				newGlyph.flip(false, true);
				this.chars.put(c, newGlyph);
				Sprite newSprite = new Sprite(newGlyph);
				this.glyphs.put(c, newSprite);
			}
		}
	}
	
	public BitmapFont getUsedFont()
	{
		return this.font;
	}

	public Sprite getGlyph(char c) {

		if (this.glyphs.get(c) != null)
			return this.glyphs.get(c);
		else
			return this.glyphs.get(' ');
	}

	public Glyph newGlyph(Character c) {
		return new Glyph(this, c) {
			@Override
			public Object getAsNew() {
				return this;
			}
		};
	}

	public Sprite copyGlyph(char c) {
		return new Sprite(this.getGlyph(c));
	}

	public Sprite copyGlyph(Sprite s) {
		if (this.glyphs.containsValue(s))
			return new Sprite(s);

		else
			return this.copyGlyph(' ');
	}

	public void dispose() {
		this.font.dispose();
	}

}
