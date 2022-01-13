package com.oligas.services;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
public class EncodeDecode {
    private static Object Random;
    /**
     * Encode valor
     * @param num
     * @return
     */
    public  String getEnconde(Integer num){
        try {
            String numRecebe=String.format("%06d",num);// Integer.toString(num);
            Base64.Encoder encode = Base64.getEncoder();
            String converte = encode.encodeToString(numRecebe.getBytes());
            return converte;
        }catch (Exception e){
            return  "Não pode codificar numero além de 8 dígitos "+e;
        }

    }

    /**
     * Decode valor
     * @param valor
     * @return
     */

    public String getDecode(String valor){
        Base64.Decoder decode = Base64.getDecoder();
        byte[] valorRetorno= decode.decode(valor);
        return new String(valorRetorno);
    }
}
