package com.yet.spring.core.annotations.loggers;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Created by User on 20.07.2017.
 */
@Component
public class FileEventLogger implements EventLogger {
    private File file;

    @Value("${events.file:target/events_log.txt}")
    private String filename;

    public FileEventLogger() {
    }

    //инициализировать файл в конструкторе не всегда хорошая идея (хотя в этом случае она работает) т.к. другие
// бины могут не успеть создаться. И вообще конструктор нужен только для создания, а логика д.б. в др. методах.
// И еще наш бин может получать другие зависимости через проперти  - создадим метод init() для проверки можно ли писать в файл
    public FileEventLogger(String filename) {
        this.filename = filename;
//        this.file = new File(filename);
    }

    //этот метод объявляется в spring.xml в теге init-method или можно поставить аннотацию @PostConstruct.
    // Он должен быть без параметров, м.б. приватным, может что-то возвращать. Когда поднимаеся контекст и видим,
    // что ему надо создать бин, он создает все зависимые бины чтобы проинжектить, вызывает конструктор бина,
    // которому передает эти параметры, вызывает сеттеры, а потом вызывает метод, который записан в в теге
    // init-method файла контекста (spring.xml) или над которім стоит аннотация @PostConstruct
    @PostConstruct
    public void init() throws IOException {
        this.file = new File(filename);
        if (file.exists() && !file.canWrite())
            throw new IOException("Can't write to file " + file.getAbsolutePath());// проверяем можно ли писать в файл
        else if (!file.exists()) file.createNewFile();
    }

    public void logEvent(Event event) {
//        System.out.println(event.toString());
//        System.out.println(file.getAbsolutePath().toString() );
        try {
            FileUtils.writeStringToFile(file, event.toString() + "\n", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
