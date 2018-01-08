package com.wix.reactnativenotifications.core.notification;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import java.util.Locale;

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
        if (status == TextToSpeech.SUCCESS) {
            if (engine.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE) {
                engine.setLanguage(Locale.US);
            } else if (engine.isLanguageAvailable(Locale.ENGLISH) == TextToSpeech.LANG_AVAILABLE) {
                engine.setLanguage(Locale.ENGLISH);
            }
            engine.speak(text, TextToSpeech.QUEUE_ADD, null, "tts");
        } else if (status == TextToSpeech.ERROR) {
        }
    }
}
