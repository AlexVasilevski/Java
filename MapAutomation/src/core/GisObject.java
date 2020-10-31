package core;

import java.util.ArrayList;

public class GisObject {

    private ArrayList<String> SemanticsStrings;
    private ArrayList<String> StringObjectDump;
    private int ObjectID;
    private ArrayList<Semantics> SemanticsArray;
    private int SemanticsStartPosition = -1;
    private boolean ContainsSemantic = false;
    private int SemanticsAmount = 0;

    public void ChangeObjectType(int NewObjectsID){
        this.ObjectID = NewObjectsID;
    }
    public int GetObjectType(){
        return this.ObjectID;
    }
    public ArrayList<String> GetObjectDump(){
        return this.StringObjectDump;
    }
    public boolean ContainsSemantic(){return ContainsSemantic;}

    GisObject(ArrayList<String> StringGISObject){
        SemanticsStrings = new ArrayList<>();
        SemanticsArray = new ArrayList<>();
        this.StringObjectDump.addAll(StringGISObject);

        boolean StartSemantics = false;

        for(String Line: StringGISObject){

            if(Line.contains(".OBJ")){
                String[] ParsedObjectID = new String[3];
                ParsedObjectID = Line.split("   ");
                this.ObjectID = Integer.parseInt(ParsedObjectID[1]);
                StartSemantics = false;
            }
            else if(Line.contains(".SEM")){
                StartSemantics = true;
                ContainsSemantic = true;
                SemanticsStartPosition = StringGISObject.indexOf(Line);
                String[] ParsedSemantics = new String[2];
                ParsedSemantics = Line.split("   ");
                SemanticsAmount = Integer.parseInt(ParsedSemantics[1]);

                continue;
            }
            else if(StartSemantics){
                SemanticsStrings.add(Line);
                String[] ParsedSemantic = new String[2];
                ParsedSemantic = Line.split("   ");
                SemanticsArray.add(new Semantics(ParsedSemantic));
            }
        }
    }

    // перевести объект в тип String
    public ArrayList<String> EncodeObject(){
        return new ArrayList<String>();
    }

    //изменить семантику объекта
    public void SetSemantics(int SemanticsCode, String NewContents){

    }

    public Semantics GetSemantics(int SemanticsCode){
        if(ContainsSemantic)
        for(Semantics Semantic: SemanticsArray){
            if(Semantic.getSemanticsCode() == SemanticsCode){
                return Semantic;
            }
        }
        return null;
    }

    public ArrayList<String> CreateWritable(){
        for(String L : StringObjectDump)
            System.out.println(L);

        return StringObjectDump;
    }

    // добавить семантику
    public void AddSemantics(Semantics NewSemantics){

    }

    public void AddSemantics(String NewSemantics){

    }

    // удалить семантику
    public void DeleteSemantics(int SemanticsCode){

    }
}
