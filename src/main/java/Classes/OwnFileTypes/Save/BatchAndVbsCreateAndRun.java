package Classes.OwnFileTypes.Save;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class BatchAndVbsCreateAndRun {

    private File batchFile;

    private List<File> vbsFiles;



    private static final String batchFileSkeleton = "start csscript ExcelMacroRunner.vbs";

}
