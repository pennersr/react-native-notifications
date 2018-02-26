package com.wix.reactnativenotifications.core.notification;

import android.annotation.TargetApi;
import android.os.Build;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import java.util.Locale;
import java.util.HashMap;

class TTS implements TextToSpeech.OnInitListener {

    Context context;
    String text;
    TextToSpeech engine;

    TTS(Context context, String text) {
        this.context = context;
        this.text = text;
        engine = new TextToSpeech(context, this);
        engine.setOnUtteranceProgressListener(new UtteranceProgressListener () {

            @Override
            public void onStart(String s) {

            }

            @Override
            public void onDone(String s) {
                engine.stop();
                engine.shutdown();
            }

            @Override
            public void onError(String s) {

            }
        });
    }


    @Override
    public void onInit(int status) {
        if (engine != null && status == TextToSpeech.SUCCESS) {
            if (engine.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE) {
                engine.setLanguage(Locale.US);
            } else if (engine.isLanguageAvailable(Locale.ENGLISH) == TextToSpeech.LANG_AVAILABLE) {
                engine.setLanguage(Locale.ENGLISH);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ttsGreater21(text);
            } else {
                ttsUnder20(text);
            }

        } else if (status == TextToSpeech.ERROR) {
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "tts");
        engine.speak(text, TextToSpeech.QUEUE_ADD, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        engine.speak(text, TextToSpeech.QUEUE_ADD, null, "tts");
    }
}
