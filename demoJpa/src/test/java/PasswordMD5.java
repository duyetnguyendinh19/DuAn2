import com.vn.common.ThymeleafUtil;
import com.vn.config.GoogleMailSender;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


public class PasswordMD5 {


    @Test
    public void testEncryptPassword() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jasypt");
        String username = encryptor.encrypt("trilm");
        String password = encryptor.encrypt("123456a@");
        System.out.println("----------------------------------- Username: " + username);
        System.out.println("----------------------------------- pass: " + password);
    }

    @Test
    public void testPassword() throws Exception {
//       List<Long> ls = new ArrayList<>();
//       ls.add(1);
//       ls.add(2);
//       ls.add(3);
//       ls.add(4);
//       System.out.println(ls);
    }

    @Test
    public void getIp() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
