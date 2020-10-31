package core;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class GisUtil {

    private String FilePath;

    public void SetSXFFileDirectory(String DirectoryName){
        this.FilePath = DirectoryName;
    }
    public String GetSXFFileDirectory(){ return this.FilePath;}

    GisUtil(String FilePath) throws FileNotFoundException{
        this.FilePath = FilePath;
        File GISFile = new File(FilePath);
        if(! GISFile.exists()) throw new FileNotFoundException();
    }

    public ArrayList<GisObject> FetchObjects() throws IOException {

        ArrayList<String> SXFFile = new ArrayList<>();
        Scanner SXFFileScanner = new Scanner(Paths.get(FilePath)); //"MapIndexTable\\MapTable.sxf"
        while (SXFFileScanner.hasNextLine()){
            SXFFile.add(SXFFileScanner.nextLine());
        }

        return ParseObjects(SXFFile);
    }

    private ArrayList<GisObject> ParseObjects(ArrayList<String> SXFFile){
        ArrayList<String> StringGISObject = new ArrayList<>();
        ArrayList<GisObject> GISObjectsArray = new ArrayList<>();

        int Flag = 0;
        for(String Line: SXFFile){

            StringGISObject.add(Line);

            if((Line.contains(".OBJ") && Flag > 0) || Line.contains(".END")){
                GISObjectsArray.add(new GisObject(StringGISObject));
                StringGISObject.clear();
                StringGISObject.add(Line);
                continue;
            }
            else if(Line.contains(".OBJ") && Flag == 0){
                Flag += 1;
                StringGISObject.clear();
                StringGISObject.add(Line);
            }
        }
        return GISObjectsArray;
    }

    public void WriteObjectsToFile(ArrayList<GisObject> ObjectArray) throws IOException{
        ArrayList<String> WritableObject = new ArrayList<>();
        for(GisObject GISObject : ObjectArray){
            WritableObject.addAll(GISObject.CreateWritable());
        }
        PrintWriter Out = new PrintWriter(new FileWriter(FilePath), true);
        for(String Line: WritableObject){
            Out.println(Line);
        }
    }
}
