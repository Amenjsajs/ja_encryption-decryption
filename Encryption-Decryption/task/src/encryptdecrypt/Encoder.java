package encryptdecrypt;

import java.io.*;

public class Encoder {
    private int salt = 0;

    public Encoder() {
    }

    public Encoder(int salt) {
        this.salt = salt;
    }

    private String crypter(String data, Algo algo, String type) {
        if (data.isEmpty()) {
            return "";
        }

        StringBuilder cryto = new StringBuilder();
        char[] chars = data.toCharArray();
        int saltMod = salt % 26;
        int alphaLen = -26;
        if (type.equalsIgnoreCase("dec")) {
            saltMod = -saltMod;
            alphaLen = 26;
        }
        char l;
        for (char c : chars) {
            l = (char) (c + saltMod);
            if (algo.equals(Algo.SHIFT)) {
                if (Character.isLetter(c)) {
                    if ((c >= 'A' && c <= 'Z' && l >= 'A' && l <= 'Z') ||
                            (c >= 'a' && c <= 'z' && l >= 'a' && l <= 'z')) {
                        cryto.append(l);
                    } else {
                        cryto.append((char) (l + alphaLen));
                    }
                } else {
                    cryto.append(c);
                }
            } else {
                cryto.append(l);
            }
        }
        return cryto.toString();
    }

    private String encode(String data, Algo algo) {
        return crypter(data, algo, "enc");
    }

    private String decode(String data, Algo algo) {
        return crypter(data, algo, "dec");
    }

    private String readFile(String path) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(path));
        String re = file.readLine();
        file.close();
        return re;
    }

    private void writeFile(String path, String text) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(path));
        file.write(text);
        file.close();
    }

    private String coderEncode(String data, String outPath, Algo algo) throws IOException {
        if (outPath == null) {
            return encode(data, algo);
        }
        writeFile(outPath, encode(data, algo));
        return "";
    }

    private String coderDecode(String data, String outPath, Algo algo) throws IOException {
        if (outPath == null) {
            return decode(data, algo);
        }
        writeFile(outPath, decode(data, algo));
        return "";
    }

    public String encode(String data, String inPath, String outPath, Algo algo) throws IOException {
        if (!data.isEmpty()) {
            return coderEncode(data, outPath, algo);
        } else if (inPath != null) {
            return coderEncode(readFile(inPath), outPath, algo);
        }
        return "";
    }

    public String decode(String data, String inPath, String outPath, Algo algo) throws IOException {
        if (!data.isEmpty()) {
            return coderDecode(data, outPath, algo);
        } else if (inPath != null) {
            return coderDecode(readFile(inPath), outPath, algo);
        }

        return "";
    }
}

enum Algo {
    SHIFT, UNICODE;
}