import com.fasterxml.jackson.databind.ObjectMapper;
import constants.AppConstant;
import models.Emotion;
import models.KomprehendDTO;
import okhttp3.*;

import java.io.IOException;

public class Synthesizer {

  String API_KEY = AppConstant.API_KEY;
  String LANG_CODE = "en";
  String URL = "https://apis.paralleldots.com/v5/emotion_batch";

  Synthesizer() {}

  public void run(String[] texts) {

    StringBuilder inputText = new StringBuilder("[ ");
    ObjectMapper objectMapper = new ObjectMapper();
    for (String text : texts) {
      if (!inputText.toString().equals("[ ")) {
        inputText.append(", ");
      }
      inputText.append("\"").append(text).append("\"");
    }
    inputText.append("]");

    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody =
        new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("api_key", API_KEY)
            .addFormDataPart("lang_code", LANG_CODE)
            .addFormDataPart("text", String.valueOf(inputText))
            .build();
    Request request =
        new Request.Builder()
            .url(URL)
            .post(requestBody)
            .addHeader("cache-control", "no-cache")
            .build();
    Response response = null;
    try {
      ResponseBody responseBody = client.newCall(request).execute().body();
      //      System.out.println(responseBody.string());
      KomprehendDTO komprehendDTO =
          objectMapper.readValue(responseBody.string(), KomprehendDTO.class);
      System.out.println(matchEmotion(komprehendDTO));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String  matchEmotion(KomprehendDTO komprehendDTO) {
    StringBuilder emotionOfUser = new StringBuilder("");
    Emotion threshold =
        Emotion.builder()
            .happy(0.1f)
            .angry(0.1f)
            .sad(0.1f)
            .bored(0.1f)
            .excited(0.1f)
            .fear(0.1f)
            .build();

    for(Emotion emotion: komprehendDTO.getEmotion()) {
      if(emotion.getAngry() > threshold.getAngry()) {
        emotionOfUser.append("Angry " + emotion.getAngry() * 100 + "%");
      }

      if(emotion.getHappy() > threshold.getHappy()) {
        emotionOfUser.append(" Happy " + emotion.getHappy() * 100 + "%");
      }

      if(emotion.getSad() > threshold.getSad()) {
        emotionOfUser.append(" Sad " + emotion.getSad() * 100 + "%");
      }

      if(emotion.getFear() > threshold.getFear()) {
        emotionOfUser.append(" Fear " + emotion.getFear() * 100 + "%");
      }

      if(emotion.getBored() > threshold.getBored()) {
        emotionOfUser.append(" Bored " + emotion.getBored() * 100 + "%");
      }

      if(emotion.getExcited() > threshold.getExcited()) {
        emotionOfUser.append(" Excited " + emotion.getExcited() * 100 + "%");
      }
      emotionOfUser.append("\n");
    }

    return emotionOfUser.toString();
  }
}
