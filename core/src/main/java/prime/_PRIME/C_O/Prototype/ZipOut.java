package prime._PRIME.C_O.Prototype;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipOut {

	public void main()
	{
		try {
		StringBuilder sb = new StringBuilder();
		sb.append("Test String");

		File f = new File("d:\\test.zip");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
		ZipEntry e = new ZipEntry("mytext.txt");
		ZipEntry d = new ZipEntry("folderName/mytext.txt");
		out.putNextEntry(e);

		byte[] data = sb.toString().getBytes();
		out.write(data, 0, data.length);
		out.closeEntry();

		out.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
}
