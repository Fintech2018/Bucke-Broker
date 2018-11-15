package com.bucketbroker.service.intf;

import java.io.File;
import java.io.IOException;

public interface BrokerService {

	String loadSystemGeneratedFeedbackToS3(String feedbackType);

	String loadFeedbackToS3(File fileToLoad);

	String loadFeedbackToS3(String feedbackToLoad) throws IOException;

}
