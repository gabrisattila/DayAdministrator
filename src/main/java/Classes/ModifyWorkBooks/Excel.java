package Classes.ModifyWorkBooks;

import lombok.Getter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

@Getter
public class Excel extends XSSFWorkbook {

	private final String path;

	public Excel(String path) throws IOException {
		super(path);
		this.path = path;
	}

}
