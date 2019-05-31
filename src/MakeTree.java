import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MakeTree {
    /*-------=   DATA MEMBERS   =-------*/
    /*-------=    CONSTANTS     =-------*/
    /*-------=   CONSTRUCTORS   =-------*/
    /*-------= INSTANCE METHODS =-------*/

    /**
     * getting arraylists for contents of the folder
     * @param SourceDir The source directory to get file from
     * @return arraylist of arraylists files and folders of the given folder
     * @throws Exception
     */
    public ArrayList<ArrayList<File>> getFilesAndFolders(String SourceDir) throws Exception {
        File dir = new File(SourceDir);
        // getting the folder's contents
        File[] filesInDir = dir.listFiles();
        ArrayList<File> onlyFiles = new ArrayList<>();
        ArrayList<File> onlyFolders = new ArrayList<>();
        if (filesInDir == null)
            throw new Exception();
        //dividing for the matching lists
        for (File file : filesInDir) {
            if (file.isFile())
                onlyFiles.add(file);
            else if (file.isDirectory())
                onlyFolders.add(file);
        }
        return new ArrayList<ArrayList<File>>() {{
            add(onlyFiles);
            add(onlyFolders);
        }};
    }

    /**
     * making the tree
     * @param sourceDir
     * @param depth
     * @param writer
     * @throws Exception
     */
    public void makeTree(File sourceDir, int depth, BufferedWriter writer) throws Exception {
        String starter = getStarer(depth, false);
        System.out.println(starter + sourceDir.getName());
        writer.write(starter + sourceDir.getName()+"\n");
        ArrayList<ArrayList<File>> dirContent = getFilesAndFolders(sourceDir.getAbsolutePath());
        ArrayList<File> files = dirContent.get(0);
        ArrayList<File> folders = dirContent.get(1);
        starter = getStarer(depth + 1, true);

        for (File file : files) {
            System.out.println(starter + file.getName());
            writer.write(starter+file.getName()+"\n");
        }

        for(File dir : folders)
            makeTree(dir,depth+1,writer);
    }

    /**
     * @param depth
     * @param File
     * @return starting string depending on depth
     */
    private String getStarer(int depth, boolean File) {
        if (depth == 0)
            return "";
        String starter = "";
        for (int i = 0; i < depth - 1; i++) {
            starter = starter + "|   ";
        }
        if (File)
            starter += "|---";
        else
            starter += "+---";
        return starter;
    }

    /**
     * main
     * @param args output file and source directory
     */
    public static void main(String[] args) {
        try {
            if (args.length!=2)
                throw new Exception();
            MakeTree treeMaker=new MakeTree();
            File source=new File(args[1]);
            BufferedWriter writer= new BufferedWriter(new FileWriter(args[0]));
            treeMaker.makeTree(source,0,writer);
            writer.close();

        }catch (Exception e){System.err.println("invalid arguments");}
    }
}
