package com.yet.spring.core.loggers;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by User on 20.07.2017.
 */
public class FileEventLogger implements EventLogger {
private String filename;
private File file;

//инициализировать файл в конструкторе не всегда хорошая идея (хотя в этом случае она работает) т.к. другие
// бины могут не успеть создаться. И вообщеконструктор нужен только для создания, а логика д.б. в др. методах.
// И еще наш бин может получать другие зависимости через проперти  - создадим метод init() для проверки можно ли писать в файл
public FileEventLogger(String filename) {
        this.filename = filename;
//        this.file = new File(filename);
    }

    //этот метод объявляется в spring.xml в теге init-method. Он должен быть без параметров, м.б. приватным,
    // может что-то возвращать. Когда поднимаеся контекст и видим, что ему надо создать бин, он создает все зависимые
    // бины чтобы проинжектить, вызывает конструктор бина, которому передает эти параметры, вызывает сеттеры,
    // а потом вызывает метод, который заиман в в теге init-method файла контекста (spring.xml)
    public void init() throws IOException{
    this.file = new File(filename);
    if (!file.canWrite()) throw new IOException("Can't write to file " + file.getAbsolutePath());// проверяем можно ли писать в файл
}

    public void logEvent(Event event)  {
//        System.out.println(event.toString());
//        System.out.println(file.getAbsolutePath().toString() );
        try {
            FileUtils.writeStringToFile(file,event.toString(),true);
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
