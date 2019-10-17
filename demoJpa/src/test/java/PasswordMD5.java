import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordMD5 {


    @Test
    public void testEncryptPassword(){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jasypt");
        String username = encryptor.encrypt("trilm");
        String password = encryptor.encrypt("123456a@");
        System.out.println("----------------------------------- Username: " + username );
        System.out.println("----------------------------------- pass: " + password );
    }

    @Test
    public void testPassword(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("---------------" + passwordEncoder.encode("123456a@"));
    }
}
