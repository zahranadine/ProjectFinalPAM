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

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
