package notifier;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Notifier {

    public static void main(String[] args) {

        WebsitePinger pinger = new WebsitePinger();

        String sender = "whitelawap@gmail.com";
        String recipient = "";

        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter the email where you would like to receive alerts: ");
        recipient = scan.nextLine();
        System.out.print("Please enter the website you would like to track: ");
        pinger.setWebsite(scan.nextLine());

        Emailer emailer = new Emailer(recipient, sender);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean success = pinger.ping();
                if(!success) {
                    System.out.printf("%s %d/5", success, pinger.getDownCount());
                } else {
                    System.out.println(success);
                }
                if(pinger.getDownCount() == 5) {
                    emailer.sendEmail(pinger.getWebsite());
                }
            }
        }, 0, 10000);

    }

}
