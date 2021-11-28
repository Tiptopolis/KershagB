package prime._PRIME.C_O.Resources;

import com.badlogic.gdx.files.FileHandle;

public class FontReference {

	// FontPath, Size, AspectRatio , Options

	// refreshFont()

	// output short?

	// Parameters
	public String name;
	public String nameExt; // name + ext
	public String path; // source .ttf file
	public String pathExt;
	public String ext;

	public float aspectRatio = 1f;
	public int size = 8;

	public FontReference(FileHandle file) {
		if (file.extension().equals("ttf")) {
			this.name = file.nameWithoutExtension();
			this.nameExt = file.name();
			this.path = file.pathWithoutExtension();
			this.pathExt = file.path();
			this.ext = file.extension();
		}
	}

	public FontReference(String name) {
		this.name = name;
		/*String temp = name;
		if (name.contains(".")) {
			temp = name.substring(0, name.lastIndexOf('.'));
		}*/

	}
	
	public FontReference(FontReference from)
	{
		this.name = from.name;
		this.nameExt = from.nameExt;
		this.path = from.path;
		this.pathExt = from.pathExt;
		this.ext = from.ext;
		this.size = from.size;
		
	}
	
	public FontReference(FontReference from, String newName, int newSize)
	{
		this.name = newName;
		this.nameExt = from.nameExt;
		this.path = from.path;
		this.pathExt = from.pathExt;
		this.ext = from.ext;
		this.size = newSize;
		
	}
	
	//splice from BitmapFont.toString();

	public String toLog() {
		String log = "";
		log += "\n";
		log += "Name: " + name;
		log += "\n";
		log += "NameExt: " + nameExt;
		log += "\n";
		log += "Path: " + path;
		log += "\n";
		log += "PathExt: " + pathExt;
		log += "\n";
		log += "Ext: " + ext;
		log += "\n";
		log += "Size: " + size + " _ " + aspectRatio;
		log += "\n";
		return log;
	}
}
