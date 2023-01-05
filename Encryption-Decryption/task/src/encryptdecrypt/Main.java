package encryptdecrypt;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String param;
        String value;

        String errorMsg = "";
        String mode = "enc";
        String data = "";
        String inPath = null;
        String outPath = null;
        Algo algo = Algo.SHIFT;
        int key = 0;
        for (int i = 0, len = args.length; i < len; i++) {
            param = args[i];
            if (!param.startsWith("-")) {
                errorMsg = "Error: parameter must start with -";
                break;
            }

            value = args[++i];
            if (value.startsWith("-")) {
                errorMsg = String.format("Error: value of param %s is required", param);
                break;
            }

            switch (param) {
                case "-mode":
                    mode = value;
                    break;
                case "-in":
                    inPath = value;
                    break;
                case "-out":
                    outPath = value;
                    break;
                case "-key":
                    key = Integer.parseInt(value);
                    break;
                case "-data":
                    data = value;
                    break;
                case "-alg":
                    try {
                        algo = Algo.valueOf(value.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        errorMsg = "Error: Invalid algo";
                    }
                    break;
            }
        }

        if (!errorMsg.isEmpty()) {
            System.out.println(errorMsg);
        } else {
            Encoder encoder = new Encoder(key);
            if (mode.equals("enc")) {
                try {
                    System.out.println(encoder.encode(data, inPath, outPath, algo));
                } catch (FileNotFoundException e){
                    System.out.println("Error: "+e.getMessage());
                }
            }

            if (mode.equals("dec")) {
                try {
                    System.out.println(encoder.decode(data, inPath, outPath, algo));
                } catch (FileNotFoundException e){
                    System.out.println("Error: "+e.getMessage());
                }
            }
        }
    }
}
