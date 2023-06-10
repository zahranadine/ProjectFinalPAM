package Model;

public class Note {

    private String judul, isi, key;
    public Note (String judul, String isi){
        this.judul = judul;
        this.isi = isi;
    }

    public Note (String judul, String isi, String key){
        this.judul = judul;
        this.isi = isi;
        this.key = key;
    }


}
