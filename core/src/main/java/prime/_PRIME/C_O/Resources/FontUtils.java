package prime._PRIME.C_O.Resources;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

public class FontUtils {

	public static FreeTypeFontGenerator fontGen;

	public static BitmapFont genFont(String filename, int size) {
		BitmapFont newFont;

		fontGen = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/" + filename));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;
		param.borderWidth = 0;

		newFont = fontGen.generateFont(param);
		fontGen.dispose();

		return newFont;
	}

	public static BitmapFont genFont(String filename, int size, int border) {
		BitmapFont newFont;

		fontGen = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/" + filename));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;
		param.borderWidth = border;

		newFont = fontGen.generateFont(param);

		fontGen.dispose();
		return newFont;
	}

	public static BitmapFont genFont(FontReference font, int size) {
		BitmapFont newFont;
		fontGen = new FreeTypeFontGenerator(Gdx.files.internal(font.pathExt));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;

		newFont = fontGen.generateFont(param);

		fontGen.dispose();

		return newFont;
	}

	public static BitmapFont regenFont(BitmapFont font, int size) {

		BitmapFont newFont;

		fontGen = new FreeTypeFontGenerator(font.getData().getFontFile());
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;

		newFont = fontGen.generateFont(param);
		
		fontGen.dispose();
		font.dispose();
		//font = newFont;

		return newFont;
	}

	public static BitmapFontCache cacheFont(BitmapFont font) {
		return new BitmapFontCache(font);
	}

	public static Vector2 getGlyphSize(BitmapFont bitmapFont, String value) {
		Vector2 temp = new Vector2(0, 0);
		GlyphLayout glyphLayout = new GlyphLayout();
		glyphLayout.setText(bitmapFont, value);

		temp.set(glyphLayout.width, glyphLayout.height);
		return temp;
	}

	public ArrayList<Glyph> getGlyphsFrom(BitmapFont bitmapFont) {
		ArrayList<Glyph> tmp = new ArrayList<Glyph>();
		// bitmapFont.getData().glyphs.
		for (Glyph[] G : bitmapFont.getData().glyphs) {
			for (Glyph g : G) {
				tmp.add(g);
			}
		}
		return tmp;
	}

}
