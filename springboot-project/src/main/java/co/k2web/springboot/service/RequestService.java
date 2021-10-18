package co.k2web.springboot.service;

import co.k2web.springboot.constants.Constants;
import co.k2web.springboot.constants.MessageConstants;
import co.k2web.springboot.dto.User;
import co.k2web.springboot.exception.ValidationException;
import co.k2web.springboot.scheduler.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class RequestService {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd-HH-mm-ss";
    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
    private static final String CONTENT_VALUE_REQUEST = "REQUEST";

    public void handleRequest(User user) {
        String valueContent = user.getContent();

        // Check value of content
        checkValueContent(valueContent);

        // Create file log request
        File requestDataFile = createFile(Constants.USER_REQUEST_LOG_FILE_PATH);

        // Store request into a hashMap
        Map<String, String> requestMap = storeRequest(valueContent);

        // Write to file
        writeFile(requestDataFile, requestMap);
    }

    private void checkValueContent(String valueContent) {
        if (!CONTENT_VALUE_REQUEST.equals(valueContent)) {
            throw new ValidationException(MessageConstants.INVALID_VALUE_FIELD_CONTENT);
        }
    }

    public File createFile(String path){
        File file = new File(path);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    logger.info("File is created successfully");
                }
            } catch (IOException e) {
                logger.info("Can not create file txt");
            }
        }
        return file;
    }

    public Map<String, String> storeRequest(String requestContent) {
        Map<String, String> requestMap = new HashMap<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        String requestDateTime = dateTimeFormatter.format(LocalDateTime.now());

        requestMap.put(requestDateTime, requestContent);
        return requestMap;
    }

    public void writeFile(File requestDataFile, Map<String, String> requestMap) {
        try {
            FileWriter fileWriter = new FileWriter(requestDataFile.getAbsoluteFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Map.Entry<String, String> entryMap : requestMap.entrySet()) {
                bufferedWriter.write(entryMap.getKey() + ":" + entryMap.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            logger.info("Problem in writing to file");
        }
    }

}
