package core;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] Arguments){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LogicalCore MainClass = new LogicalCore();
                MainClass.SetRunning(true);

                String Argument0 = "MapPool"; // TODO delete later
                String Argument1 = "MapIndexTable\\Table.txf";
                String Argument2 = "801";
                String Argument3 = "227";

                int MapIndexSemantics = Integer.parseInt(Argument2);
                int NewObjectType = Integer.parseInt(Argument3);
                String DirectoryName = Argument0;

                System.out.println("Рабочая директория: [" + DirectoryName + "]\nФайл базы: FileBase.txt");
                try{
                    ArrayList<String> NewFileArray = MainClass.CheckDirectory(DirectoryName);
                    System.out.println("Из них добавлено " + NewFileArray.size() + " новых файлов в директорию.");
                    GisUtil GIS = new GisUtil(Argument1);
                    ArrayList<GisObject> GisObjectArray;
                    GisObjectArray = GIS.FetchObjects();
                    System.out.println("Обнаружено " + GisObjectArray.size() + " объектов в файле");


                    for(String NewFile: NewFileArray){
                        ArrayList<Byte> CodedMapIndex;

                        CodedMapIndex = MainClass.FetchMapIndex(NewFile);
                        String DecodedMapIndex = MainClass.DecodeMapIndex(CodedMapIndex);



                        for(GisObject GISObject: GisObjectArray){
                            if(GISObject.ContainsSemantic()){
                                String Semantics = GISObject.GetSemantics(MapIndexSemantics).getSemanticContents();
                                if(Semantics.equals(DecodedMapIndex)){
                                    GISObject.ChangeObjectType(NewObjectType);
                                }
                            }
                        }
                        continue;
                    }
                    GIS.WriteObjectsToFile(GisObjectArray);
                    MainClass.UpdateFileBase();
                }
                catch (IOException UserException){
                    // TODO add allert message

                }
                catch (NullPointerException UserException){

                }
            }
        });
    }
}
