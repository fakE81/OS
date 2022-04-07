package Memory;

public class Word {
    private String data;


    Word(){
        data = "    ";
    }
    public Word(String s){
        data = s;
    }

    public String getValue(){
        return data;
    }
    public char[] getCharValue(){
        return data.toCharArray();
    }
    public void setValue(String s){
        data = s;
    }
}
