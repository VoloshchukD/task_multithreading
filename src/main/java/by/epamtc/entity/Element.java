package by.epamtc.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.concurrent.locks.ReentrantLock;

@XmlRootElement(name = "element")
public class Element {

    @XmlElement(name = "value")
    private int value;

    private static ReentrantLock lock = new ReentrantLock();

    public int getValue() {
        return value;
    }

    public void changeValue(int value) {
        if (lock.tryLock()) {
            this.value = value;
        }
    }

    public void unlock() {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
