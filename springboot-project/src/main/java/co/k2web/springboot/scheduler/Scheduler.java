package co.k2web.springboot.scheduler;

import co.k2web.springboot.constants.Constants;
import co.k2web.springboot.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Scheduler {

    @Autowired
    private RequestService requestService;

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    @Scheduled(fixedRate = 5000)
    public void scheduler() {
        LinkedHashMap<String, String> listUser = getListUserRequestFromTextFile();

        // create file if it not exist
        String userRequestDataFilePath = "C://TestFile//saving_time.txt";
        File userRequestFile = requestService.createFile(userRequestDataFilePath);

        // Save data to file
        saveDataToFile(listUser, userRequestFile);
    }

    private void saveDataToFile(LinkedHashMap<String, String> listUser, File userRequestFile) {
        try {
            FileWriter userRequestWriter;
            userRequestWriter = new FileWriter(userRequestFile.getAbsoluteFile(), true);
            BufferedWriter bufferWriter = new BufferedWriter(userRequestWriter);
            int totalRequests = 0;

            for (Map.Entry<String, String> entry : listUser.entrySet()) {
                String dateTimeUserRequest = entry.getKey();

                String strDateUserRequest = dateTimeUserRequest.substring(0,10);
                String strTimeUserRequest = dateTimeUserRequest.substring(11);

                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormatter = new SimpleDateFormat("HH-mm-ss");
                Date dateTime = new Date();
                String strNowDate = dateFormatter.format(dateTime);
                String strNowTime = timeFormatter.format(dateTime);

                Date timeUserRequest = timeFormatter.parse(strTimeUserRequest);
                Date nowTime = timeFormatter.parse(strNowTime);

                if(strDateUserRequest.equals(strNowDate)){
                    long differenceTime = nowTime.getTime() - timeUserRequest.getTime();
                    if(differenceTime <=3600000){
                        bufferWriter.write("Time: " + entry.getKey() + ", content: " + entry.getValue() + "\n");
                        totalRequests += 1;
                    }
                }
            }
            bufferWriter.write("Total requests: " + totalRequests + " -- Time check: " + dateTimeFormatter.format(LocalDateTime.now()));
            bufferWriter.newLine();
            bufferWriter.close();

        }
        catch (IOException | ParseException | java.text.ParseException e) {
            logger.info("Fail to save data to txt file");
        }
    }

    // get list user data from text file
    public static LinkedHashMap<String, String> getListUserRequestFromTextFile() {
        LinkedHashMap<String, String> listUser = new LinkedHashMap<String, String>();
        BufferedReader br = null;

        try {
            String filePath = Constants.USER_REQUEST_FILE_PATH;
            File file = new File(filePath);
            br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                String timeRequest = parts[0].trim();
                String requestContent = parts[1].trim();

                if (!timeRequest.equals(""))
                    listUser.put(timeRequest, requestContent);
            }
        } catch (Exception e) {
            logger.info("Fail to get data from txt file");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    logger.info("Fail to close BufferedReader");
                }
            }
        }
        return listUser;
    }

}
