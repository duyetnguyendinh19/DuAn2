import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;


public class PasswordMD5 {

    @Test
    public void testEncryptPassword(){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jasypt");
        String username = encryptor.encrypt("root");
        String password = encryptor.encrypt("123456");
        System.out.println("----------------------------------- Username: " + username );
        System.out.println("----------------------------------- pass: " + password );
    }
}
