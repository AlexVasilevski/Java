package core;

public class Semantics {


    private int SemanticsCode;
    private String SemanticContents;

    Semantics(String[] ParsedSemantic){
        this.SemanticsCode = Integer.parseInt(ParsedSemantic[0]);
        this.SemanticContents = ParsedSemantic[1];
    }

    public int getSemanticsCode() {
        return SemanticsCode;
    }
    public void setSemanticsCode(int semanticsCode) {
        SemanticsCode = semanticsCode;
    }
    public String getSemanticContents() {
        return SemanticContents;
    }
    public void setSemanticContents(String semanticContents) {
        SemanticContents = semanticContents;
    }
    public String GetStringSemantics(){
        return SemanticsCode + "\t" + SemanticContents;
    }
}
