package prime._PRIME.C_O.Resources;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import prime._PRIME.DefaultResources;
import prime._PRIME.GdxFileUtils;





public class FontAtlas {

	//public yRoot root;

	private FileHandle RootDirectory = Gdx.files.internal("./assets");
	private String defaultInternalDir = "./assets/data/fonts/";
	public FileHandle FontDirectory = Gdx.files.internal(defaultInternalDir);// location of .ttf files to use

	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;

	private ArrayList<FileHandle> fileList; // will need to be made some kind of static, modable
	private ArrayList<FontReference> refList; // will need to be made some kind of static, modable
	// //<FontRef, FontSize> //FontName@FontSize exists
	// public HashMap<FontReference, Integer> ReferenceCache; // fonts@various
	// sizes, ensures Fonts can be gotten or regenerated
	public static ArrayList<FontReference> ReferenceCache;
	public static HashMap<BitmapFont, Integer> FontCache;

	public static BitmapFont SystemDefault = new BitmapFont();
	public static FontReference DefaultReference;
	public static GlyphSheet DefaultGlyphs;
	
	public static FreeTypeFontGenerator fontGen;
	private BitmapFont genF;

	public FontAtlas() {
		

		this.fileList = new ArrayList<FileHandle>();
		this.refList = new ArrayList<FontReference>();
		ReferenceCache = new ArrayList<FontReference>();
		FontCache = new HashMap<BitmapFont, Integer>();

		Log(" <>FontAtlas");
		//this.genDefaultFont();

		this.fileList = GdxFileUtils.fillFilesFrom(fileList, FontDirectory.toString());
		for (FileHandle h : fileList) {
			// Log(" ->" + h.toString() + " _" + h.nameWithoutExtension());
			FontReference newRef = new FontReference(h);
			this.refList.add(newRef);
			ReferenceCache.add(newRef);
		}

		for (FontReference r : refList) {
			// Log("-" + r.toLog() + "-");

			genF = genFont(r, r.size);
			FontCache.put(genF, r.size);
		}

		this.genDefaultFont();
		
		Log(this.toLog());		
		DefaultGlyphs = new GlyphSheet(SystemDefault,DefaultResources.PERMISSIBLE_CHARS);
	}
	
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
	
	public static BitmapFont genFont(FontReference font, int size) {
		BitmapFont newFont;
		fontGen = new FreeTypeFontGenerator(Gdx.files.internal(font.pathExt));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;

		newFont = fontGen.generateFont(param);

		fontGen.dispose();

		return newFont;
	}

	public static BitmapFont getFont(String name, Integer size) {
		for (BitmapFont f : FontCache.keySet()) {
			if (FontCache.containsKey(size)) {
				return f;
			}
		}
		return SystemDefault;
	}

	public static BitmapFont getFont(FontReference r) {
		for (BitmapFont f : FontCache.keySet()) {
			if (r.name == f.getData().name && FontCache.get(f) == r.size) {
				return f;
			}
		}
		return SystemDefault;
	}

	public static FontReference getReference(String name) {
		for (FontReference r : ReferenceCache) {
			if (r.name.equals(name)) {
				return r;
			}
		}
		return DefaultReference;
	}

	public static FontReference getReference(String name, int size) {
		for (FontReference r : ReferenceCache) {
			if (r.name.equals(name) && r.size == size) {
				return r;
			}
		}
		return DefaultReference;
	}

	private void genDefaultFont() {
		Log("...Generating default fonts...");
		SystemDefault = new BitmapFont();
		FontReference defaultRef = new FontReference("Default");
		defaultRef.ext = "ttf";
		defaultRef.path = "./data/fonts/.internal";
		defaultRef.pathExt = "./data/fonts/.internal/"+SystemDefault.getData().name + ".ttf";
		defaultRef.nameExt = "Default.ttf";
		defaultRef.size = 15;
		DefaultReference = defaultRef;
		// this.ReferenceCache.put(defaultRef, defaultRef.size);
		this.ReferenceCache.add(defaultRef);
		this.FontCache.put(SystemDefault, defaultRef.size);
	}

	
	@Deprecated
	private void genDebugFonts() {

		FontReference baseR = this.getReference("arial", 8);
		ArrayList<FontReference> debugRefs = new ArrayList<FontReference>();
		ArrayList<BitmapFont> debugFonts = new ArrayList<BitmapFont>();

		FontReference tmpNewRef;
		BitmapFont tmpNewFont;

		int[] debugSizes = new int[] { 8, 16, 32, 64 };
		for (int i : debugSizes) {
			tmpNewRef = new FontReference(baseR, "DebugGlyph", i);
			tmpNewFont = genFont(tmpNewRef, i);
			this.ReferenceCache.add(tmpNewRef);
			this.FontCache.put(tmpNewFont, i);
		}
	}
	
	
	//
	public String toLog() {
		String log = "\n";
		log += "__FontAtlasLog: GdxInternal.";
		log += FontDirectory.toString();
		log += "\n";
		Log("UserHomeDirectory:" + System.getProperty("user.home") + "");

		log += GdxFileUtils.listDirectory(FontDirectory.toString());
		log += "\n";
		log += "FONT-&-SIZE";
		log += "\n";
		log += "________________________";
		log += "\n";
		log += "SystemFont: " + this.SystemDefault;
		log += "\n";
		log += "FontCache:        HashMap<(BitmapFont)Font, (Integer)Size>";
		log += "\n";
		for (BitmapFont f : FontCache.keySet()) {
			// Log("-" + r.toLog() + "-");
			log += " - " + f;
			log += "\n";
		}
		log += "\n";
		log += "ReferenceCache:        ArrayList<FontReference>";
		log += "\n";
		// for (FontReference f : ReferenceCache.keySet()) {
		for (FontReference f : ReferenceCache) {
			// Log("-" + r.toLog() + "-");
			log += f.toLog();
			log += "\n";
		}
		log += "\n";

		return log;
	}

}
