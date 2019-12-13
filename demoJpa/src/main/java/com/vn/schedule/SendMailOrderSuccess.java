package com.vn.schedule;

import com.vn.common.ThymeleafUtil;
import com.vn.config.GoogleMailSender;
import com.vn.jpa.Bill;
import com.vn.service.BillService;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendMailOrderSuccess {

    @Resource
    private BillService billService;

    @Scheduled(cron = "0 1 * * * ?") // 10 phút chạy 1 lần gửi mail cho KH đã nhận được hàng do nhân viên cập nhật Trạng thái
    private void scheduleSendMailCusOrderSuccess(){
        try {
            List<Bill> lsbill = billService.findByTypeStatusAndMailStatus(Bill.STATUSPAYMENT.PAID.value(), Bill.MAILSTATUS.UNPAID.value());
            for(Bill each : lsbill){
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("name",each.getName());
                responseMap.put("id", each.getId());
                GoogleMailSender mailSender = new GoogleMailSender();
                final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath("html/MailThankiuCustomer.html", (HashMap<String, Object>) responseMap);
                mailSender.sendSimpleMailWarningTLS("ÔTôKê<tanbv.dev@gmail.com>", each.getEmail(), "[ÔTôKê] EMail cảm ơn Quý Khách", htmlContent);
                each.setMailStatus(Bill.MAILSTATUS.PAID.value());
                billService.update(each);
                Thread.sleep(500);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
