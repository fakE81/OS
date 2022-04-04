package Memory;

// Zodis, tik nezinau ar 4B geriau ar 8B
public class Word {
    private String data;


    Word(){
        data = "____";
    }
    public Word(String s){
        data = s;
    }

    public String getValue(){
        return data;
    }
    public void setValue(String s){
        data = s;
    }
    // Seteriai geteriai kai paduodamas int.
}
