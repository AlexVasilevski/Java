package core;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.*;


public class LogicalCore {

    private boolean Running;
    private String WorkingDirectory = "";
    private ArrayList<String> ExistedFiles;
    private ArrayList<String> AddedFiles;
    private ArrayList<String> FoundedFiles;

    public boolean IsRunning(){ return Running; }
    public void SetRunning(boolean RunFlag){ Running = RunFlag; }

    public String GetWorkingDirectory(){
        return this.WorkingDirectory;
    }
    public void SetWorkingDirectory(String NewDirectory){
        this.WorkingDirectory = NewDirectory;
    }

    public ArrayList<String> GetExistedFiles(){
        return this.ExistedFiles;
    }
    public void SetExistedFiles(ArrayList<String> NewFileArray){
        this.ExistedFiles = NewFileArray;
    }

    //возвращает список добавленных в директорию файлов
    public ArrayList<String> CheckDirectory(String DirectoryName) throws IOException, NullPointerException{

        File FileDirectory = new File(DirectoryName);
        Scanner FileScanner = new Scanner(Paths.get("FileBase\\FileBase.txt"));
        WorkingDirectory = DirectoryName;

        String[] FoundedFiles = FileDirectory.list();
        this.FoundedFiles = new ArrayList<String>(Arrays.asList(FoundedFiles));
        System.out.println("Обнаружено " + FoundedFiles.length + " файлов в рабочей директории.");

        if(FoundedFiles.length == 0){
            throw new NullPointerException();
        }

        ArrayList<String> ExistedFiles = new ArrayList<>();
        ArrayList<String> AddedFiles = new ArrayList<>();

        while(FileScanner.hasNextLine())
            ExistedFiles.add(FileScanner.nextLine());

        this.ExistedFiles = ExistedFiles;

        for(String FoundedFile : FoundedFiles){
            boolean InFileBase = false;
            for(String ExistedFile : ExistedFiles){
                if (FoundedFile.equals(ExistedFile)) {
                    InFileBase = true;
                }
                continue;
            }

            if(InFileBase == false){
                AddedFiles.add(FoundedFile);
            }
        }
        this.AddedFiles = AddedFiles;

        return AddedFiles;
    }

    //обновляет файл базы сборной таблицы
	//дописывая в него свежедобавленные файлы
    public void UpdateFileBase() throws IOException, FileNotFoundException {

        PrintWriter Out = new PrintWriter(new FileWriter("FileBase\\FileBase.txt"), true);
        for(String FoundedFile: FoundedFiles){
            Out.println(FoundedFile);
        }
    }

	// извлечение номенклатурных записей из файла
    public ArrayList<Byte> FetchMapIndex(String FileName) throws IOException {
        ArrayList<Byte> ByteArray = new ArrayList<>();

        FileChannel ReadChannel = new FileInputStream((WorkingDirectory+"\\"+FileName)).getChannel();
        ByteBuffer SXFBuffer = ByteBuffer.allocate(2048);
        ReadChannel.read(SXFBuffer);
        SXFBuffer.flip();

        while(SXFBuffer.hasRemaining()){
            ByteArray.add(SXFBuffer.get());
        }

        ArrayList<Byte> CodedMapIndex = new ArrayList<>();
        for(int Counter = 26; Counter < 34; ++Counter){
            CodedMapIndex.add(ByteArray.get(Counter));
        }

        return CodedMapIndex;
    }

    //Decodes binary
    public String DecodeMapIndex(ArrayList<Byte> MapIndex){
        String DecodedIndex = "";

        for(int Index = 0; Index < MapIndex.size(); ++Index){
            int Character = MapIndex.get(Index);
            DecodedIndex += (char)Character;
        }

        return DecodedIndex;
    }


}
