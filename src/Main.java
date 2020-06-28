import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        ZipUtil.pack(new File("/Users/asaprykin/Documents/java/AssignmentTcpIpProject"), new File("/Users/asaprykin/Documents/java/AssignmentTcpIpProject" + "/directory.zip"));
    }
}
